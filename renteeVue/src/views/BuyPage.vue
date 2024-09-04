<script setup lang="ts">
import {numStore} from "@/store/menu";
import {onMounted} from "vue";

const store3 = numStore();


onMounted(()=>{
  const num = store3.getNum;
  console.log("数字:", num);
  let con = document.getElementById("con")
  let su = document.createTextNode("恭喜你抢购成功！");
  let error = document.createTextNode("抢购完了，下次再来吧！！");
  if (num > 0){
    console.log("AAAAAAAAA");
    con.appendChild(su);
  }else{
    console.log("BBBBBBBBBBB");
    con.appendChild(error);
  }
})

</script>

<template>
  <div class="success">
    <span id="con"></span>
  </div>

</template>

<style scoped>

</style>