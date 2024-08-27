/**
 * 接口封装
 */
import axios from 'axios';
import { isObject, isArray, isNumber, isString, replaceUrlPlaceholder, isExternal } from '../util';

const toMethod = options => ( 
  params = {},
  config = {},
) => {
  let { url } = options;
  if (!url) return Promise.reject();
  const defaultParams = options.params || null;
  const method = options.method || 'get';
  url = replaceUrlPlaceholder(url, (key) => {
    if (isNumber(params) || isString(params)) {
      return params;
    } else if (isObject(params)) {
      const val = params[key];
      if (val) {
        params[key] = undefined;
        return val;
      }
    }
    return key;
  });
  let data = {};
  if(isArray(params)){
    data = params;
  }
  if (params[''] && isArray(params[''])) {
    data = params[''];
  } else if (isObject(params)) {
    data = params;
  }
  if (defaultParams) {
    Object.keys(defaultParams).forEach((k) => {
      data[k] = defaultParams[k];
    });
  }
  if (isExternal(url)) {
    config.baseURL = url;
    url = '';
  }
  return ['post', 'put', 'patch'].indexOf(method) > -1 ? axios[method](url, data, config) : axios[method](url, Object.assign(config, { params: data }));
};

const toMethods = (actions) => {
  const facade = {};
  Object.keys(actions).forEach((key) => {
    facade[key] = toMethod(actions[key]);
  });
  return facade;
};

const generateApiMap = () => {
  const facade = {};
  const allFiles = import.meta.glob('./api/**/*.js', { eager: true });

  for (const key in allFiles) {
    const module = allFiles[key]; // 直接获取模块对象
    console.log(key);
    console.log(module); // 输出模块内容
    console.log(typeof module); // 查看模块内容的类型

    if (module) {
      const obj = module.default; // 获取模块的默认导出

      if (obj) {
        // 如果 obj 是对象，直接处理其方法
        Object.keys(obj).forEach((methodKey) => {
          facade[methodKey] = !obj[methodKey].url ? toMethods(obj[methodKey]) : toMethod(obj[methodKey]);
        });
      }
    }
  }
  return facade;
};

const api = generateApiMap();
export default api;


