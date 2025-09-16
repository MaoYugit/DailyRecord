<script setup>
import TodoItem from './TodoItem.vue'

const emit = defineEmits(['toggle-complete', 'delete-todo'])

defineProps({
  todos: {
    type: Array,
    required: true,
  },
})
</script>

<template>
  <ul class="todo-list">
    <TodoItem
      v-for="todo in todos"
      :key="todo.id"
      :todo-item="todo"
      @toggle-complete="emit('toggle-complete', $event)"
      @delete-todo="emit('delete-todo', $event)"
    />
  </ul>
</template>
