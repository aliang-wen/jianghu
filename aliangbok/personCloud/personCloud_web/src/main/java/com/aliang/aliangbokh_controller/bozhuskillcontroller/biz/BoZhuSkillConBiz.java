package com.aliang.aliangbokh_controller.bozhuskillcontroller.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;

/**
 * @author: 良
 */
public interface BoZhuSkillConBiz {
    // 增
    public ReturnInfo addBokSkill(String appId, String params);
    // 删
    public ReturnInfo delBokSkill(String appId, String params);
    // 查
    public ReturnInfo findBokSkillList(String appId, String params);
    // 改
    public ReturnInfo updateBokSkill(String appId, String params);
}
