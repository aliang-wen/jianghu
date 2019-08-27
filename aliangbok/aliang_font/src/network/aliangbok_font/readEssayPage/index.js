import { API } from '../../../api';

export const readEssayFun = params => {
  return hyNetWork.post(API.readEssayFun, params).then(res => res.data);
};

export const ssss = params => {
  return hyNetWork.post(API.ssss, params).then(res => res.data);
};

