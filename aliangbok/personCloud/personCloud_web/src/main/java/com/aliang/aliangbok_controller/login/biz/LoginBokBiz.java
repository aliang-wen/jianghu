package com.aliang.aliangbok_controller.login.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface LoginBokBiz {
    public ReturnInfo LoginBok(String params, String appId);

    public ReturnInfo giveKeyIv(String appId);

    public ReturnInfo getSecurityCode( String appId);
}
