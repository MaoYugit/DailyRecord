import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// 导入 highlight.js 的样式主题
import 'highlight.js/styles/atom-one-dark.css'

// 导入全局样式
import './assets/main.css'

const app = createApp(App)

app.use(router)
app.mount('#app')
