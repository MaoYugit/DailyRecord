<script setup>
import { ref, reactive, watch } from 'vue'
import courseImage from './assets/logo.svg'
import CourseInfo from './components/CourseInfo.vue'
import CourseActions from './components/CourseActions.vue'

// 使用 reactive 来组织课程相关数据
const course = reactive({
  name: 'Vue 3 大师课',
  price: 89.9,
  stock: 15,
  onSale: true,
  description: '这是一门帮助你<strong>从入门到精通</strong>Vue 3的课程。',
  image: courseImage,
  features: ['核心基础', 'Composition API', 'Vue Router', 'Pinia', '实战项目'],
})

// 购物车和用户相关数据
const cart = ref(0)

// 响应式状态
const message = ref('')
const checked = ref(true)
const hasAlerted = ref(false)
const hasStockAlerted = ref(false)

// 监听器
watch(cart, (newValue) => {
  if (newValue >= 5 && !hasAlerted.value) {
    alert('你已经购买了5件或更多商品，享受额外优惠！')
    hasAlerted.value = true
  }
})

watch(
  () => course.stock,
  (newStock) => {
    if (newStock > 0 && newStock < 5 && !hasStockAlerted.value) {
      alert('库存即将售罄，请尽快补货！')
      hasStockAlerted.value = true
    }
  },
)

// 方法
function handleAddToCart(quantity) {
  if (quantity > course.stock) {
    alert('库存不足，无法加入购物车！')
    return
  }
  cart.value += quantity
  course.stock -= quantity
}

function handleAddStock(quantity) {
  course.stock += quantity
}
</script>

<template>
  <div class="course-page">
    <div class="course-card">
      <div class="course-image">
        <img :src="course.image" :alt="course.name" />
        <span v-if="course.onSale" class="sale-badge">热卖中</span>
      </div>

      <div class="course-details">
        <CourseInfo :course="course" />

        <CourseActions
          :stock="course.stock"
          @add-to-cart="handleAddToCart"
          @add-to-stock="handleAddStock"
        />
        <p class="cart-info">当前购物车数量: {{ cart }}</p>
        <div class="extra-controls">
          <div class="form-group">
            <label for="message">留言:</label>
            <input type="text" id="message" v-model="message" placeholder="给卖家留言..." />
            <p v-if="message">你的留言: {{ message }}</p>
          </div>
          <div class="form-group">
            <input type="checkbox" id="checkbox" v-model="checked" />
            <label for="checkbox">同意服务条款 ({{ checked }})</label>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局和布局 */
.course-page {
  font-family:
    -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f4f7f6;
  padding: 2rem;
}

.course-card {
  display: flex;
  flex-direction: row;
  max-width: 900px;
  width: 100%;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

/* 课程图片 */
.course-image {
  position: relative;
  flex: 0 0 300px;
  background-color: #e9ecef;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.course-image img {
  max-width: 100%;
  height: auto;
  transition: transform 0.3s ease;
}

.course-image:hover img {
  transform: scale(1.05);
}

.sale-badge {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background-color: #ff4d4f;
  color: white;
  padding: 0.3rem 0.8rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: bold;
}

/* 课程详情 */
.course-details {
  flex: 1;
  padding: 2.5rem;
  color: #333;
}

.cart-info {
  font-size: 0.9rem;
  color: #718096;
}

/* 额外控件 */
.extra-controls {
  margin-top: 2rem;
  border-top: 1px solid #e2e8f0;
  padding-top: 1.5rem;
}

.form-group {
  margin-bottom: 1rem;
  gap: 0.5rem;
}

.form-group label {
  display: inline-block;
  margin-right: 0.5rem;
  color: #4a5568;
  font-size: 0.9rem;
}

.form-group input[type='text'] {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #cbd5e0;
  border-radius: 6px;
  font-size: 0.9rem;
}

.form-group input[type='checkbox'] {
  margin-right: 0.5rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .course-card {
    flex-direction: column;
  }
  .course-image {
    flex: 0 0 200px;
  }
  .course-details {
    padding: 1.5rem;
  }
  .course-details h1 {
    font-size: 1.8rem;
  }
  .current-price {
    font-size: 2rem;
  }
}
</style>
