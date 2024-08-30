<script setup lang="ts">
import LqForm from "@/pagination/components/LqForm.vue";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import LqTable from "@/pagination/components/LqTable.vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import {ElMessage} from "element-plus";
import updateCars from "@/pagination/request/api/updateCars.js";
// import axios from "axios/index";

/*  */
const global = getCurrentInstance().appContext.config.globalProperties;
const dialogFormVisible = ref(false)
const editdialogFormVisible = ref(false)
const searchForm = ref({
  innerAttrs: {
    labelPosition: 'right',
    labelWidth: 200,
  },
  model: {
    "carName": "",
  },
  formItems: [
    {type: 'input', label: '车辆名称: ', prop: 'carName', span: 12, maxlength: 30, placeholder: '请输入车辆名称'},
  ],
  buttonsAttrs: {
    align: 'left'
  }
});
const query = () => {
  // resetPagination();
  loadData(1, tableData.value.pageSize);
}

const querydata = () => {
  loadData2(1, tableData.value.pageSize);
}

const saveCars = () => {
  if (global.$api.saveCars(form.value)){
    ElMessage.success('新增成功');
    dialogFormVisible.value = false;
  }
}
const edit = (row: any) => {
  editdialogFormVisible.value = true;
  form.value = { ...row };
}
const update = () => {
  if (global.$api.updateCars(form.value)){
    ElMessage.success('新增成功');
    editdialogFormVisible.value = false;
  }
}
// const delete = (row: any) => {
//   form.value = { ...row };
//   global.$api.deleteCars(form.value);
// };
const form = ref({
  carName: '',
  hourPrice:'',
  dropLocation:'',
  dropDate:'',
  carStatus:''
})


const columns = [
  {label: '', type: "selection", width: 60},
  {label: '序号', type: "index", width: 60},
  {label: "车辆名称", prop: 'carName', minWidth: 60},
  {label: "小时价格", prop: 'hourPrice', minWidth: 60},
  {label: '投放日期', prop: 'dropDate', minWidth: 60},
  {label: '投放地点', prop: 'dropLocation', minWidth: 80},
  {label: '汽车状态', prop: 'carStatus', minWidth: 80},
  {label: '操作', prop: 'id', slotName: 'operate', slotted: true, align: 'center', fixed: 'right', minWidth: 120},
]
const tableData = ref({
  records: [],
  pageSize: 10,
  tableSize: 'default',
  border: true,
});
const paginationProps = reactive({
  pageSize: tableData.value.pageSize,
  pageSizes: [5, 10, 20, 50, 100],
  total: 0,
  pageNum: 1,
  small: false,
  paginationAlign: 'right',
});
const handlePagination = ({pageNum, pageSize = 10}) => {
  loadData(pageNum, pageSize);
}
// const resetPagination = () => {
//   paginationProps.pageNum = 1;
//   paginationProps.pageSize = tableData.value.pageSize;
//   paginationProps.total = 0;
// };

