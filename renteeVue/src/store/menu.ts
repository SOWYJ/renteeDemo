import {defineStore} from "pinia";
import {computed, ref} from "vue";


export const menuStore = defineStore('menu', () => {
    // 定义状态state
    const menuData = ref()

    const detailData = ref({
        id: '',
        carName: '',
        carType: '',
        brand: '',
        color:'',
        seats:'',
        hourPrice: '',
        dropLocation: '',
        dropDate: '',
        carStatus: ''
    }); // 新增的状态
    // 定义操作：action（就是给menuData赋值），即支持同步，也支持异步

    const setMenu = (data: any) => {
        menuData.value = data
    }

    const setDetailData = (data: Record<string, any>) => {
        detailData.value = data;
    };

    // 定义getter，数据可能需要加工，所以才有了getter
    const getMenuData = computed(() => {
        return menuData.value;
    })

    const getDetailData = computed(() => detailData.value);
    return { menuData, detailData, setMenu, setDetailData, getMenuData, getDetailData };
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

    const getActiveIndex = computed(() => {
        return activeIndex.value;
    });

    return {
        activeIndex,
        setActiveIndex,
        getActiveIndex
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
    });

    return {menuData, setMenu, getMenuData}
}, {
    persist: {
        enabled: true
    }
});

export const numStore = defineStore('num', () => {
    // 定义状态state
    const num = ref()

    // 定义操作：action（就是给menuData赋值），即支持同步，也支持异步

    const setNum = (data: any) => {
        num.value = data
    }
    // 定义getter，数据可能需要加工，所以才有了getter
    const getNum = computed(() => {
        return num.value;
    })

    return {num, setNum, getNum}
}, {
    persist: {
        enabled: true
    }
});
