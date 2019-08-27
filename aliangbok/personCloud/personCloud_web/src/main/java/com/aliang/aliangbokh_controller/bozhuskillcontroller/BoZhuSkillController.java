package com.aliang.aliangbokh_controller.bozhuskillcontroller;

import com.aliang.aliangbok_service.bozhuskillservice.biz.BoZhuSkillServiceBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.bozhuskillcontroller.biz.BoZhuSkillConBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 技能的增删查改
 * @author: 阿良
 */
@Controller
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class BoZhuSkillController extends ReturnFun implements BoZhuSkillConBiz {
    @Resource
    BoZhuSkillServiceBiz boZhuSkillService;

    @RequestMapping(value = "addBokSkill/{appId}")
    public @ResponseBody
    ReturnInfo addBokSkill(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(boZhuSkillService.addBoZhuSkill(params, appId));
    }

    @RequestMapping(value = "delBokSkill/{appId}")
    public @ResponseBody ReturnInfo delBokSkill(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(boZhuSkillService.delBoZhuSkill(params, appId));
    }

    @RequestMapping(value = "findBokSkillList/{appId}")
    public @ResponseBody ReturnInfo findBokSkillList(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(boZhuSkillService.findBoZhuSkill(params, appId));
    }

    @RequestMapping(value = "updateBokSkill/{appId}")
    public @ResponseBody ReturnInfo updateBokSkill(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(boZhuSkillService.updateBoZhuSkill(params, appId));
    }
}
