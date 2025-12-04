<script setup>
import { ref, onMounted } from "vue";
import { getComments, createComment } from "@/api/comment";
import { useUserStore } from "@/stores/user";
import gsap from "gsap";

const props = defineProps({
  articleId: {
    type: [String, Number],
    required: true,
  },
});

const userStore = useUserStore();
const comments = ref([]);
const newComment = ref("");
const replyTo = ref(null);
const replyContent = ref("");

const fetchComments = async () => {
  try {
    const res = await getComments({ articleId: props.articleId });

    if (Array.isArray(res)) {
      const map = {};
      const roots = [];
      res.forEach((c) => {
        c.children = [];
        map[c.id] = c;
      });
      res.forEach((c) => {
        if (c.parent_id && c.parent_id !== 0 && map[c.parent_id]) {
          map[c.parent_id].children.push(c);
        } else {
          roots.push(c);
        }
      });
      comments.value = roots;
    } else {
      comments.value = [];
    }
  } catch (error) {
    console.error("Failed to load comments:", error);
  }
};

const submitComment = async (parentId = 0) => {
  if (!userStore.isLoggedIn) {
    alert("Please login to comment");
    return;
  }

  const content = parentId === 0 ? newComment.value : replyContent.value;
  if (!content.trim()) return;

  try {
    await createComment({
      articleId: props.articleId,
      content: content,
      parentId: parentId,
    });

    // Clear inputs
    newComment.value = "";
    replyContent.value = "";
    replyTo.value = null;

    // Reload comments
    await fetchComments();
  } catch (error) {
    console.error("Failed to post comment:", error);
    alert("Failed to post comment");
  }
};

const setReply = (comment) => {
  if (replyTo.value === comment.id) {
    replyTo.value = null;
  } else {
    replyTo.value = comment.id;
  }
};

onMounted(() => {
  fetchComments();
});
</script>

<template>
  <div class="comment-section">
    <h3>Comments</h3>

    <div class="comment-form">
      <textarea
        v-model="newComment"
        placeholder="Leave a shard of thought..."
        :disabled="!userStore.isLoggedIn"
      ></textarea>
      <div class="form-actions">
        <button @click="submitComment(0)" :disabled="!userStore.isLoggedIn">
          Post Comment
        </button>
        <span v-if="!userStore.isLoggedIn" class="login-hint"
          >Login to comment</span
        >
      </div>
    </div>

    <div class="comments-list">
      <div v-for="comment in comments" :key="comment.id" class="comment-item">
        <div class="comment-avatar">
          <!-- Placeholder avatar -->
          <div class="avatar-placeholder">
            {{ comment.nickname ? comment.nickname[0].toUpperCase() : "U" }}
          </div>
        </div>
        <div class="comment-content">
          <div class="comment-header">
            <span class="nickname">{{ comment.nickname }}</span>
            <span class="date">{{
              new Date(comment.create_time).toLocaleDateString()
            }}</span>
          </div>
          <p>{{ comment.content }}</p>
          <button class="reply-btn" @click="setReply(comment)">Reply</button>

          <!-- Reply Form -->
          <div v-if="replyTo === comment.id" class="reply-form">
            <textarea
              v-model="replyContent"
              placeholder="Write a reply..."
            ></textarea>
            <button @click="submitComment(comment.id)">Submit Reply</button>
          </div>

          <!-- Children -->
          <div
            v-if="comment.children && comment.children.length > 0"
            class="replies"
          >
            <div
              v-for="child in comment.children"
              :key="child.id"
              class="comment-item child-comment"
            >
              <div class="comment-avatar small">
                <div class="avatar-placeholder">
                  {{ child.nickname ? child.nickname[0].toUpperCase() : "U" }}
                </div>
              </div>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="nickname">{{ child.nickname }}</span>
                  <span class="date">{{
                    new Date(child.create_time).toLocaleDateString()
                  }}</span>
                </div>
                <p>{{ child.content }}</p>
              </div>
            </div>
          </div>
        </div>
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

.comment-form textarea,
.reply-form textarea {
  width: 100%;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid var(--color-border);
  color: var(--color-text-main);
  padding: 1rem;
  border-radius: 8px;
  min-height: 100px;
  margin-bottom: 1rem;
}

.form-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

button {
  background: var(--color-accent-rational);
  color: #000;
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.login-hint {
  color: var(--color-text-sub);
  font-size: 0.9rem;
}

.comments-list {
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.comment-item {
  display: flex;
  gap: 1rem;
}

.avatar-placeholder {
  width: 40px;
  height: 40px;
  background: var(--color-accent-mystic);
  color: #000;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
}

.comment-content {
  flex: 1;
}

.comment-header {
  margin-bottom: 0.5rem;
}

.nickname {
  font-weight: bold;
  color: var(--color-accent-rational);
  margin-right: 1rem;
}

.date {
  color: var(--color-text-sub);
  font-size: 0.8rem;
}

.reply-btn {
  background: none;
  color: var(--color-text-sub);
  padding: 0;
  font-size: 0.8rem;
  margin-top: 0.5rem;
}

.reply-btn:hover {
  color: var(--color-text-main);
}

.replies {
  margin-top: 1rem;
  padding-left: 1rem;
  border-left: 2px solid var(--color-border);
}

.child-comment {
  margin-top: 1rem;
}

.avatar-placeholder.small {
  width: 30px;
  height: 30px;
  font-size: 0.8rem;
}
</style>
