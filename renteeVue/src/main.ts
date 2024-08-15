import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'

import piniaPersist from 'pinia-plugin-persist'

import 'font-awesome/css/font-awesome.min.css'

const app = createApp(App)
const pinia = createPinia();
pinia.use(piniaPersist)

app.use(router)
app.use(ElementPlus)
app.use(pinia)

app.mount('#app')
