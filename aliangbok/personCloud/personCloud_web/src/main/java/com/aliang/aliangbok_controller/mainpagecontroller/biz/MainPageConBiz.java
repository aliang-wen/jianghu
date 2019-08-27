package com.aliang.aliangbok_controller.mainpagecontroller.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;

/**
 * 展示页首页的接口
 */
public interface MainPageConBiz {
    // 访问获取的博客人的基本信息
    public ReturnInfo getUserInfo(String appId);
    // 查询轮播文章
    public ReturnInfo getUserInfoCarousel(String appId);
    // 查询展示上下兰文章
    public ReturnInfo getUserInfoShowBox(String appId);
    // 这里是查询文章展示
    public ReturnInfo findEssayListPageSer(String appId, String params);
    // 查询所有的树状的字典项
    public ReturnInfo finDictionaryOptioinAllListCon(String appId, String params);
    // 获取文章类型
    public ReturnInfo getEssaySeriesListCon(String appId);
    // 获取博主的信息
    public ReturnInfo findShowUserMainPageAboutMyselfCon(String appId);
    // 时间轴获取数据
    public ReturnInfo getEssayServiceTimeLineListCon(String appId, String params);
}
