<template>
  <el-form :model="form.model"
           :rules="rules || {}"
           ref="ruleFormRef"
           v-bind="{...form.innerAttrs}"
  >
    <el-row>
      <el-col :span="24">
        <slot name="header"></slot>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <slot name="preppend"></slot>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col v-for="(item, index) in form.formItems" :key="index" :span="item.span"
              :class="item.hide?'hidden-lg-and-down':''">
        <el-form-item :label="item.label" :prop="item.prop">
          <!-- 根据 item.type 动态渲染不同类型的表单控件 -->
          <component
              v-if="item.type === 'input'"
              :is="getFormItemComponent('input')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              v-model.trim="form.model[item.prop]"
              :rules="item.rules"
              v-on="item.events || {}"
              clearable
          >
          </component>
          <component
              v-if="item.type === 'number'"
              :is="getFormItemComponent('number')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              v-model.number="form.model[item.prop]"
              :rules="item.rules"
              v-on="item.events || {}"
              clearable
          >
          </component>
          <component
              v-if="item.type === 'textarea'"
              :is="getFormItemComponent('textarea')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              v-model="form.model[item.prop]"
              :rules="item.rules"
              clearable
          >
          </component>
          <component
              v-else-if="item.type === 'select'"
              :is="getFormItemComponent('select')"
              :label="item.label"
              :prop="item.prop"
              :disabled="item.disabled || form.innerAttrs.readonly"
              v-model="form.model[item.prop]"
              :rules="item.rules"
              :multiple="item.multiple || false"
              :allow-create="item.allowCreate || false"
              :default-first-option="item.defaultFirstOption || false"
              :collapseTags="item.collapseTags || false"
              :collapseTagsTooltip="item.collapseTagsTooltip || false"
              :placeholder="item.placeholder || '请选择'"
              :filterable="item.filterable||false"
              v-on="item.events || {}"
              clearable
          >
            <el-option v-for="option in item"
                       :key="option.value"
                       :label="option.label"
                       :value="option.value">
            </el-option>
          </component>
          <component
              v-else-if="item.type === 'radio'"
              :is="getFormItemComponent('radio')"
              v-model="form.model[item.prop]"
              :rules="item.rules"
              v-on="item.events || {}"
              :disabled="item.disabled || form.innerAttrs.readonly"
          >
            <el-radio v-for="(option,index) in item.options"
                      :label="option.value"
                      :name="item.prop"
                      :size="item.size || 'small'">
              {{ option.label }}
            </el-radio>
          </component>
          <component
              v-else-if="item.type === 'checkbox'"
              :is="getFormItemComponent('checkbox')"
              v-model="form.model[item.prop]"
              :rules="item.rules"
              v-on="item.events || {}"
              :disabled="item.disabled || form.innerAttrs.readonly"
          >
            <el-checkbox v-for="(option,index) in item.options"
                         :label="option.value || option.label"
                         :name="item.prop"
                         :size="item.size || 'small'">
              {{ option.label }}
            </el-checkbox>
          </component>
          <component
              v-else-if="item.type === 'date'"
              :is="getFormItemComponent('datePicker')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              :prop="item.prop"
              v-model="form.model[item.prop]"
              v-on="item.events || {}"
              :rules="item.rules"
          >
          </component>
          <component
              v-else-if="item.type ==='daterange'"
              :is="getFormItemComponent('dateRange')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              range-separator=" ~ "
              start-placeholder="年-月-日"
              end-placeholder="年-月-日"
              :prop="item.prop"
              v-model="form.model[item.prop]"
              :rules="item.rules"
              value-format="YYYY-MM-DD"
          >
          </component>
          <component
              v-else-if="item.type === 'time'"
              :is="getFormItemComponent('timePicker')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              :prop="item.prop"
              v-model="form.model[item.prop]"
              v-on="item.events || {}"
              :rules="item.rules"
          >
          </component>
          <component
              v-else-if="item.type === 'datetime'"
              :is="getFormItemComponent('datetime')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              :prop="item.prop"
              v-model="form.model[item.prop]"
              v-on="item.events || {}"
              :rules="item.rules"
          >
          </component>
          <component
              v-else-if="item.type === 'datetimerange'"
              :is="getFormItemComponent('datetimeRange')"
              v-bind="{...item, readonly:form.innerAttrs.readonly}"
              :prop="item.prop"
              v-model="form.model[item.prop]"
              v-on="item.events || {}"
              :rules="item.rules"
          >
          </component>
          <component
              v-else-if="item.type === 'upload'"
              :is="getFormItemComponent('upload')"
              :label="item.label"
              :prop="item.prop"
              :limit="item.limit || 1"
              v-model="form.model[item.prop]"
              :rules="item.rules"
              :disabled="form.innerAttrs.readonly"
          >
          </component>
          <component
              v-else-if="item.type === 'slot'"
              :is="getFormItemComponent('slot')"
              :label="item.label"
              :style="item.style"
              :class="item.class"
              :span="24"
          >
            <template #default="scope">
              <slot :name="item.prop" :age="100" v-bind="{...scope}"></slot>
            </template>
          </component>
          <component
              v-else-if="item.type === 'blank'"
              :is="getFormItemComponent('blank')"
              :span="24"
          >
          </component>
          <component
              v-else-if="item.type === 'divider'"
              :is="getFormItemComponent('divider')"
              :span="24"
          >
          </component>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row
        :style="{'text-align': (form.buttonsAttrs? form.buttonsAttrs.align || 'center' : 'center'),'align-items': 'center'}">
      <el-col :span="24">
        <!-- 表单底部的操作按钮区域 -->
        <slot name="footer"></slot>
      </el-col>
    </el-row>
  </el-form>
