import {createRouter, createWebHistory} from 'vue-router'
// // import HomeView from '../views/HomeView.vue'
// import User from "@/views/User.vue";
// import Menu from "@/views/Menu.vue";
// import PasswordChange from "@/views/PasswordChange.vue";
// import App from "@/App.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  //路由表
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('../components/Index.vue'),
      redirect: '/main',
      children: [
        {
          path: '/main',
          name: 'main',
          component: () => import('../components/Main.vue')
        }
      ]
    }
  ]
})
export default router