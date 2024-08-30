<script setup lang="ts">
import {UserFilled, ArrowDown} from '@element-plus/icons-vue'
import {computed, onMounted, ref} from "vue";
import router from "@/router";
import {ElMessage, ElMessageBox} from "element-plus";

const username = ref<string | null>(null);


onMounted(() => {
  username.value = sessionStorage.getItem('name');
});

const displayText = computed(() => {
  return username.value ? username.value : '请登录';
});


// 计算属性，判断下拉框是否应禁用
const isDropdownDisabled = computed(() => {
  return !username.value || username.value.trim() === ''
})

const loginOut=()=>{
  ElMessageBox.confirm(
      '确定退出登录?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        localStorage.setItem("token", "");
        sessionStorage.setItem('name',"");
        ElMessage({
          type: 'success',
          message: '退出成功',
        })
        setTimeout(() => {
          window.location.reload();
        }, 1000);
      })

}

const login=()=>{
  router.push("/login");
}

</script>

<template>
  <div class="header">
    <div class="left-content">
      <img src="../static/wcart.png" alt="" class="icon">
      <el-text class="text">爷京租车行</el-text>
    </div>
    <div class="keep-right">
      <el-avatar :icon="UserFilled"/>
      <span></span>
      <el-dropdown :disabled="isDropdownDisabled">
        <span class="el-dropdown-link">
          <el-text class="txt" @click="login">{{ displayText }} </el-text>
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="">修改用户名</el-dropdown-item>
            <el-dropdown-item @click="">修改密码</el-dropdown-item>
            <el-dropdown-item @click="loginOut">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  width: 100%;
  height: 65px;
  background-color: #346dff;
}

.left-content {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
}

.icon {
  height: 100%;
  width: 51px;
  margin-left: 10px;
  color: white;
}

.text {
  margin-left: 10px; /* 文本与图片之间的间距 */
  font-size: 30px;
  font-weight: bold;
  color: white;
}

.keep-right {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  margin-left: auto; /* 将 .keep-right 推到容器的右边 */
  margin-right: 200px; /* 留出右侧间距，可以根据需要调整 */
}

.txt {
  font-size: 30px;
  color: white;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>