<template>
  <li :class="[{ completed: todoItem.completed }, `item-${theme}`]">
    <div class="view">
      <input
        type="checkbox"
        class="toggle"
        :checked="todoItem.completed"
        @change="todoStore.toggleTodo(todoItem.id)"
      />
      <label>{{ todoItem.text }}</label>
      <button class="destroy" @click="showConfirmModal = true">×</button>
    </div>
  </li>

  <BaseModal
    :show="showConfirmModal"
    @close="showConfirmModal = false"
    @confirm="handleDeleteConfirm"
  >
    <template #header>
      <h3>确认删除</h3>
    </template>
    <p>你确定要删除 "{{ todoItem.text }}" 吗?</p>
    <template #footer="{ close, confirm }">
      <button class="btn-secondary" @click="close">手下留情</button>
      <button class="btn-danger" @click="confirm">狠心删除</button>
    </template>
  </BaseModal>
</template>

<script setup>
import { ref, inject } from 'vue'
import { useTodoStore } from '../stores/todoStore'
import BaseModal from './BaseModal.vue'

const props = defineProps({
  todoItem: {
    type: Object,
    required: true,
  },
})

const todoStore = useTodoStore()
const theme = inject('theme', 'light')
const showConfirmModal = ref(false)

const handleDeleteConfirm = () => {
  todoStore.deleteTodo(props.todoItem.id)
  showConfirmModal.value = false
}
</script>
