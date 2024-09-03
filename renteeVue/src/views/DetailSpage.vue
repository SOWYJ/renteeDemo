<template>
  <div style="margin-top: 20px"></div>
  <el-form :model="formData" label-width="120px" class="form-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="车辆名称">
          <el-input v-model="formData.carName" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="投放地点">
          <el-input v-model="formData.dropLocation" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="车型">
          <el-input v-model="formData.carType" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="投放日期">
          <el-input v-model="formData.dropDate" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="车牌号">
          <el-input v-model="formData.licensePlate" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="租车时间">
          <el-input v-model="formData.rentalTime" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="座位数">
          <el-input v-model="formData.seats" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="还车时间">
          <el-input v-model="formData.returnTime" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="小时费用">
          <el-input v-model="formData.hourPrice" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汽车状态">
          <el-input v-model="formData.carStatus" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <div class="form-buttons">
      <el-button type="primary" @click="goBack">返回</el-button>
    </div>
  </el-form>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import {menuStore} from "@/store/menu";
const router = useRouter();

const store = menuStore();
const menuData = ref();
const formData = ref({
  carName: '',
  carType: '',
  brand: '',
  color: '',
  seats: '',
  licensePlate: '',
  hourPrice: '',
  dropLocation: '',
  dropDate: '',
  carStatus: '',
  rentalTime:'',
  returnTime:''
});


onMounted(() => {
  formData.value = store.getDetailData;
  console.log("store得到的数据",formData);
});

const goBack = () => {
  store.setDetailData(null);
  router.back(); // This will navigate to the previous page
};
</script>

<style scoped>
.form-container {
  max-width: 95%;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.el-form-item {
  margin-bottom: 20px;
}

.el-form-item .el-input {
  border-radius: 4px;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  padding: 0 10px;
}

.el-form-item .el-form-item__label {
  font-weight: bold;
  color: #333;
}

.form-buttons {
  margin-top: 20px;
  text-align: right;
}
</style>
