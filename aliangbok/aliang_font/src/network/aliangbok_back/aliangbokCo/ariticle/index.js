import { API } from '../../../../api';

// 查询列表
export const findEssayTableList = params => {
  return hyNetWork.post(API.findEssayTableList, params).then(res => res.data);
};
// 查询单挑
export const findEssayById = params => {
  return hyNetWork.post(API.findEssayById, params).then(res => res.data);
};
// 修改所有的文章信息
export const updateEssayById = params => {
  return hyNetWork.post(API.updateEssayById, params).then(res => res.data);
};
// 删除
export const delEssayById = params => {
  return hyNetWork.post(API.delEssayById, params).then(res => res.data);
};
// 修改部分文章信息
export const updateEssayByIdChangeSwitch = params => {
  return hyNetWork.post(API.updateEssayByIdChangeSwitch, params).then(res => res.data);
};
// 删除图片
export const delPictureTitle = params => {
  return hyNetWork.post(API.delPictureTitle, params).then(res => res.data);
};

