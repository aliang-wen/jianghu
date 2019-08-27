package com.aliang.aliangbok_service.pritucefileservice;

import com.aliang.aliangbok_dao.picfiledao.PicFileDao;
import com.aliang.aliangbok_entity.picfileentity.PicFileEntity;
import com.aliang.aliangbok_service.pritucefileservice.biz.PictureFileSerBiz;
import com.aliang.aliangbok_until.proputil.PropUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: 这里是处理图片的业务逻辑层
 */

@Service
public class PritureFileService implements PictureFileSerBiz {
    @Resource
    PicFileDao picFileDao;

    // 这是上传文件调用的方法
    public Map<String, Object> savePicFile(MultipartFile file, String params) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PicFileEntity picFileEntity = new PicFileEntity();
        // 图片先存到本地 再将数据保存到数据库
        if (null != file && null != file.getOriginalFilename() && "" != file.getOriginalFilename()) {
            picFileEntity.setFileName(file.getOriginalFilename());
        } else {
            picFileEntity.setFileName("");
        }
        String newfileName= UUID.randomUUID() + picFileEntity.getFileName().substring(picFileEntity.getFileName().lastIndexOf("."));
        picFileEntity.setFileCuuId(newfileName);
        // 设置图片保存路径
        File newfile=new File(PropUtil.getProperty("pcs.saveFileIn") + "/" + params, newfileName);
        // 这里是服务器的图片的地址
        picFileEntity.setFilePath(PropUtil.getProperty("pcs.saveFileIn") + "/" + params + newfileName);
        // 这里是图片的服务器的绝对路径
        picFileEntity.setFileUrl("/" + params + "/" +newfileName);
        picFileEntity.setEssayFCuuId(params);
        //新的图片
        try {
            if(!newfile.exists()){
                newfile.mkdirs();
            }
            //把内存图片写入磁盘中
            file.transferTo(newfile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (picFileDao.addPicFile(picFileEntity)) {
           returnMap.put("flag", "success");
           returnMap.put("url", PropUtil.getProperty("pcs.fileUrl") + "/" + params + "/" +newfileName);
           returnMap.put("name", picFileEntity.getFileName());
        } else {
            returnMap.put("flag", "fail");
            returnMap.put("url", "");
            returnMap.put("name", "");
        }
        return returnMap;
    }

    // 这里删除单个文件调用的方法
    public Map<String, Object> delPicFile(String appId, String picFileCuuId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(picFileCuuId);
        PicFileEntity picFileEntity = new PicFileEntity();
        if (jsonObject.has("picFileCuuid")) {
            picFileEntity.setFileCuuId(jsonObject.getString("picFileCuuid"));
        } else {
            picFileEntity.setFileCuuId("");
        }
        // 删除单个要去数据库查一下
        PicFileEntity picFileEntityFind = picFileDao.findPicFileById(picFileEntity);
        String pathFile = "";
        if (null != picFileEntityFind) {
            pathFile = picFileEntityFind.getFilePath();
        }
        File file = new File(pathFile + "/"+jsonObject.getString("picFileCuuid"));
        // 文件是否存在 如果存在的化就执行删除
        if (file.exists()) {
            this.deleteFile(file);
            if (picFileDao.delPicFile(picFileEntity)) {
                returnMap.put("flag", "success");
                returnMap.put("message", "文件删除成功");
            } else {
                returnMap.put("flag", "fail");
                returnMap.put("message", "文件删除失败");
            }
        } else {
            picFileDao.delPicFile(picFileEntity);
            returnMap.put("flag", "success");
            returnMap.put("message", "文件删除成功");
        }
        return returnMap;
    }
    // 这里是单独的递归处理删除的方法
    private void deleteFile(File file){
         if(file.exists()){
             if(file.isDirectory()){
                 File[] files = file.listFiles();
                 for(int i=0; i<files.length; i++){
                     deleteFile(files[i]);
                 }
             }
         }
         file.delete();
    }
}
