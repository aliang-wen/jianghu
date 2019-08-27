package com.aliang.aliangbok_service.mainpage;

import com.aliang.aliangbok_dao.chinaaredao.ChianAreaDao;
import com.aliang.aliangbok_dao.dictionaryoptiondao.DictionaryOptionDao;
import com.aliang.aliangbok_dao.essaydao.EssayDao;
import com.aliang.aliangbok_dao.userdao.UserDao;
import com.aliang.aliangbok_entity.chianareaentity.ChinaAreaEntity;
import com.aliang.aliangbok_entity.dictionaryoptionentity.DictionaryOptionEntity;
import com.aliang.aliangbok_entity.essayentity.EssayEntity;
import com.aliang.aliangbok_entity.userinfo.UserInfo;
import com.aliang.aliangbok_service.mainpage.biz.MainPageBiz;
import com.aliang.aliangbok_until.aseutil.AESutil;
import com.aliang.aliangbok_until.proputil.PropUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: 这里是后台首页的方法  暂时还没有写
 * @Param:
 * @author: 阿良
 */
@Service
public class MainPage implements MainPageBiz {
    @Resource
    UserDao userDao;
    @Resource
    ChianAreaDao chianAreaDao;
    @Resource
    EssayDao essayDao;

    // 通过loginToken获取博客信息
    public Map<String, Object> findAllUserInfo(String Params) {
        String id ="";
        Map<String,Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(Params);
        try {
            String allInfo = AESutil.desEncrypt(jsonObject.getString("loginToken"));
            String arrInfo[] = allInfo.split("-");
            id = arrInfo[1];
        } catch (Exception e) {
            System.out.println(e);
        }
        UserInfo bokuser = userDao.findUserInfoById(id);
        map.put("bokuser", bokuser);
        map.put("flag", "success");
        return map;
    }

    public Map<String, Object> getUserInfoService(String appId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        UserInfo userInfo = userDao.findShowUserMainPage();
        if (null != userInfo) {
            // 慢的原因这里要查询一个省市区的树状结构
            List<ChinaAreaEntity> list = chianAreaDao.getAllChinarea();
            String[] addArr = userInfo.getUserAddress().split("-");
            String strTemp ="";
            strTemp = this.getChindessAddresss(list, addArr, 0, addArr.length-1);
            userInfo.setUserAddress(strTemp);
            returnMap.put("flag", "success");
            returnMap.put("message", "获取到了博主信息");
            returnMap.put("userInfo", userInfo);
        } else {
            returnMap.put("flag", "success");
            returnMap.put("message", "未获取到了博主信息");
        }
        return returnMap;
    }

    private String getChindessAddresss(List<ChinaAreaEntity> list, String[] params, int m, int n){
        String str = "";
        if(null != list) {
            for(ChinaAreaEntity ch : list ) {
                if(ch.getValue().equals(params[m])) {
                    str = ch.getLabel();
                    if (m < n) {
                        str +=this.getChindessAddresss(ch.getChildren(), params, m+1,n);
                    }
                }
            }
        }
        return str;
    }

