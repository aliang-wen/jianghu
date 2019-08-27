package com.aliang.aliangbok_service.headerpicservice;

import com.aliang.aliangbok_dao.headerpicture.HeaderPictureDao;
import com.aliang.aliangbok_entity.headerpicture.HeaderPicture;
import com.aliang.aliangbok_service.headerpicservice.biz.HeaderPicBiz;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: 头像上传的service biz
 */
@Service
public class HeaderPicService implements HeaderPicBiz {
    @Resource
    HeaderPictureDao headerPictureDao;

    public Map<String, Object> uploadHeaderPicService(String params, String appId) {
        HeaderPicture headerPicture = new HeaderPicture();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        if(jsonObject.has("picBase")) {
            headerPicture.setSamllPicInfo(jsonObject.getString("picBase"));
        } else {
            headerPicture.setSamllPicInfo("");
        }
        if (jsonObject.has("fileId") && !("".equals(jsonObject.getString("fileId")))) {
            headerPicture.setSmallPicId(jsonObject.getString("fileId"));
            // 这里走的是修改的方法
            if (headerPictureDao.updateHeaderPic(headerPicture) > 0) {
                returnMap.put("flag", "success");
                returnMap.put("message", "上传成功");
                return returnMap;
            } else {
                returnMap.put("flag", "fail");
                returnMap.put("message", "上传出错");
                return returnMap;
            }
        } else {
            if (jsonObject.has("userId") && !("".equals(jsonObject.getString("userId")))) {
                // 这里走的是新增的方法
                headerPicture.setUserId(jsonObject.getString("userId"));
                headerPicture.setSmallPicId(jsonObject.getString("userId") + UUID.randomUUID().toString().trim().replaceAll("-", ""));
                if (headerPictureDao.addHeaderPic(headerPicture) > 0) {
                    returnMap.put("flag", "success");
                    returnMap.put("message", "上传成功");
                    return returnMap;
                } else {
                    returnMap.put("flag", "fail");
                    returnMap.put("message", "上传出错");
                    return returnMap;
                }
            } else {
                // 这里走的是错误的方法
                returnMap.put("flag", "fail");
                returnMap.put("message", "上传出错");
                return returnMap;
            }
        }
    }

}
