<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

// --- 引入\编辑器组件 ---
import VditorEditor from "@/components/common/VditorEditor.vue";

// 引入 Store 和 API
import { useUserStore } from "@/stores/user";
import { createArticle, updateArticle, getArticleById } from "@/api/article";
import { uploadFile } from "@/api/attachment";
import { getCategories, createCategory } from "@/api/category";
import { getTags, createTag } from "@/api/tag";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

// --- 状态定义 ---
const isEditMode = computed(() => !!route.params.id);
const loading = ref(false);
const saving = ref(false);

// 表单数据
const form = ref({
  title: "",
  summary: "",
  content: "", // 将通过 v-model 绑定给 VditorEditor
  coverImage: "",
  categoryId: "",
  status: 1,
  tags: [],
});

// 元数据
const categories = ref([]);
const availableTags = ref([]);

// UI 交互状态
const catSearchText = ref("");
const showCatDropdown = ref(false);
const tagSearchText = ref("");
const showTagDropdown = ref(false);
const tagInputRef = ref(null);

// --- 辅助函数 ---
const generateSlug = (text) => {
  return text
    .toLowerCase()
    .trim()
    .replace(/[^\w\s-]/g, "")
    .replace(/[\s_-]+/g, "-")
    .replace(/^-+|-+$/g, "");
};

// --- 核心逻辑：编辑器上传回调 ---
// 这个函数作为 Prop 传给子组件，让子组件在用户选择图片后调用
const handleEditorUpload = async (files, vditorInstance) => {
  const file = files[0];
  if (!file) return;
  try {
    // 1. 调用上传 API
    const res = await uploadFile(file, userStore.user.id);

    // 2. 拼接 URL
    const name = file.name;
    const baseURL = "http://localhost:8080"; // 建议移至环境变量 import.meta.env.VITE_API_URL
    const url = baseURL + res.fileUrl;

    // 3. 将 Markdown 图片语法插入编辑器
    vditorInstance.insertValue(`![${name}](${url})`);

    // 返回 null 告诉 Vditor 我们已经处理完了
    return null;
  } catch (error) {
    console.error("Upload failed:", error);
    return "Upload failed"; // 返回错误信息供 Vditor 显示
  }
};

// --- Category 逻辑 (保持原有逻辑) ---
const filteredCategories = computed(() => {
  if (!catSearchText.value) return categories.value;
  return categories.value.filter((c) =>
    c.name.toLowerCase().includes(catSearchText.value.toLowerCase())
  );
});

const onCatFocus = () => {
  showCatDropdown.value = true;
  if (form.value.categoryId) {
    const found = categories.value.find((c) => c.id === form.value.categoryId);
    if (found) catSearchText.value = found.name;
  }
};

const selectCategory = (cat) => {
  form.value.categoryId = cat.id;
  catSearchText.value = cat.name;
  showCatDropdown.value = false;
};

const handleCreateCategory = async () => {
  const name = catSearchText.value.trim();
  if (!name) return;
  const existing = categories.value.find((c) => c.name === name);
  if (existing) {
    selectCategory(existing);
    return;
  }
  try {
    const newCat = {
      name: name,
      slug: generateSlug(name) || `cat-${Date.now()}`,
    };
    const res = await createCategory(newCat);
    categories.value.push(res);
    selectCategory(res);
  } catch (error) {
    console.error("Create category failed:", error);
  }
};

// --- Tag 逻辑 (保持原有逻辑) ---
const filteredTags = computed(() => {
  const selectedIds = new Set(form.value.tags.map((t) => t.id));
  let list = availableTags.value.filter((t) => !selectedIds.has(t.id));
  if (tagSearchText.value) {
    list = list.filter((t) =>
      t.name.toLowerCase().includes(tagSearchText.value.toLowerCase())
    );
  }
  return list;
});

const selectTag = (tag) => {
  form.value.tags.push(tag);
  tagSearchText.value = "";
  showTagDropdown.value = false;
  if (tagInputRef.value) tagInputRef.value.focus();
};

const removeTag = (index) => {
  form.value.tags.splice(index, 1);
};

const handleCreateTag = async () => {
  const name = tagSearchText.value.trim();
  if (!name) return;
  const existing = availableTags.value.find((t) => t.name === name);
  if (existing) {
    const isSelected = form.value.tags.find((t) => t.id === existing.id);
    if (!isSelected) selectTag(existing);
    return;
  }
  try {
    const newTag = {
      name: name,
      slug: generateSlug(name) || `tag-${Date.now()}`,
    };
    const res = await createTag(newTag);
    availableTags.value.push(res);
    selectTag(res);
  } catch (error) {
    console.error("Create tag failed:", error);
  }
};

