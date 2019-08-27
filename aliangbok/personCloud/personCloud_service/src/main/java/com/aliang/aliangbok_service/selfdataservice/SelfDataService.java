package com.aliang.aliangbok_service.selfdataservice;

import com.aliang.aliangbok_dao.userdao.UserDao;
import com.aliang.aliangbok_entity.userinfo.UserInfo;
import com.aliang.aliangbok_service.selfdataservice.biz.SelfDataDerviceBiz;
import com.aliang.aliangbok_until.aseutil.AESutil;
import com.aliang.aliangbok_until.proputil.PropUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class SelfDataService implements SelfDataDerviceBiz {
    @Resource
    UserDao userDao;

    public Map<String, Object> updateUserInfoByIdService(String params, String appId) {
        // 这里进行数据解析
        JSONObject jsonObject = new JSONObject(params);
        UserInfo userInfo = new UserInfo();
        String id = "";
        try {
            String allInfo = AESutil.desEncrypt(jsonObject.getString("loginToken"));
            String arrInfo[] = allInfo.split("-");
            id = arrInfo[1];
        } catch (Exception e) {
            System.out.println(e);
        }
        userInfo.setUserId(id);
        if (jsonObject.has("bigName")) {
            userInfo.setBigName(jsonObject.getString("bigName"));
        } else {
            userInfo.setBigName("");
        }
        if (jsonObject.has("hobby")) {
            userInfo.setHobby(jsonObject.getString("hobby"));
        } else {
            userInfo.setHobby("");
        }
        if (jsonObject.has("qqNumber")) {
            userInfo.setQqNumber(jsonObject.getString("qqNumber"));
        } else {
            userInfo.setQqNumber("");
        }
        if (jsonObject.has("userAddressString")) {
            userInfo.setUserAddress(jsonObject.getString("userAddressString"));
        } else {
            userInfo.setUserAddress("");
        }
        if (jsonObject.has("userTel")) {
            userInfo.setUserTel(jsonObject.getString("userTel"));
        } else {
            userInfo.setUserTel("");
        }
        if (jsonObject.has("sex")) {
            userInfo.setSex(jsonObject.getString("sex"));
        } else {
            userInfo.setSex("");
        }
        if (jsonObject.has("aboutMyself")) {
            userInfo.setAboutMyself(jsonObject.getString("aboutMyself"));
        } else {
            userInfo.setAboutMyself("");
        }
        if (jsonObject.has("bokFlag")) {
            userInfo.setBokFlag(jsonObject.getString("bokFlag"));
        } else {
            userInfo.setBokFlag("");
        }
        if (jsonObject.has("bokSkill")) {
            userInfo.setBokSkill(jsonObject.getString("bokSkill"));
        } else {
            userInfo.setBokSkill("");
        }
        Map<String,Object> map = new HashMap<String, Object>();
        if (userDao.updateUserInfoById(userInfo)) {
            map.put("flag", "success");
        } else {
            map.put("flag", "error");
        }
        return map;
    }
}
