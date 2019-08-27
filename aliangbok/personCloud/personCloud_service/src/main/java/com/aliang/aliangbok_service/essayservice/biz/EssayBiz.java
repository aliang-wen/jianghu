package com.aliang.aliangbok_service.essayservice.biz;

import com.aliang.aliangbok_entity.essayentity.EssayEntity;

import java.util.List;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: 文章增删查找的方法
 */
public interface EssayBiz {
    // 查询文章列表 和总数
    public Map<String, Object> findEssayTable(String params, String appId);
    // 根据id查询一条文章
    public Map<String, Object> findEssayById(String params, String appId);
    // 删除文章
    public Map<String, Object> delEssayTableById(String params, String appId);
    // 修改所有的文章信息
    public Map<String, Object> updateEssayTableById(String params, String appId);
    // 修改带有开关的文章信息
    public Map<String, Object> updateEssayByIdChangeSwitch(String params, String appId);
    // 阅读文章页面的方法
    public Map<String, Object> readEssayInfo(String params, String appId);
    // 查询文章类型
    public Map<String, Object> getEssaySeriesListService();
}
