<script setup lang="ts">
import {onMounted, onUnmounted, ref} from "vue";
import {couponStore, numStore, useCarouselStore} from "@/store/menu";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useRoute} from "vue-router";

const store2 = couponStore();
const store3 = numStore();
const route = useRoute();

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


const index = ref()
const startTime = ref()
const currentPrice = ref()
let totalNum = {value: 0}
const couponImg = ref()


onMounted(() => {
  index.value = route.query.index;
  couponImg.value = route.query.couponImg;
  startTime.value = route.query.startTime;
  currentPrice.value = route.query.currentPrice;
  totalNum = route.query.totalNum;
  console.log("EEEEEEEEE:", startTime.value);
  console.log("DDDDDDDDDD:", currentPrice.value);
  console.log("RRRRRRRRR:", totalNum.value);
  console.log("YYYYYYYY:", index?.value);
  // $("#img").before("<el-image src='" + store2.getMenuData[loopData.value].couponImg + "'" +" style='width: 500px;height: 500px;z-index: 2'/>" )
  // console.log("<el-image src='" + store2.getMenuData[loopData.value].couponImg + "'" +" style='width: 500px;height: 500px;z-index: 2'/>")
  // $("#img").attr(':src',store2.getMenuData[loopData.value].couponImg)
  console.log("轮播：", store2.getMenuData);
  console.log("轮播3333333：", index?.value);
  console.log("图片！！！！！！！！！:", couponImg?.value);
  console.log("时间！！！！！！！！！:", startTime?.value);

  calculateTimeRemaining(); // 初次计算

  const interval = setInterval(() => {
    calculateTimeRemaining();
  }, 1000);

  onUnmounted(() => {
    clearInterval(interval); // 清除定时器，防止内存泄漏
  });
});

const spikes = () => {
  // totalNum.value -= 1;
  // store2.setMenu(store2.getMenuData);
  let updateCoupon = store2.getMenuData[index?.value];
  console.log("TTTTTTTTTTTTT:", updateCoupon);
  if (store2.getMenuData[index?.value].totalNum > 0) {
    axios.post('http://localhost:8889/activity/decreaseNum', updateCoupon, {
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(() => {
      store3.setNum(store2.getMenuData[activeIndex].totalNum)
      totalNum -= 1;
      // updateCoupon.totalNum -= 1;
      // store2.getMenuData[index?.value] = updateCoupon;
      // store2.setMenu(updateCoupon);
      ElMessage({
        type: "success",
        message: "购买成功！",
        showClose: true,
        grouping: true,
      });
    }).finally(() => {
      router.push('/content')
    })
  } else {
    router.push('/content');
    store3.setNum(store2.getMenuData[activeIndex].totalNum);
    ElMessage({
      type: "error",
      message: "库存紧张！",
      showClose: true,
      grouping: true,
    });
  }

}

// const spikes = () => {
//   store2.getMenuData[index?.value].totalNum = store2.getMenuData[index?.value].totalNum - num.value;
//   store2.setMenu(store2.getMenuData);
//   let updateCoupon = store2.getMenuData[index?.value];
//   console.log("TTTTTTTTTTTTT:", updateCoupon);
//   if (store2.getMenuData[index?.value].totalNum >= 0) {
//     axios.post('http://localhost:8889/activity/decreaseNum', updateCoupon, {
//       headers: {
//         'Content-Type': 'application/json'
//       }
//     }).then(() => {
//       updateCoupon.totalNum -= 1;
//       store2.getMenuData[index?.value] = updateCoupon;
//       store2.setMenu(updateCoupon);
//       store3.setNum(updateCoupon.totalNum)
//       ElMessage({
//         type: "success",
//         message: "购买成功！",
//         showClose: true,
//         grouping: true,
//       });
//     }).finally(() => {
//       router.push('/content')
//     })
//   } else {
//     store3.setNum(updateCoupon.totalNum);
//     router.push('/content');
//     ElMessage({
//       type: "error",
//       message: "库存紧张！",
//       showClose: true,
//       grouping: true,
//     });
//   }
//
// }

const num = ref(1);
const isClick = ref(true)

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>Header</el-header>
      <el-main style="width: auto; border: 2px solid wheat;">
        <div id="wrapper">
          <!--          <div id="img">-->

          <!--          </div>-->
          <!--          <el-image id="img"  style="width: 500px;height: 500px;"></el-image>-->
<!--          <el-image-->
<!--              v-if="store2.getMenuData && store2.getMenuData[index?.value]"-->
<!--              :src="store2.getMenuData[index?.value]?.couponImg"-->
<!--              style="width: 600px;height: auto;z-index: 2">-->
<!--          </el-image>-->
          <el-image
              v-if="couponImg"
              :src="couponImg"
              style="width: 600px;height: auto;z-index: 2">
          </el-image>
          <div class="content" style="font-size: 20px">
            <span>秒杀开始时间：</span>
<!--            <span>{{ formatDate(store2.getMenuData[activeIndex].startTime) }}</span>-->
            <span v-if="startTime != null">{{ formatDate(startTime) }}</span>
            <div style="margin: 10px 0;">
              <span>倒计时：</span>
              <span>{{ formattedTime }}</span>
            </div>
            <span style="color: black">秒杀价：</span>
<!--            <span>{{ store2.getMenuData[activeIndex].currentPrice }}</span>-->
            <span v-if="currentPrice">{{ currentPrice }}</span>
            <div style="margin-top: 10px">
              <span style="color: black">购买数量：</span>
              <el-input-number v-model="num" min="1" max="1"/>
<!--              <span>( 库存：{{ store2.getMenuData[activeIndex].totalNum > 0 ? store2.getMenuData[activeIndex].totalNum : 0  }} )</span>-->
              <span v-if="spikes">( 库存：{{totalNum - 1}} )</span>
              <span v-else>( 库存：{{totalNum}} )</span>
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