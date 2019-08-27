package com.aliang.aliangbokh_controller.publiccontroller;

import com.aliang.aliangbok_dao.chinaaredao.ChianAreaDao;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.publiccontroller.biz.PublicControllerBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class PublicController extends ReturnFun implements PublicControllerBiz {
    @Resource
    ChianAreaDao chianAreaDao;

    @RequestMapping(value = "findAllChianArea/{appId}")
    public @ResponseBody
    ReturnInfo findAllChianArea(@PathVariable String appId) {
        return returnInfoObj(chianAreaDao.getAllChinarea());
    }
}
