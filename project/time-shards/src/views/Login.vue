<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
import gsap from 'gsap';

const userStore = useUserStore();
const router = useRouter();

const username = ref('');
const password = ref('');
const error = ref('');

const handleLogin = async () => {
  try {
    await userStore.login({ username: username.value, password: password.value });
    router.push('/');
  } catch (err) {
    error.value = err.message || 'Login failed';
    gsap.fromTo('.error-msg', { x: -10 }, { x: 10, duration: 0.1, repeat: 5, yoyo: true });
  }
};

onMounted(() => {
  gsap.from('.auth-card', {
    y: 50,
    opacity: 0,
    duration: 0.8,
    ease: 'back.out(1.7)'
  });
});
</script>

<template>
  <div class="auth-container">
    <div class="auth-card">
      <h2>Access Terminal</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>Identity</label>
          <input v-model="username" type="text" required />
        </div>
        <div class="form-group">
          <label>Passcode</label>
          <input v-model="password" type="password" required />
        </div>
        <div v-if="error" class="error-msg">{{ error }}</div>
        <button type="submit" class="auth-btn">Connect</button>
      </form>
      <div class="auth-footer">
        <router-link to="/register">Initialize New Identity</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}

.auth-card {
  background: rgba(10, 10, 15, 0.8);
  backdrop-filter: blur(10px);
  padding: 3rem;
  border-radius: 12px;
  border: 1px solid var(--color-border);
  width: 100%;
  max-width: 400px;
  box-shadow: 0 0 20px rgba(0, 243, 255, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: var(--color-accent-mystic);
  text-transform: uppercase;
  letter-spacing: 2px;
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--color-text-sub);
  font-family: var(--font-mono);
  font-size: 0.9rem;
}

input {
  width: 100%;
  padding: 0.8rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid var(--color-border);
  color: var(--color-text-main);
  border-radius: 4px;
  transition: border-color 0.3s;
}

input:focus {
  outline: none;
  border-color: var(--color-accent-rational);
  box-shadow: 0 0 10px rgba(0, 243, 255, 0.2);
}

.auth-btn {
  width: 100%;
  padding: 1rem;
  background: var(--color-accent-rational);
  color: #000;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-transform: uppercase;
  letter-spacing: 1px;
  transition: transform 0.2s, box-shadow 0.2s;
}

.auth-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 0 15px var(--color-accent-rational);
}

.error-msg {
  color: #ff4d4d;
  margin-bottom: 1rem;
  text-align: center;
  font-size: 0.9rem;
}

.auth-footer {
  margin-top: 1.5rem;
  text-align: center;
  font-size: 0.9rem;
}

.auth-footer a {
  color: var(--color-text-sub);
  text-decoration: none;
  border-bottom: 1px dashed var(--color-text-sub);
}

.auth-footer a:hover {
  color: var(--color-accent-mystic);
  border-color: var(--color-accent-mystic);
}
</style>
