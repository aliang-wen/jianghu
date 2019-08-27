package com.aliang.aliangbokh_controller.selfdata;

import com.aliang.aliangbok_service.selfdataservice.biz.SelfDataDerviceBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.selfdata.biz.selfDataBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 这里是客户页面的方法 但是查询方法不在这里
 */
@Controller
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class selfData extends ReturnFun implements selfDataBiz {
    @Resource
    SelfDataDerviceBiz selfDataDervice;

    // 这里只有一个客户的修改方法
    @RequestMapping(value = "updateSelfData/{appId}")
    @ResponseBody
    public ReturnInfo updateSelfData(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(selfDataDervice.updateUserInfoByIdService(params, appId));
    }
}
