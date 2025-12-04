<script setup>
import { ref, onMounted, computed, onBeforeUnmount } from "vue";
import { useRoute, useRouter } from "vue-router";
// 引入 Vditor 核心库和样式
import Vditor from "vditor";
import "vditor/dist/index.css";

// 引入你的 Store 和 API
import { useUserStore } from "@/stores/user";
import { createArticle, updateArticle, getArticleById } from "@/api/article";
import { uploadFile } from "@/api/attachment";
import { getCategories } from "@/api/category";
import { getTags } from "@/api/tag";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const isEditMode = computed(() => !!route.params.id);
const loading = ref(false);
const saving = ref(false);

// Vditor 实例引用
const vditorInstance = ref(null);

// 表单数据
const form = ref({
  title: "",
  slug: "",
  summary: "",
  content: "",
  cover_image: "",
  category_id: "",
  status: 1,
  tags: [],
});

const categories = ref([]);
const availableTags = ref([]);

// --- Vditor 初始化逻辑 ---
const initVditor = () => {
  vditorInstance.value = new Vditor("vditor", {
    // 让编辑器占满父容器高度
    height: "100%",
    // 核心模式：'ir' (Instant Rendering) 即时渲染模式 (Typora 风格)
    mode: "ir",
    placeholder: "在此处开始你的创作...",

    // 1. UI 主题设为暗色 (影响工具栏图标颜色)
    theme: "dark",

    // 禁用缓存，防止新建时出现上次未保存的内容
    cache: { enable: false },

    // 2. 预览区域配置 (关键)
    preview: {
      theme: {
        // 必须设为 dark，告诉 Vditor 内容区是暗色的，不要强制加白色背景
        current: "dark",
      },
      hljs: {
        // 代码高亮风格：native 是纯黑底，dracula 是深色吸血鬼风格
        style: "native",
        lineNumber: true,
      },
    },

    // 工具栏配置
    toolbar: [
      "emoji",
      "headings",
      "bold",
      "italic",
      "strike",
      "link",
      "|",
      "list",
      "ordered-list",
      "check",
      "outdent",
      "indent",
      "|",
      "quote",
      "line",
      "code",
      "inline-code",
      "insert-before",
      "insert-after",
      "|",
      "upload",
      "table",
      "|",
      "undo",
      "redo",
      "|",
      "edit-mode",
      "fullscreen",
    ],

    // 图片上传配置
    upload: {
      accept: "image/*",
      handler: async (files) => {
        const file = files[0];
        if (!file) return;
        try {
          // 调用后端 API 上传
          const res = await uploadFile(file, userStore.user.id);
          const name = file.name;

          console.log("上传接口返回结果:", res);

          const baseURL = "http://localhost:8080";

          const url = baseURL + res.fileUrl;

          // 插入 Markdown 图片语法
          vditorInstance.value.insertValue(`![${name}](${url})`);
          return null;
        } catch (error) {
          console.error("Upload failed:", error);
          return "Upload failed";
        }
      },
    },

    // 监听输入，同步数据
    input: (value) => {
      form.value.content = value;
    },

    // 初始化完成后尝试回填数据
    after: () => {
      if (form.value.content) {
        vditorInstance.value.setValue(form.value.content);
      }
    },
  });
};

// --- 数据加载逻辑 ---
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
      form.value = {
        ...article,
        tags: article.tags ? article.tags.map((t) => t.id) : [],
      };
      // 数据加载完成，如果编辑器也好了，就赋值
      if (vditorInstance.value) {
        vditorInstance.value.setValue(form.value.content);
      }
    } catch (error) {
      console.error("Article load error:", error);
    } finally {
      loading.value = false;
    }
  }
};

// --- 上传封面图 ---
const handleCoverUpload = async (event) => {
  const file = event.target.files[0];
  if (!file) return;

  try {
    const res = await uploadFile(file, userStore.user.id);

    console.log("封面上传结果:", res);

    // [核心修复]
    // 2. 必须拼接后端地址，否则前端 <img src="/uploads/..."> 会去 5173 找图片导致 404
    const fullUrl = "http://localhost:8080" + res.fileUrl;

    // 赋值给表单，这样图片就能立马显示出来了
    form.value.cover_image = fullUrl;
  } catch (error) {
    console.error("Cover upload failed:", error);
    alert("Cover upload failed");
  }
};

