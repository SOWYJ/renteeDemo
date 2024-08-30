<script setup lang="ts">
import LqForm from "@/pagination/components/LqForm.vue";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import LqTable from "@/pagination/components/LqTable.vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import {ElMessage} from "element-plus";
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
  carType: '',
  brand: '',
  color: '',
  seats: '',
  licensePlate: ''
})


const columns = [
  {label: '', type: "selection", width: 60},
  {label: '序号', type: "index", width: 50},
  {label: "车辆名称", prop: 'carName', minWidth: 50},
  {label: "车辆种类", prop: 'carType', minWidth: 50},
  {label: '品牌', prop: 'brand', minWidth: 50},
  {label: '颜色', prop: 'color', minWidth: 50},
  {label: '座位数', prop: 'seats', minWidth: 20},
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
    if (response && response.success) { // 根据实际的 API 返回结构调整检查逻辑
      // 重新查询数据

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
  // global.$api.deliveryCars(form.value);
  query();
}
const detail = (row: any) => {
  // 其他操作
  // editdialogFormVisible.value = true;
  form.value = { ...row };
  router.push({ path: '/main/DetailSpage', query: { id: row.id } });
};

import { reactive } from 'vue'
import router from "@/router";

const formInline = reactive({
  user: '',
  region: '',
  date: '',
})

const onSubmit = () => {
  console.log('submit!')
}

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
        <el-button @click="dialogFormVisible = false">取消</el-button>
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
        <el-input v-model="formInline.user" placeholder="费用" clearable />
      </el-form-item>
      <el-form-item label="投放地点">
        <el-select
            v-model="formInline.region"
            placeholder="选择地点"
            clearable
        >
          <el-option label="Zone one" value="shanghai" />
          <el-option label="Zone two" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="投放日期">
        <el-date-picker
            v-model="formInline.date"
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
