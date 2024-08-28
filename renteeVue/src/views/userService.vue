<script setup lang="ts">

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
            <el-text class="text2">更多></el-text>
          </div>
        </div>
        <div class="main-content">
          <div class="Logos"></div>
          <div class="Logos"></div>
          <div class="Logos"></div>
          <div class="Logos"></div>
          <div class="Logos"></div>
          <div class="Logos"></div>
          <div class="Logos"></div>
          <div class="Logos"></div>
        </div>
      </div>

      <div class="r-area">
        <div class="content" >
          <img src="../static/coupons.png" alt="" class="icon">
          <el-text class="text1">限时优惠</el-text>
        </div>
        <div class="r-main">

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
</style>