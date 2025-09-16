<template>
  <UINotification />
  <div id="app-wrapper" :class="theme">
    <div class="todoapp">
      <TodoHeader
        ref="headerRef"
        placeholder="输入待办事项，然后回车"
        data-testid="todo-input-header"
      />
      <section class="main">
        <TodoList />
      </section>
      <footer class="footer" v-if="todoStore.totalCount > 0">
        <span>
          <strong>{{ todoStore.completedCount }}</strong>
          / {{ todoStore.totalCount }} 项已完成
        </span>
        <button @click="toggleTheme">切换到 {{ theme === 'light' ? '暗色' : '亮色' }} 主题</button>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, provide } from 'vue'
import { useTodoStore } from './stores/todoStore'
import TodoHeader from './components/TodoHeader.vue'
import TodoList from './components/TodoList.vue'
import UINotification from './components/UINotification.vue'

const todoStore = useTodoStore()
const headerRef = ref(null)
const theme = ref('light')

provide('theme', theme)

const toggleTheme = () => {
  theme.value = theme.value === 'light' ? 'dark' : 'light'
}

onMounted(() => {
  headerRef.value?.focusInput()
})
</script>
