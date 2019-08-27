/**
 *  解析方法 配置一个路径 就能完成导入和路径的配置
 * @param {*} name 是src下面的*路径下的具体的路径
 * @param {*} path 是src下的具体的*目录
 */

const asyncRouterPage = (name, path = 'pages') => {
  let pageComponent = `${name}Page`;
  // 这里用的是import 加载速度慢  采用require
  // pageComponent = resolve => {
  //   import(`@/${path}/${name}`).then(module => {
  //     resolve(module);
  //   });
  // };
  // 为什么采用require就快了
  pageComponent = resolve => require([`@/${path}/${name}`], resolve)
  return pageComponent;
};
export default asyncRouterPage;
