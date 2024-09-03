<script setup lang="ts">
import {ElTable, Table} from 'element-plus'
import axios from "axios";
import {onMounted, ref} from "vue";
import {ElMessage,ElMessageBox} from "element-plus";
import moment from 'moment';
import type { TabsPaneContext } from 'element-plus'
import {menuStore} from "@/store/menu";
const store = menuStore();

const activeName = ref('first')

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}

const centerDialogVisible = ref(false)


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




const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
onMounted(()=>{
  console.log("页面加载")

  query();
})
const query = () => {
  axios.get("http://localhost:8084/getAlllease")
      .then((res) => {
        console.log("后端返回的数据：", res);
        userList.value = res.data;
      })
}

const userList=ref();



const saveLease=()=> {
  axios.post('http://localhost:8084/saveLease', form.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(res=>{
    ElMessage({type: "success", message: "保存成功！", duration: 4000});
    query();
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
    axios.get("http://localhost:8084/deleteLease",{
      params:{
        id:row.id
      }
    }).then(res=>{
      ElMessage({type: "success", message: "删除成功！", duration: 4000});
      query();
    })
  })
  }
const updateLease=()=>{
  axios.post('http://localhost:8084/updateLease', form.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(res=>{
    ElMessage({type: "success", message: "保存成功！", duration: 4000});
    query();
  })
}

const state=()=>{

  axios.post('http://localhost:8084/state', form.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(res=>{
    ElMessage({type: "success", message: "出租成功！", duration: 4000});
    query();
  })

}




const form = ref({
  id:'',
  carName:'',
  carType:'',
  brand:'',
  seats:'',
  hourPrice:'',
  rentalTime:'',
  returnTime:'',
  sta:''
})

const Table = ref({
  renter:'',
  licenseId:'',
  rentalTime:'',
  returnTime:'',
  difference:'',
  pay:''
})

const editLease = (row) => {
  form.value = { ...row };
  dialogTableVisible.value = true;
};

const addLease = () => {
  // 清空表单用于新增
  form.value = {
    id: '',
    carName: '',
    carType: '',
    brand: '',
    seats: '',
    hourPrice: '',
    rentalTime: '',
    returnTime: '',
    sta:''
  };
  dialogFormVisible.value = true;
};

const calculateTotalHours = (rentalTime, returnTime) => {
  const rentalMoment = moment(rentalTime);
  const returnMoment = moment(returnTime);
  const diffInMinutes = returnMoment.diff(rentalMoment, 'minutes');
  const diffInHours = diffInMinutes / 60;
  return Math.round(diffInHours * 10) / 10; // 精度为0.1小时
};


// 计算总费用
// const calculateTotalCharge = (totalHours, hourPrice) => {
//   if (!totalHours || !hourPrice) return 0;
//
//   const normalCharge = 72 * hourPrice; // 三天内正常收费
//   const discountedCharge = (totalHours - 72) * hourPrice * 0.75; // 三天后的优惠收费
//
//   return totalHours <= 72 ? totalHours * hourPrice : normalCharge + discountedCharge;
// };
const calculateTotalCharge = (totalHours, hourPrice) => {
  // 如果 totalHours 或 hourPrice 为空或者 totalHours 是负数，返回0
  if (!totalHours || !hourPrice || totalHours < 0) return 0;

  const normalCharge = 72 * hourPrice; // 三天内正常收费
  const discountedCharge = (totalHours - 72) * hourPrice * 0.75; // 三天后的优惠收费

  return totalHours <= 72 ? totalHours * hourPrice : normalCharge + discountedCharge;
};

</script>

<template>
  <el-button type="primary" @click="addLease">新增</el-button>
  <el-table :data="userList" style="width: 100%">
    <el-table-column prop="id" label="序号" width="130" />
    <el-table-column prop="carName" label="汽车名称" width="130" />
    <el-table-column prop="brand" label="商标" width="130" />
    <el-table-column prop="seats" label="座位" width="130" />
    <el-table-column prop="hourPrice" label="每小时收费" width="130" />
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
    <el-table-column prop="sta" label="出租状态" width="130" />
    <el-table-column label="租车" width="200"  align="center">

      <template #default="scope">
        <el-button
            size="small"
            type="primary"
            @click="dialogTableVisible = true; editLease(scope.row)"
        >
          租车
        </el-button>
<!--        <el-button  size="small"  @click="dialogTableVisible=true">-->
<!--          Edit-->
<!--        </el-button>-->
        <el-button
            size="small"
            type="danger"
            @click="deleteLease(scope.$index, scope.row)"
        >
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogFormVisible" title="新增用户" width="500">
    <el-form :model="form">
      <el-form-item label="序号" label-width="140px">
        <el-input v-model="form.id" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="汽车名称" label-width="140px">
        <el-input v-model="form.carName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="车牌类型" label-width="140px">
        <el-input v-model="form.carType" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商标" label-width="140px">
        <el-input v-model="form.brand" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="座位" label-width="140px">
        <el-input v-model="form.seats" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="每小时收费" label-width="140px">
        <el-input v-model="form.hourPrice" autocomplete="off"/>
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

  <el-dialog v-model="dialogTableVisible" title="修改用户" width="1000" >
    <el-form  :model="form">

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
      <el-form-item label="价格" label-width="140px"> {{form.hourPrice}}元/小时</el-form-item>
      <el-form-item label="总价钱" label-width="140px">{{ calculateTotalCharge(calculateTotalHours(form.rentalTime, form.returnTime),form.hourPrice)}}元</el-form-item>
    </el-form>
    <template #footer>

    </template>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="方案一" name="first">
          <el-text class="mx-1" size="large">方案一:三天以内，每小时正常收费;三天以后每小时享75%优惠额度</el-text>
        <div class="dialog-footer">
          <el-button @click="dialogTableVisible = false">取消</el-button>
          <!-- 先保存、再关闭对话框 -->
          <el-button type="primary" @click="updateLease">
            保存
          </el-button>
          <el-button plain @click="centerDialogVisible = true">
            提交订单
          </el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="方案二" name="second"></el-tab-pane>

    </el-tabs>
  </el-dialog>
  <el-dialog
      v-model="centerDialogVisible"
      title="支付"
      width="500"
      align-center
  >

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="state() , centerDialogVisible = false , dialogTableVisible=false">
          确认支付
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
.mx-1{
   height: 50px;
  margin-left: 80px;
}
</style>