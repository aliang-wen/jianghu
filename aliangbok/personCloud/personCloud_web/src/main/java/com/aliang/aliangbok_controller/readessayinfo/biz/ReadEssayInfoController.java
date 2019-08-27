package com.aliang.aliangbok_controller.readessayinfo.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;

/**
 * @Description: 接口阅读页面的
 * @Param: ${tags}
 * @author: 阿良
 */
public interface ReadEssayInfoController {
    // 获取文章和和客户信息
    public ReturnInfo readEssayFun(String appId, String params);
}
