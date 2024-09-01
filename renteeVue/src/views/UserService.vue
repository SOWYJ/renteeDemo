<script setup lang="ts">
import router from "@/router";
import {getCurrentInstance, onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import {couponStore, useCarouselStore} from "@/store/menu";

const store2 = couponStore(); // 轮播图
const carouselStore = useCarouselStore(); // 索引
const {activeIndex} = carouselStore;


const global = getCurrentInstance()?.appContext.config.globalProperties;
const preferential = (index: any) => {
  console.log("RRRRRRRR:", carouselStore.activeIndex);
  router.push({
    path: "/preferential",
   query: {index}
  });
};


const imgs = ref([]);
// const imgs = ref();
// const imgs2 = [];
onMounted(() => {
  axios.get('http://localhost:8889/activity/getAllCoupons').then(res => {
    console.log("AAAAAAAAAA", res);
    imgs.value = res.data;
    store2.setMenu(res.data)
    // imgs.value.forEach(img=>{
    //   imgs2.push(img.couponImg)
    // })
    console.log("sssssssssssss", imgs);
    // console.log("adadadadawd",imgs2);
  })
})


</script>

<template>
  <div class="wrapper">
    <div class="l-area">
      <div class="top-content">
        <div class="content">
          <img src="../static/orcart.png" alt="" class="icon">
          <el-text class="text1">热租品牌</el-text>
        </div>
        <div class="r-cent">
          <el-text class="text2" @click="getCart">更多></el-text>
        </div>
      </div>
      <div class="main-content">
        <!--          <div class="Logos" v-for="(item, index) in brandList.slice(0, 8)"-->
        <!--               :key="index"-->
        <!--               @click="navigateToDetail(item.brand)">-->
        <!--            &lt;!&ndash; 只显示 brand 字段的值 &ndash;&gt;-->
        <!--            <span>{{ item.brand }}</span>-->
        <!--          </div>-->
      </div>
    </div>

    <div class="r-area">
      <div class="content">
        <img src="../static/coupons.png" alt="" class="icon">
        <el-text class="text1">限时优惠</el-text>
      </div>
      <div class="r-main">
        <el-carousel height="550px" indicator-position="outside" v-model:active-index="activeIndex">
          <!-- 使用 v-for 遍历 imgs 数组 -->
          <el-carousel-item v-for="(item, index) in imgs" :key="index" @click="preferential(index)">
            <img :src="item.couponImg" alt="" style="width: 100%; height: 100%; object-fit: cover;">
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>
  </div>
</template>

<style scoped>
.wrapper {
  display: flex;
  width: 82%;
  min-height: 100vh; /* 使 wrapper 占满整个视口高度 */
  justify-content: space-between; /* 水平方向分布 l-area 和 r-area */
  align-items: center; /* 使左右区域垂直方向上对齐 */
  margin: 0 auto; /* 使内容水平居中 */
  border: 1px solid red;
}

.l-area {
  width: 33%; /* 设置 l-area 的宽度为 1:2 */
  display: flex;
  flex-direction: column;
  justify-content: center; /* 使左边区域内容在垂直方向居中 */
}

.r-area {
  width: 66%; /* 设置 r-area 的宽度为 2:1 */
  margin-left: 30px;
  margin-bottom: 13px;
}

.top-content {
  margin-left: 10px;
  width: 100%;
  display: flex;
  align-items: center; /* 垂直方向居中 */
  justify-content: space-between; /* 水平方向分布 icon、文字和更多 */
}

.content {
  display: flex;
  align-items: center; /* 使图片和文本在垂直方向上居中对齐 */
  white-space: nowrap; /* 防止文本换行 */
}

.icon {
  width: 40px; /* 设置图片的宽度 */
  height: 40px; /* 设置图片的高度，使图片与文本等高 */
  margin-right: 10px; /* 图片与文本之间的间距 */
}

.text1 {
  font-size: 25px;
  color: #f4a130;
  line-height: 40px; /* 设置与图片高度一致的行高，使文本等高 */
}

.text2 {
  font-size: larger;
  color: #585858;
  line-height: 40px; /* 确保与 icon 高度对齐 */
}

.main-content {
  width: 100%;
  display: grid; /* 网格布局 */
  grid-template-columns: repeat(2, 1fr); /* 将网格划分为两列 */
  gap: 20px; /* 网格项之间的间距 */
  padding: 10px;
}

.Logos {
  width: 100%; /* 占满容器宽度 */
  height: 122px;
  border: 1px solid red;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  text-align: center; /* 文字居中 */
}

.Logos {
  width: 100%; /* 占满容器宽度 */
  height: 122px;
  border: 1px solid red;
}

.r-main {
  width: 100%;
  height: 550px;
  border: 1px solid red;
  margin-top: 10px; /* 与上方的文字保持 10px 距离 */
}

.r-cent {
  display: flex;
  justify-content: flex-end; /* 让“更多>”对齐到最右侧 */
  width: 66%;
}

.el-carousel__item h3 {
  display: flex;
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>