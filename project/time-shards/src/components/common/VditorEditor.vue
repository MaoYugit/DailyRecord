<script setup>
/**
 * VditorEditor.vue
 * 封装后的 Vditor 编辑器组件
 *
 * 功能：
 * 1. 提供 v-model 支持，实现双向数据绑定
 * 2. 封装 Vditor 的生命周期（挂载、销毁）
 * 3. 隔离 Vditor 的 CSS 样式覆盖
 * 4. 通过 Props 接收上传回调，解耦业务 API
 */
import { ref, onMounted, onBeforeUnmount, watch } from "vue";
import Vditor from "vditor";
import "vditor/dist/index.css";

// --- Props 定义 ---
const props = defineProps({
  // 用于 v-model 绑定内容
  modelValue: {
    type: String,
    default: "",
  },
  // 占位符文本
  placeholder: {
    type: String,
    default: "在此处开始你的创作...",
  },
  // 编辑器高度，默认 100% 撑满父容器
  height: {
    type: String,
    default: "100%",
  },
  // 上传处理函数：接收 (files, vditorInstance)，返回 Promise
  // 如果不传，则编辑器内的上传按钮可能会报错或不可用
  uploadHandler: {
    type: Function,
    default: null,
  },
});

// --- Emits 定义 ---
const emits = defineEmits(["update:modelValue", "after"]);

// DOM 引用
const vditorRef = ref(null);
// Vditor 实例引用
const vditorInstance = ref(null);

/**
 * 初始化 Vditor
 */
const initVditor = () => {
  vditorInstance.value = new Vditor(vditorRef.value, {
    height: props.height,
    mode: "ir", // 即时渲染模式
    placeholder: props.placeholder,
    theme: "dark", // 基础主题
    cache: { enable: false }, // 禁用本地存储缓存

    // 预览配置
    preview: {
      theme: { current: "dark" },
      hljs: { style: "native", lineNumber: true },
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
      // 自定义上传处理
      handler: async (files) => {
        if (props.uploadHandler) {
          // 调用父组件传入的上传逻辑
          return await props.uploadHandler(files, vditorInstance.value);
        }
        console.warn("未配置 uploadHandler");
        return "Upload handler not defined";
      },
    },

    // 输入回调：实现 v-model 的核心
    input: (value) => {
      emits("update:modelValue", value);
    },

    // 初始化完成回调
    after: () => {
      // 如果初始有值（如编辑模式），设置进去
      if (props.modelValue) {
        vditorInstance.value.setValue(props.modelValue);
      }
      // 抛出 after 事件，父组件如果想操作 instance 可以监听
      emits("after", vditorInstance.value);
    },
  });
};

/**
 * 监听 modelValue 变化
 * 场景：API 异步获取文章详情后，需要回显到编辑器
 */
watch(
  () => props.modelValue,
  (newVal) => {
    // 关键判断：只有当新值与编辑器当前值不一致时才 setValue
    // 否则在打字时会导致光标跳到开头或闪烁
    if (vditorInstance.value && newVal !== vditorInstance.value.getValue()) {
      vditorInstance.value.setValue(newVal);
    }
  }
);

// --- 生命周期 ---
onMounted(() => {
  initVditor();
});

onBeforeUnmount(() => {
  if (vditorInstance.value) {
    vditorInstance.value.destroy(); // 清理 DOM 和事件监听
    vditorInstance.value = null;
  }
});

// 暴露实例给父组件（以防万一父组件需要直接调用 insertValue 等方法）
defineExpose({
  vditorInstance,
});
</script>

<template>
  <!-- Vditor 挂载节点 -->
  <div ref="vditorRef" class="vditor-wrapper"></div>
</template>

<style scoped>
/* 
 * 将原父组件中的 Deep Selector 样式全部移到这里
 * 实现了样式的完全隔离
 */

.vditor-wrapper {
  /* 确保组件撑满外部容器 */
  width: 100%;
  height: 100%;
  border-radius: 8px;
  border: 1px solid var(--color-border);
  text-align: left;
  box-sizing: border-box;
}

/* 覆盖 Vditor 内部样式 */
:deep(.vditor) {
  --vditor-border-color: var(--color-border);
  background-color: rgba(0, 0, 0, 0.2) !important;
  color: var(--color-text-main, #eee) !important;
  border: none !important; /* 去除双重边框 */
}

:deep(.vditor-toolbar) {
  background-color: transparent !important;
  border-bottom: 1px solid var(--color-border) !important;
  padding-left: 10px;
}

:deep(.vditor-content) {
  background-color: transparent !important;
}

:deep(.vditor-reset) {
  background-color: transparent !important;
  text-align: left !important;
  color: #eee !important;
}

:deep(.vditor-reset pre),
:deep(.vditor-reset code) {
  background-color: rgba(0, 0, 0, 0.5) !important;
  font-family: "Fira Code", monospace;
  border-radius: 4px;
}

:deep(.vditor-ir__node--expand) {
  background-color: rgba(0, 0, 0, 0.5) !important;
  box-shadow: none !important;
}

:deep(.vditor-ir__node--expand textarea) {
  background-color: transparent !important;
  color: #ffd700 !important;
  font-family: "Fira Code", monospace;
}

:deep(.vditor-reset div[data-block="0"]) {
  background-color: rgba(0, 0, 0, 0.5) !important;
}

:deep(.vditor-counter) {
  background: transparent !important;
  border-top: 1px solid var(--color-border) !important;
  color: #aaa !important;
}

:deep(.vditor-ir__node) {
  caret-color: #fff !important;
}
</style>
