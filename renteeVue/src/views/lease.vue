<script setup lang="ts">
import {ElTable, Table} from 'element-plus'
import axios from "axios";
import {onMounted, ref} from "vue";
import {ElMessage,ElMessageBox} from "element-plus";
import moment from 'moment';

//转日期格式
function formatDateTime(dateStr) {
  const date = new Date(dateStr);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const seconds = date.getSeconds().toString().padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

//以小时为单位计算时间差
function calculateTimeDifference(rentalTime, returnTime) {
  const rental = new Date(rentalTime);
  const returnDate = new Date(returnTime);
  const differenceInMilliseconds = Math.abs(returnDate - rental);
  const differenceInHours = differenceInMilliseconds / (1000 * 60 * 60);
  return parseFloat(differenceInHours.toFixed(1));
}

//计算收费
function calculatePay(rentalTime, returnTime) {
  const rental = new Date(rentalTime);
  const returnDate = new Date(returnTime);
  const differenceInMilliseconds = Math.abs(returnDate - rental);
  const differenceInHours = differenceInMilliseconds / (1000 * 60 * 60);
  let totalPay = 0;

  if (differenceInHours <= 24) {
    totalPay = differenceInHours * 15;
  } else {
    const first24Hours = 24 * 15; // 第一个24小时的费用
    const remainingHours = differenceInHours - 24; // 超过24小时之后的时间
    const additionalPay = remainingHours * 25; // 超过24小时之后的费用
    totalPay = first24Hours + additionalPay;
  }
  return parseFloat(totalPay.toFixed(1));
}



const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
onMounted(()=>{
  console.log("页面加载")

  query();
})
const query = () => {
  axios.get("http://localhost:8083/getAlllease")
      .then((res) => {
        console.log("后端返回的数据：", res);
        userList.value = res.data;
      })
}

const userList=ref();
const handleEdit=(index, row)=>{
  dialogTableVisible.value = true
  Table.value=row;
}


const saveLease=()=> {
  axios.post('http://localhost:8083/saveLease', form.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

const deleteLease=(index,row)=>{
  console.log("索引",index)
  console.log("当前行数据",row)

  ElMessageBox.confirm(`确认要删除吗!`, "确认信息", {
    type: "warning",
    distinguishCancelAndClose: true,
    confirmButtonText: "删除",
    cancelButtonText: "取消",
  }).then(()=>{
    axios.post("http://localhost:8083/deleteLease",{
      params:{
        licenseId:row.licenseId
      }
    }).then(res=>{
      ElMessage({type: "success", message: "删除成功！", duration: 4000});
      query();
    })
  })
  }







const form = ref({
  renter:'',
  licenseId:'',
  rentalTime:'',
  returnTime:''
})

const Table = ref({
  renter:'',
  licenseId:'',
  rentalTime:'',
  returnTime:'',
  difference:'',
  pay:''
})




</script>

<template>
  <el-button type="primary" @click="dialogFormVisible=true">新增</el-button>
  <el-table :data="userList" style="width: 100%">
    <el-table-column prop="renter" label="租赁人" width="180" />
    <el-table-column prop="licenseId" label="车牌号" width="180" />
    <el-table-column prop="rentalTime" label="借车时间" width="180">
      <template #default="scope">
        {{ formatDateTime(scope.row.rentalTime) }}
      </template>
    </el-table-column>
    <el-table-column prop="returnTime" label="还车时间" width="180" >
      <template #default="scope">
        {{ formatDateTime(scope.row.returnTime) }}
      </template>
    </el-table-column>
    <el-table-column prop="difference" label="时间差" width="180" >
      <template #default="scope">
        {{ calculateTimeDifference(scope.row.rentalTime, scope.row.returnTime) }} 小时
      </template>
    </el-table-column>
    <el-table-column prop="pay" label="需付" >
      <template #default="scope">
        {{ calculatePay(scope.row.rentalTime, scope.row.returnTime) }} 元
      </template>
    </el-table-column>
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
        <el-date-picker
            v-model="form.rentalTime"
            type="datetime"
            placeholder="Select date and time"
        />
      </el-form-item>
      <el-form-item label="还车时间" label-width="140px">
        <el-date-picker
            v-model="form.returnTime"
            type="datetime"
            placeholder="Select date and time"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <!-- 先保存、再关闭对话框 -->
        <el-button type="primary" @click="saveLease">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>


</template>

<style scoped>

</style>