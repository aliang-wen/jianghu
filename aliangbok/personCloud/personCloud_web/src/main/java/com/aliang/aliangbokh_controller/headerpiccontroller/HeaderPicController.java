package com.aliang.aliangbokh_controller.headerpiccontroller;

import com.aliang.aliangbok_service.headerpicservice.HeaderPicService;
import com.aliang.aliangbok_service.headerpicservice.biz.HeaderPicBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.headerpiccontroller.biz.HeaderPicConBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 添加头像
 * @author: 阿良
 */
@Controller
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class HeaderPicController extends ReturnFun implements HeaderPicConBiz {
    @Resource
    HeaderPicBiz headerPic;

    @RequestMapping(value = "uploadHeaderPic/{appId}")
    public @ResponseBody
    ReturnInfo uploadHeaderPic(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(headerPic.uploadHeaderPicService(params, appId));
    }

}
