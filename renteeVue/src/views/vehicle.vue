<script setup lang="ts">
import LqForm from "@/pagination/components/LqForm.vue";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import LqTable from "@/pagination/components/LqTable.vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import {ElMessage} from "element-plus";
import {menuStore} from "@/store/menu";
import axios from 'axios';
const store = menuStore();


import updateCars from "@/pagination/request/api/updateCars.js";

/*  */
const global = getCurrentInstance().appContext.config.globalProperties;
const dialogFormVisible = ref(false)
const editdialogFormVisible = ref(false)
const deliverydialogFormVisible = ref(false)
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
  resetPagination();
  loadData(1, tableData.value.pageSize);
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
    ElMessage.success('保存成功');
    editdialogFormVisible.value = false;
    query();
  }
}
// const delete = (row: any) => {
//   form.value = { ...row };
//   global.$api.deleteCars(form.value);
// };
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
  {label: '', type: "selection", width: 60},
  {label: '序号', type: "index", width: 50},
  {label: "车辆名称", prop: 'carName', minWidth: 50},
  {label: "车辆种类", prop: 'carType', minWidth: 50},
  {label: '品牌', prop: 'brand', minWidth: 50},
  {label: '颜色', prop: 'color', minWidth: 50},
  {label: '座位数', prop: 'seats', minWidth: 50},
  {label: '车牌号', prop: 'licensePlate', minWidth: 50},
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
const resetPagination = () => {
  paginationProps.pageNum = 1;
  paginationProps.pageSize = tableData.value.pageSize;
  paginationProps.total = 0;
};
const loadData = (pageNum = 1, pageSize = 10) => {
  global.$api.viewProduct(
      {
        "current": pageNum,
        "size": pageSize,
        "carName": searchForm.value.model.carName
      }
  ).then((res) => {
    console.log("后台返回的数据",res);
    tableData.value.records = res.data.records;
    paginationProps.total = res.data.total;
  })
};


onMounted(() => {
  setTimeout(() => {
    query();
  }, 50)
})


import { ElMessage, ElMessageBox } from 'element-plus'


