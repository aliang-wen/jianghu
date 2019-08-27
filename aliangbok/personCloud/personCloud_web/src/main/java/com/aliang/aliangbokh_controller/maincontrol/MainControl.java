package com.aliang.aliangbokh_controller.maincontrol;

import com.aliang.aliangbok_service.mainpage.biz.MainPageBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.maincontrol.biz.MainControlBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Controller
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class MainControl extends ReturnFun implements MainControlBiz {
    @Resource
    MainPageBiz mainPageBiz;
    // 这个方法是发现所有的客户信息进行展示修改
    @RequestMapping(value = "findAllUserInfoCon/{appId}")
    public @ResponseBody
    ReturnInfo findAllUserInfoCon(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(mainPageBiz.findAllUserInfo(params));
    }
}
