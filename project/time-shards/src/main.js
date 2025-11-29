import { createApp } from "vue";
import { createPinia } from "pinia";
import Particles from "vue3-particles";
import App from "./App.vue";
import router from "./router";
import "./style.css";
import i18n from "./locales";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(Particles);
app.use(i18n);

app.mount("#app");
