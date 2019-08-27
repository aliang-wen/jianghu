package com.aliang.personCloud_dao.test;

import com.aliang.personCloud_entity.test.AliangTest_entity;
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
public interface AliangTest_dao {
    AliangTest_entity findAliangbok (@Param(value = "aliangTestEntity") AliangTest_entity aliangTestEntity);
}
