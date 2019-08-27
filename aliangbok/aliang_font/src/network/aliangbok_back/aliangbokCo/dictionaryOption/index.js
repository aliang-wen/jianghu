import { API } from '../../../../api';

export const findDictionaryOptionTableList = params => {
  return hyNetWork.post(API.findDictionaryOptionTableList, params).then(res => res.data);
};
export const addDictionaryOptionList = params => {
  return hyNetWork.post(API.addDictionaryOptionList, params).then(res => res.data);
};
// 删除字典项方法
export const delDictionaryOption = params => {
  return hyNetWork.post(API.delDictionaryOption, params).then(res => res.data);
};
