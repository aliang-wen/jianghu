package com.aliang.aliangbok_controller.readessayinfo;

import com.aliang.aliangbok_controller.readessayinfo.biz.ReadEssayInfoController;
import com.aliang.aliangbok_service.essayservice.biz.EssayBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
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
@RequestMapping(value = "readEssayPage/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public class ReadEssayInfo extends ReturnFun implements ReadEssayInfoController {

    @Resource
    EssayBiz essayBiz;

    @RequestMapping(value = "readEssayFun/{appId}")
    public @ResponseBody
    ReturnInfo readEssayFun(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(essayBiz.readEssayInfo(params, appId));
    }
}
