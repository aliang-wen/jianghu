package com.aliang.aliangbok_dao.picfiledao;

import com.aliang.aliangbok_entity.picfileentity.PicFileEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 这里是图片数据库的添加删除
 */
public interface PicFileDao {
    // 添加文件 图片
    public boolean addPicFile(@Param(value = "picFileEntity") PicFileEntity picFileEntity);
    // 删除文件图片
    boolean delPicFile(@Param(value = "picFileEntity") PicFileEntity picFileEntity);
    // 删除图片根据文章的副id
    boolean delPicFileByEssayFCuuid(@Param(value = "picFileEntity") PicFileEntity picFileEntity);
    // 查询图片数据
    PicFileEntity findPicFileById(@Param(value = "picFileEntity") PicFileEntity picFileEntity);
}
