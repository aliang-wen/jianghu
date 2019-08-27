import { WEATHERAPI, API, TitleUploadPath, PageUploadPath } from '../api';

// 标题上传的路径
export const TitleUploadPathUrl = () => {
  return TitleUploadPath;
};
// 文章内容上传接口
export const PageUploadPathUrl = () => {
  return PageUploadPath;
};
export const oclock = () => {
  // 这是获取时间
  var str = '';
  var d = new Date();
  var year = d.getFullYear();
  var month = d.getMonth() + 1;
  var da = d.getDate();
  var week = d.getDay();
  var hour = d.getHours();
  var min = d.getMinutes();
  var sec = d.getSeconds();
  str += year + '年&nbsp;&nbsp;' + month + '月&nbsp;&nbsp;' + da + '日&nbsp;&nbsp;';
  var timeTitle = '';
  // 时分秒的获取
  if (hour >= 23) {
    timeTitle = '夜深了请注意休息';
  } else if (hour < 6) {
    timeTitle = '已经凌晨了庆祝休息';
  } else if (hour >= 6 && hour < 12) {
    timeTitle = '努力工作吧';
  } else if (hour >= 12 && hour < 14) {
    timeTitle = '珍惜中午休息时间';
  } else if (hour >= 14 && hour < 18) {
    timeTitle = '努力工作';
  } else if (hour >= 18 && hour < 23) {
    timeTitle = '愉快的夜生活';
  }
  if (hour > 12) {
    hour = hour - 12;
    if (hour >= 6) {
      str += '晚上&nbsp;&nbsp;';
    } else {
      str += '下午&nbsp;&nbsp;';
    }
  } else {
    if (hour >= 6) {
      str += '上午&nbsp;&nbsp;';
    } else {
      str += '凌晨&nbsp;&nbsp;';
    }
  }
  if (hour < 10) {
    str += '0';
  }
  str += hour + ':';
  if (min < 10) {
    str += '0';
  }
  str += min + ':';
  if (sec < 10) {
    str += '0';
  }
  str += sec + '&nbsp;&nbsp;';

  switch (week) {
    case 0:
      str += '星期日&nbsp;&nbsp;';
      break;
    case 1:
      str += '星期一&nbsp;&nbsp;';
      break;
    case 2:
      str += '星期二&nbsp;&nbsp;';
      break;
    case 3:
      str += '星期三&nbsp;&nbsp;';
      break;
    case 4:
      str += '星期四&nbsp;&nbsp;';
      break;
    case 5:
      str += '星期五&nbsp;&nbsp;';
      break;
    case 6:
      str += '星期六&nbsp;&nbsp;';
      break;
  }
  return str + '-' + timeTitle;
};

// 请求天气的方法
export const getWeatherFromUrl = params => {
  return hyNetWork.get(WEATHERAPI, params).then(res => res.data);
};

// 公共方法获取国家 省份 市区 县区
const findAllChianAreaFun = params => {
  return hyNetWork.post(API.findAllChianArea, params).then(res => res.data);
};
// 这些的是将返回的地址数据进行保存防止地址重复请求浪费资源
// 获取所属公司
export const findAllChianArea = (appId, callback) => {
  var allChinaArea = [];
  // 如果session里有所属公司，直接获取
  if (sessionStorage.allChinaArea !== null && sessionStorage.allChinaArea !== '' && sessionStorage.allChinaArea !== undefined) {
    allChinaArea = JSON.parse(sessionStorage.allChinaArea);
    callback(allChinaArea);
  } else { // 如果session里没有所属公司，查询所属公司
    findAllChianAreaFun({ dynamicParams: appId }).then(data => {
      console.log(data);
      if (data.result !== null && data.result !== undefined && data.statusCode === '200') {
        allChinaArea = data.result; // 所属公司(字典项)
        sessionStorage.allChinaArea = JSON.stringify(data.result); // 将所属公司数组存入session
        callback(allChinaArea);
      }
    });
  }
};
// 获取字典项
export const findDictionaryOptionList = params => {
  return hyNetWork.post(API.findDictionaryOptionList, params).then(res => res.data);
};
// 获取字典项
export const finDictionaryOptioinAllListCon = params => {
  return hyNetWork.post(API.finDictionaryOptioinAllListCon, params).then(res => res.data);
};
// 时间戳转变成时间（HHHH-MM-DD hh:mm:ss）
export const formatTime = (stamp) => {
  if (stamp !== null && stamp !== undefined && stamp !== '') {
    var date = new Date(stamp);
    var y = date.getFullYear(); // 年
    var m = date.getMonth() + 1; // 月
    var d = date.getDate(); // 日
    var h = date.getHours(); // 时
    var i = date.getMinutes(); // 分
    var s = date.getSeconds(); // 秒
    m = m < 10 ? ('0' + m) : m;
    d = d < 10 ? ('0' + d) : d;
    h = h < 10 ? ('0' + h) : h;
    i = i < 10 ? ('0' + i) : i;
    s = s < 10 ? ('0' + s) : s;
    return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
  }
  return '';
};
// 时间戳转变成时间（HHHH-MM-DD）
export const formatDate = (stamp) => {
  if (stamp !== null && stamp !== undefined && stamp !== '') {
    var date = new Date(stamp);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
  }
  return '';
};
