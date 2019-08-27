/**
 *作者：阿良
 * 作用：配置axios信息的地方
 */

import axios from 'axios';
import router from '../router';

// 这里采用axios创建实例的方法来 创建一个自己添加了配置的axios
var defaultHeaders = {
  Accept: 'application/json, text/plain, */*; charset=utf-8',
  // 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF8'
  'Content-Type': 'application/json;charset=UTF8'
};

// 这里全局配置axios  withCredentials = true;
// axios.defaults.withCredentials = true;

var axiosInstance = axios.create({
  timeout: 8 * 1000, // 设置请求的时间超过8秒报错
  headers: Object.assign(axios.defaults.headers.common, defaultHeaders)
});

// 请求拦截（配置发送请求的信息）
axiosInstance.interceptors.request.use(
  config => {
    // config.headers['accessToken'] = 'token';
    config._reqeustStartTimestamp = Date.now();
    config.headers['verifyToken'] = '123456789ceshikey';
    if (window.hy.db.sessionStorage.getValue('loginToken')) {
      config.headers['loginToken'] = window.hy.db.sessionStorage.getValue('accessToken') || sessionStorage.getItem('loginToken');
    }
    // 这里要对一个特殊的请求 就是天气请求 不是自己的接口 要除去请求头
    if (config.url.indexOf('weather_mini') > -1) {
      delete config.headers.verifyToken;
      delete config.headers.loginToken;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截（配置请求回来的信息）
axiosInstance.interceptors.response.use(
  response => {
    const da = response.data;
    if (da.statusCode === '603') { // 表示不能连接
      ModalBox.error({
        title: '请求失败',
        content: da.message,
        onOk: () => {
          router.push({
            path: '/404'
          });
        }
      });
      return Promise.reject({
        code: '603',
        message: da.message
      });
    }
    if (da.statusCode === '604') { // 表示没有登录
      ModalBox.error({
        title: 'Title',
        content: da.message,
        onOk: () => {
          router.push({
            path: '/login'
          });
        },
        onCancel: () => {
        }
      });
      return Promise.reject({
        code: '604',
        message: da.message
      });
    }
    return response;
  },
  error => {
    return Promise.reject(error);
  }
);

export default { axiosInstance }; // 默认导出的一个对象