const handleTagInputDelete = () => {
  if (tagSearchText.value === "" && form.value.tags.length > 0) {
    form.value.tags.pop();
  }
};

// --- 数据加载与初始化 ---
const fetchMetadata = async () => {
  try {
    const [catRes, tagRes] = await Promise.all([getCategories(), getTags()]);
    categories.value = catRes || [];
    availableTags.value = tagRes || [];
  } catch (error) {
    console.error("Metadata error:", error);
  }
};

const initForm = async () => {
  if (!userStore.isLoggedIn) {
    router.push("/login");
    return;
  }
  await fetchMetadata();

  if (isEditMode.value) {
    loading.value = true;
    try {
      const article = await getArticleById(route.params.id);

      // 处理 Tags 回显逻辑
      let currentTags = [];
      if (article.tags && article.tags.length > 0) {
        if (typeof article.tags[0] === "object") {
          currentTags = article.tags;
        } else {
          currentTags = availableTags.value.filter((t) =>
            article.tags.includes(t.id)
          );
        }
      }

      form.value = {
        ...article,
        tags: currentTags,
      };

      // 回显 Category 名称
      if (form.value.categoryId) {
        const cat = categories.value.find(
          (c) => c.id === form.value.categoryId
        );
        if (cat) catSearchText.value = cat.name;
      }

      // 注意：这里无需手动调用编辑器 setValue，v-model 会自动处理 form.value.content 的变化
    } catch (error) {
      console.error("Article load error:", error);
    } finally {
      loading.value = false;
    }
  }
};

// --- 表单提交 ---
const handleCoverUpload = async (event) => {
  const file = event.target.files[0];
  if (!file) return;
  try {
    const res = await uploadFile(file, userStore.user.id);
    const fullUrl = "http://localhost:8080" + res.fileUrl;
    form.value.coverImage = fullUrl;
  } catch (error) {
    console.error("Cover upload failed:", error);
  }
};

const saveArticle = async () => {
  // 1. 简单校验
  if (!form.value.title || !form.value.content) {
    alert("Title and Content are required");
    return;
  }

  saving.value = true;
  try {
    const data = {
      ...form.value,
      user_id: userStore.user.id,
      tags: form.value.tags, // 传递 Tag 对象列表
    };
    if (!catSearchText.value) data.categoryId = null;

    if (isEditMode.value) {
      await updateArticle(data);
    } else {
      await createArticle(data);
    }
    router.push("/");
  } catch (error) {
    console.error("Save failed:", error);
    alert("Failed to save");
  } finally {
    saving.value = false;
  }
};

// 点击外部关闭下拉菜单
const handleClickOutside = (e) => {
  const sidebar = document.querySelector(".sidebar");
  if (sidebar && !sidebar.contains(e.target)) {
    showCatDropdown.value = false;
    showTagDropdown.value = false;
  }
};

onMounted(() => {
  initForm();
  document.addEventListener("click", handleClickOutside);
});
// onBeforeUnmount 不需要手动销毁编辑器了，子组件自己会处理
</script>

