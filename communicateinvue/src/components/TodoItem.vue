<script setup>
import { defineEmits } from 'vue'

const props = defineProps({
  todoItem: {
    type: Object,
    required: true,
  },
})

const emit = defineEmits(['toggle-complete', 'delete-todo'])

function handleDelete() {
  emit('delete-todo', props.todoItem.id)
}
</script>
<template>
  <li :class="{ completed: todoItem.completed }">
    <div class="view">
      <input
        type="checkbox"
        class="toggle"
        :checked="todoItem.completed"
        @change="emit('toggle-complete', todoItem.id)"
      />
      <label>{{ todoItem.text }}</label>
      <button class="destroy" @click="handleDelete">×</button>
    </div>
  </li>
</template>
