<template>
  <div class="custom-input-container">
    <!--
      1. 绑定 value
      2. 监听 input 事件并向上触发 update:modelValue
      3. 监听 focus 和 blur 来控制动画效果
    -->
    <input
      type="text"
      :value="modelValue"
      @input="handleInput"
      @focus="isFocused = true"
      @blur="isFocused = false"
    />

    <!-- 当有焦点或者输入框有内容时，label 处于 active 状态 -->
    <label :class="{ active: isFocused || modelValue }">
      {{ label }}
    </label>

    <div class="line"></div>
    <!-- 当获得焦点时，激活底线动画 -->
    <div class="active-line" :class="{ active: isFocused }"></div>
  </div>
</template>

<script setup lang="ts" name="AtguiguInput">
import { ref } from 'vue'

// 接收 v-model 默认传进来的 modelValue，以及一个自定义的 label
defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  label: {
    type: String,
    default: '请输入内容',
  },
})

// 声明 v-model 必须的事件
const emit = defineEmits(['update:modelValue'])

// 控制 UI 动画的内部状态
const isFocused = ref(false)

// 处理输入逻辑
function handleInput(e) {
  const target = e.target
  emit('update:modelValue', target.value)
}
</script>

<style scoped>
/* 颜色变量 */
.custom-input-container {
  --primary-color: #42b983;
  --text-color: #35495e;
  --label-color: #888;
  --border-color: #ccc;

  position: relative;
  margin: 25px 0;
  width: 300px; /* 给个固定宽度方便观察 */
}

input {
  width: 100%;
  border: none;
  border-bottom: 1px solid var(--border-color);
  padding: 10px 0;
  background-color: transparent;
  font-size: 16px;
  color: var(--text-color);
  outline: none;
  position: relative;
  z-index: 1;
}

label {
  position: absolute;
  top: 10px;
  left: 0;
  font-size: 16px;
  color: var(--label-color);
  pointer-events: none;
  transition: all 0.2s ease-in-out;
  z-index: 0;
}

/* 标签上浮的效果：当 active 类存在时触发 */
label.active {
  top: -15px;
  left: 0;
  font-size: 12px;
  color: var(--primary-color);
  font-weight: bold;
}

.line {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 1px;
  background-color: var(--border-color);
}

.active-line {
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 2px;
  background-color: var(--primary-color);
  transition: all 0.3s ease-in-out;
}

/* 底线从中间向两边展开 */
.active-line.active {
  width: 100%;
  left: 0;
}
</style>