<template>
  <div class="editor-container">
    <!-- 顶部 Header -->
    <div class="editor-header">
      <h2>{{ isEditMode ? "Edit Shard" : "New Shard" }}</h2>
      <div class="actions">
        <button @click="saveArticle" class="primary-btn" :disabled="saving">
          {{ saving ? "Saving..." : "Publish" }}
        </button>
      </div>
    </div>

    <div class="editor-layout">
      <!-- 左侧主编辑区 -->
      <div class="main-editor">
        <div class="form-group title-group">
          <input v-model="form.title" placeholder="Title" class="title-input" />
        </div>

        <!-- 
             核心修改点：
             替换原有的 <div id="vditor"> 
             使用封装好的组件 
        -->
        <div class="editor-wrapper-container">
          <VditorEditor
            v-model="form.content"
            :upload-handler="handleEditorUpload"
            height="100%"
          />
        </div>
      </div>

      <!-- 右侧侧边栏 (保持不变) -->
      <div class="sidebar">
        <!-- Category -->
        <div class="form-group relative-group">
          <label>Category</label>
          <input
            type="text"
            v-model="catSearchText"
            @focus="onCatFocus"
            @input="showCatDropdown = true"
            @keydown.enter.prevent="handleCreateCategory"
            placeholder="Search or create category..."
            autocomplete="off"
          />
          <ul v-if="showCatDropdown" class="dropdown-menu">
            <li
              v-for="cat in filteredCategories"
              :key="cat.id"
              @click="selectCategory(cat)"
            >
              {{ cat.name }}
            </li>
            <li
              v-if="catSearchText && filteredCategories.length === 0"
              class="create-option"
              @click="handleCreateCategory"
            >
              Create category "{{ catSearchText }}"
            </li>
            <li
              v-if="!catSearchText && filteredCategories.length === 0"
              class="empty-option"
            >
              Type to search...
            </li>
          </ul>
        </div>

        <!-- Tags -->
        <div class="form-group relative-group">
          <label>Tags</label>
          <div class="tags-input-container" @click="tagInputRef?.focus()">
            <span
              v-for="(tag, index) in form.tags"
              :key="tag.id"
              class="tag-chip"
            >
              {{ tag.name }}
              <i @click.stop="removeTag(index)">&times;</i>
            </span>
            <input
              ref="tagInputRef"
              v-model="tagSearchText"
              @focus="showTagDropdown = true"
              @input="showTagDropdown = true"
              @keydown.enter.prevent="handleCreateTag"
              @keydown.backspace="handleTagInputDelete"
              placeholder="Add tag..."
              class="tag-input-field"
              autocomplete="off"
            />
          </div>
          <ul v-if="showTagDropdown && tagSearchText" class="dropdown-menu">
            <li
              v-for="tag in filteredTags"
              :key="tag.id"
              @click="selectTag(tag)"
            >
              {{ tag.name }}
            </li>
            <li
              v-if="filteredTags.length === 0"
              class="create-option"
              @click="handleCreateTag"
            >
              Create tag "{{ tagSearchText }}"
            </li>
          </ul>
        </div>

        <!-- Summary & Cover -->
        <div class="form-group">
          <label>Summary</label>
          <textarea v-model="form.summary" rows="3"></textarea>
        </div>
        <div class="form-group">
          <label>Cover Image</label>
          <input type="file" @change="handleCoverUpload" accept="image/*" />
          <div v-if="form.coverImage" class="image-preview">
            <img :src="form.coverImage" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 
   在这里我们删除了所有 :deep(.vditor...) 的样式 
   只保留布局样式，非常清爽
*/

.editor-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
  height: calc(100vh - 80px);
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

.main-editor {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 新增：给编辑器组件一个明确的 Flex 容器，确保它能占满剩余空间 */
.editor-wrapper-container {
  flex: 1;
  overflow: hidden;
  /* 如果你不加这个，Vditor 可能会撑破布局 */
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
  outline: none;
}

/* --- 侧边栏样式 (保持不变) --- */
.sidebar {
  width: 300px;
  overflow-y: auto;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  padding: 1rem;
}

.form-group {
  margin-bottom: 1.5rem;
}
.relative-group {
  position: relative;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #aaa;
  font-size: 0.9rem;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid var(--color-border);
  color: #eee;
  padding: 0.5rem;
  border-radius: 4px;
  outline: none;
}
.form-group input:focus,
.form-group textarea:focus {
  border-color: #00ff9d;
}

/* Dropdown & Tags 样式 (保持不变) */
.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: #2a2a2a;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  margin-top: 4px;
  max-height: 200px;
  overflow-y: auto;
  z-index: 1000;
  list-style: none;
  padding: 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
}

.dropdown-menu li {
  padding: 0.5rem;
  cursor: pointer;
  color: #ddd;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  font-size: 0.9rem;
}

.dropdown-menu li:hover {
  background: rgba(0, 255, 157, 0.2);
  color: #fff;
}

.create-option {
  color: #00ff9d !important;
  font-weight: bold;
}
.empty-option {
  color: #777;
  cursor: default;
  font-style: italic;
}

.tags-input-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid var(--color-border);
  padding: 0.4rem;
  border-radius: 4px;
  min-height: 38px;
  cursor: text;
}
.tags-input-container:focus-within {
  border-color: #00ff9d;
}

.tag-chip {
  background: rgba(0, 255, 157, 0.15);
  color: #00ff9d;
  padding: 0.2rem 0.6rem;
  border-radius: 12px;
  font-size: 0.85rem;
  display: flex;
  align-items: center;
  gap: 0.4rem;
}
.tag-chip i {
  cursor: pointer;
  font-style: normal;
  font-weight: bold;
  opacity: 0.7;
}
.tag-chip i:hover {
  opacity: 1;
  color: #fff;
}

.tag-input-field {
  flex: 1;
  min-width: 60px;
  background: transparent !important;
  border: none !important;
  padding: 0.2rem !important;
  margin: 0 !important;
  color: #eee;
  outline: none;
}
.tag-input-field:focus {
  box-shadow: none !important;
}

.image-preview img {
  width: 100%;
  margin-top: 0.5rem;
  border-radius: 4px;
}
.primary-btn {
  background: #00ff9d;
  color: #000;
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
}
.primary-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