    public Map<String, Object> getUserInfoCarousel(String appId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<EssayEntity> listEssay = essayDao.findEssayListOfCarousel();
        for (int i = 0; i < listEssay.size(); i++) {
            if (null != listEssay.get(i).getTitlePpicture() && (! "".equals(listEssay.get(i).getTitlePpicture()))) {
                listEssay.get(i).setTitlePpicture(listEssay.get(i).getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
        }
        if (null != listEssay) {
            returnMap.put("flag", "success");
            returnMap.put("message", "获取信息成功");
            returnMap.put("listEssay", listEssay);
        } else {
            returnMap.put("flag", "fail");
            returnMap.put("message", "获取信息失败");
        }
        return returnMap;
    }

    public Map<String, Object> getUserInfoShowBox(String appId) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<EssayEntity> listEssay = essayDao.findEssayListOfShowBox();
        for (int i = 0; i < listEssay.size(); i++) {
            if (null != listEssay.get(i).getTitlePpicture() && (! "".equals(listEssay.get(i).getTitlePpicture()))) {
                listEssay.get(i).setTitlePpicture(listEssay.get(i).getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
        }
        if (null != listEssay) {
            returnMap.put("flag", "success");
            returnMap.put("message", "获取信息成功");
            returnMap.put("listEssay", listEssay);
        } else {
            returnMap.put("flag", "fail");
            returnMap.put("message", "获取信息失败");
        }
        return returnMap;
    }

    public Map<String, Object> findEssayListPageSer(String appId, String params) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
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
        PageHelper.startPage(essayCurrentPage, essaySizePage);
        if (jsonObject.has("conType")) {
            map.put("conType", jsonObject.getString("conType"));
        } else {
            map.put("conType", "");
        }
        if (jsonObject.has("essaySeries")) {
            map.put("essaySeries", jsonObject.getString("essaySeries"));
        } else {
            map.put("essaySeries", "");
        }
        if (jsonObject.has("showOrder")) {
            map.put("showOrder", jsonObject.getString("showOrder"));
        } else {
            map.put("showOrder", "");
        }
        List<EssayEntity> listEssay = essayDao.findEssayListPage(map);
        for (int i = 0; i < listEssay.size(); i++) {
            if (null != listEssay.get(i).getTitlePpicture() && (! "".equals(listEssay.get(i).getTitlePpicture()))) {
                listEssay.get(i).setTitlePpicture(listEssay.get(i).getTitlePpicture().replace(PropUtil.getProperty("pcs.fileUrlReplace"), PropUtil.getProperty("pcs.fileUrl")));
            }
        }
        Integer total = essayDao.findEssayListPageCount(map);
        if (null != listEssay) {
            returnMap.put("flag", "success");
            returnMap.put("message", "获取信息成功");
            returnMap.put("listEssay", listEssay);
            returnMap.put("total", total);
        } else {
            returnMap.put("flag", "fail");
            returnMap.put("message", "获取信息失败");
        }
        return returnMap;
    }

    public Map<String, Object> findShowUserMainPageAboutMyself(String appId, String params) {
        UserInfo userInfo = userDao.findShowUserMainPageAboutMyself();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (null != userInfo) {
            returnMap.put("flag", "success");
            returnMap.put("message", "查询成功");
            returnMap.put("userInfo", userInfo);
        } else {
            returnMap.put("flag", "error");
            returnMap.put("flag", "查询失败");
        }
        return returnMap;
    }

    // 文章时间轴的展示
    public Map<String, Object> getEssayServiceTimeLineListService(String appId, String params) {
        Map<String, Object> mapTemp = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<EssayEntity> essayListTime = new ArrayList<EssayEntity>();
        JSONObject jsonObject = new JSONObject(params);
        Integer essayCurrentPage = 1;
        Integer essaySizePage = 10;
        if (jsonObject.has("essayCurrentPage")) {
            essayCurrentPage = jsonObject.getInt("essayCurrentPage");
        } else {
            essayCurrentPage = 1;
        }
        if (jsonObject.has("essaySizePage")) {
            essaySizePage = jsonObject.getInt("essaySizePage");
        } else {
            essaySizePage = 10;
        }
        PageHelper.startPage(essayCurrentPage, essaySizePage);
        essayListTime = essayDao.getEssayServiceTimeLineList(mapTemp);
        // 这里是获取总的条数的方法
        PageInfo pageInfo = new PageInfo<EssayEntity>(essayListTime,essaySizePage);
        if (null != essayListTime && essayListTime.size() >=0) {
            returnMap.put("flag","success");
            returnMap.put("essayList", essayListTime);
            returnMap.put("total",pageInfo.getTotal());
        } else {
            returnMap.put("flag","fail");
            returnMap.put("message","获取数据失败");
        }
        return returnMap;
    }

}
