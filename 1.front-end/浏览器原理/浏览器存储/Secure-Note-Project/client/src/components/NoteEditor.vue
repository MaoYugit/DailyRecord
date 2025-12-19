<!-- client/src/components/NoteEditor.vue -->
<template>
  <!-- 动态绑定 class 来实现主题切换 -->
  <div class="editor-container" :class="{ 'dark-mode': isDarkMode }">
    <div class="toolbar">
      <h3>📝 我的笔记</h3>
      <button @click="toggleTheme">
        切换为{{ isDarkMode ? "亮色" : "暗黑" }}模式
      </button>
      <span class="status" v-if="lastSavedTime">
        上次自动保存: {{ lastSavedTime }}
      </span>
    </div>

    <!-- 笔记输入区域 -->
    <textarea
      v-model="noteContent"
      placeholder="开始写作... (内容会自动保存到 LocalStorage)"
    ></textarea>

    <div class="footer">
      <button @click="clearDraft" class="danger-btn">清空草稿</button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import { localStore } from "../utils/storage"; // 引入封装好的工具

// --- 功能 1: 自动保存草稿 (LocalStorage) ---
const noteContent = ref("");
const lastSavedTime = ref("");

// 1. 初始化时，从 LocalStorage 读取上次没写完的内容
onMounted(() => {
  const savedDraft = localStore.get("draft_note");
  if (savedDraft) {
    noteContent.value = savedDraft;
    lastSavedTime.value = "恢复成功";
  }

  // 恢复主题设置
  const savedTheme = localStore.get("theme_setting");
  if (savedTheme === "dark") {
    isDarkMode.value = true;
  }
});

// 2. 监听输入，实时保存 (防抖优化是进阶点，这里先直接存)
watch(noteContent, (newVal) => {
  localStore.set("draft_note", newVal);

  // 更新保存时间显示
  const now = new Date();
  lastSavedTime.value = `${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;
});

const clearDraft = () => {
  noteContent.value = "";
  localStore.remove("draft_note");
  lastSavedTime.value = "已清空";
};

// --- 功能 2: 主题切换 (LocalStorage) ---
const isDarkMode = ref(false);

const toggleTheme = () => {
  isDarkMode.value = !isDarkMode.value;
  // 持久化存储主题偏好
  localStore.set("theme_setting", isDarkMode.value ? "dark" : "light");
};
</script>

<style scoped>
.editor-container {
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 8px;
  transition: all 0.3s;
  margin-top: 20px;
}

textarea {
  width: 100%;
  height: 200px;
  margin-top: 10px;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.status {
  font-size: 12px;
  color: #888;
}

/* 暗黑模式样式 */
.dark-mode {
  background-color: #333;
  color: white;
}
.dark-mode textarea {
  background-color: #555;
  color: white;
  border: 1px solid #666;
}
.danger-btn {
  background-color: #ff4444;
  color: white;
  border: none;
  padding: 5px 10px;
  margin-top: 10px;
  cursor: pointer;
}
</style>
