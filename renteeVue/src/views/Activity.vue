<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";


const dialogVisible = ref(false);
const formLabelWidth = '140px';
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
  }).then(res=>{
    console.log("保存：", res);
  }).catch(error=>{
    console.log("失败：", error);
  })
};

</script>

<template>
  <el-button @click="dialogVisible = true" type="primary">新增秒杀</el-button>


  <el-dialog class="dia" v-model="dialogVisible" title="新增活动" width="1000">
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