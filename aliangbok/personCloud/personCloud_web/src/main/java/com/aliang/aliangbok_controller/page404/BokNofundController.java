package com.aliang.aliangbok_controller.page404;

import com.aliang.aliangbok_controller.page404.biz.BokNofundControllerBiz;
import com.aliang.aliangbok_service.page404.biz.Page404Biz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
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
public class BokNofundController extends ReturnFun implements BokNofundControllerBiz {
    @Resource
    Page404Biz page404Biz;

    @RequestMapping(value = "get404Info",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public @ResponseBody
    ReturnInfo get404Info () {
        Map<String, Object> map = page404Biz.analyzeUrl("http://qzonestyle.gtimg.cn/qzone_v6/lostchild/data.js");
        return returnInfoObj(map);
    }
}
