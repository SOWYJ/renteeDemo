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
            <el-button style="margin-bottom: 5px" type="" @click="detail(scope.row)">详情</el-button>
            <el-button style="margin-bottom: 5px" type="danger" @click="deleteEntKeyProcess(scope.row)">取消投放</el-button>
          </template>

          <!-- 自定义 carStatus 列的显示内容 -->
          <template #carStatus="{ row }">
            <span v-if="row.carStatus === 'in-use'" class="status-in-use" :style="{ color: 'red' }">
              租赁中
              <div class="spinner">
                <div class="inner-circle"></div>
              </div>
            </span>
            <span v-if="row.carStatus === 'available'" :style="{ color: 'green' }">
              未租赁
            </span>
            <span v-if="row.carStatus === 'charging'" :style="{ color: 'greenyellow' }">
              充电中
            </span>
            <span v-if="row.carStatus === 'under-maintenance'" :style="{ color: 'grey' }">
              维修中
            </span>
          </template>
        </lq-table>
      </div>
    </el-config-provider>


  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, getCurrentInstance } from 'vue';
import LqForm from '@/pagination/components/LqForm.vue';
import LqTable from '@/pagination/components/LqTable.vue';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import { ElMessage, ElMessageBox } from 'element-plus';
import router from "@/router";

// 获取全局 API 实例
const global = getCurrentInstance().appContext.config.globalProperties;

const dialogFormVisible = ref(false);
const editdialogFormVisible = ref(false);
import {menuStore} from "@/store/menu";
import axios from 'axios';


const store = menuStore();
const searchForm = ref({
  innerAttrs: {
    labelPosition: 'right',
    labelWidth: 200,
  },
  model: {
    carName: '',
  },
  formItems: [
    { type: 'input', label: '车辆名称: ', prop: 'carName', span: 12, maxlength: 30, placeholder: '请输入车辆名称' },
  ],
  buttonsAttrs: {
    align: 'left',
  },
});

const form = ref({
  id: '',
  carName: '',
  carType: '',
  brand: '',
  color:'',
  seats:'',
  hourPrice: '',
  dropLocation: '',
  dropDate: '',
  carStatus: '',
  licensePlate:'',
  rentalTime:'',
  returnTime:''
});

const columns = [
  { label: '', type: 'selection', width: 60 },
  { label: '序号', type: 'index', width: 60 },
  { label: '车辆名称', prop: 'carName', minWidth: 60 },
  { label: '小时价格', prop: 'hourPrice', minWidth: 60 },
  { label: '投放日期', prop: 'dropDate', minWidth: 60 },
  { label: '投放地点', prop: 'dropLocation', minWidth: 80 },
  { label: '汽车状态', prop: 'carStatus', minWidth: 80, slotName: 'carStatus' },
  { label: '操作', prop: 'id', slotName: 'operate', slotted: true, align: 'center', fixed: 'right', minWidth: 120 },
];

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

const handlePagination = ({ pageNum, pageSize = 10 }) => {
  loadData(pageNum, pageSize);
};

const query = () => {
  loadData(1, tableData.value.pageSize);
};

const querydata = () => {
  loadData2(1, tableData.value.pageSize);
};

const saveCars = () => {
  global.$api.saveCars(form.value)
      .then(() => {
        ElMessage.success('新增成功');
        dialogFormVisible.value = false;
      })
      .catch(() => {
        ElMessage.error('新增失败');
      });
};

const query1 = async () => {
  try {
    const res = await axios.get("http://localhost:8084/getAlllease");
    // console.log("userList 数据：", res);
    userList.value = res.data || []; // 确保 userList 是一个数组
  } catch (error) {
    // console.error("请求失败：", error);
    userList.value = []; // 请求失败时，确保 userList 也是一个数组
  }
};
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需要加1
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const userList = ref([]);
// 查找 id 并执行 store 操作
const detail = async (row: any) => {
  // 其他操作
  // editdialogFormVisible.value = true;

  await query1(); // 等待数据加载完成

  form.value = { ...row };
  // console.log("UUUU", form.value);

  // 确保 userList.value 是一个数组
  if (Array.isArray(userList.value)) {
    // 查找 userList 中是否有与 form.id 匹配的对象
    const matchedUser = userList.value.find(user => user.id === form.value.id);

    if (matchedUser) {
      form.value.seats = matchedUser.seats;
      form.value.carType = matchedUser.carType;
      form.value.hourPrice = matchedUser.hourPrice;
      form.value.licensePlate = matchedUser.licensePlate;
      form.value.rentalTime = matchedUser.rentalTime;
      form.value.returnTime = matchedUser.returnTime;

      // form.value=matchedUser;
      form.value.dropDate = formatDate(matchedUser.dropDate);
      console.log("UUUU222", form.value);
      store.setDetailData(form.value);
    } else {
      console.log("未找到匹配的用户");
    }
  } else {
    console.error("userList 不是一个有效的数组");
  }

  // 继续路由跳转
  router.push({ path: '/main/DetailSpage', query: { id: row.id } });
};
const update = () => {
  global.$api.updateCars(form.value)
      .then(() => {
        ElMessage.success('更新成功');
        editdialogFormVisible.value = false;
      })
      .catch(() => {
        ElMessage.error('更新失败');
      });
};

const deleteEntKeyProcess = (row: any) => {
  ElMessageBox.confirm(
      '你确定要取消投放吗？',
      '提醒！',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        global.$api.deleteCarSate({ id: row.id })
            .then(() => {
              ElMessage.success('取消成功');
              query();
            })
            .catch(() => {
              ElMessage.error('取消失败');
            });
      })
      .catch(() => {
        ElMessage.info('已取消');
      });
};

const loadData = (pageNum = 1, pageSize = 10) => {
  global.$api.queryState({
    current: pageNum,
    size: pageSize,
    carName: searchForm.value.model.carName,
  })
      .then((res) => {
        console.log("投放数据",res.data);
        const formattedRecords = res.data.map(item => ({
          id: item.car.id,
          carName: item.car.carName,
          carType: item.car.carType,
          brand: item.car.brand,
          color: item.car.color,
          hourPrice: item.carState.hourPrice,
          dropLocation: item.carState.dropLocation,
          dropDate: item.carState.dropDate,
          carStatus: item.carState.carstatus,
        }));
        tableData.value.records = formattedRecords;
        paginationProps.total = res.total;
      })
      .catch(() => {
        ElMessage.error('加载数据失败');
      });
};

const loadData2 = (pageNum = 1, pageSize = 10) => {
  global.$api.obquery({
    carName: searchForm.value.model.carName,
  })
      .then((res) => {
        const formattedRecords = res.data.map(item => ({
          id: item.car.id,
          carName: item.car.carName,
          carType: item.car.carType,
          brand: item.car.brand,
          color: item.car.color,
          hourPrice: item.carState.hourPrice,
          dropLocation: item.carState.dropLocation,
          dropDate: item.carState.dropDate,
          carStatus: item.carState.carstatus,
        }));

        tableData.value.records = formattedRecords;
      })
      .catch(() => {
        ElMessage.error('加载数据失败');
      });
};

onMounted(() => {
  setTimeout(() => {
    query();
  }, 50);
});
</script>

<style scoped>
.table-container {
  max-height: 450px; /* 设置固定高度 */
  overflow-y: auto;  /* 启用垂直滚动条 */
}

.status-in-use {
  display: flex;
  align-items: center;
}

.spinner {
  position: relative;
  width: 24px; /* 圆圈的大小 */
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
