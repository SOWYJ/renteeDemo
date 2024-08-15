<script setup>
import {ref} from "vue";

defineProps(['menu'])
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
  <li>
<!--    <router-link v-if="menu.menuUrl==null" @click.stop.prevent="expandSub" to="">-->
<!--      <i :class="menu.icon"></i><span>{{ menu.menuName }}</span>-->
<!--    </router-link>-->
    <!--    有子菜单，to为0，意思是可以展开-->
    <a href="javascript:void(0)" v-if="menu.menuUrl==null" @click="expandSub">
      <i :class="menu.icon"></i><span>{{ menu.menuName }}</span>
    </a>
    <!--    没有子菜单，有具体的url，to就是menu.menuUrl-->
    <router-link v-else-if="menu.menuUrl!=null" :to="menu.menuUrl">
      <i :class="menu.icon"></i><span>{{ menu.menuName }}</span>
    </router-link>

    <ul v-if="menu.childMenuList" :class="{sub,subHide}">
      <tree-menu v-for="(childMenu,index) in menu.childMenuList" :menu="childMenu" :key="index"></tree-menu>
    </ul>
  </li>
</template>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
