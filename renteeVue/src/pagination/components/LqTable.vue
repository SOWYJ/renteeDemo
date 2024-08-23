<script setup lang="ts">
import { ref, toRef, computed, onMounted } from "vue";
//属性定义
const props = defineProps<{
  pagination?: Object;
  columns: Array<Object>;
  records?: Array<Object>;
  tableSize?: String;
  loading?: boolean;
}>();

//事件定义
const emit = defineEmits<{
  (e: "onSelections", value: Array<Object>): void;
  (e: "onPagination", value: Object): void;
}>();

const paginationProps = toRef(props, "pagination");

onMounted(() => {
  console.log(props.columns);
  console.log("Table component onMounted....");
});

//多选操作行集合
const _selections = ref([]);
const selectionChange = selectItem => {
  _selections.value = selectItem;
  emit("onSelections", _selections);
};
//分页
const handleCurrentChange = val => {
  paginationProps.value.pageNum = val;
  emit("onPagination", {
    pageNum: paginationProps.value.pageNum,
    pageSize: paginationProps.value.pageSize,
  });
};
//更改页面大小
const handleSizeChange = val => {
  console.log(`页面大小：`, val);
  paginationProps.value.pageSize = val;
  emit("onPagination", {
    pageNum: 1,
    pageSize: val,
  });
};
const getToFixed = (val: Number | String, arr: Number) => {
  let arrs = Number(arr).toFixed(val);
  return arrs;
};

//数据加载状态控制
const loading = ref(false);
const startLoading = async () => {
  loading.value = true;
};
const stopLoading = async () => {
  loading.value = false;
};
defineExpose({ startLoading, stopLoading });
</script>

<template>
  <!-- 数据列表 -->
  <div id="table">
    <el-row>
      <el-col :span="24">
        <el-table
          v-bind="{ ...$attrs }"
          v-loading="loading"
          :data="records"
          :size="tableSize || 'default'"
          @selection-change="selectionChange"
          :header-cell-style="{ background: '#e1eaf6', color: '#333333' }"
          :stripe="true"
          highlight-current-row
        >
          <!-- <slot name="prepend"></slot> -->
          <template v-for="(column, index) in columns">
            <el-table-column v-if="column.type === 'link'" v-bind="{ ...column }" show-overflow-tooltip>
              <template #default="scope" :key="index">
                <el-link type="primary" @click="column.event.click.call(column, scope.row)">
                  <span v-if="column.isSelect == true">
                    {{ useDictStore().getDictNameBy(column.dictKey, scope.row[column.prop]) }}
                  </span>
                  <span v-else>
                    {{ scope.row[column.prop] }}
                  </span>
                </el-link>
              </template>
            </el-table-column>
            <el-table-column v-bind="column" :index="index" v-else-if="column.slotted || column.slotName" show-overflow-tooltip>
              <template #default="scope" :key="index">
                <slot :name="column.slotName || column.prop" v-bind="{ ...scope, ...column }"></slot>
              </template>
            </el-table-column>
            <el-table-column v-bind="column" :index="index" v-else-if="column.dictKey" show-overflow-tooltip>
              <template #default="scope" :key="index">
                {{ useDictStore().getDictNameBy(column.dictKey, scope.row[column.prop]) }}
              </template>
            </el-table-column>
            <el-table-column v-bind="column" :index="index" v-else-if="column.dictsKey" show-overflow-tooltip>
              <template #default="scope" :key="index">
                {{ getDictNames(column.toFixed, scope.row[column.prop]) }}
              </template>
            </el-table-column>
            <el-table-column v-bind="column" :index="index" v-else-if="column.toFixed" show-overflow-tooltip>
              <template #default="scope" :key="index">
                {{ Number(scope.row[column.prop]).toFixed(column.toFixed) }}
              </template>
            </el-table-column>
            <el-table-column v-bind="column" :index="index" v-else-if="column.unit" show-overflow-tooltip>
              <template #default="scope" :key="index">
                {{ Number(scope.row[column.prop]) + column.unit }}
              </template>
            </el-table-column>

            <el-table-column v-else :key="index" v-bind="{ ...column }" show-overflow-tooltip></el-table-column>
          </template>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <div id="pagination" v-if="paginationProps != null || paginationProps != undefined" style="padding-right: 10px">
          <el-pagination
            :page-sizes="paginationProps.pageSizes"
            :page-size="paginationProps.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="paginationProps.total"
            :pageNum="paginationProps.pageNum"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            background
          ></el-pagination>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped></style>
