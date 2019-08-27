// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
// 这里是全局变量的配置
import Vue from 'vue';
// 图片裁剪的组件
import VueCropper from 'vue-cropper';

// iview样式
import 'iview/dist/styles/iview.css';
// 引入字体
import './assets/style/font/font.css';
// 配置文件引入
import conf from './config';
// 加载额外的方法包
import './utils';

// 这里是制定路径的配置
import App from './App';
// 引入路由配置
import router from './router';
// 引入store 进行实例化
import store from './store';

Vue.use(VueCropper);

// 这里相当于创建绑定#app对象之前的赋值
Object.keys(conf).map(e => {
  Vue.use(conf[e]);
});

// 这里是使浏览器控制台不打印那么多的信息
Vue.config.productionTip = false;

// 这里是对路由进行监控 是一个路由的全局钩子函数
router.beforeEach((to, from, next) => {
  // to即将进入的目标路由对象，from当前导航正要离开的路由， next : 下一步执行的函数钩子
  if (to.path === '/login' || to.path === '/logOut' || (to.path).indexOf('back') < 0) {
    sessionStorage.clear();
    // window.hy.db.cookie.remove('loginToken'); // 这里的时候当访问没有登录页面的时候会清掉 因为cookie是浏览器全局的 相同的域下是同一个
    next(); // 如果即将进入登录路由，则直接放行
  } else {
    // 这个的主要的作用就是查看session中的登录token是不是超时了 如果超时就会要重新登录
    if (sessionStorage.getItem('loginToken') !== null && sessionStorage.getItem('loginToken').length > 0) {
      const loginToken = window.hy.db.cookie.getValue('loginToken');
      if (loginToken !== null && loginToken !== '' && loginToken !== undefined) {
        window.hy.db.cookie.setValue('loginToken', loginToken, {
          expires: 60 * 60 * 1000
        });
        next();
      } else {
        ModalBox.error({
          title: '请求超时',
          content: '登录超时，请求失败，请重新登录',
          onOk: () => {
            sessionStorage.clear();
            window.hy.db.cookie.remove('loginToken');
            router.push({
              path: '/login'
            });
          }
        });
      }
    } else {
      // 未登录则定向到登录路由
      next({
        path: '/login'
      });
    }
  }
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});
