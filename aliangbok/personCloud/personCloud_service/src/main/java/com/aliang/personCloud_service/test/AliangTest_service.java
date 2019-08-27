package com.aliang.personCloud_service.test;

import com.aliang.aliangbok_dao.chinaaredao.ChianAreaDao;
import com.aliang.personCloud_dao.test.AliangTest_dao;
import com.aliang.personCloud_entity.test.AliangTest_entity;
import com.aliang.personCloud_service.test.biz.AliangTest_servicebiz;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class AliangTest_service implements AliangTest_servicebiz {
    @Resource
    AliangTest_dao aliangTest_dao;

    public AliangTest_entity findAliangbok_servicebiz(String aliangTestEntity) {
        JSONObject jsonObject = new JSONObject(aliangTestEntity);
        AliangTest_entity aliangTest_entity_temp = new AliangTest_entity();
        aliangTest_entity_temp.setAd_username(jsonObject.getString("username"));
        aliangTest_entity_temp.setAd_password(jsonObject.getString("password"));
        AliangTest_entity aliangTest_entity = aliangTest_dao.findAliangbok(aliangTest_entity_temp);
        return  aliangTest_entity;
    }
}
