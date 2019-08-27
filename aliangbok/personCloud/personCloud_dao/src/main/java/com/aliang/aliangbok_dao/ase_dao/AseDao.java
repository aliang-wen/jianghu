package com.aliang.aliangbok_dao.ase_dao;

import com.aliang.aliangbok_entity.aseentit.AseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Repository
public interface AseDao {
    // 查询方法
    public AseEntity getAseInfo(@Param(value = "appId") String appId);
    // 修改方法
    public boolean updateAseInfo(@Param(value = "aseEntity") AseEntity aseEntity);
}
