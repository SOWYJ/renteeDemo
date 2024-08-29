<script setup lang="ts">
import {onMounted, onUnmounted, ref} from "vue";
import {couponStore, useCarouselStore} from "@/store/menu";
import axios from "axios";
import {ElMessage} from "element-plus";

const store2 = couponStore();
const loopData = ref();

const carouselStore = useCarouselStore();
const {activeIndex} = carouselStore;

function formatDate(isoString: any) {
  const date = new Date(isoString);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const seconds = date.getSeconds().toString().padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

const targetTime = new Date(store2.getMenuData[activeIndex].startTime).getTime(); // 目标时间
const formattedTime = ref('');

// 计算剩余时间
function calculateTimeRemaining() {
  const currentTime = new Date().getTime();
  const timeDifference = targetTime - currentTime;

  if (timeDifference > 0) {
    const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

    formattedTime.value = `${days}天 ${hours}小时 ${minutes}分钟 ${seconds}秒`;
  } else {
    formattedTime.value = '秒杀已开始';
    isClick.value = false;
  }
}


onMounted(() => {
  console.log("轮播：", store2.getMenuData);
  console.log("轮播3333333：", activeIndex);
  console.log("图片！！！！！！！！！:", store2.getMenuData[activeIndex].couponImg);
  console.log("时间！！！！！！！！！:", store2.getMenuData[activeIndex].startTime);
  loopData.value = store2.getMenuData;

  calculateTimeRemaining(); // 初次计算

  const interval = setInterval(() => {
    calculateTimeRemaining();
  }, 1000);

  onUnmounted(() => {
    clearInterval(interval); // 清除定时器，防止内存泄漏
  });
});

const spikes = ()=>{
  store2.getMenuData[activeIndex].totalNum = store2.getMenuData[activeIndex].totalNum - num.value;
  store2.setMenu(store2.getMenuData);
  let updateCoupon = store2.getMenuData[activeIndex];
  console.log("TTTTTTTTTTTTT:", updateCoupon);
  axios.post('http://localhost:8889/activity/decreaseNum',updateCoupon,{
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

const num = ref(1);
const isClick = ref(true)

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>Header</el-header>
      <el-main style="width: auto; border: 2px solid wheat;">
        <div id="wrapper">
          <el-image :src="store2.getMenuData[activeIndex].couponImg" style="width: 500px;height: 500px;"></el-image>
          <div class="content" style="font-size: 20px">
            <span>秒杀开始时间：</span>
            <span>{{ formatDate(store2.getMenuData[activeIndex].startTime) }}</span>
            <div style="margin: 10px 0;">
              <span>倒计时：</span>
              <span>{{ formattedTime }}</span>
            </div>
            <span style="color: black">秒杀价：</span>
            <span>{{ store2.getMenuData[activeIndex].currentPrice }}</span>
            <div style="margin-top: 10px">
              <span style="color: black">购买数量：</span>
              <el-input-number v-model="num" :min="1" :max="1"/>
              <span>( 库存：{{ store2.getMenuData[activeIndex].totalNum }} )</span>
            </div>
            <el-button type="primary" @click="spikes">立即秒杀</el-button>
          </div>
        </div>

      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
#wrapper {
  display: flex;
}

.content {
  margin-left: 100px;
}

.content span {
  color: red;
}

</style>