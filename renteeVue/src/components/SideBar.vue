<script setup lang="ts">
import {onMounted, ref} from "vue";
import {menuStore} from "@/store/menu";
import TreeMenu from "@/components/TreeMenu.vue";

  const store=menuStore();
  const menuData = ref();
  onMounted(()=>{
    console.log("菜单数据，菜单数据：",store.getMenuData);
    menuData.value=store.getMenuData;
  })

  const sub=ref(true);
  const subHide=ref(true);
  const subShow=ref(false);
  const expandSub=()=> {
    console.log("展开子菜单");
    if (subHide.value == true) {
      subHide.value = false;
      subShow.value = true;
    } else {
      subHide.value = true;
      subShow.value = false;
    }
  }


</script>

<template>
<div class="sidebar">
  <ul id="parentMenu" class="parent" v-for="(menu,index) in menuData" :key="index">
    <tree-menu :menu="menu"></tree-menu>
  </ul>
</div>
</template>

<style>
.sidebar{
  width: 15%;
  height: 100%;
  background-color: snow;
  float: left;
}
li{
  list-style: none;
  position: relative;
  padding-left: 10px;
  width: 189px;
  border: 1px solid #e5e5e5;
  border-style: none none solid none;/* 如果某一边没设置，则会使用对面的样式 */
}
.sub{
  position: relative;
  padding-left: 7px;
}
.subHide{
  display: none;
}
.subShow{
  display: block;
}
li > a{
  text-decoration: none;
  color: #585858;
  display: block;
  line-height: 40px;
}
.sub li:last-child{
  border: none;
}
span{
  display: inline-block;
  margin-left: 10px;
}
.icon{
  position: relative;
  top: 2px;
}
.sub > li:before {
  content: "";
  display: block;
  width: 7px;
  position: absolute;
  z-index: 1;
  left: 0px;
  top: 17px;
  border: 1px dotted;
  border-width: 1px 0 0;
}
.sub:before {
  content: "";
  display: block;
  position: absolute;
  z-index: 1;
  left: 5px;
  top: 0;
  bottom: 0;
  border: 1px dotted;
  border-width: 0 0 0 1px;
}
</style>