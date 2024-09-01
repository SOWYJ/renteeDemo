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
        <el-form-item label="车型">
          <el-input v-model="formData.carType" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="品牌">
          <el-input v-model="formData.brand" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="颜色">
          <el-input v-model="formData.color" disabled></el-input>
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
        <el-form-item label="车牌号">
          <el-input v-model="formData.licensePlate" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="小时价格">
          <el-input v-model="formData.hourPrice" disabled></el-input>
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
        <el-form-item label="投放日期">
          <el-input v-model="formData.dropDate" disabled></el-input>
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

const router = useRouter();

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
  carStatus: ''
});

const fetchData = () => {
  // Simulating data fetch
  formData.value = {
    carName: '奥迪A4',
    carType: '轿车',
    brand: '奥迪',
    color: '黑色',
    seats: '5',
    licensePlate: '京A12345',
    hourPrice: '100',
    dropLocation: '北京市朝阳区',
    dropDate: '未投放',
    carStatus: '空闲中'
  };
};

onMounted(() => {
  fetchData();
});

const goBack = () => {
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
