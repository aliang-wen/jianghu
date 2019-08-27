package com.aliang.aliangbok_service.bozhuskillservice;

import com.aliang.aliangbok_dao.bozhuskill_dao.BoZhuSkillDao;
import com.aliang.aliangbok_entity.bozhuskill.BoZhuSkillEntity;
import com.aliang.aliangbok_service.bozhuskillservice.biz.BoZhuSkillServiceBiz;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: 技能增删查改的业务逻辑
 * @author: 阿良
 */
@Service
public class BoZhuSkillService implements BoZhuSkillServiceBiz {
    @Resource
    BoZhuSkillDao boZhuSkillDao;

    public Map<String, Object> addBoZhuSkill(String params, String appId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        BoZhuSkillEntity boZhuSkillEntity = new BoZhuSkillEntity();
        if (jsonObject.has("userId")) {
            boZhuSkillEntity.setUserId(jsonObject.getString("userId"));
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "添加出错111");
            return returnMap;
        }
        if (jsonObject.has("skName")) {
            boZhuSkillEntity.setSkName(jsonObject.getString("skName"));
        } else {
            boZhuSkillEntity.setSkName("");
        }
        if (jsonObject.has("skScore")) {
            Integer tempNum = Integer.valueOf(jsonObject.getString("skScore"));
            if (tempNum< 0) {
                boZhuSkillEntity.setSkScore(0);
            } else if (tempNum> 100) {
                boZhuSkillEntity.setSkScore(100);
            } else {
                boZhuSkillEntity.setSkScore(jsonObject.getInt("skScore"));
            }
        } else {
            boZhuSkillEntity.setSkScore(0);
        }
        boZhuSkillEntity.setSkId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
        if (boZhuSkillDao.addBozhuSkill(boZhuSkillEntity) > 0) {
            returnMap.put("falg", "success");
            returnMap.put("message", "添加成功");
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "添加失败112");
        }
        return returnMap;
    }

    public Map<String, Object> delBoZhuSkill(String params, String appId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        BoZhuSkillEntity boZhuSkillEntity = new BoZhuSkillEntity();
        if (jsonObject.has("skId")) {
           boZhuSkillEntity.setSkId(jsonObject.getString("skId"));
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "删除失败111");
        }
        if (boZhuSkillDao.delBozhuSkill(boZhuSkillEntity) > 0) {
            returnMap.put("falg", "success");
            returnMap.put("message", "删除成功");
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "删除失败112");
        }
        return returnMap;
    }

    public Map<String, Object> findBoZhuSkill(String params, String appId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        BoZhuSkillEntity boZhuSkillEntity = new BoZhuSkillEntity();
        if (jsonObject.has("userId")) {
            boZhuSkillEntity.setUserId(jsonObject.getString("userId"));
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "查询失败111");
            return returnMap;
        }
        List<BoZhuSkillEntity> boZhuSkillEntitiesLits = boZhuSkillDao.findBoZhuSkills(boZhuSkillEntity);
        if (null != boZhuSkillEntitiesLits) {
            returnMap.put("falg", "success");
            returnMap.put("skillList", boZhuSkillEntitiesLits);
            returnMap.put("message", "查询成功");
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "查询失败112");
        }
        return returnMap;
    }

    public Map<String, Object> updateBoZhuSkill(String params, String appId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        BoZhuSkillEntity boZhuSkillEntity = new BoZhuSkillEntity();
        if (jsonObject.has("skId")) {
            boZhuSkillEntity.setSkId(jsonObject.getString("skId"));
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "修改出错111");
            return returnMap;
        }
        if (jsonObject.has("skName")) {
            boZhuSkillEntity.setSkName(jsonObject.getString("skName"));
        } else {
            boZhuSkillEntity.setSkName("");
        }
        if (jsonObject.has("skScore")) {
            Integer tempNum = Integer.valueOf(jsonObject.getString("skScore"));
            if (tempNum< 0) {
                boZhuSkillEntity.setSkScore(0);
            } else if (tempNum> 100) {
                boZhuSkillEntity.setSkScore(100);
            } else {
                boZhuSkillEntity.setSkScore(jsonObject.getInt("skScore"));
            }
        } else {
            boZhuSkillEntity.setSkScore(0);
        }
        if (boZhuSkillDao.updateBozhuSkill(boZhuSkillEntity) > 0) {
            returnMap.put("falg", "success");
            returnMap.put("message", "修改成功");
        } else {
            returnMap.put("falg", "fail");
            returnMap.put("message", "修改失败112");
        }
        return returnMap;
    }
}
