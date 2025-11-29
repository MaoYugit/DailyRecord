<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useArticleStore } from '@/stores/article';
import { createArticle, updateArticle, getArticleById } from '@/api/article';
import { uploadFile } from '@/api/attachment';
import { getCategories } from '@/api/category';
import { getTags } from '@/api/tag';
import { useUserStore } from '@/stores/user';
import { renderMarkdown } from '@/utils/markdown';

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const articleStore = useArticleStore();

const isEditMode = computed(() => !!route.params.id);
const loading = ref(false);
const saving = ref(false);

const form = ref({
    title: '',
    slug: '',
    summary: '',
    content: '',
    cover_image: '',
    category_id: '',
    status: 1, // 1: Published, 0: Draft
    tags: [] // Array of tag IDs
});

const categories = ref([]);
const availableTags = ref([]);
const previewContent = computed(() => renderMarkdown(form.value.content));
const showPreview = ref(false);

const fetchMetadata = async () => {
    try {
        const [catRes, tagRes] = await Promise.all([getCategories(), getTags()]);
        categories.value = catRes || [];
        availableTags.value = tagRes || [];
    } catch (error) {
        console.error('Failed to load metadata:', error);
    }
};

const initForm = async () => {
    if (!userStore.isLoggedIn) {
        router.push('/login');
        return;
    }

    await fetchMetadata();

    if (isEditMode.value) {
        loading.value = true;
        try {
            const article = await getArticleById(route.params.id);
            form.value = {
                ...article,
                tags: article.tags ? article.tags.map(t => t.id) : [] // Assuming backend returns tags array
            };
        } catch (error) {
            console.error('Failed to load article:', error);
        } finally {
            loading.value = false;
        }
    }
};

const handleImageUpload = async (event) => {
    const file = event.target.files[0];
    if (!file) return;

    try {
        const res = await uploadFile(file, userStore.user.id);
        // Assuming res.file_url is the URL
        form.value.cover_image = res.file_url;
    } catch (error) {
        console.error('Upload failed:', error);
        alert('Image upload failed');
    }
};

const saveArticle = async () => {
    if (!form.value.title || !form.value.content) {
        alert('Title and Content are required');
        return;
    }

    saving.value = true;
    try {
        const data = { ...form.value, user_id: userStore.user.id };
        if (isEditMode.value) {
            await updateArticle(data);
        } else {
            await createArticle(data);
        }
        router.push('/');
    } catch (error) {
        console.error('Save failed:', error);
        alert('Failed to save article');
    } finally {
        saving.value = false;
    }
};

onMounted(() => {
    initForm();
});
</script>

<template>
    <div class="editor-container">
        <div class="editor-header">
            <h2>{{ isEditMode ? 'Edit Shard' : 'New Shard' }}</h2>
            <div class="actions">
                <button @click="showPreview = !showPreview" class="secondary-btn">
                    {{ showPreview ? 'Edit' : 'Preview' }}
                </button>
                <button @click="saveArticle" class="primary-btn" :disabled="saving">
                    {{ saving ? 'Saving...' : 'Publish' }}
                </button>
            </div>
        </div>

        <div class="editor-layout">
            <div class="main-editor" v-show="!showPreview">
                <div class="form-group">
                    <input v-model="form.title" placeholder="Title" class="title-input" />
                </div>
                <div class="form-group">
                    <textarea v-model="form.content" placeholder="Write your thoughts in Markdown..." class="content-input"></textarea>
                </div>
            </div>

            <div class="preview-pane markdown-body" v-show="showPreview" v-html="previewContent"></div>

            <div class="sidebar">
                <div class="form-group">
                    <label>Slug (URL)</label>
                    <input v-model="form.slug" placeholder="custom-url-slug" />
                </div>
                <div class="form-group">
                    <label>Summary</label>
                    <textarea v-model="form.summary" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <label>Category</label>
                    <select v-model="form.category_id">
                        <option value="">Select Category</option>
                        <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
                    </select>
                </div>
                <!-- Tags could be a multi-select or tag input, keeping simple for now -->
                
                <div class="form-group">
                    <label>Cover Image</label>
                    <input type="file" @change="handleImageUpload" accept="image/*" />
                    <div v-if="form.cover_image" class="image-preview">
                        <img :src="form.cover_image" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.editor-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
    height: calc(100vh - 80px); /* Adjust based on navbar */
    display: flex;
    flex-direction: column;
}

.editor-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
}

.editor-layout {
    display: flex;
    gap: 2rem;
    flex: 1;
    overflow: hidden;
}

.main-editor, .preview-pane {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    background: rgba(255, 255, 255, 0.02);
    border-radius: 8px;
    padding: 1rem;
}

.sidebar {
    width: 300px;
    overflow-y: auto;
    background: rgba(255, 255, 255, 0.02);
    border-radius: 8px;
    padding: 1rem;
}

.title-input {
    font-size: 2rem;
    font-weight: bold;
    background: transparent;
    border: none;
    border-bottom: 1px solid var(--color-border);
    color: var(--color-text-main);
    width: 100%;
    padding: 0.5rem 0;
    margin-bottom: 1rem;
}

.content-input {
    flex: 1;
    background: transparent;
    border: none;
    color: var(--color-text-main);
    font-family: 'Fira Code', monospace;
    font-size: 1rem;
    resize: none;
    outline: none;
}

.form-group {
    margin-bottom: 1.5rem;
}

.form-group label {
    display: block;
    margin-bottom: 0.5rem;
    color: var(--color-text-sub);
    font-size: 0.9rem;
}

.form-group input, .form-group textarea, .form-group select {
    width: 100%;
    background: rgba(0, 0, 0, 0.2);
    border: 1px solid var(--color-border);
    color: var(--color-text-main);
    padding: 0.5rem;
    border-radius: 4px;
}

.image-preview img {
    width: 100%;
    margin-top: 0.5rem;
    border-radius: 4px;
}

.primary-btn {
    background: var(--color-accent-rational);
    color: #000;
    border: none;
    padding: 0.5rem 1.5rem;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
}

.secondary-btn {
    background: transparent;
    border: 1px solid var(--color-border);
    color: var(--color-text-main);
    padding: 0.5rem 1.5rem;
    border-radius: 4px;
    margin-right: 1rem;
    cursor: pointer;
}

.primary-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}
</style>
