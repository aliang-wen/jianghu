package com.aliang.aliangbok_service.bozhuskillservice.biz;

import java.util.Map;

/**
 * @Description: 技能业务逻辑的增删查改
 * @author: 阿良
 */
public interface BoZhuSkillServiceBiz {
    // 增
    public Map<String , Object> addBoZhuSkill(String params, String appId);
    // 删
    public Map<String , Object> delBoZhuSkill(String params, String appId);
    // 查
    public Map<String , Object> findBoZhuSkill(String params, String appId);
    // 改
    public Map<String , Object> updateBoZhuSkill(String params, String appId);
}
