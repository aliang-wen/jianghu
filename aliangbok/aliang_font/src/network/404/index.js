import { API } from '../../api';

export const get404Info = params => {
  return hyNetWork.post(API.get404Info, params).then(res => res.data);
};

// 删除
export const get404Infotest = params => {
  return hyNetWork.post(API.get404Info, params).then(res => res.data);
};
