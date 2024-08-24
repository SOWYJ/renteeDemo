import axios from "axios";
import { ElMessage, ElMessageBox, ElNotification, ElLoading } from "element-plus";
import { API_HOST, API_BASE_URL } from "../config";
axios.defaults.baseURL = API_HOST + API_BASE_URL;
axios.defaults.headers.post["Content-Type"] = "application/json;charset=UTF-8";

const loading = (() => {
  let loadingInstance = null;
  return {
    show(text) {
      loadingInstance = ElLoading.service({
        lock: true,
        text,
        background: "rgba(0, 0, 0, 0.6)",
      });
    },
    close() {
      loadingInstance.close();
    },
  };
})();

/**
 * request 拦截
 */
axios.interceptors.request.use(
  config => {
    loading.show("加载中");
    return config;
  },
  error => {
    ElMessage.error({
      message: "加载超时",
    });
    Promise.reject(error);
  }
);
/**
 * respone 拦截
 */
const responseStatusHandler = {

  401: () => {
    ElMessageBox.alert("接口没有权限访问", {
      type: "error",
      showClose: false,
      closeOnPressEscape: false,
      confirmButtonText: "重新登录",
    }).then(() => {
      event.emit("logout");
    });
  },
  402: () => {
    event.emit("logout");
  },
  403: () => {
    event.emit("logout");
  },
  404: () => {
    ElNotification.error({
      title: "错误",
      message: "当前应用不支持这个功能",
    });
  },
};
axios.interceptors.response.use(
  response => {
    loading.close();
    return response;
  },
  error => {
    loading.close();
    // 默认除了2XX之外的都是错误的，就会走这里
    if (error.response) {
      const { status } = error.response;
      const handlerFunc = responseStatusHandler[status];
      if (handlerFunc) handlerFunc();
      else {
        ElMessage.error({
          title: error.status,
          message: error.response.data.msg || error.response.data.message || error.message,
        });
      }
    }
    return Promise.reject(error);
  }
);
export default axios;
