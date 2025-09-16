<template>
  <ul class="todo-list">
    <TodoItem v-for="todo in todoStore.todos" :key="todo.id" :todo-item="todo" />
  </ul>
</template>

<script setup>
import TodoItem from './TodoItem.vue'
import { useTodoStore } from '../stores/todoStore'
const todoStore = useTodoStore()
</script>
