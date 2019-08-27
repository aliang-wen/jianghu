package com.aliang.aliangbok_dao.logintoken;

import com.aliang.aliangbok_entity.logintokenentity.LoginTokenEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Repository
public interface LoginTokenDao {
    // 添加方法
    boolean insertLoginTokeDao(@Param(value = "loginTokenEntity")LoginTokenEntity loginTokenEntity);
    // 更新方法
    boolean updateLoginTokenDao(@Param(value = "loginTokenEntity")LoginTokenEntity loginTokenEntity);
    // 查询方法
    LoginTokenEntity getLoginTokenDao(@Param(value = "loginToken")String loginToken);
}
