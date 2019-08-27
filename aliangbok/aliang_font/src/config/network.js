/**
 * 作用：封装axios方法的地方
 */
import qs from 'qs';
import http from './http';

// get方式的数据处理
const getParams = (parms, isMock) => {
  let query = '';
  let log = '';
  if (parms && hy.base.isString(parms)) { // 当parms为字符串的时候
    query = '/' + parms;
    log = '动态参数';
  } else if (parms && hy.base.isArray(parms)) { // 当parms为数组的时候
    if (hy.base.isObject(parms[parms.length - 1])) {
      query = '/' + parms.slice(0, -1).join('/') + '?' + qs.stringify(hy.base.objDisEmpty(parms[parms.length - 1])); // objDisEmpty取出空键值对
    } else {
      query = '/' + parms.join('/');
    }
    log = '动态参数';
  } else if (parms && hy.base.isObject(parms) && Object.keys(parms).length) { // 当parms为对象的时候
    query = '?' + qs.stringify(hy.base.objDisEmpty(parms));
    log = '查询字符串参数';
  }
  if (isMock) {
    console.info(`Mock数据 ${log}:`, parms);
  }
  return query;
};
// post方式的数据处理
const postParams = (parms, isMock, isJSON) => {
  let dynamicParams = '';
  if (parms && hy.base.isObject(parms) && parms.hasOwnProperty('dynamicParams')) { // 如果存在appi就取出来
    const dyncParams = parms['dynamicParams'] || '';
    if (dyncParams && hy.base.isString(dyncParams)) {
      dynamicParams = '/' + dyncParams;
    } else if (dyncParams && hy.base.isArray(dyncParams)) {
      dynamicParams = '/' + dyncParams.join('/');
    }
    delete parms.dynamicParams; // 删除cuuid的选项
    if (isMock) {
      console.info(`Mock数据 动态参数是:`, dyncParams);
    }
  }
  const queryStr = parms ? isJSON ? hy.base.objDisEmpty(parms) : qs.stringify(hy.base.objDisEmpty(parms)) : {};
  return {
    dynamicParams, // appid
    queryStr // 参数
  };
};

// 这里进行封装
class HyNetworking {
  constructor(isMock = false, isJSON = true) {
    this.isMock = isMock;
    this.isJSON = isJSON;
  }
  // 这里是get的数据处理
  get(url, parms, isUrl) { // 地址 参数 ？？
    const query = getParams(parms, this.isMock);
    return isUrl ? url + query : http.axiosInstance.get(url + query);
  }
  // 这里是post的数据处理
  post(url, parms, headers = {}) {
    const {
      dynamicParams,
      queryStr
    } = postParams(parms, this.isMock, this.isJSON);
    return http.axiosInstance.post(url + (this.isMock ? '' : dynamicParams), queryStr, headers); // 这里对地址进行了拼接和调用方法
  }
}

export default HyNetworking;
