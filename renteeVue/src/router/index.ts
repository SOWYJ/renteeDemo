import {createRouter, createWebHistory} from 'vue-router'
// // import HomeView from '../views/HomeView.vue'
// import User from "@/views/User.vue";
// import Menu from "@/views/Menu.vue";
// import PasswordChange from "@/views/PasswordChange.vue";
// import App from "@/App.vue";
import vehicle from "../views/vehicle.vue";

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
                    component: () => import('../components/Main.vue'),
                    children:[
                        {
                            path: '/main/vehicle',
                            name: 'vehicle',
                            // component: vehicle
                            component: () => import('../views/vehicle.vue')
                        },
                        {
                            path: '/main/userService',
                            name: 'userService',
                            // component: vehicle
                            component: () => import('../views/userService.vue')
                        },
                        {
                            path: '/main/activity',
                            name: 'activity',
                            component: () => import('../views/Activity.vue')
                        },
                        {
                            path: '/main/lease',
                            name:'lease',
                            component:()=>import('../views/lease.vue')
                        }

                    ]
                }
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/Login.vue')
        }
    ]
})

router.beforeEach((to, from) => {
    if (to.path == '/main') {
        // 开始写代码判断是否已经登录，如果登录了，就放行，否则，就跳转到登录页面
        let name = sessionStorage.getItem("name");
        if (name != null) {
            // 已经登录过，直接导航到main
            return '/main/userService';
        } else {
            // 还未登录，直接导航到login页面
            router.push("/login");
        }
    }
})
export default router