package com.aliang.aliangbok_dao.chinaaredao;

import com.aliang.aliangbok_entity.chianareaentity.ChinaAreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Repository
public interface ChianAreaDao {
    public List<ChinaAreaEntity> getAllChinarea();
}
