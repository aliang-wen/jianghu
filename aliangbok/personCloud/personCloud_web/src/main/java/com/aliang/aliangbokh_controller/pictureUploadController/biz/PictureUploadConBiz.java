package com.aliang.aliangbokh_controller.pictureUploadController.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: 图片 文件的controller处理接口
 */
public interface PictureUploadConBiz {
    // 上传图片的方法
    ReturnInfo uploadPictureTitle(MultipartFile file, String uploadTitleFile);
    // 删除图片的方法
    ReturnInfo delPictureTitle(String appId, String params);
    // 上传文章内容文件的接口
    ReturnInfo uploadPictureEssay(MultipartFile file, String essayFCuuId);
}
