package com.aliang.aliangbokh_controller.maincontrol.biz;


import com.aliang.aliangbok_until.returninfo.ReturnInfo;

import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface MainControlBiz {
    ReturnInfo findAllUserInfoCon(String appId, String params);
}