const response = {
  data: [
    {
      car: {
        id: '',
        carName: "",
        carType: null,
        brand: null,
        color: null,
        seats: null,
        licensePlate: null
      },
      carState: {
        id: null,
        hourPrice: '',
        dropLocation: "",
        dropDate: "",
        carStatus: ""
      }
    },
    // 其他对象
  ],
  status: 200,
  statusText: "OK",
  headers: "AxiosHeaders",
};
const loadData = (pageNum = 1, pageSize = 10) => {
  global.$api.queryState(
      {
        "current": pageNum,
        "size": pageSize,
        "carName": searchForm.value.model.carName
      }
  ).then((res) => {
    console.log("后台返回的数据",res);
    response.data=res.data;
    // console.log("后台返回的数据111111111111111",response);
    // tableData.value.records = res.data.records;
    const formattedRecords = res.data.map(item => ({
      id: item.car.id, // 提取 car 对象中的 id
      carName: item.car.carName,
      carType: item.car.carType,
      brand: item.car.brand,
      color: item.car.color,
      // 其他字段根据需求填充
      hourPrice: item.carState.hourPrice,
      dropLocation: item.carState.dropLocation,
      dropDate: item.carState.dropDate,
      carStatus: item.carState.carStatus
    }));

    // 将格式化的记录赋值给 tableData
    tableData.value.records = formattedRecords;

    paginationProps.total = res.data.total;
  })
};
const loadData2 = (pageNum = 1, pageSize = 10) => {
  global.$api.obquery(
      {
        "carName": searchForm.value.model.carName
      }
  ).then((res) => {
    console.log("后台返回的数据",res);
    response.data=res.data;
    // console.log("后台返回的数据111111111111111",response);
    // tableData.value.records = res.data.records;
    const formattedRecords = res.data.map(item => ({
      id: item.car.id, // 提取 car 对象中的 id
      carName: item.car.carName,
      carType: item.car.carType,
      brand: item.car.brand,
      color: item.car.color,
      // 其他字段根据需求填充
      hourPrice: item.carState.hourPrice,
      dropLocation: item.carState.dropLocation,
      dropDate: item.carState.dropDate,
      carStatus: item.carState.carStatus
    }));

    // 将格式化的记录赋值给 tableData
    tableData.value.records = formattedRecords;

    // paginationProps.total = res.data.total;
  })
};

onMounted(() => {
  setTimeout(() => {
    query();
  }, 50)
})


import { ElMessage, ElMessageBox } from 'element-plus'
import axios from "axios";

const deleteEntKeyProcess = (row: any) => {
  ElMessageBox.confirm(
      '你确定要删除吗？',
      '提醒！',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        form.value = { ...row };
        global.$api.deleteCars(form.value);
        query();
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消',
        })
      })
}


</script>

<template>
  <div style="margin-top: 10px">
    <el-config-provider :locale="zhCn">
      <lq-form :form="searchForm">
        <template #footer>
          <div style="margin-top:-50px;margin-left: 550px;">
            <el-button type="primary" @click="querydata">查询</el-button>
          </div>
        </template>
      </lq-form>
      <div class="table-container">
        <lq-table ref="tableRef"
                  :columns="columns"
                  :records="tableData.records"
                  size="default"
                  :border="true"
                  :header-cell-style="{background:'#e1eaf6', color:'#333333'}"
                  :stripe="true"
                  :pagination="paginationProps"
                  @on-pagination="handlePagination"
        >
          <template #operate="scope">
            <el-button style="margin-bottom: 5px" type="" @click="edit(scope.row)">详情</el-button>
          </template>
        </lq-table>
      </div>
    </el-config-provider>

    <el-dialog v-model="editdialogFormVisible" title="编辑车辆" width="500">
      <el-form :model="form">
        <el-form-item label="车辆名称" label-width="140px">
          <el-input v-model="form.carName" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="小时价格" label-width="140px">
          <el-input v-model="form.hourPrice" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="投放地点" label-width="140px">
          <el-input v-model="form.dropLocation" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="投放日期" label-width="140px">
          <el-input v-model="form.dropDate" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="汽车状态" label-width="140px">
          <div class="status-container">
            <el-input v-model="form.carStatus" autocomplete="off" disabled/>
            <div v-if="form.carStatus === 'in-use'" class="spinner">
              <div class="inner-circle"></div>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="editdialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="update">
            保存
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>



<style scoped>
.table-container {
  max-height: 450px; /* 设置固定高度 */
  overflow-y: auto;  /* 启用垂直滚动条 */
}
.vehicle-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.status-available {
  color: green;
}

.status-in-use {
  color: red;
  display: flex;
  align-items: center;
}

.spinner {
  position: relative;
  width: 24px; /* 增大圆圈尺寸以适应小圆圈 */
  height: 24px;
  margin-left: 8px;
  border-radius: 50%;
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-left: 4px solid red;
  animation: spin 1s linear infinite;
}

.spinner .inner-circle {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 8px;
  height: 8px;
  background: red;
  border-radius: 50%;
  transform: translate(-50%, -50%);
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>

