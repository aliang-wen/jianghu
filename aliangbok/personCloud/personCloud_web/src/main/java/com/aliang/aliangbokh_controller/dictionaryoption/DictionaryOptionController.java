package com.aliang.aliangbokh_controller.dictionaryoption;

import com.aliang.aliangbok_service.dictionaryoption.biz.DictionaryOptionServiceBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.dictionaryoption.biz.DictionaryOptionControllerBiz;
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
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class DictionaryOptionController extends ReturnFun implements DictionaryOptionControllerBiz {
    @Resource
    DictionaryOptionServiceBiz dictionaryOptionService;

    @RequestMapping(value = "findDictionaryOptionTableList/{appId}")
    public @ResponseBody
    ReturnInfo findDictionaryOptionTableList(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(dictionaryOptionService.finDictionaryOptioinTableSer(params, appId));
    }
    @RequestMapping(value = "addDictionaryOptionList/{appId}")
    public @ResponseBody
    ReturnInfo addDictionaryOptionList(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(dictionaryOptionService.addDictionaryOption(params, appId));
    }
    @RequestMapping(value = "delDictionaryOption/{appId}")
    public @ResponseBody
    ReturnInfo delDictionaryOption(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(dictionaryOptionService.delDictionaryOption(params, appId));
    }
}
