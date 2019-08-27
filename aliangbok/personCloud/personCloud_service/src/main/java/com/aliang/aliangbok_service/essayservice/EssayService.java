package com.aliang.aliangbok_service.essayservice;

import com.aliang.aliangbok_dao.essaydao.EssayDao;
import com.aliang.aliangbok_dao.picfiledao.PicFileDao;
import com.aliang.aliangbok_entity.essayentity.EssayEntity;
import com.aliang.aliangbok_entity.picfileentity.PicFileEntity;
import com.aliang.aliangbok_service.essayservice.biz.EssayBiz;
import com.aliang.aliangbok_until.aseutil.AESutil;
import com.aliang.aliangbok_until.getpageparams.PageParams;
import com.aliang.aliangbok_until.proputil.PropUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title：${enclosing_method}
 * @Description: 文章增删查改的方法
 */
@Service
public class EssayService implements EssayBiz {
    @Resource
    EssayDao essayDao;

    @Resource
    PicFileDao picFileDao;

    // 查询列表和总数
    public Map<String, Object> findEssayTable(String params, String appId) {
        PageParams pageParams = new PageParams();
        Map<String,Object> mapTemp = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        Map<String,Object> map = new HashMap<String, Object>();
        // 传进来的东西要先判断是否存在
        if (jsonObject.has("essayNum")) {
            mapTemp.put("essayNum", jsonObject.getString("essayNum"));
        } else {
            mapTemp.put("essayNum", "");
        }
        if (jsonObject.has("essayTitle")) {
            mapTemp.put("conTitle", jsonObject.getString("essayTitle"));
        } else {
            mapTemp.put("conTitle", "");
        }
        Integer allEssayNum = essayDao.allEssayNum(mapTemp);
        if (null != allEssayNum) {
            map.put("total", allEssayNum);
        } else {
            map.put("flag", "fail") ;
            map.put("message", "查询总条数失败");
            return map;
        }
        Integer essayCurrentPage = 0;
        if (jsonObject.has("essayCurrentPage")) {
            essayCurrentPage = jsonObject.getInt("essayCurrentPage");
        } else {
            essayCurrentPage = 1;
        }
        Integer essaySizePage = 0;
        if (jsonObject.has("essaySizePage")) {
            essaySizePage = jsonObject.getInt("essaySizePage");
        } else {
            essaySizePage = 10;
        }
//        这里将原来的分页注释掉  使用mybatis分页插件进行分页
//        pageParams.culPage(essayCurrentPage, allEssayNum, essaySizePage);
//        mapTemp.put("startIndex", pageParams.getStartIndex());
//        mapTemp.put("endIndex", pageParams.getEndIndex());

//        这里使用的是分页插件
        PageHelper.startPage(essayCurrentPage, essaySizePage);
        List<EssayEntity> essayList = essayDao.findEssayList(mapTemp);
        // 这里是获取总的条数的方法
//        PageInfo pageInfo = new PageInfo<EssayEntity>(essayList,essaySizePage);
        if (null != essayList) {
           map.put("flag", "success");
           map.put("message", "查询列表数据成功");
           map.put("essayTableLsit", essayList);
        } else {
            map.put("flag", "fail");
            map.put("message", "查询列表数据失败");
        }
        return map;
    }

