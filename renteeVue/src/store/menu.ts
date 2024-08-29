import {defineStore} from "pinia";
import {computed, ref} from "vue";
import axios from "axios";


export const menuStore = defineStore('menu', () => {
    // 定义状态state
    const menuData = ref()

    // 定义操作：action（就是给menuData赋值），即支持同步，也支持异步

    const setMenu = (data: any) => {
        menuData.value = data
    }
    // 定义getter，数据可能需要加工，所以才有了getter
    const getMenuData = computed(() => {
        return menuData.value;
    })

    return {menuData, setMenu, getMenuData}
}, {
    persist: {
        enabled: true
    }
});
export const useCarouselStore = defineStore('carouselStore', () => {
    const activeIndex = ref(0); // 初始化 activeIndex

    const setActiveIndex = (index) => {
        activeIndex.value = index;
    };

    return {
        activeIndex,
        setActiveIndex,
    };


}, {
    persist: {
        enabled: true
    }
});

export const couponStore = defineStore('coupon', () => {
    // 定义状态state
    const menuData = ref()

    // 定义操作：action（就是给menuData赋值），即支持同步，也支持异步

    const setMenu = (data: any) => {
        menuData.value = data
    }
    // 定义getter，数据可能需要加工，所以才有了getter
    const getMenuData = computed(() => {
        return menuData.value;
    })

    return {menuData, setMenu, getMenuData}
}, {
    persist: {
        enabled: true
    }
});
