/**
 * 作用：拼接url地址的地方
 */

export const testShow = (host, prefix = '', suffix = '') =>
  host + prefix + '/testShow' + suffix;
// 这应该是测试方法
export const login = (host, prefix = '', suffix = '') =>
  host + prefix + '/login' + suffix;
// 这里是40的方法
export const get404Info = (host, prefix = '', suffix = '') =>
  host + prefix + '/get404Info' + suffix;
// 这里是登录方法
export const LoginBok = (host, prefix = '', suffix = '') =>
  host + prefix + '/LoginBok' + suffix;
// 这里是请求后台出来的加密件 key iv
export const giveKeyIv = (host, prefix = '', suffix = '') =>
  host + prefix + '/giveKeyIv' + suffix;
// 这里是请求验证码
export const getSecurityCode = (host, prefix = '', suffix = '') =>
  host + prefix + '/getSecurityCode' + suffix;
// 这里是进行
export const findAllUserInfoCon = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/findAllUserInfoCon' + suffix;
// 获取国家各个省份的地址
export const findAllChianArea = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/findAllChianArea' + suffix;
// 获取国家各个省份的地址
export const updateSelfData = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/updateSelfData' + suffix;
// 获取字典项的方法 列表
export const findDictionaryOptionTableList = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/findDictionaryOptionTableList' + suffix;
// 添加字典项的方法
export const addDictionaryOptionList = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/addDictionaryOptionList' + suffix;
// 删除字典项的方法
export const delDictionaryOption = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/delDictionaryOption' + suffix;
// 查询文章的列表
export const findEssayTableList = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/findEssayTableList' + suffix;
// 查询文章单条
export const findEssayById = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/findEssayById' + suffix;
// 修改文章所有信息
export const updateEssayById = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/updateEssayById' + suffix;
// 修改文章部分信息
export const updateEssayByIdChangeSwitch = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/updateEssayByIdChangeSwitch' + suffix;
// 删除文章// 查询文章的列表
export const delEssayById = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/delEssayById' + suffix;
// 删除图片
export const delPictureTitle = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/delPictureTitle' + suffix;
// 得到博主的信息
export const getUserInfo = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/getUserInfo' + suffix;
// 得到轮播框信息
export const getUserInfoCarousel = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/getUserInfoCarousel' + suffix;
// 得到上下框的信息
export const getUserInfoShowBox = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/getUserInfoShowBox' + suffix;
// 这里是查询文章展示
export const findEssayListPageSer = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/findEssayListPageSer' + suffix;
// 获取字典项的方法 这个是传什么就拿什么
export const findDictionaryOptionList = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/findDictionaryOptionList' + suffix;
// 获取所有的字典项 树状结构的
export const finDictionaryOptioinAllListCon = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/finDictionaryOptioinAllListCon' + suffix;
// 获取阅读的文章系列
export const getEssaySeriesListCon = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/getEssaySeriesListCon' + suffix;
// 获取阅读的文章
export const readEssayFun = (host, prefix = '', suffix = '') =>
  host + prefix + '/readEssayPage/readEssayFun' + suffix;
// 上传头像
export const uploadHeaderPic = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/uploadHeaderPic' + suffix;
// 技能的添加
export const addBokSkill = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/addBokSkill' + suffix;
// 技能的删除
export const delBokSkill = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/delBokSkill' + suffix;
// 技能的修改
export const updateBokSkill = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/updateBokSkill' + suffix;
// 技能的查询
export const findBokSkill = (host, prefix = '', suffix = '') =>
  host + prefix + '/back/findBokSkillList' + suffix;
// 技能的查询
export const findShowUserMainPageAboutMyselfCon = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/findShowUserMainPageAboutMyselfCon' + suffix;
// 时间轴的文章的获取
export const getEssayServiceTimeLineListCon = (host, prefix = '', suffix = '') =>
  host + prefix + '/mainPage/getEssayServiceTimeLineListCon' + suffix;