    public Map<String, Object> findEssayById(String params, String appId) {
        EssayEntity essayEntity = new EssayEntity();
        JSONObject jsonObject = new JSONObject(params);
        Map<String,Object> map = new HashMap<String, Object>();
        String id = "";
        if (jsonObject.has("essayId")) {
           essayEntity.setEssayId(jsonObject.getString("essayId"));
        } else {
            if (jsonObject.has("userToken")) {
                try {
                    String allInfo = AESutil.desEncrypt(jsonObject.getString("userToken"));
                    String arrInfo[] = allInfo.split("-");
                    id = arrInfo[1];
                    essayEntity.setEssayId("temp_"+id);
                    essayEntity.setUserId(id);
                } catch (Exception e) {
                    System.out.println(e);
                    return null;
                }
            } else {
                essayEntity.setEssayId("");
            }
        }
        EssayEntity findEssayEntity = essayDao.findEssayById(essayEntity);
        if (null != findEssayEntity) {
            if (null != findEssayEntity.getTitlePpicture() && (! "".equals(findEssayEntity.getTitlePpicture()))) {
                findEssayEntity.setTitlePpicture(findEssayEntity.getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
            if (null != findEssayEntity.getConText() && (! "".equals(findEssayEntity.getConText()))) {
                findEssayEntity.setConText(findEssayEntity.getConText().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
            map.put("falg", "success");
            map.put("essay", findEssayEntity);
            map.put("message", "获取文章信息成功");
        } else {
            // 这里是查询查不到的时候 也就是临时的文章不存在的时候
            essayEntity.setEssayFCuuId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            if(essayDao.addEssay(essayEntity)) {
                findEssayEntity = essayDao.findEssayById(essayEntity);
//                findEssayEntity.setTitlePpicture(findEssayEntity.getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
//                findEssayEntity.setConText(findEssayEntity.getConText().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
                if (null != findEssayEntity) {
                    map.put("falg", "success");
                    map.put("essay", findEssayEntity);
                    map.put("message", "获取文章信息成功");
                } else {
                    map.put("falg", "fail");
                    map.put("message", "获取文章信息失败");
                }
            } else {
                map.put("falg", "fail");
                map.put("message", "插入文章信息失败");
            }
        }
        return map;
    }

    public Map<String, Object> delEssayTableById(String params, String appId) {
        EssayEntity essayEntity = new EssayEntity();
        JSONObject jsonObject = new JSONObject(params);
        Map<String,Object> map = new HashMap<String, Object>();
        if (jsonObject.has("essayId")) {
            essayEntity.setEssayId(jsonObject.getString("essayId"));
        } else {
            essayEntity.setEssayId("");
        }
        EssayEntity essayEntityTemp = essayDao.findEssayById(essayEntity);
//        没有图片和地址不用转换
//        essayEntityTemp.setTitlePpicture(essayEntityTemp.getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
//        essayEntityTemp.setConText(essayEntityTemp.getConText().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
        PicFileEntity picFileEntity = new PicFileEntity();
        picFileEntity.setEssayFCuuId(essayEntityTemp.getEssayFCuuId());
        picFileDao.delPicFileByEssayFCuuid(picFileEntity);
        String pathFile = PropUtil.getProperty("pcs.saveFileIn") + "/"+essayEntityTemp.getEssayFCuuId();
        File file = new File(pathFile);
        this.deleteFile(file);
        boolean flag = false;
        flag = essayDao.delEssayById(essayEntity);
        if (flag) {
            map.put("falg", "success") ;
            map.put("message", "删除信息成功") ;
        } else {
            map.put("falg", "fail") ;
            map.put("message", "删除信息失败") ;
        }
        return map;
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
    // 将所有的数据进行了保存
    public Map<String, Object> updateEssayTableById(String params, String appId) {
        Map<String,Object> map = new HashMap<String, Object>();
        EssayEntity essayEntity = new EssayEntity();
        JSONObject jsonObject = new JSONObject(params);
        if (jsonObject.has("essayId")) {
            essayEntity.setEssayId(jsonObject.getString("essayId"));
        } else {
            essayEntity.setEssayId("");
        }
        if (jsonObject.has("userId")) {
            essayEntity.setUserId(jsonObject.getString("userId"));
        } else {
            if (jsonObject.has("userToken")) {
                try {
                    String allInfo = AESutil.desEncrypt(jsonObject.getString("userToken"));
                    String arrInfo[] = allInfo.split("-");
                    essayEntity.setUserId(arrInfo[1]);
                } catch (Exception e) {
                    System.out.println(e);
                    map.put("falg","fail");
                    map.put("message","未知错误");
                    return map;
                }
            } else {
                essayEntity.setUserId("");
            }
        }
        if (jsonObject.has("essayNum")) {
            essayEntity.setEssayNum(jsonObject.getString("essayNum"));
        } else {
            essayEntity.setEssayNum("");
        }
        if (jsonObject.has("conText")) {
            // 存的时候将服务器的地址进行替换
            essayEntity.setConText(jsonObject.getString("conText").replace(PropUtil.getProperty("pcs.fileUrl"), PropUtil.getProperty("pcs.fileUrlReplace")));
        } else {
            essayEntity.setConText("");
        }
        if (jsonObject.has("conTitle")) {
            essayEntity.setConTitle(jsonObject.getString("conTitle"));
        } else {
            essayEntity.setConTitle("");
        }
        if (jsonObject.has("conType")) {
            essayEntity.setConType(jsonObject.getString("conType"));
        } else {
            essayEntity.setConType("");
        }
        if (jsonObject.has("essaySeries")) {
            essayEntity.setEssaySeries(jsonObject.getString("essaySeries"));
        } else {
            essayEntity.setEssaySeries("");
        }
        if (jsonObject.has("CarouselShow")) {
            essayEntity.setCarouselShow(jsonObject.getString("CarouselShow"));
        } else {
            essayEntity.setCarouselShow("SWITCH_OFF");
        }
        if (jsonObject.has("showBox")) {
            essayEntity.setShowBox(jsonObject.getString("showBox"));
        } else {
            essayEntity.setShowBox("SWITCH_OFF");
        }
        if (jsonObject.has("readCount")) {
            essayEntity.setReadCount(jsonObject.getInt("readCount"));
        } else {
            essayEntity.setReadCount(0);
        }
        if (jsonObject.has("recomStart")) {
            essayEntity.setRecomStart(jsonObject.getDouble("recomStart"));
        } else {
            essayEntity.setRecomStart(0);
        }
        if (jsonObject.has("conFTitle")) {
            essayEntity.setConFTitle(jsonObject.getString("conFTitle"));
        } else {
            essayEntity.setConFTitle("");
        }
        if (jsonObject.has("conGText")) {
            essayEntity.setConGText(jsonObject.getString("conGText"));
        } else {
            essayEntity.setConGText("");
        }
        if (jsonObject.has("ariticleState")) {
            essayEntity.setAriticleState(jsonObject.getString("ariticleState"));
        } else {
            essayEntity.setAriticleState("SWITCH_OFF");
        }
        if (jsonObject.has("ariticlePrivate")) {
            essayEntity.setAriticlePrivate(jsonObject.getString("ariticlePrivate"));
        } else {
            essayEntity.setAriticlePrivate("SWITCH_OFF");
        }
        if (jsonObject.has("titlePpicture")) {
            // 存的时候将服务器进行域名替换
            essayEntity.setTitlePpicture(jsonObject.getString("titlePpicture").replace(PropUtil.getProperty("pcs.fileUrl"), PropUtil.getProperty("pcs.fileUrlReplace")));
        } else {
            essayEntity.setTitlePpicture("");
        }
        if (jsonObject.has("essayFCuuId")) {
            essayEntity.setEssayFCuuId(jsonObject.getString("essayFCuuId"));
        } else {
            essayEntity.setEssayFCuuId("");
        }
        boolean flag = false;
        // 这里进行两种新增情况的判断
        String saveType = "";
        if (jsonObject.has("saveType")) {
           saveType =  jsonObject.getString("saveType");
        } else {
            saveType =  "";
        }
        if ("SAVE".equals(saveType)) {
            // 这里是永久保存 分两种情况一种是有文章编号的时候 那是在原来的基础上修改  一种是没有项目编号的时候  那是新增的要修改uuid
            if ("".equals(essayEntity.getEssayNum())) {
                EssayEntity essayEntityTemp = new EssayEntity();
                // 这里是没有的时候
                essayEntityTemp.setEssayId(essayEntity.getEssayId());
                essayEntityTemp.setUserId(essayEntity.getUserId());
                // 这里是副的cuuid
                essayEntityTemp.setEssayFCuuId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
                Date day=new Date();
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                // 这里是新增的情况要将essayId 和 essayNum进行修改
                essayEntity.setEssayNum("E"+df.format(day));
                essayEntity.setEssayId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
                // 修改成功后要进行插入临时数据处理
                essayEntity.setOldEssayId(essayEntityTemp.getEssayId());
                flag= essayDao.updateEssayById(essayEntity);
                if (flag) {
                    if (!essayDao.addEssay(essayEntityTemp)) {
                        map.put("falg","fail");
                        map.put("message","插入数据失败");
                        return  map;
                    }
                }
            } else {
                // 这里是在原有的基础上修改 id不会变
                essayEntity.setOldEssayId(essayEntity.getEssayId());
                flag= essayDao.updateEssayById(essayEntity);
            }
        } else {
            // 这里是临时保存 id不会变
            essayEntity.setOldEssayId(essayEntity.getEssayId());
            flag= essayDao.updateEssayById(essayEntity);
        }

        if (flag) {
            EssayEntity essay = essayDao.findEssayById(essayEntity);
            if (null != essay) {
                map.put("falg","success");
                map.put("essay", essay);
                map.put("message","数据修改成功");
            } else {
                map.put("falg","fail");
                map.put("message","查询保存的数据失败");
            }
            map.put("falg","success");
            map.put("message","数据修改成功");

        } else {
            map.put("falg","fail");
            map.put("message","数据修改失败");
        }
        return map;
    }

    public Map<String, Object> updateEssayByIdChangeSwitch(String params, String appId) {
        Map<String,Object> map = new HashMap<String, Object>();
        EssayEntity essayEntity = new EssayEntity();
        JSONObject jsonObject = new JSONObject(params);
        if (jsonObject.has("essayId")) {
            essayEntity.setEssayId(jsonObject.getString("essayId"));
        } else {
            essayEntity.setEssayId("");
        }
        if (jsonObject.has("conType")) {
            essayEntity.setConType(jsonObject.getString("conType"));
        } else {
            essayEntity.setConType("");
        }
        if (jsonObject.has("carouselShow")) {
            essayEntity.setCarouselShow(jsonObject.getString("carouselShow"));
        } else {
            essayEntity.setCarouselShow("SWITCH_OFF");
        }
        if (jsonObject.has("showBox")) {
            essayEntity.setShowBox(jsonObject.getString("showBox"));
        } else {
            essayEntity.setShowBox("SWITCH_OFF");
        }
        if (jsonObject.has("recomStart")) {
            essayEntity.setRecomStart(jsonObject.getInt("recomStart"));
        } else {
            essayEntity.setRecomStart(0);
        }
        if (jsonObject.has("ariticleState")) {
            essayEntity.setAriticleState(jsonObject.getString("ariticleState"));
        } else {
            essayEntity.setAriticleState("SWITCH_OFF");
        }
        if (jsonObject.has("ariticlePrivate")) {
            essayEntity.setAriticlePrivate(jsonObject.getString("ariticlePrivate"));
        } else {
            essayEntity.setAriticlePrivate("SWITCH_OFF");
        }
        boolean flag = false;
        flag= essayDao.updateEssayByIdChangeSwitch(essayEntity);
        if (flag) {
            map.put("falg","success");
            map.put("message","数据修改成功");
        } else {
            map.put("falg","fail");
            map.put("message","数据修改失败");
        }
        return map;
    }

    public Map<String, Object> readEssayInfo(String params, String appId) {
        Map<String,Object> map = new HashMap<String, Object>();
        EssayEntity essayEntity = new EssayEntity();
        EssayEntity essayEntityTwo = new EssayEntity();
        JSONObject jsonObject = new JSONObject(params);
        if (jsonObject.has("essayId")) {
            essayEntity.setEssayId(jsonObject.getString("essayId"));
        } else {
            essayEntity.setEssayId("");
        }
        try {
            essayEntityTwo = essayDao.readEssayInfoFun(essayEntity);
            if (null != essayEntityTwo.getTitlePpicture() && (! "".equals(essayEntityTwo.getTitlePpicture()))) {
                essayEntityTwo.setTitlePpicture(essayEntityTwo.getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
            if (null != essayEntityTwo.getConText() && (! "".equals(essayEntityTwo.getConText()))) {
                essayEntityTwo.setConText(essayEntityTwo.getConText().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (null != essayEntityTwo) {
           map.put("flag", "sucess");
           map.put("essay", essayEntityTwo);
        } else {
            map.put("flag", "fail");
            map.put("essay", null);
        }
        return map;
    }

    public Map<String, Object> getEssaySeriesListService() {
        Map<String ,Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list = essayDao.getEssaySeriesList();
        if (null != list) {
            map.put("list", list);
            map.put("flag", "success");
        } else {
            map.put("flag", "fail");
        }
        return map;
    }


}
