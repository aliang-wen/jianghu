package com.aliang.aliangbok_service.logintokenservice.biz;

import com.aliang.aliangbok_entity.logintokenentity.LoginTokenEntity;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface LoginTokenServiceBiz {
    // 添加方法
    boolean insertLoginTokeService(LoginTokenEntity loginTokenEntity);
    // 更新方法
    boolean updateLoginTokenService(LoginTokenEntity loginTokenEntity);
    // 查询方法
    LoginTokenEntity getLoginTokenService(String loginToken);
}
