import { API } from '../../../../api';

export const updateSelfData = params => {
  return hyNetWork.post(API.updateSelfData, params).then(res => res.data);
};
export const ssssssss = params => {
  return hyNetWork.post(API.getSecurityCode, params).then(res => res.data);
};
