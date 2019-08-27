package com.aliang.aliangbok_dao.dictionaryoptiondao;

import com.aliang.aliangbok_entity.dictionaryoptionentity.DictionaryOptionEntity;
import org.apache.ibatis.annotations.Param;

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
public interface DictionaryOptionDao {
    // 增
    public boolean addDictionaryOption(@Param(value = "dictionaryOptionEntity") DictionaryOptionEntity dictionaryOptionEntity);
    // 删finDictionaryOptioin
    public boolean delDictionaryOption(@Param(value = "dictionaryOptionEntity") DictionaryOptionEntity dictionaryOptionEntity);
    // 查
    public List<DictionaryOptionEntity> finDictionaryOptioin(@Param(value = "map") Map<String, Object> map);
    public List<DictionaryOptionEntity> finDictionaryOptioinTable(@Param(value = "map") Map<String, Object> map);
    public List<DictionaryOptionEntity> finDictionaryOptioinAllList(@Param(value = "map") Map<String, Object> map);
    // 查出所有的数据的量
    public Integer finDictionaryOptioinTableNum(@Param(value = "map") Map<String, Object> map);
    // 改
    public boolean updateDictionaryOption(@Param(value = "dictionaryOptionEntity") DictionaryOptionEntity dictionaryOptionEntity);
}
