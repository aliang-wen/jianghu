import { API } from '../../../api';

export const testShow = params => {
  return hyNetWork.post(API.testShow, params).then(res => res.data);
};
export const LoginBok = params => {
  return hyNetWork.post(API.LoginBok, params).then(res => res.data);
};
export const giveKeyIv = params => {
  return hyNetWork.post(API.giveKeyIv, params).then(res => res.data);
};
export const getSecurityCode = params => {
  return hyNetWork.post(API.getSecurityCode, params).then(res => res.data);
};
