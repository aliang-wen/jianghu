import { API } from '../../../../api';

export const findAllUserInfoCon = params => {
  return hyNetWork.post(API.findAllUserInfoCon, params).then(res => res.data);
};
export const getSecurityCode = params => {
  return hyNetWork.post(API.getSecurityCode, params).then(res => res.data);
};
