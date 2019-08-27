package com.aliang.aliangbokh_controller.essaycontroller.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface EssayComtrollerBiz {
    // 查询文章列表
    ReturnInfo findEssayTableList(String appId, String params);
    // 查询一条文章
    ReturnInfo findEssayById(String appId, String params);
    // 修改所有文章信息
    ReturnInfo updateEssayById(String appId, String params);
    // 删除文章
    ReturnInfo delEssayById(String appId, String params);
    // 修改switch的文章信息
    ReturnInfo updateEssayByIdChangeSwitch(String appId, String params);
}
