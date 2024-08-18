<script setup lang="ts">
  import {ref} from "vue";
  import axios from "axios";
  import router from "@/router";
  import {menuStore} from "@/store/menu";

  const store=menuStore();

  const form = ref({
    username: 'admin',
    password: '123456'
  })
  const login=()=>{
    /**
     * 把用户名和密码发送给后端
     * 后端再去验证
     */
    axios.post('http://localhost:8080/getAllMenu',{
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).then(res=>{
      console.log("登陆结果",res);
      // if (res.data.code == 200){
        //把菜单数据存入store
        const menuData = res.data.slice(0, 4);  // 只存储0-3项
        store.setMenu(menuData);
        // 把username放进sessionStorage
        sessionStorage.setItem("name","dada");
        //页面跳转
      router.push('/main');
      // }
    })
  }
</script>

<template>
  <el-form :model="form" label-width="auto" style="max-width: 600px">
    <el-form-item label="用户名">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login">登录</el-button>
    </el-form-item>
  </el-form>

</template>

<style scoped>

</style>