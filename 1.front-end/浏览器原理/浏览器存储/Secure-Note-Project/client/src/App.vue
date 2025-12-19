<template>
  <div style="padding: 50px">
    <h1>Cookie 鉴权实战</h1>

    <div v-if="!isLoggedIn">
      <input v-model="username" placeholder="用户名 (admin)" />
      <input v-model="password" type="password" placeholder="密码 (123)" />
      <button @click="handleLogin">登录</button>
    </div>

    <div v-else>
      <h2>欢迎回来, {{ username }}</h2>
      <button @click="handleLogout">退出登录</button>
      <button @click="checkCookies">检查 Cookie (看控制台)</button>
      <NoteEditor />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import request from "./api/request";

import NoteEditor from "./components/NoteEditor.vue";

const username = ref("admin");
const password = ref("123");
const isLoggedIn = ref(false);

const handleLogin = async () => {
  try {
    const res = await request.post("/auth/login", {
      username: username.value,
      password: password.value,
    });

    if (res.success) {
      alert("登录成功！请打开 F12 -> Application -> Cookies 查看");
      isLoggedIn.value = true;
    }
  } catch (error) {
    alert("登录失败");
  }
};

const handleLogout = async () => {
  await request.post("/auth/logout");
  isLoggedIn.value = false;
  alert("Cookie 已清除");
};

const checkCookies = () => {
  console.log("--- JS 读取 Cookie 测试 ---");
  console.log("所有可读 Cookie:", document.cookie);

  if (document.cookie.includes("auth_token")) {
    console.error("❌ 危险！auth_token 竟然能被 JS 读到！HttpOnly 设置失败！");
  } else {
    console.log("✅ 安全！JS 读不到 auth_token (因为它是 HttpOnly 的)");
  }

  if (document.cookie.includes("user_group")) {
    console.log("✅ 正常：user_group 没设 HttpOnly，所以能读到");
  }
};
</script>
