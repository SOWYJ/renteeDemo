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
  const allFiles =  import.meta.glob("./api/**.js",{eager:true})
  for(const key in allFiles){
    console.log(key);
    allFiles[key]().then(mod =>{
      const obj = mod.default ;
       if(obj){   
          Object.keys(obj).forEach((key) => {
            facade[key] = !obj[key].url ? toMethods(obj[key]) : toMethod(obj[key]);
          });
       }
    })
  }
    return facade;
};


const api = generateApiMap();
// Vue.prototype.$api = api;
export default api;
