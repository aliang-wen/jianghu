package com.aliang.aliangbok_dao.headerpicture;

import com.aliang.aliangbok_entity.headerpicture.HeaderPicture;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 这里是头像的保存修改
 * @author: 阿良
 */
public interface HeaderPictureDao {
    // 头像添加方法
    public Integer addHeaderPic(@Param(value = "headerPicture") HeaderPicture headerPicture);
    // 头像删除方法
    public Integer updateHeaderPic(@Param(value = "headerPicture") HeaderPicture headerPicture);
}
