<script setup lang="ts">
//导入组件
import MyHead from "@/components/Head.vue";
import SideBar from "@/components/SideBar.vue";
import Main from "@/components/Main.vue";
</script>

<template>
<!--  使用组件-->
  <div class="index">
<!--    <my-head></my-head>-->
    <MyHead></MyHead>
    <side-bar></side-bar>
    <RouterView></RouterView>
  </div>

</template>

<style>
 *{
   margin: 0;
   padding: 0;
 }
 .index{
   height: 1000px;
 }
</style>