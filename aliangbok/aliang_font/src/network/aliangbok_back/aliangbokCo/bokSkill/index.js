import { API } from '../../../../api';

export const addBokSkill = params => {
  return hyNetWork.post(API.addBokSkill, params).then(res => res.data);
};
export const delBokSkill = params => {
  return hyNetWork.post(API.delBokSkill, params).then(res => res.data);
};
export const updateBokSkill = params => {
  return hyNetWork.post(API.updateBokSkill, params).then(res => res.data);
};
export const findBokSkill = params => {
  return hyNetWork.post(API.findBokSkill, params).then(res => res.data);
};