</template>

<script setup lang="ts">
import {
  ref,
  reactive,
  toRef
} from "vue";
import {
  ElInput,
  ElSelect,
  ElRadioGroup,
  ElCheckboxGroup,
  ElButton,
  ElDatePicker,
  ElTimePicker,
  ElUpload,
  ElCol,
  ElDivider,
  FormInstance,
} from 'element-plus';


//属性定义
const props = defineProps<{
  form: Object,
  rules?: Object,
  formRef?: Object
}>()


// const formRef = toRef(props, 'formRef');
const event = () => {
}

// 获取表单控件组件的方法
function getFormItemComponent(type: string) {
  switch (type) {
    case 'input':
      return ElInput;
    case 'number':
      return ElInput;
    case 'textarea':
      return ElInput;
    case 'select':
      return ElSelect;
    case 'radio':
      return ElRadioGroup;
    case 'checkbox':
      return ElCheckboxGroup;
    case 'datePicker':
      return ElDatePicker;
    case 'dateRange':
      return ElDatePicker;
    case 'timePicker':
      return ElTimePicker;
    case 'datetime':
      return ElDatePicker;
    case 'datetimeRange':
      return ElDatePicker;
    case 'button':
      return ElButton;
    case 'upload':
      return ElUpload;
    case 'blank':
      return ElCol;
    case 'slot':
      return ElCol;
    case 'divider':
      return ElDivider;
      // ...其他类型的情况
  }
}

const ruleFormRef = ref<FormInstance>();

const validateForm = async () => {
  try {
    return await ruleFormRef.value.validate();
  } catch (error) {
    return false;
  }
}

const resetFields = async () => {
  ruleFormRef.value.clearValidate();
  ruleFormRef.value.resetFields();
}

const clearValidate = async (fields: []) => {
  ruleFormRef.value.clearValidate(fields);
}

const validateField = async (fields: []) => {
  ruleFormRef.value.validateField(fields);
}

defineExpose({
  validateForm,
  resetFields,
  clearValidate,
  validateField
});

</script>

<style scoped>

</style>
