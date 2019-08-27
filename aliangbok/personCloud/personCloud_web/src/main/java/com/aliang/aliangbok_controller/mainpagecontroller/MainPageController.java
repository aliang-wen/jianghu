package com.aliang.aliangbok_controller.mainpagecontroller;

import com.aliang.aliangbok_controller.mainpagecontroller.biz.MainPageConBiz;
import com.aliang.aliangbok_entity.userinfo.UserInfo;
import com.aliang.aliangbok_service.dictionaryoption.biz.DictionaryOptionServiceBiz;
import com.aliang.aliangbok_service.essayservice.biz.EssayBiz;
import com.aliang.aliangbok_service.mainpage.biz.MainPageBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 主页上的访问方法
 */
@Controller
@RequestMapping(value = "mainPage/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class MainPageController extends ReturnFun implements MainPageConBiz {
    @Resource
    MainPageBiz mainPage;
    @Resource
    DictionaryOptionServiceBiz dictionaryOptionService;

    @Resource
    EssayBiz essayBiz;

    @RequestMapping(value = "getUserInfo/{appId}")
    public @ResponseBody
    ReturnInfo getUserInfo(@PathVariable String appId) {
        return returnInfoObj(mainPage.getUserInfoService(appId));
    }

    @RequestMapping(value = "getUserInfoCarousel/{appId}")
    public @ResponseBody
    ReturnInfo getUserInfoCarousel(@PathVariable String appId) {
        return returnInfoObj(mainPage.getUserInfoCarousel(appId));
    }

    @RequestMapping(value = "getUserInfoShowBox/{appId}")
    public @ResponseBody
    ReturnInfo getUserInfoShowBox(@PathVariable String appId) {
        return returnInfoObj(mainPage.getUserInfoShowBox(appId));
    }

    @RequestMapping(value = "findEssayListPageSer/{appId}")
    public @ResponseBody
    ReturnInfo findEssayListPageSer(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(mainPage.findEssayListPageSer(appId, params));
    }
    @RequestMapping(value = "findDictionaryOptionList/{appId}")
    public @ResponseBody
    ReturnInfo findDictionaryOptionList(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(dictionaryOptionService.finDictionaryOptioinArr(params, appId));
    }
    @RequestMapping(value = "finDictionaryOptioinAllListCon/{appId}")
    public @ResponseBody
    ReturnInfo finDictionaryOptioinAllListCon(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(dictionaryOptionService.finDictionaryOptioinAllListService(appId, params));
    }
    @RequestMapping(value = "getEssaySeriesListCon/{appId}")
    public @ResponseBody
    ReturnInfo getEssaySeriesListCon(@PathVariable String appId) {
        return returnInfoObj(essayBiz.getEssaySeriesListService());
    }

    @RequestMapping(value = "findShowUserMainPageAboutMyselfCon/{appId}")
    public @ResponseBody
    ReturnInfo findShowUserMainPageAboutMyselfCon(@PathVariable String appId) {
        return returnInfoObj(mainPage.findShowUserMainPageAboutMyself(appId, ""));
    }

    // 这里是时间轴获取文章的信息
    @RequestMapping(value = "getEssayServiceTimeLineListCon/{appId}")
    public @ResponseBody
    ReturnInfo getEssayServiceTimeLineListCon(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(mainPage.getEssayServiceTimeLineListService(appId, params));
    }
}
