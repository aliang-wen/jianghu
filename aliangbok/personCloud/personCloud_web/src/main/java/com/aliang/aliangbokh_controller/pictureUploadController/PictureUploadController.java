package com.aliang.aliangbokh_controller.pictureUploadController;

import com.aliang.aliangbok_service.pritucefileservice.biz.PictureFileSerBiz;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbokh_controller.pictureUploadController.biz.PictureUploadConBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * @Description: 图片文件的处理 Controller
 */
@Controller
@RequestMapping(value = "back/",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
public  class PictureUploadController extends ReturnFun implements PictureUploadConBiz {
    @Resource
    PictureFileSerBiz pictureFileSer;

    @RequestMapping(value = "uploadPictureTitle")
    public @ResponseBody
    ReturnInfo uploadPictureTitle(@RequestParam MultipartFile file, @RequestParam String essayFCuuId) {
        return returnInfoObj(pictureFileSer.savePicFile(file, essayFCuuId));
    }

    @RequestMapping(value = "delPictureTitle/{appId}")
    public @ResponseBody
    ReturnInfo delPictureTitle(@PathVariable String appId, @RequestBody String params) {
        return returnInfoObj(pictureFileSer.delPicFile(appId, params));
    }
    @RequestMapping(value = "uploadPictureEssay")
    public @ResponseBody
    ReturnInfo uploadPictureEssay(@RequestParam MultipartFile upFile, @RequestParam String essayFCuuId) {
        return returnInfoObj(pictureFileSer.savePicFile(upFile, essayFCuuId));
    }

}
