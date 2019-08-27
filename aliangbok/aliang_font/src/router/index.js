import Vue from 'vue';
import Router from 'vue-router';

import asyncRouterPage from './router';

Vue.use(Router);

export const routerList = [
  {
    path: '/404',
    name: '404',
    component: asyncRouterPage('404')
  },
  {
    path: '/login',
    name: 'login',
    component: asyncRouterPage('aliangbok_back/login')
  },
  {
    path: '/test',
    name: 'test',
    component: asyncRouterPage('aliangbok_back/test')
  },
  // 这里是前端访客页面的路由的配置
  {
    path: '/',
    name: 'layout',
    component: asyncRouterPage('layout', 'views'),
    redirect: '/mainPage',
    children: [
      {
        path: 'mainPage',
        name: 'mainPage',
        component: asyncRouterPage('aliangbok_font/mainPage'),
        meta: {
          title: ['博客','首页'],
        }
      },
      {
        path: 'allPagesList',
        name: 'allPagesList',
        component: asyncRouterPage('aliangbok_font/allPagesList'),
        meta: {
          title: ['博客','文章总览'],
        }
      },
      {
        path: 'readEssayPage',
        name: 'readEssayPage',
        component: asyncRouterPage('aliangbok_font/readEssayPage'),
        meta: {
          title: ['博客', '文章阅读'],
        }
      },
      {
        path: 'findEssayList',
        name: 'findEssayList',
        component: asyncRouterPage('aliangbok_font/findEssayList'),
        meta: {
          title: ['博客', '相关查询'],
        }
      },
      {
        path: 'Timeline',
        name: 'Timeline',
        component: asyncRouterPage('aliangbok_font/Timeline'),
        meta: {
          title: ['博客', '时间轴'],
        }
      },
      {
        path: 'aboutMyselfInfo',
        name: 'aboutMyselfInfo',
        component: asyncRouterPage('aliangbok_font/aboutMyselfInfo'),
        meta: {
          title: ['博客', '关于博主'],
        }
      }
    ]
  },
  // 这里是登录完成后进行的后台的操作界面
  {
    path: '/back', // 这里是外层的框架
    name: 'back',
    component: asyncRouterPage('layouth', 'views'),
    redirect: '/back/welcome',
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
    },
    children: [
      {
        path: 'welcome',
        name: 'welcome',
        component: asyncRouterPage('aliangbok_back/aliangbokCo/welcome'),
        meta: {
          requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          title: ['博客管理','首页'],
        },
      },
      {
        path: 'selfData',
        name: 'selfData',
        component: asyncRouterPage('aliangbok_back/aliangbokCo/selfData'),
        meta: {
          requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          title: ['博客管理','个人信息'],
        },
      },
      {
        path: 'dictionaryOption',
        name: 'dictionaryOption',
        component: asyncRouterPage('aliangbok_back/aliangbokCo/dictionaryOption'),
        meta: {
          requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          title: ['博客管理','字典项'],
        },
      },
      {
        path: 'addArticle',
        name: 'addArticle',
        component: asyncRouterPage('aliangbok_back/aliangbokCo/addAriticle'),
        meta: {
          requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          title: ['博客管理','文章管理'],
        },
      }
    ]
  },
  {
    path: '/back/editarticlepage',
    name: '/back/editarticlepage',
    component: asyncRouterPage('aliangbok_back/aliangbokCo/editarticlepage'),
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
    },
  },
  {
    path: '/logOut',
    redirect: '/login',
  },
  {
    path: '*',
    redirect: '/404',
  }
];

export default new Router({
  // 这个方法作用是返回上一级页面的时候滚动条的位置
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return {
        x: 0,
        y: 0
      };
    }
  },
  routes: routerList
});
