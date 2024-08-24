import { Base64 } from "js-base64";
import { ElMessage } from "element-plus";
const toStr = Object.prototype.toString;
export function isUndef(v) {
  return v === undefined || v === null;
}

export function isDef(v) {
  return v !== undefined && v !== null;
}

export function isTrue(v) {
  return v === true;
}

export function isFalse(v) {
  return v === false;
}
export function isRegExp(v) {
  return toStr.call(v) === "[object RegExp]";
}
export function isArray(v) {
  return toStr.call(v) === "[object Array]";
}
export function isString(v) {
  return toStr.call(v) === "[object String]";
}
export function isObject(v) {
  return toStr.call(v) === "[object Object]";
}
export function isFunction(v) {
  return toStr.call(v) === "[object Function]";
}
export function isNumber(v) {
  return toStr.call(v) === "[object Number]";
}

/**
 * 权限判断树的层级
 * @param {*} areaId //传进来的行政区域ID  string类型
 * @returns
 */
export function getAreaIdNumber(areaId) {
  if (areaId.substr(-6) * 1 === 0) {
    return 3;
  } else if (areaId.substr(-4).substring(0, 2) * 1 > 0 && areaId.substr(-2) * 1 === 0) {
    return 2;
  } else if (areaId.substr(-4).substring(0, 2) * 1 > 0 && areaId.substr(-2) * 1 > 0) {
    return 1;
  } else {
    return 0;
  }
}
/**
 * 权限判断页面的层级
 * @param {*} areaId //传进来的行政区域ID  string类型
 * @returns
 */
export function getAreaIdLevel(areaId) {
  if (areaId.substr(-6) * 1 === 0) {
    return "市级";
  } else if (areaId.substr(-4).substring(0, 2) * 1 > 0 && areaId.substr(-2) * 1 === 0) {
    return "县区级";
  } else if (areaId.substr(-4).substring(0, 2) * 1 > 0 && areaId.substr(-2) * 1 > 0) {
    return "镇街级";
  } else {
    return "返回的数据导致报错";
  }
}
/**
 * 权限判断页面的层级
 * @param {*} areaId //传进来的行政区域ID  string类型
 * @returns
 */
export function getAreaIdString(areaId) {
  if (areaId.substr(-6) * 1 === 0) {
    return "municipal";
  } else if (areaId.substr(-4).substring(0, 2) * 1 > 0 && areaId.substr(-2) * 1 === 0) {
    return "county";
  } else if (areaId.substr(-4).substring(0, 2) * 1 > 0 && areaId.substr(-2) * 1 > 0) {
    return "town";
  } else {
    return "";
  }
}
/**
 * 移除数组项
 */
export function remove(arr, item) {
  if (arr.length) {
    const index = arr.indexOf(item);
    if (index > -1) {
      return arr.splice(index, 1);
    }
  }
  return arr;
}
/**
 */
export function matches(pattern, name) {
  if (Array.isArray(pattern)) {
    return pattern.indexOf(name) > -1;
  } else if (typeof pattern === "string") {
    return pattern.split(",").indexOf(name) > -1;
  } else if (isRegExp(pattern)) {
    return pattern.test(name);
  }
  return false;
}

export function replacePlaceholder(str = "", pattern, handleCallback) {
  return str.replace(pattern, v => {
    const key = v.slice(1);
    return handleCallback(key);
  });
}

export function replaceUrlPlaceholder(url, handleCallback) {
  return replacePlaceholder(url, /:[a-zA-Z]+/g, handleCallback);
}

