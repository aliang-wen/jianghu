package com.aliang.aliangbok_controller.login;

import com.aliang.aliangbok_controller.login.biz.LoginBokBiz;
import com.aliang.aliangbok_service.loginbok.biz.LoginBokServiceBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Controller
public class LoginBok extends ReturnFun implements LoginBokBiz {
    @Resource
    LoginBokServiceBiz loginBokService;

    // 登录方法
    @RequestMapping(value = "LoginBok/{appId}",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public @ResponseBody ReturnInfo LoginBok(@RequestBody String params, @PathVariable String appId) {
        Map<String, Object> map = loginBokService.loginBok(params, appId);
        return returnInfoObj(map) ;
    }
    @RequestMapping(value = "giveKeyIv/{appId}",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public @ResponseBody ReturnInfo giveKeyIv(@PathVariable String appId) {
        Map<String ,Object> map = loginBokService.getKeyIv(appId);
        return returnInfoObj(map) ;
    }
    @RequestMapping(value = "getSecurityCode/{appId}",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public @ResponseBody ReturnInfo getSecurityCode(@PathVariable String appId) {
        Map<String ,Object> map = loginBokService.getSecurityCode();
        return returnInfoObj(map) ;
    }
}
