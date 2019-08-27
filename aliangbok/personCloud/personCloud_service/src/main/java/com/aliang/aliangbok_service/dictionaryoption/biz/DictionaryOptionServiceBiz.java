package com.aliang.aliangbok_service.dictionaryoption.biz;


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
public interface DictionaryOptionServiceBiz {
    // 查询服符合条件的字典项
    public Map<String, Object> finDictionaryOptioinTableSer(String params, String appId);
    // 使用那些字典项的时候查询那些字典项
    public Map<String,Object> finDictionaryOptioinArr(String params, String appId);

    // 新增字典项
    public Map<String, Object> addDictionaryOption(String params, String appId);
    // 修改字典项
    // 删除字典项(这里要先删除子的才能删除主的字典项)
    public Map<String, Object> delDictionaryOption(String params, String appId);

    // 获取列表形式的数据结构
    public Map<String, Object> finDictionaryOptioinAllListService(String appId, String params);

}
