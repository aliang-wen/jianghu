package com.aliang.aliangbok_service.mainpage.biz;

import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface MainPageBiz {
//    根据userid查询博主信息
    Map<String, Object> findAllUserInfo(String Params);
    // 无条件获取博主信息
    Map<String, Object> getUserInfoService(String appId);
    // 获取轮播框的数据
    Map<String, Object> getUserInfoCarousel(String appId);
    // 获取上下盒子的数据
    Map<String, Object> getUserInfoShowBox(String appId);
    // 获取文章前台展示
    Map<String, Object> findEssayListPageSer(String appId, String params);
    // 获取博主的详细信息
    Map<String, Object> findShowUserMainPageAboutMyself(String appId, String params);
    // 文章时间轴的展示
    Map<String, Object> getEssayServiceTimeLineListService(String appId, String params);

}
