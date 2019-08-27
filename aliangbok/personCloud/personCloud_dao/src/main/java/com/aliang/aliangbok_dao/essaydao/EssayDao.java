package com.aliang.aliangbok_dao.essaydao;

import com.aliang.aliangbok_entity.essayentity.EssayEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: 文章增删查改的dao
 */
public interface EssayDao {
    // 添加文章 主要是添加 博主 添加父id 添加临时主id
    public boolean addEssay(@Param(value = "essayEntity")EssayEntity essayEntity);
    // 删除
    public boolean delEssayById(@Param(value = "essayEntity")EssayEntity essayEntity);
    // 修改文章信息
    public boolean updateEssayById(@Param(value = "essayEntity")EssayEntity essayEntity);
    // 修改带有开关的文章信息
    public boolean updateEssayByIdChangeSwitch(@Param(value = "essayEntity")EssayEntity essayEntity);
    // 根据id查找
    public EssayEntity findEssayById(@Param(value = "essayEntity")EssayEntity essayEntity);
    // 根据进行列表查询
    public List<EssayEntity> findEssayList(@Param(value = "map") Map<String, Object> map);
    // 列表查询的总的条数
    public Integer allEssayNum(@Param(value = "map") Map<String, Object> map);

    // 这里是轮播框子的查询
    List<EssayEntity> findEssayListOfCarousel();
    // 上下两个展示框
    List<EssayEntity> findEssayListOfShowBox();
    // 查询文章展示 根据分类进行查询
    List<EssayEntity> findEssayListPage(@Param(value = "map") Map<String, Object> map);
    // 查询文章的总的条数
    Integer findEssayListPageCount(@Param(value = "map") Map<String, Object> map);
    // 文章的阅读页面
    EssayEntity readEssayInfoFun(@Param(value = "essayEntity")EssayEntity essayEntity);
    // 获取文章的系列类型
    List<String> getEssaySeriesList();
    // 获取按时间排序的文章
    List<EssayEntity> getEssayServiceTimeLineList(@Param(value = "map") Map<String, Object> map);
}
