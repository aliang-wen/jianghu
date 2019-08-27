import { Modal } from 'iview';
import el from './elmComponents';
import HyNetworking from './network';


const E = require('wangeditor'); // 使用下载的源码
// xss防止xss攻击
const xss = require('xss');

// 这里是要被main.js调用的 这里面是只要vue一个参数的所有的方法
const needOnlyVue = Vue => { // vue因该是一个全局变量 可以进行方法赋值？？？
  Vue.prototype.$aliang = '阿良'; // 这里就是设置this的全局变量
  el(Vue); // 调用使用的组件
  window.hyNetWork = new HyNetworking(); // 加载的时候 将HyNetworking设置成 任何地方可用的超级常量
  window.ModalBox = Modal;
  window.E = E; // 使用 npm 安装
  window.XSS = xss;
};

export default { needOnlyVue };
