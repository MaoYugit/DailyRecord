<script setup>
import { computed } from 'vue'

const props = defineProps({
  course: {
    type: Object,
    required: true,
  },
})

// 计算属性，用于动态显示内容
const stockStatusText = computed(() => {
  if (props.course.stock > 10) {
    return `库存充足 (${props.course.stock})`
  } else if (props.course.stock > 0) {
    return `库存紧张 (${props.course.stock})`
  } else {
    return '已售罄'
  }
})

const discountedPrice = computed(() => (props.course.price * 0.8).toFixed(2))

const stockStatusClass = computed(() => {
  if (props.course.stock > 10) {
    return 'sufficient'
  } else if (props.course.stock > 0) {
    return 'tight'
  } else {
    return 'sold-out'
  }
})
</script>
<template>
  <div>
    <h1>{{ course.name }}</h1>
    <p class="description" v-html="course.description"></p>

    <div class="price-section">
      <span class="current-price">¥{{ discountedPrice }} (8折)</span>
      <span v-if="course.onSale" class="original-price">¥{{ course.price }}</span>
    </div>

    <div class="features">
      <h3>课程特色</h3>
      <ul>
        <li v-for="feature in course.features" :key="feature">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-check-circle-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"
            />
          </svg>
          {{ feature }}
        </li>
      </ul>
    </div>

    <div class="stock-status" :class="stockStatusClass">
      {{ stockStatusText }}
    </div>
  </div>
</template>

<style scoped>
/* 课程详情 */

.course-details h1 {
  font-size: 2.2rem;
  font-weight: 700;
  margin-top: 0;
  margin-bottom: 0.5rem;
  color: #1a202c;
}

.description {
  font-size: 1rem;
  color: #555;
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

/* 价格 */
.price-section {
  display: flex;
  align-items: baseline;
  margin-bottom: 1.5rem;
}

.current-price {
  font-size: 2.5rem;
  font-weight: 700;
  color: #e53e3e;
}

.original-price {
  font-size: 1.2rem;
  color: #718096;
  margin-left: 0.8rem;
  text-decoration: line-through;
}

/* 课程特色 */
.features {
  margin-bottom: 1.5rem;
}

.features h3 {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 0.8rem;
  color: #4a5568;
}

.features ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.features li {
  display: flex;
  align-items: center;
  font-size: 1rem;
  margin-bottom: 0.5rem;
  color: #4a5568;
}

.features li svg {
  color: #48bb78;
  margin-right: 0.5rem;
  flex-shrink: 0;
}

/* 库存状态 */
.stock-status {
  font-size: 0.9rem;
  font-weight: 500;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  display: inline-block;
  margin-bottom: 1.5rem;
}

.stock-status.sufficient {
  background-color: #c6f6d5;
  color: #2f855a;
}
.stock-status.tight {
  background-color: #feebc8;
  color: #975a16;
}
.stock-status.sold-out {
  background-color: #fed7d7;
  color: #9b2c2c;
}
</style>
