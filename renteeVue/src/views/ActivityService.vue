<script setup lang="ts">

import axios from "axios";
import {onMounted, ref} from "vue";
import {ElMessage} from 'element-plus'


const dialogVisible = ref(false);
const coupon = ref({
  startTime: '',
  endTime: '',
  isKill: '',
  isSpecial: '',
  couponImg: '',
  couponContent: '',
  firstPrice: '',
  currentPrice: '',
  totalNum: '',
  sales: '',
});
const saveCoupon = () => {
  axios.post('http://localhost:8081/saveCoupon', coupon.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(() => {
    ElMessage({
      type: "success",
      message: "保存成功！",
      showClose: true,
      grouping: true,
    })
    // console.log("保存：", res);
  }).catch(() => {
    ElMessage({
      type: "error",
      message: "保存失败失败！",
      showClose: true,
      grouping: true,
    })
    // console.log("失败：", error);
  })
  dialogVisible.value = false;
};

onMounted(() => {
  getAllCoupons();
});
const couponList = ref([]);
const getAllCoupons = () => {
  axios.get('http://localhost:8081/getAllCoupons')
      .then(res => {
        couponList.value = res.data
        console.log("数据:", couponList.value);
        console.log("res:", res.data);
      })
};

const deleteCoupon = (index, row) => {
  console.log("AAAAAAAAAAA:", row);
}


</script>

<template>

  <div class="common-layout">
    <el-container>
      <el-header style="border: 1px solid wheat">
        <el-button @click="dialogVisible = true" type="primary">新增秒杀</el-button>
      </el-header>

      <el-main style="border: 1px solid wheat">

        <el-table
            :data="couponList"
            :default-sort="{ prop: ['startTime','endTime'], order: 'descending' }"
            style="width: 100%">
          <el-table-column type="selection" width="55"/>
          <el-table-column fixed prop="couponId" label="ID" width="290"/>
          <el-table-column prop="isKill" label="秒杀" width="120"/>
          <el-table-column prop="isSpecial" label="特价" width="120"/>
          <el-table-column prop="startTime" sortable label="开始时间" width="165"/>
          <el-table-column prop="endTime" sortable label="结束时间" width="165"/>
          <el-table-column prop="firstPrice" label="定价/元" width="120"/>
          <el-table-column prop="currentPrice" label="现价/元" width="120"/>
          <el-table-column prop="totalNum" label="库存" width="120"/>
          <el-table-column prop="sales" label="销量" width="120"/>
          <el-table-column fixed="right" label="Operations" width="160">
            <template #default>
              <el-button type="danger" size="small" @click="deleteCoupon(scope.$index, scope.row)">删除</el-button>
              <el-button type="primary" size="small" @click="handleClick((scope.$index, scope.row))">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-main>
    </el-container>
  </div>


  <el-dialog class="dia" v-model="dialogVisible" title="新增活动" width="1000" draggable>
    <el-form :model="coupon">
      <el-row :gutter="20">
        <el-col :span="5">
          <el-form-item label="定价" label-width="50px">
            <el-input v-model="coupon.firstPrice" autocomplete="off"/>
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="10">
          <el-form-item label="现价" label-width="50px">
            <el-input v-model="coupon.currentPrice" autocomplete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-form-item label="库存" label-width="50px">
            <el-input v-model="coupon.totalNum" autocomplete="off"/>
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="10">
          <el-form-item label="销量" label-width="50px">
            <el-input v-model="coupon.sales" autocomplete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-form-item label="是否特价" label-width="100px">
            <el-input v-model="coupon.isSpecial" autocomplete="off"/>
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="10">
          <el-form-item label="是否秒杀" label-width="100px">
            <el-input v-model="coupon.isKill" autocomplete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <div>
        <el-form-item label="秒杀开始时间" label-width="150px">
          <el-date-picker
              v-model="coupon.startTime"
              type="datetime"
              placeholder="开始时间"
          />
        </el-form-item>
        <el-form-item label="秒杀结束时间" label-width="150px">
          <el-date-picker
              v-model="coupon.endTime"
              type="datetime"
              placeholder="结束时间"
          />
        </el-form-item>
      </div>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveCoupon">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>

</style>