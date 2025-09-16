import { defineStore } from 'pinia'
import emitter from '../utils/emitter'

export const useTodoStore = defineStore('todo', {
  state: () => ({
    todos: [
      { id: 1, text: '学习 Pinia State', completed: true },
      { id: 2, text: '学习 Pinia Actions', completed: false },
    ],
    newTodo: '',
  }),
  getters: {
    completedCount: (state) => state.todos.filter((t) => t.completed).length,
    totalCount: (state) => state.todos.length,
  },
  actions: {
    addTodo() {
      if (!this.newTodo.trim()) return
      this.todos.unshift({
        id: Date.now(),
        text: this.newTodo,
        completed: false,
      })
      this.newTodo = ''
      emitter.emit('show-notification', '新待办已添加！')
    },
    deleteTodo(id) {
      const todo = this.todos.find((t) => t.id === id)
      if (todo) {
        this.todos = this.todos.filter((t) => t.id !== id)
        emitter.emit('show-notification', `"${todo.text}" 已被删除`)
      }
    },
    toggleTodo(id) {
      const todo = this.todos.find((t) => t.id === id)
      if (todo) {
        todo.completed = !todo.completed
      }
    },
  },
})
