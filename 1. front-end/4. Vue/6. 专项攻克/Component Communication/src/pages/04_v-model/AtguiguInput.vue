<template>
  <div class="custom-input-container">
    <input />
    <label> </label>
    <div class="line"></div>
    <div class="active-line"></div>
  </div>
</template>

<script setup name="AtguiguInput"></script>

<style scoped>
/* 定义颜色变量，方便主题定制 */
:root {
  --primary-color: #42b983;
  --text-color: #35495e;
  --label-color: #888;
  --border-color: #ccc;
}

.custom-input-container {
  position: relative;
  margin: 20px 0;
  width: 100%;
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

/* 浮动标签的激活状态 */
label.active {
  top: -15px;
  left: 0;
  font-size: 12px;
  color: var(--primary-color);
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

/* 输入框激活时底部线条的动画 */
.active-line.active {
  width: 100%;
  left: 0;
}
</style>
