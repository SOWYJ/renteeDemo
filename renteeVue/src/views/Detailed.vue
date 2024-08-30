<script setup lang="ts">
import {computed, getCurrentInstance, onMounted, ref} from "vue";
import {useRoute} from "vue-router";

const global = getCurrentInstance().appContext.config.globalProperties;
const route = useRoute(); // 使用useRoute获取当前路由
const brandList = ref([]); // 初始化brandList
const selectedBrand = ref(''); // 用于存储选中的品牌

onMounted(() => {
  // 获取传递过来的brand参数
  const passedBrand = route.query.brand as string;

  // 如果有传递过来的brand，则设置选中的品牌和el-autocomplete的值
  if (passedBrand) {
    selectedBrand.value = passedBrand;
    state1.value = passedBrand;
  }


  global.$api.getAllCarts().then(res => {
    console.log(res.data);
    brandList.value = res.data.map(item => {
      return {
        carName: item.carName,
        carType: item.carType,
        brand: item.brand, // 提取brand字段
        color: item.color,
        seats: item.seats,
        licensePlate: item.licensePlate
      }
    });
    console.log(brandList.value);
    // 更新restaurants以包含brand字段
    restaurants.value = brandList.value.map(item => ({
      value: item.brand, // 只使用brand字段
      link: '' // link字段可以根据需求填充或忽略
    }));
  });
});

interface RestaurantItem {
  value: string
  link: string
}

const state1 = ref('')

const restaurants = ref<RestaurantItem[]>([])

const querySearch = (queryString: string, cb: (results: RestaurantItem[]) => void) => {
  const results = queryString
      ? restaurants.value.filter(createFilter(queryString))
      : restaurants.value;
  // 调用回调函数返回建议项
  cb(results);
}

const createFilter = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
        restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

const handleSelect = (item: RestaurantItem) => {
  selectedBrand.value = item.value; // 设置选中的品牌
}

const filteredBrandList = computed(() => {
  // 过滤数据
  const filteredData = selectedBrand.value
      ? brandList.value.filter(item => item.brand === selectedBrand.value)
      : brandList.value;

  // 按品牌字母顺序排序
  return filteredData.sort((a, b) => a.brand.localeCompare(b.brand));
});

</script>

<template>
  <div class="flex gap-4" >
    <div>
      <el-autocomplete
          v-model="state1"
          :fetch-suggestions="querySearch"
          clearable
          class="inline-input w-50"
          placeholder="Please Input"
          @select="handleSelect"
      />
    </div>
  </div>
  <el-table :data="filteredBrandList" border style="width: 100%">
    <el-table-column prop="carName" label="车名" width="180" />
    <el-table-column prop="carType" label="车型" width="180" />
    <el-table-column prop="brand" label="品牌" />
    <el-table-column prop="color" label="颜色" />
    <el-table-column prop="seats" label="座位数" />
    <el-table-column prop="licensePlate" label="车牌" />
  </el-table>
</template>

<style scoped>
.flex{
  width: 230px;
}
</style>