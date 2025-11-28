<script setup>
import { ref, onMounted } from "vue";
import { getComments, createComment } from "@/api";
import { useUserStore } from "@/stores/user";

const props = defineProps({
  articleId: {
    type: [String, Number],
    required: true,
  },
});

const comments = ref([]);
const newCommentContent = ref("");
const userStore = useUserStore();
const loading = ref(false);

const loadComments = async () => {
  try {
    const res = await getComments(props.articleId);
    comments.value = res || [];
  } catch (error) {
    console.error("Failed to load comments:", error);
  }
};

const submitComment = async () => {
  if (!newCommentContent.value.trim()) return;

  try {
    // 获取昵称逻辑：优先取昵称 -> 其次取用户名 -> 最后默认"时空旅人"
    const nickname =
      userStore.user?.nickname || userStore.user?.username || "Time Traveler";

    await createComment({
      articleId: props.articleId,
      content: newCommentContent.value,
      userId: userStore.user?.id,
      nickname: nickname, // <--- 【添加这一行】
    });

    newCommentContent.value = "";
    await loadComments();
  } catch (error) {
    console.error("Failed to post comment:", error);
    alert("Failed to post comment: " + error.message);
  }
};

onMounted(() => {
  loadComments();
});
</script>

<template>
  <div class="comment-section">
    <h3>Comments</h3>

    <div v-if="userStore.isAuthenticated" class="comment-form">
      <textarea
        v-model="newCommentContent"
        placeholder="Leave a trace..."
        rows="3"
      ></textarea>
      <button @click="submitComment">Transmit</button>
    </div>
    <div v-else class="login-prompt">
      <router-link to="/login">Login</router-link> to comment.
    </div>

    <div class="comments-list">
      <div v-for="comment in comments" :key="comment.id" class="comment-item">
        <div class="comment-header">
          <span class="author">{{ comment.username || "Anonymous" }}</span>
          <span class="date">{{
            new Date(comment.createTime).toLocaleString()
          }}</span>
        </div>
        <div class="comment-content">
          {{ comment.content }}
        </div>
      </div>
      <div v-if="comments.length === 0" class="no-comments">
        No signals detected.
      </div>
    </div>
  </div>
</template>

<style scoped>
.comment-section {
  margin-top: 3rem;
  border-top: 1px solid var(--color-border);
  padding-top: 2rem;
}

.comment-form {
  margin-bottom: 2rem;
}

textarea {
  width: 100%;
  padding: 1rem;
  background: var(--color-bg-secondary);
  border: 1px solid var(--color-border);
  color: var(--color-text-main);
  border-radius: 4px;
  margin-bottom: 0.5rem;
  resize: vertical;
}

button {
  background: var(--color-accent-rational);
  color: #fff;
  border: none;
  padding: 0.5rem 1.5rem;
  cursor: pointer;
  border-radius: 4px;
  font-family: var(--font-mono);
}

.comment-item {
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid var(--color-border-subtle);
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: var(--color-text-sub);
}

.author {
  font-weight: bold;
  color: var(--color-accent-mystic);
}

.no-comments {
  color: var(--color-text-sub);
  font-style: italic;
}
</style>
