package com.aliang.aliangbok_dao.bozhuskill_dao;

import com.aliang.aliangbok_entity.bozhuskill.BoZhuSkillEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:  对应的账号的技能的
 */
public interface BoZhuSkillDao {
    // 添加
    public Integer addBozhuSkill(@Param(value = "boZhuSkillEntity")BoZhuSkillEntity boZhuSkillEntity);
    // 修改
    public Integer updateBozhuSkill(@Param(value = "boZhuSkillEntity")BoZhuSkillEntity boZhuSkillEntity);
    // 删除
    public Integer delBozhuSkill(@Param(value = "boZhuSkillEntity")BoZhuSkillEntity boZhuSkillEntity);
    // 这里是查询
    public List<BoZhuSkillEntity> findBoZhuSkills(@Param(value = "boZhuSkillEntity")BoZhuSkillEntity boZhuSkillEntity);
}
