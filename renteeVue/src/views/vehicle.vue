<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";

const getVehicle =()=>{
  axios.post("http://localhost:8081/getAllVehicle")
      .then((res) => {
        console.log("后端返回的数据：", res);
        vehicleList.value = res.data;
      })
}
const vehicleList = ref();

onMounted(()=>{
  console.log("车辆数据查询");
  getVehicle();
})

</script>

<template>
  <el-table :data="vehicleList" border style="width: 100%">
    <el-table-column prop="id" label="序号" width="60px" />
    <el-table-column prop="carName" label="车辆名称" />
    <el-table-column prop="carType" label="汽车种类" />
    <el-table-column prop="brand" label="汽车品牌"/>
    <el-table-column prop="color" label="颜色"/>
    <el-table-column prop="seats" label="座位数"/>
    <el-table-column prop="licensePlate" label="车牌"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small">
          Edit
        </el-button>
        <el-button
            size="small"
            type="danger"
        >
          Delete
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>