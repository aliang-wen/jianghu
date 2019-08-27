package com.aliang.aliangbok_service.headerpicservice.biz;

import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface HeaderPicBiz {
    // 添加头像
    public Map<String, Object> uploadHeaderPicService(String params, String appId);
}
