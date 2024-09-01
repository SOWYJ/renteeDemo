<script setup lang="ts">
import {getCurrentInstance, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router";

const global = getCurrentInstance().appContext.config.globalProperties;

const registerForm = ref({
  userName: '',
  password: '',
  userId: ''
});


const verify =()=>{
  if (!registerForm.value.userName) {
    ElMessage.error('用户名不能为空！');
    return;
  }
  if (registerForm.value.password == registerForm.value.userId) {
    ElMessage.error('新密码不能与旧密码相同')
    return;
  }
  global.$api.verify(registerForm.value).then(res=>{+
      console.log(res)
      if (res.data.code ==500){
        ElMessage.error(res.data.msg);
      }
      if (res.data.code ==200){
        ElMessage.success(res.data.data);
        localStorage.setItem("token", "");
        sessionStorage.setItem('name',"");
        setTimeout(() => {
          router.push("/login");
        }, 1000);
      }
  })
}


</script>

<template>
  <div>
    <div class="image-container">
      <img src="https://file.moyublog.com/d/file/2024-04-22/80de18bf8cc64332ce23e31e38a4a87b.jpg" alt="">
    </div>
    <div class="container">
      <div class="box">
        <div>
          <div class="title">
            <img src="../static/enroll.png" alt="" class="icon">
            <el-text class="mx-1">修改密码</el-text>
          </div>
          <el-form :model="registerForm" label-width="auto" style="max-width: 600px">
            <el-form-item label="用户名">
              <el-input v-model="registerForm.userName" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item style="letter-spacing:0.20em;" label="密 码">
              <el-input v-model="registerForm.password" type="password" placeholder="请输入当前密码" show-password/>
            </el-form-item>
            <el-form-item style="letter-spacing:0.20em;" label="密 码">
              <el-input v-model="registerForm.userId" type="password" placeholder="请输入新密码" show-password/>
            </el-form-item>
            <el-form-item class="btn-2">
              <el-button class="btn" type="success" @click="verify">修改</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.image-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1; /* 确保图片在背景层 */
}

.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保图片覆盖整个容器，并保持比例 */
}

.title {
  display: flex; /* 使用弹性盒子布局 */
  align-items: center; /* 使图标和文本垂直居中对齐 */
  margin-bottom: 20px;
  border-bottom: 1px solid #000; /* 添加2px厚的黑色底部边框 */
  padding-bottom: 10px;
}

.icon {
  height: 30px; /* 设置图标的高度，使其与文本的高度一致 */
  margin-right: 10px; /* 在图标和文本之间添加一些间距 */
}

.mx-1 {
  display: flex; /* 使用弹性盒子布局 */
  justify-content: center;
  font-size: 30px;
  font-weight: bold;
  letter-spacing: 0.60em;
  color: black;
}

.container {
  position: relative;
  z-index: 2;
  display: flex; /* 使用弹性盒子布局 */
  justify-content: center; /* 水平方向居中 */
  align-items: center; /* 垂直方向居中 */
  height: 100vh; /* 设置容器高度为视口高度，确保内容垂直居中 */
}

.box {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  /* 其他样式 */
}


.btn-2 {
  display: flex;
  justify-content: space-between; /* 或者使用 space-around */
}

.btn {
  flex: 1; /* 使按钮在容器中占据相同的空间 */
  margin: 0 5px; /* 给按钮之间增加一些间距 */
}
</style>