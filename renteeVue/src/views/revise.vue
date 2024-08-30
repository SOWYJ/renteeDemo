<script setup lang="ts">

</script>

<template>
  <div>
    <div class="image-container">
      <img src="https://file.moyublog.com/d/file/2024-04-22/80de18bf8cc64332ce23e31e38a4a87b.jpg" alt="">
    </div>
    <div class="container">
      <div class="box">

        <!--登录界面-->
        <div v-if="isRegister">
          <div class="title">
            <img src="../static/cart.png" alt="" class="icon">
            <el-text class="mx-1">爷京租车行</el-text>
          </div>
          <el-form :model="form" label-width="auto" style="max-width: 600px">
            <el-form-item label="用户名">
              <el-input v-model="form.username" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item style="letter-spacing:0.20em;" label="密 码">
              <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="login" class="full-width">登录</el-button>
            </el-form-item>
            <el-button type="success" @click="changeForm" class="full-width">注册</el-button>
          </el-form>
        </div>

        <!--注册界面-->
        <div v-if="!isRegister">
          <div class="title">
            <img src="../static/enroll.png" alt="" class="icon">
            <el-text class="mx-1">注册新账号</el-text>
          </div>
          <el-form :model="registerForm" label-width="auto" style="max-width: 600px">
            <el-form-item label="用户名">
              <el-input v-model="registerForm.username" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item style="letter-spacing:0.20em;" label="密 码">
              <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" show-password/>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码"
                        show-password/>
            </el-form-item>
            <el-form-item class="btn-2">
              <el-button class="btn" type="success" @click="enroll">注册</el-button>
              <el-button class="btn" type="info" @click="changeForm">返回登录</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>