/**
 * 这里是配置api的地方
 */
import * as allApi from './api';

// const host = 'http://47.104.83.82:8080/personCloud';
// const host = 'http://localhost:8080/personCloud';
// 下面这里应该是发到一个服务器里面的采用这种方式
const host = process.env.NODE_ENV === 'production' ? 'http://ycolor.aliang.work:8080/personCloud' : 'http://localhost:8080/personCloud'; // 来判断是开发还是生产环境

const api = {};

Object.keys(allApi).map(e => {
  api[e] = allApi[e](host);
});

export const API = api; // 这里是指定导出 要使用名字才能导出  给network文下使用

export default { api }; // 这里是默认导出
// 天气的api
export const WEATHERAPI = 'http://wthrcdn.etouch.cn/weather_mini';
// 标题图片的上传接口
export const TitleUploadPath = host + '/back/uploadPictureTitle';
// 文章内容的上传接口
export const PageUploadPath = host + '/back/uploadPictureEssay';
// 图片展示的备份接口
export const picUrlBank = process.env.NODE_ENV === 'production' ? 'http://ycolor.aliang.work:8080/pictureFile/yushibujue.jpg' : 'http://localhost:88/pictureFile/yushibujue.jpg'; // 来判断是开发还是生产环境
