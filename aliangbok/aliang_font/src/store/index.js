import Vue from 'vue';
import Vuex from 'vuex';

// 初始 state 对象 相当于vue中的data 区别是state是vuex的唯一数据源
import state from './state';
import mutations from './mutation';
import getters from './getter';
import actions from './action';

Vue.use(Vuex);

export default new Vuex.Store({
  // 在开发环境中开启 严格模式 在严格模式下，只要 Vuex 状态在 mutation
  // 方法外被修改就会抛出错误。这确保了所有状态修改都会明确的被调试工具跟踪。 在生产环境中要 关闭严格模式  严格模式会对状态树进行深度监测来检测不合适的修改
  // —— 确保在发布阶段关闭它避免性能损耗。

  // 这里是一些全局状态
  state: state,

  // 这里是对state的状态进行包装， 写一些方法对state中的数据进行过滤
  getters: getters,

  // 这里是改变全局状态的 如果不适用mutation进行对state修改，直接修改state就会导致有异常
  mutations: mutations,

  // 这里面的一些数据业务逻辑
  actions: actions
});
