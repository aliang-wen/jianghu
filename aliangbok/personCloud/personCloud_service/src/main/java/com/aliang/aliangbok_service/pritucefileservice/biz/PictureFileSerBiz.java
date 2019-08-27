package com.aliang.aliangbok_service.pritucefileservice.biz;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface PictureFileSerBiz {
    // 保存图片 时候添加到数据库返回地址给路径
    public Map<String ,Object> savePicFile(MultipartFile file, String params);
    // 删除图片
    public Map<String, Object> delPicFile(String appId, String picFileCuuId);
}
