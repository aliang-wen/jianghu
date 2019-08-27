package com.aliang.aliangbokh_controller.essaycontroller;

import com.aliang.aliangbok_service.essayservice.biz.EssayBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.essaycontroller.biz.EssayComtrollerBiz;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Title：${enclosing_method}
 * @Description: 关于文章的增删查改
 */
@Controller
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class EssayController extends ReturnFun implements EssayComtrollerBiz {
   @Resource
    EssayBiz essayBiz;

    @RequestMapping(value = "findEssayTableList/{appId}")
    public @ResponseBody
    ReturnInfo findEssayTableList(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(essayBiz.findEssayTable(params, appId));
    }

    @RequestMapping(value = "findEssayById/{appId}")
    public @ResponseBody
    ReturnInfo findEssayById(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(essayBiz.findEssayById(params, appId));
    }

    @RequestMapping(value = "updateEssayById/{appId}")
    public @ResponseBody
    ReturnInfo updateEssayById(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(essayBiz.updateEssayTableById(params, appId));
    }

    @RequestMapping(value = "delEssayById/{appId}")
    public @ResponseBody
    ReturnInfo delEssayById(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(essayBiz.delEssayTableById(params, appId));
    }
    @RequestMapping(value = "updateEssayByIdChangeSwitch/{appId}")
    public @ResponseBody
    ReturnInfo updateEssayByIdChangeSwitch(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(essayBiz.updateEssayByIdChangeSwitch(params, appId));
    }
}
