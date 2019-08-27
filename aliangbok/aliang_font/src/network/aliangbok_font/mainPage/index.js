import { API } from '../../../api';

export const getUserInfo = params => {
  return hyNetWork.post(API.getUserInfo, params).then(res => res.data);
};

export const getUserInfoCarousel = params => {
  return hyNetWork.post(API.getUserInfoCarousel, params).then(res => res.data);
};

export const getUserInfoShowBox = params => {
  return hyNetWork.post(API.getUserInfoShowBox, params).then(res => res.data);
};
export const findEssayListPageSer = params => {
  return hyNetWork.post(API.findEssayListPageSer, params).then(res => res.data);
};
export const getEssaySeriesListCon = params => {
  return hyNetWork.post(API.getEssaySeriesListCon, params).then(res => res.data);
};
export const findShowUserMainPageAboutMyselfCon = params => {
  return hyNetWork.post(API.findShowUserMainPageAboutMyselfCon, params).then(res => res.data);
};
export const getEssayServiceTimeLineListCon = params => {
  return hyNetWork.post(API.getEssayServiceTimeLineListCon, params).then(res => res.data);
};
