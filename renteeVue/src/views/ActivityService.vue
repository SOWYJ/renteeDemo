<script setup lang="ts">

import axios from "axios";
import {onMounted, ref} from "vue";
import {ElMessage, UploadInstance, UploadProps, UploadUserFile} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'


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
  // const date = new Date(coupon.value.startTime);
  // const isoStr = date.toISOString();
  // console.log(isoStr);
  // const strWithoutLetters = isoStr.replace(/[a-zA-Z]/g, ' ');
  // console.log(strWithoutLetters);

  console.log("数据啊啊啊啊：", coupon.value);
  axios.post('http://localhost:8083/saveCoupon', coupon.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(() => {
    // console.log("响应：", res.data.startTime.replace("T"," "));
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
      message: "保存失败！",
      showClose: true,
      grouping: true,
    })
    // console.log("失败：", error);
  }).finally(() => {
    dialogVisible.value = false;
    getAllCoupons();
  })
};

onMounted(() => {
  getAllCoupons();
});
const couponList = ref([]);
const getAllCoupons = () => {
  axios.get('http://localhost:8083/getAllCoupons')
      .then(res => {
        res.data.forEach((re: any) => {
          re.startTime = re.startTime.replace(".000Z", "");
          re.startTime = re.startTime.replace("T", " ");
          re.endTime = re.endTime.replace("T", " ");
          re.endTime = re.endTime.replace(".000Z", "");
        })
        couponList.value = res.data
      })
};
const handleDelete = (index: any, row: any) => {
  axios.post('http://localhost:8083/deleteCoupon', row, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(() => {
    ElMessage({
      type: "success",
      message: "删除成功！",
      showClose: true,
      grouping: true,
    });
  }).catch(() => {
    ElMessage({
      type: "error",
      message: "操作失败！",
      showClose: true,
      grouping: true,
    });
  }).finally(() => {
    getAllCoupons();
  })
};
const f = ref<UploadUserFile[]>([
  {
    name: Math.random().toString(),
    url: ''
  }
])

const updateCoupon = ref();
const updateVisible = ref(false);
const handleEdit = (index: any, row: any) => {
  console.log("编辑：", row);
  updateCoupon.value = row;
  f.value.forEach(img=>{
    img.url = row.couponImg
  });
  console.log("OOOOOOOOOOOO:", updateCoupon.value);
  console.log("图片22222：", updateCoupon.value.couponImg);
  updateVisible.value = true;
};

const updateData = () => {
  axios.post('http://localhost:8083/updateCoupon', updateCoupon.value, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(() => {
    ElMessage({
      type: "success",
      message: "修改成功！",
      showClose: true,
      grouping: true,
    });
  }).catch(() => {
    ElMessage({
      type: "error",
      message: "修改失败！",
      showClose: true,
      grouping: true,
    });
  }).finally(() => {
    updateVisible.value = false;
    getAllCoupons();
  })
};

const fileList = ref<UploadInstance>();

const handleAvatarSuccess2: UploadProps['onSuccess'] = (response) => {
  console.log("上传回调:", response);
  updateCoupon.value.couponImg = response
};
const handleAvatarSuccess: UploadProps['onSuccess'] = (response) => {
  console.log("上传回调:", response);
  coupon.value.couponImg = response
};
const handleRemove = () => {
  coupon.value.couponImg = "";
};
const handleRemove2 = () => {
  updateCoupon.value.couponImg = "";
};

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
          <el-table-column fixed="right" width="165" label="操作">
            <template #default="scope">
              <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
                Edit
              </el-button>
              <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
              >
                Delete
              </el-button>
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

      <el-col :span="5">
        <el-form-item label="描述" label-width="50px">
          <el-input
              v-model="coupon.couponContent"
              style="width: 540px"
              :rows="5"
              type="textarea"
              placeholder="请输入："
          />
        </el-form-item>
      </el-col>

      <el-upload
          :file-list="fileList"
          action="http://localhost:8081/upload"
          list-type="picture-card"
          :on-success="handleAvatarSuccess"
          :on-remove="handleRemove"
          :limit="1"

      >
        <el-icon>
          <Plus/>
        </el-icon>
      </el-upload>

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

  <!--  数据修改-->
  <el-dialog class="dia" v-model="updateVisible" title="修改活动" width="1000" draggable>
    <el-form :model="updateCoupon">
      <el-row :gutter="20">
        <el-col :span="5">
          <el-form-item label="定价" label-width="50px">
            <el-input v-model="updateCoupon.firstPrice" autocomplete="off"/>
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="10">
          <el-form-item label="现价" label-width="50px">
            <el-input v-model="updateCoupon.currentPrice" autocomplete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-form-item label="库存" label-width="50px">
            <el-input v-model="updateCoupon.totalNum" autocomplete="off"/>
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="10">
          <el-form-item label="销量" label-width="50px">
            <el-input v-model="updateCoupon.sales" autocomplete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-form-item label="是否特价" label-width="100px">
            <el-input v-model="updateCoupon.isSpecial" autocomplete="off"/>
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="10">
          <el-form-item label="是否秒杀" label-width="100px">
            <el-input v-model="updateCoupon.isKill" autocomplete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-col :span="5" :offset="10">
        <el-form-item label="描述" label-width="100px">
          <el-input
              v-model="updateCoupon.couponContent"
              style="width: 540px"
              :rows="5"
              type="textarea"
              placeholder="请输入："
          />
        </el-form-item>
      </el-col>
      <div>
        <el-form-item label="秒杀开始时间" label-width="150px">
          <el-date-picker
              v-model="updateCoupon.startTime"
              type="datetime"
              placeholder="开始时间"
          />
        </el-form-item>
        <el-form-item label="秒杀结束时间" label-width="150px">
          <el-date-picker
              v-model="updateCoupon.endTime"
              type="datetime"
              placeholder="结束时间"
          />
        </el-form-item>
      </div>

      <el-upload
          v-model:file-list="f"
          action="http://localhost:8081/upload"
          list-type="picture-card"
          :on-success="handleAvatarSuccess2"
          :on-remove="handleRemove2"
          :limit="1"
      >
        <el-icon>
          <Plus/>
        </el-icon>
      </el-upload>


    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="updateVisible = false">取消</el-button>
        <el-button type="primary" @click="updateData">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>

</style>