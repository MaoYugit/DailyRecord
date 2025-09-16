<script setup>
import { ref } from 'vue'
import TodoList from './components/TodoList.vue'

const todos = ref([
  { id: 1, text: 'learn vue props', completed: true },
  { id: 2, text: '完成教学项目', completed: false },
])

const handleToggleComplete = (id) => {
  const todo = todos.value.find((t) => t.id === id)
  if (todo) {
    todo.completed = !todo.completed
  }
}

const handleDeleteTodo = (id) => {
  todos.value = todos.value.filter((t) => t.id !== id)
}
</script>

<template>
  <div class="todoapp">
    <TodoList
      :todos="todos"
      @toggle-complete="handleToggleComplete"
      @delete-todo="handleDeleteTodo"
    />
  </div>
</template>

<style scoped></style>
