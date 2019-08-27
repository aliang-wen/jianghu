package com.aliang.aliangbok_service.loginbok.biz;


import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface LoginBokServiceBiz {
    // 登录时候向后台请求信息
    public Map<String, Object> loginBok(String params, String appId);

    // 登录之前请求这里后台的 key iv
    public Map<String, Object> getKeyIv(String appId);

    // 获取验证码图片和验证码token给前台
    Map<String, Object> getSecurityCode();
}
