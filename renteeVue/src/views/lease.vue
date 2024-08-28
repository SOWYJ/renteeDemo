<script setup lang="ts">
import {ElTable, Table} from 'element-plus'
import axios from "axios";
import {onMounted, ref} from "vue";



const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
onMounted(()=>{
  console.log("页面加载")

  axios.get("http://localhost:8082/getAlllease")
      .then((res)=>{
        console.log("后端返回的数据：",res);

        userList.value=res.data;

      })
})


const local =()=>{
  axios.post('http://localhost:8082/getAlllease', {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  }).then(res=>{
    console.log("登陆", res.data);
    userList.value=res.data;
  })
}
const userList=ref();
const handleEdit=(index, row)=>{
  dialogTableVisible.value = true
  Table.value=row;
}
const form = ref({
  renter:'',
  licenseId:'',
  rentaltime:''
})
const Table = ref({
  userId:'',
  username:'',
  userPhone:'',
  userAddress:''
})




</script>

<template>
  <el-button type="primary" @click="dialogFormVisible=true">新增</el-button>
  <el-table :data="userList" style="width: 100%">
    <el-table-column prop="userId" label="租赁人" width="180" />
    <el-table-column prop="username" label="车牌号" width="180" />
    <el-table-column prop="username" label="借车时间" width="180" />
    <el-table-column prop="userPhone" label="还车时间" width="180" />
    <el-table-column prop="userPhone" label="时间差" width="180" />
    <el-table-column prop="userAddress" label="需付" />
    <el-table-column label="编辑">
      <template #default="scope">
        <el-button  size="small" @click="handleEdit(scope.$index, scope.row)">
          Edit
        </el-button>
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
        >
          Delete
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogFormVisible" title="新增用户" width="500">
    <el-form :model="form">
      <el-form-item label="租车人" label-width="140px">
        <el-input v-model="form.renter" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="车牌号" label-width="140px">
        <el-input v-model="form.licenseId" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="借车时间" label-width="140px">
        <el-input v-model="form.rentaltime" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <!-- 先保存、再关闭对话框 -->
        <el-button type="primary" @click="saveUser">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>


</template>

<style scoped>

</style>