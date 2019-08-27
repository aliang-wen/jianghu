package com.aliang.aliangbok_service.selfdataservice.biz;

import com.aliang.aliangbok_entity.userinfo.UserInfo;

import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface SelfDataDerviceBiz {
    public Map<String, Object> updateUserInfoByIdService(String params, String appId);
}