const deleteEntKeyProcess = async (row: any) => {
  try {
    // 显示确认对话框
    await ElMessageBox.confirm(
        '你确定要删除吗？',
        '提醒！',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    // 更新表单数据
    form.value = { ...row };

    // 调用删除 API
    const response = await global.$api.deleteCars(form.value);

    // 检查删除是否成功
    if (response && response.success) {

      // 显示成功消息
      ElMessage({
        type: 'success',
        message: '删除成功',
      });
      query();
    } else {
      // 如果响应未表明成功，抛出错误
      throw new Error('删除失败');
    }
  } catch (error) {
    // 判断错误类型
    if (error.response && error.response.status === 500) {
      // 处理服务器内部错误 (500)
      ElMessage({
        type: 'error',
        message: '车辆在投放中，无法删除',
      });
    } else if (error === 'cancel') {
      // 处理用户取消的情况
      ElMessage({
        type: 'info',
        message: '已取消',
      });
    } else {
      ElMessage({
        type: 'success',
        message: '删除成功',
      });
      query();
    }
  }
};

const deliveryCars = (row: any) => {
  deliverydialogFormVisible.value=true;
  form.value = { ...row };
  formInline.value.id= form.value.id;
  // query();
}

const query1 = async () => {
  try {
    const res = await axios.get("http://localhost:8084/getAlllease");
    console.log("userList 数据：", res);
    userList.value = res.data || []; // 确保 userList 是一个数组
  } catch (error) {
    // console.error("请求失败：", error);
    userList.value = []; // 请求失败时，确保 userList 也是一个数组
  }
};
const formatDateTime = (dateTimeString) => {
  const date = new Date(dateTimeString);

  // 获取日期部分
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需要加1
  const day = String(date.getDate()).padStart(2, '0');

  // 获取时间部分
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  // 组合成所需格式
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};


const userList = ref([]);
// 查找 id 并执行 store 操作
const detail = async (row: any) => {
  // 其他操作


  await query1(); // 等待数据加载完成

  form.value = { ...row };

  console.log("form数据", form.value);

  try {
    const res = await global.$api.qCarState(form.value);



    // 格式化记录
    const carState = res.data;

    form.value.dropDate = carState.dropDate;
    form.value.carStatus = carState.carstatus;
    form.value.dropLocation=carState.dropLocation;


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

        // 格式化日期
        form.value.dropDate = formatDateTime(form.value.dropDate);
        form.value.rentalTime = formatDateTime(form.value.rentalTime);
        form.value.returnTime = formatDateTime(form.value.returnTime);
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

  } catch (error) {
    console.error("请求失败", error);
  }
};


import { reactive } from 'vue'
import router from "@/router";

const formInline = ref({
  id:'',
  hourPrice: '',
  dropLocation: '',
  dropDate: '',
  carstatus:'available'
})

const open2 = () => {
  ElMessage({
    message: '投放成功',
    type: 'success',
  })
  deliverydialogFormVisible.value=false;
}

const open4 = () => {
  ElMessage.error('车辆已经在投放中')
}
const onSubmit = async () => {
  console.log('submit!');
  console.log("WWWWWWWWWWWWW", formInline.value);

  try {
    await global.$api.deliveryCars(formInline.value);
    open2();
  } catch (error) {
    if (error.response && error.response.status === 500) {
      open4();
    } else {
      console.error('提交失败:', error);
    }
  }
};


</script>

<template>
  <div style="margin-top: 10px"></div>
  <el-config-provider :locale="zhCn">
    <lq-form :form="searchForm">
      <template #footer>
        <div style="margin-top:-50px;margin-left: 550px;">
          <el-button type="primary" @click="query">查询</el-button>
          <el-button type="success" @click="dialogFormVisible = true">新增</el-button>
<!--          <el-button type="warning" @click="handleExport">导出</el-button>-->
          <el-button type="danger" @click="deleteEntKeyMaterialBatch">批量删除</el-button>
        </div>
      </template>
    </lq-form>
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
        <el-button style="margin-bottom: 5px" type="" @click="deliveryCars(scope.row)">投放</el-button>
        <br>
        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
        <el-button type="danger" @click="deleteEntKeyProcess(scope.row)">删除</el-button>
      </template>
    </lq-table>
  </el-config-provider>



  <el-dialog v-model="dialogFormVisible" title="新增车辆" width="500">
    <el-form :model="form">
      <el-form-item label="车辆名称" label-width="140px">
        <el-input v-model="form.carName" autocomplete="off"/>
      </el-form-item>
<!--      <el-form-item label="车辆种类" label-width="140px">-->
<!--        <el-input v-model="form.carType" autocomplete="off"/>-->
<!--      </el-form-item>-->
      <el-form-item label="品牌" label-width="140px">
        <el-input v-model="form.brand" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="颜色" label-width="140px">
        <el-input v-model="form.color" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="座位数" label-width="140px">
        <el-input v-model="form.seats" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="车牌号" label-width="140px">
        <el-input v-model="form.licensePlate" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <!-- 先保存、再关闭对话框 -->
        <el-button type="primary" @click="saveCars" >
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="editdialogFormVisible" title="编辑车辆" width="500">
    <el-form :model="form">
      <el-form-item label="车辆名称" label-width="140px">
        <el-input v-model="form.carName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="车辆种类" label-width="140px">
        <el-input v-model="form.carType" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="品牌" label-width="140px">
        <el-input v-model="form.brand" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="颜色" label-width="140px">
        <el-input v-model="form.color" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="座位数" label-width="140px">
        <el-input v-model="form.seats" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="车牌号" label-width="140px">
        <el-input v-model="form.licensePlate" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editdialogFormVisible = false;">取消</el-button>
        <!-- 先保存、再关闭对话框 -->
        <el-button type="primary" @click="update" >
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>


  <el-dialog v-model="deliverydialogFormVisible" title="投放信息">
    <el-form  :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="小时费用">
        <el-input v-model="formInline.hourPrice" placeholder="费用" clearable />
      </el-form-item>
      <el-form-item label="投放地点">
        <el-select
            v-model="formInline.dropLocation"
            placeholder="选择地点"
            clearable
        >
          <el-option label="上海市海宾区" value="上海海宾区" />
          <el-option label="桂林市雁山区" value="桂林市雁山区" />
          <el-option label="上海市海宾区" value="上海海宾区" />
          <el-option label="北京市朝阳区" value="北京市朝阳区" />
          <el-option label="广州市天河区" value="广州市天河区" />
          <el-option label="深圳市南山区" value="深圳市南山区" />
          <el-option label="杭州市西湖区" value="杭州市西湖区" />

        </el-select>
      </el-form-item>
      <el-form-item label="投放日期">
        <el-date-picker
            v-model="formInline.dropDate"
            type="date"
            placeholder="选择日期"
            clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

</template>

<style scoped>
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}
</style>
