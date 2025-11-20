<template>
  <div class="modal-overlay" v-if="show" @click.self="closeModal">
    <div class="modal-content">
      <header class="modal-header">
        <slot name="header">默认标题</slot>
      </header>
      <main class="modal-body">
        <slot></slot>
      </main>
      <footer class="modal-footer">
        <slot name="footer" :close="closeModal" :confirm="confirmAction">
          <button @click="closeModal">取消</button>
          <button @click="confirmAction">确认</button>
        </slot>
      </footer>
    </div>
  </div>
</template>

<script setup>
defineProps({ show: Boolean })
const emit = defineEmits(['close', 'confirm'])

const closeModal = () => emit('close')
const confirmAction = () => emit('confirm')
</script>
