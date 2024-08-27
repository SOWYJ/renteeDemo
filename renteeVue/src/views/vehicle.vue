<script setup lang="ts">
import LqForm from "@/pagination/components/LqForm.vue";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import LqTable from "@/pagination/components/LqTable.vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';

/*  */
const global = getCurrentInstance().appContext.config.globalProperties;

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

const columns = [
  {label: '', type: "selection", width: 60},
  {label: '序号', type: "index", width: 50},
  {label: "车辆名称", prop: 'carName', minWidth: 50},
  {label: "车辆种类", prop: 'carType', minWidth: 50},
  {label: '品牌', prop: 'brand', minWidth: 50},
  {label: '颜色', prop: 'color', minWidth: 50},
  {label: '座位数', prop: 'seats', minWidth: 50},
  {label: '车牌号', prop: 'licensePlate', minWidth: 50},
  {label: '操作', prop: 'id', slotName: 'operate', slotted: true, align: 'center', fixed: 'right', minWidth: 90},
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
</script>

<template>
  <div style="margin-top: 10px"></div>
  <el-config-provider :locale="zhCn">
    <lq-form :form="searchForm">
      <template #footer>
        <div style="margin-top:-50px;margin-left: 550px;">
          <el-button type="primary" @click="query">查询</el-button>
          <el-button type="success" @click="add">新增</el-button>
          <el-button type="warning" @click="handleExport">导出</el-button>
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
        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
        <el-button type="danger" @click="deleteEntKeyProcess(scope.row)">删除</el-button>
      </template>
    </lq-table>
  </el-config-provider>
</template>

<style scoped>

</style>