const saveArticle = async () => {
  // 再次确保获取最新内容
  if (vditorInstance.value) {
    form.value.content = vditorInstance.value.getValue();
  }
  if (!form.value.title || !form.value.content) {
    alert("Title and Content are required");
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
    router.push("/");
  } catch (error) {
    console.error("Save failed:", error);
    alert("Failed to save");
  } finally {
    saving.value = false;
  }
};

onMounted(() => {
  initVditor();
  initForm();
});

// 组件销毁前清理编辑器实例 (虽然 Vditor 通常会自动处理，但这是好习惯)
onBeforeUnmount(() => {
  if (vditorInstance.value) {
    vditorInstance.value.destroy();
  }
});
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
        <!-- 标题 -->
        <div class="form-group title-group">
          <input v-model="form.title" placeholder="Title" class="title-input" />
        </div>

        <!-- Vditor 挂载点 -->
        <div id="vditor" class="vditor-wrapper"></div>
      </div>

      <!-- 右侧侧边栏 -->
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
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">
              {{ cat.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <label>Cover Image</label>
          <input type="file" @change="handleCoverUpload" accept="image/*" />
          <div v-if="form.cover_image" class="image-preview">
            <img :src="form.cover_image" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 你的基础样式变量 */
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

/* --- Vditor 样式强力覆盖 (核心部分) --- */

/* 1. 外层容器 */
.vditor-wrapper {
  flex: 1;
  border-radius: 8px;
  border: 1px solid var(--color-border);
  text-align: left;
}

/* 2. 覆盖 Vditor 全局背景 [已修改] */
:deep(.vditor) {
  --vditor-border-color: var(--color-border);

  /* 修改这里：将 transparent 改为你想要的 0.2 透明度黑 */
  background-color: rgba(0, 0, 0, 0.2) !important;

  color: var(--color-text-main, #eee) !important;
}

/* 3. 工具栏背景透明 */
/* 工具栏会继承父级的 rgba(0,0,0,0.2)，所以这里保持 transparent 即可 */
:deep(.vditor-toolbar) {
  background-color: transparent !important;
  border-bottom: 1px solid var(--color-border) !important;
  padding-left: 10px;
}

/* 4. 内容区背景透明 */
/* 保持透明，让它显示父级 (.vditor) 的背景色 */
:deep(.vditor-content) {
  background-color: transparent !important;
}
:deep(.vditor-reset) {
  background-color: transparent !important;
  text-align: left !important;
  color: #eee !important;
}

/* --- 代码块样式修复 --- */

/* 状态A: 渲染后的代码块 (Preview State) */
:deep(.vditor-reset pre),
:deep(.vditor-reset code) {
  /* 保持 0.5 (比背景 0.2 更深)，这样代码块会有“凹陷”或“凸起”的层次感 */
  background-color: rgba(0, 0, 0, 0.5) !important;
  font-family: "Fira Code", monospace;
  border-radius: 4px;
}

/* 状态B: 编辑中的代码块 (Expand/Edit State) */
:deep(.vditor-ir__node--expand) {
  /* 保持 0.5，与状态A一致，防止点击时颜色跳变 */
  background-color: rgba(0, 0, 0, 0.5) !important;
  box-shadow: none !important;
}

/* 状态B里的输入框 */
:deep(.vditor-ir__node--expand textarea) {
  background-color: transparent !important;
  color: #ffd700 !important;
  font-family: "Fira Code", monospace;
}

/* 隐藏代码块上方的语言栏背景 */
:deep(.vditor-reset div[data-block="0"]) {
  background-color: rgba(0, 0, 0, 0.5) !important;
}

/* --- 其他 UI 微调 --- */

:deep(.vditor-counter) {
  background: transparent !important;
  border-top: 1px solid var(--color-border) !important;
  color: #aaa !important;
}

:deep(.vditor-ir__node) {
  caret-color: #fff !important;
}

/* --- 侧边栏样式 --- */
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
