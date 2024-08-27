import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'

import piniaPersist from 'pinia-plugin-persist'

import 'font-awesome/css/font-awesome.min.css'

import api from "./pagination/request/api.js";
import http from "./pagination/request/index.js";
import '@/pagination/css/index.css'

const app = createApp(App)
const pinia = createPinia();
pinia.use(piniaPersist)

app.use(router)
app.use(ElementPlus)
app.use(pinia)

// app.mount('#app')




app.config.globalProperties.$api = api;
app.config.globalProperties.$http = http;
app.use(ElementPlus).mount('#app')
