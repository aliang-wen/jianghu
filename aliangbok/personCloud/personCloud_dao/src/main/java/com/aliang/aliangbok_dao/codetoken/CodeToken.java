package com.aliang.aliangbok_dao.codetoken;

import com.aliang.aliangbok_entity.securitycode.SecurityCodeEntity;
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
public interface CodeToken {
    // 插入方法
    public boolean inertSecurityCode(@Param(value = "securityCodeEntity")SecurityCodeEntity securityCodeEntity);
    // 查询方法
    public SecurityCodeEntity getCode(@Param(value = "codeToken") String codeToken);
}