function capitalizeFirstLetter(string) {
  return string.charAt(0) + string.slice(1);
}
export function handleRequireContext(requireContext, handle) {
  requireContext.keys().forEach(key => {
    const context = requireContext(key);
    // 因为得到的file格式是: `./filename.js`, 所以这里我们去掉头和尾，只保留真正的文件名: `filename`
    const name = capitalizeFirstLetter(key.replace(/^\.\//, "").replace(/\.\w+$/, ""));
    if (handle) handle(name, context);
  });
}
export function isExternal(path) {
  return /^[a-z|A-Z]*:\/\//.test(path);
}

export const requestAnimFrame =
  window.requestAnimationFrame ||
  window.webkitRequestAnimationFrame ||
  window.mozRequestAnimationFrame ||
  window.msRequestAnimationFrame ||
  function raf(callback) {
    return setTimeout(callback, 1000 / 60);
  };
export const cancelAnimFrame =
  window.cancelAnimationFrame ||
  window.webkitCancelAnimationFrame ||
  window.mozCancelAnimationFrame ||
  window.msCancelAnimationFrame ||
  function raf(timer) {
    window.clearTimeout(timer);
  };
export const scrollToTop = (el, animation = false) => {
  if (!animation) {
    el.scrollTop = 0;
    return;
  }
  const anim = () => {
    if (el.scrollTop <= 0) return;
    el.scrollTop -= 30;
    requestAnimFrame(anim);
  };
  requestAnimFrame(anim);
};
export const parseJWT = jwt => {
  if (!jwt) return null;
  const jwtArr = jwt.split(".");
  if (!jwtArr[1]) return null;
  const payload = JSON.parse(Base64.decode(jwtArr[1]));
  if (!isObject(payload)) return null;
  return payload;
};

/**
 * 根据身份证号码计算取年龄
 *
 * @param idCard 身份证号
 * @returns {number} 年龄
 */
export function getAgeByIdCard(idCard) {
  let age = 0;
  if (idCard !== null && idCard !== "") {
    // 获取年龄
    const myDate = new Date();
    const month = myDate.getMonth() + 1;
    const day = myDate.getDate();
    age = myDate.getFullYear() - idCard.substring(6, 10) - 1;
    if (idCard.substring(10, 12) < month || (idCard.substring(10, 12) === month && idCard.substring(12, 14) <= day)) {
      age += 1;
    }
  }
  return age;
}
/*
 ** 时间戳转换成指定格式日期
 ** eg.
 ** dateFormat(11111111111111, 'Y年m月d日 H时i分')
 ** → "2322年02月06日 03时45分"
 */
export const dateFormat = (timestamp, formats) => {
  // formats格式包括
  // 1. Y-m-d
  // 2. Y-m-d H:i:s
  // 3. Y年m月d日
  // 4. Y年m月d日 H时i分
  formats = formats || "Y-m-d";

  var zero = function (value) {
    if (value < 10) {
      return "0" + value;
    }
    return value;
  };

  var myDate = timestamp ? new Date(timestamp) : new Date();

  var year = myDate.getFullYear();
  var month = zero(myDate.getMonth() + 1);
  var day = zero(myDate.getDate());

  var hour = zero(myDate.getHours());
  var minite = zero(myDate.getMinutes());
  var second = zero(myDate.getSeconds());

  return formats.replace(/Y|m|d|H|i|s/gi, function (matches) {
    return {
      Y: year,
      m: month,
      d: day,
      H: hour,
      i: minite,
      s: second,
    }[matches];
  });
};
//获取数组中最大值接近的整数
export const setYAxisMaxVal = (arrayF, maxValue = 100) => {
  const flatten = arr => {
    while (arr.some(item => Array.isArray(item))) {
      arr = [].concat(...arr);
    }
    return arr;
  };
  const returnMaxValue = (array, referMax) => {
    try {
      if (Math.max(...array.flat(Infinity)) > referMax) {
        referMax = referMax - (Math.max(...array.flat(Infinity)) % referMax) + Math.max(...array.flat(Infinity));
      }
      return referMax;
    } catch (error) {
      if (Math.max(...flatten(array)) > referMax) {
        referMax = referMax - (Math.max(...flatten(array)) % referMax) + Math.max(...flatten(array));
      }
      return referMax;
    }
  };
  maxValue = returnMaxValue(arrayF, maxValue);
  return maxValue;
};

//获取数组中最小值接近的整数
export const setYAxisMinVal = (arrayF, minValue = 100) => {
  // const flatten = (arr) => {
  //     while (arr.some((item) => Array.isArray(item))) {
  //         arr = [].concat(...arr);
  //     }
  //     return arr;
  // };
  const returnMinValue = (array, referMin) => {
    try {
      let min = Math.min(...array.flat(Infinity));
      if (min > 0) {
        referMin = min - (min % referMin);
      } else {
        if (Math.abs(min) < minValue) {
          referMin = min - ((minValue - Math.abs(min)) % minValue);
        } else {
          referMin = min - ((minValue - Math.abs(min)) % minValue) - minValue;
        }
      }
      return referMin;
    } catch (error) {
      //console.log(error)
    }
  };
  minValue = returnMinValue(arrayF, minValue);
  return minValue;
};
// 去除参数中的空值
export const delUndefined = ob => {
  for (let e in ob) {
    if (typeof ob[e] === "undefined" || ob[e] === null || ob[e] === "") {
      delete ob[e];
    } else if (ob[e].constructor === Object) {
      if (Object.keys(ob[e]).length === 0) {
        delete ob[e];
      } else {
        delUndefined(ob[e]);
      }
    } else if (ob[e].constructor === Array) {
      ob[e].map(function (seg) {
        if (typeof seg === "object") {
          delUndefined(seg);
        }
      });
    }
  }
  return ob;
};
/**
 * 判断是否为数组或者字符串
 */
/**
 *
 * @param {*} daJudgmentData 判断数据
 * @param {*} confirmData  确定数据
 * @param {*} replaceData  替换数据
 * @returns
 */
export function dataType(daJudgmentData, confirmData, replaceData) {
  if (daJudgmentData == "") {
    return confirmData;
  } else if (daJudgmentData == null) {
    return confirmData;
  } else if (daJudgmentData == undefined) {
    return confirmData;
  } else if (daJudgmentData.length == 0) {
    return confirmData;
  } else {
    return replaceData;
  }
}
/**
 * 获取文件信息
 *
 * @param fileUrl 文件地址
 * @returns {[]|*[]} 处理后的文件地址
 */
export function getFileList(fileUrl) {
  if (!fileUrl) {
    return [];
  }
  const fileList = [];
  const fileUrlList = fileUrl.split(",");
  for (let i = 0; i < fileUrlList.length; i += 1) {
    fileUrlList[i] = `${commonConst.filePath}${fileUrlList[i]}`;
    const suffix = fileUrlList[i].split("/");
    //console.log(fileUrlList[i])
    const file = {
      name: suffix[suffix.length - 1],
      url: `http://${fileUrlList[i]}`,
    };
    fileList.push(file);
  }
  //console.log(fileList)
  return fileList;
}

/**
 * 将上传附件的共同路径删除删掉
 *
 * @param filePathList
 */
export function fileFormat(filePathList) {
  //console.log(filePathList)
  let fileFormatList = [];
  //console.log(fileFormatList)
  fileFormatList = [];
  for (let i = 0; i < filePathList.length; i += 1) {
    console.log(filePathList[i], commonConst.filePath);
    if (filePathList[i].indexOf(commonConst.filePath) < 0) {
      fileFormatList.push(filePathList[i].substring(commonConst.filePath.length + 7, filePathList[i].length));
    } else {
      fileFormatList.push(filePathList[i]);
    }
  }
  console.log(fileFormatList);
  return fileFormatList;
}

/**
 * 移除数组项
 * @param {*} arr 数组
 * @param {*} item 删除项目
 */
export function removeArray(arr, item) {
  //console.log(arr, item)
  for (let i = 0; i < arr.length; i += 1) {
    if (arr[i].indexOf(item) !== -1) {
      arr.splice(i, 1);
      return;
    }
  }
}

/**
 * api请求异常处理方法
 *
 * @param e 异常信息
 */
export function errorMessage(e) {
  if (e.response.status.substring(0, 1) === "4") {
    return;
  }
  if (e.response.data.code) {
    ElMessage({
      type: "error",
      message: e.response.data.msg,
      offset: "100",
    });
  } else {
    ElMessage({
      type: "error",
      message: e.response.data.error,
      offset: "100",
    });
  }
}
