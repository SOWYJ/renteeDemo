import {defineStore} from "pinia";
import {computed, ref} from "vue";
import axios from "axios";

export const menuStore=defineStore('menu',()=>{
    //定义状态
    const menuData=ref();

    //定义操作：action(就是给menuData赋值）,即支持同步，也支持异步
    const setMenu= (data:Object)=>{
        menuData.value=data;
    }

    //定义getter,数据可能需要加工，所以才有了getter
    const getMenuData=computed(()=>{
        return menuData.value;
    })

    return {menuData,setMenu,getMenuData}



},{
    persist:{
        enabled: true
    }
})
