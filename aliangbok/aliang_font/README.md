# aliang

> aliang

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).


```javascript
.
├── LICENSE
├── README-zh.md
├── README.md
├── build 项目工程化文件(一般不需要修改)
├── config 项目工程化配置文件
├── favicon.ico
├── index.html
├── package-lock.json
├── package.json
├── node_modules 项目所需的 node 模块包
├── src 项目代码主目录
│   ├── App.vue 根组件
│   ├── main.js 入口文件
│   ├── api 项目中所需的 API
│   │   ├── api.js
│   │   ├── code.js
│   │   ├── config.js 项目中需要的常量设置
│   │   └── index.js
│   ├── assets 资源文件目录
│   │   ├── images
│   │   ├── javascript
│   │   └── styles 样式目录
│   │   ├── base.scss
│   │   ├── common
│   │   ├── element-ui.scss
│   │   ├── mixins
│   │   ├── reset.scss
│   │   ├── style.scss
│   │   ├── transition
│   │   └── variables.scss
│   ├── components 组件目录
│   │   ├── chart
│   │   ├── global.js
│   │   ├── index.js
│   │   ├── nav-menu
│   ├── config 配置目录
│   │   ├── elmComponents.js
│   │   ├── http.js
│   │   ├── index.js
│   │   ├── initEcharts.js
│   │   ├── mock.js
│   │   ├── network.js
│   │   ├── proxyTable.js
│   │   └── requireAuth.js
│   ├── directives 指令目录
│   │   └── index.js
│   ├── filters 过滤器目录
│   │   └── index.js
│   ├── mixins 混合文件目录
│   │   └── index.js
│   ├── mock mock 数据规则配置
│   │   └── index.js
│   ├── network 页面网络请求目录
│   ├── pages 路由页面
│   │   ├── 404
│   │   │   ├── index.vue
│   │   │   └── style.scss
│   │   ├── auth
│   │   │   ├── index.vue
│   │   │   └── style.scss
│   ├── plugins 关于 Vue 中的插件
│   │   └── index.js
│   ├── router 路由
│   │   ├── index.js
│   │   └── router.js
│   ├── store 状态管理
│   │   ├── actions.js
│   │   ├── getters.js
│   │   ├── index.js
│   │   ├── modules
│   │   │   ├── app.js
│   │   │   ├── index.js
│   │   │   ├── tabs.js
│   │   │   ├── tagsView.js
│   │   │   └── user.js
│   │   ├── mutations.js
│   │   ├── state.js
│   │   └── types.js
│   ├── utils 工具库
│   │   ├── getAllPagesPath.js
│   │   ├── hy
│   │   │   ├── animate.js
│   │   │   ├── base.js
│   │   │   ├── compute.js
│   │   │   ├── database.js
│   │   │   ├── date.js
│   │   │   ├── encrypt.js
│   │   │   ├── http.js
│   │   │   ├── index.js
│   │   │   ├── prototype.js
│   │   │   ├── regex.js
│   │   │   ├── system.js
│   │   │   ├── tool.js
│   │   │   ├── ui.js
│   │   │   └── untils
│   │   ├── index.js
│   │   └── treeSelectDataFormat.js
│   └── views 布局目录
│   ├── header
│   │   ├── index.vue
│   │   └── style.scss
│   ├── layout
│   │   ├── index.vue
│   │   └── style.scss
│   ├── logo
│   │   ├── index.vue
│   │   └── style.scss
│   ├── main
│   │   ├── index.vue
│   │   └── style.scss
│   ├── name
│   │   ├── index.vue
│   │   └── style.scss
│   ├── navigation
│   │   ├── index.vue
│   │   └── style.scss
│   ├── tabs
│   │   ├── index.vue
│   │   └── style.scss
│   └── user
│   ├── index.vue
│   └── style.scss
└── static
├── common
└── dirty-data mock 数据
├── index.js
└── json-data
└── workBenchData.json
```



