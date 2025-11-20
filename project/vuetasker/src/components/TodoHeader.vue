<template>
  <header class="header">
    <h1>VueTasker</h1>
    <div class="input-group">
      <input
        ref="inputRef"
        v-bind="$attrs"
        type="text"
        v-model="todoStore.newTodo"
        @keyup.enter="todoStore.addTodo"
      />
      <button @click="todoStore.addTodo">添加</button>
    </div>
  </header>
</template>

<script setup>
import { ref, defineExpose } from 'vue'
import { useTodoStore } from '../stores/todoStore'
const todoStore = useTodoStore()

defineOptions({
  inheritAttrs: false,
})

const inputRef = ref(null)
const focusInput = () => {
  inputRef.value?.focus()
}

defineExpose({
  focusInput,
})
</script>
