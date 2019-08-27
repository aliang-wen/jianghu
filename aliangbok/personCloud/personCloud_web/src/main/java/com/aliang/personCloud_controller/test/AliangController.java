package com.aliang.personCloud_controller.test;

import com.aliang.aliangbok_dao.chinaaredao.ChianAreaDao;
import com.aliang.aliangbok_entity.chianareaentity.ChinaAreaEntity;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.personCloud_controller.test.biz.AliangControllerBiz;
import com.aliang.personCloud_entity.test.AliangTest_entity;
import com.aliang.personCloud_service.test.biz.AliangTest_servicebiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
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
public class AliangController extends ReturnFun implements AliangControllerBiz {
    @Resource
    AliangTest_servicebiz aliangService;
    @Resource
    ChianAreaDao chianAreaDao;

    @RequestMapping(value = "testShow",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public @ResponseBody
    ReturnInfo testShow(@RequestBody String params) {
        AliangTest_entity aliangTest_entity = aliangService.findAliangbok_servicebiz(params);
        List<ChinaAreaEntity> list = chianAreaDao.getAllChinarea();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("aliangTest_entity",aliangTest_entity);
        map.put("listChinaArea",list);
        return returnInfoObj(map);
    }

//    调试好几天一直抱406的错误是因为produces = "application/json; charset=utf-8"配置错了 最好采用字符串佳偶
//    @RequestMapping(value = "testTwo",produces="application/json; charset=utf-8")
//    @ResponseBody
//    public Map<String, String> testTwo(@RequestParam(value = "username") String username) {
//        System.out.println(username);
//        Map<String,String> testMap = new HashMap<String, String>();
//        testMap.put("test", "小花花");
//        return testMap;
//    }
//    这里是做jsp视图解析的测试
//    @RequestMapping(value = "test",produces = "application/text;charset=utf-8")
//    public String test() {
//        AliangTest_entity aliangEntity = aliangService.sum(10, 30);
//        return "test";
//    }
}
