package com.aliang.aliangbok_entity.securitycode;

import java.util.Date;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SecurityCodeEntity {
    private String tokenCode;
    private String code;
    private Date creat_code_time;

    public SecurityCodeEntity() {
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreat_code_time() {
        return creat_code_time;
    }

    public void setCreat_code_time(Date creat_code_time) {
        this.creat_code_time = creat_code_time;
    }
}
