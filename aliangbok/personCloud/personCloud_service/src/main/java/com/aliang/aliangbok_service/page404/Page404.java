package com.aliang.aliangbok_service.page404;

import com.aliang.aliangbok_service.page404.biz.Page404Biz;
import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import com.aliang.aliangbok_until.urlbydata.GetUrlData;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.xml.transform.Templates;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class Page404 implements Page404Biz {
    public Map<String, Object> analyzeUrl(String Url) {
        String bodyString = null;
        String jsonString = null;
        Document document = GetUrlData.analyzeHTMLByString(Url);
        Element body = document.body();
        bodyString = body.toString();
        jsonString = bodyString.substring(bodyString.indexOf("{") ,bodyString.lastIndexOf("}")+1);
        //下面是把拿到的json字符串转成 json对象
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonDataArray = jsonObject.getJSONArray("data");
        JSONArray TempjosnArry = new JSONArray();
        if (jsonDataArray.length() > 5) {
            int c= 0;
            for (int i = 0; i < 5; i++) {
                c = (int)(Math.random()*(jsonDataArray.length()));
                TempjosnArry.put(jsonDataArray.get(c));
                jsonDataArray.remove(c);
            }
        } else {
            TempjosnArry = jsonDataArray;
        }
        JSONObject jsonConsObject = jsonObject.getJSONObject("contacts");
        if (null != TempjosnArry && !"null".equals(TempjosnArry) && TempjosnArry.length() > 0) {
            list.clear();
            for (int i = 0; i < TempjosnArry.length(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", ((JSONObject)TempjosnArry.get(i)).getString("id"));
                map.put("name", ((JSONObject)TempjosnArry.get(i)).getString("name"));
                map.put("sex", ((JSONObject)TempjosnArry.get(i)).getString("sex"));
                map.put("birth_time", ((JSONObject)TempjosnArry.get(i)).getString("birth_time"));
                map.put("lost_time", ((JSONObject)TempjosnArry.get(i)).getString("lost_time"));
                map.put("child_pic", "http://qzonestyle.gtimg.cn/qzone_v6/lostchild/images/"+((JSONObject)TempjosnArry.get(i)).getString("child_pic"));
                map.put("lost_place", ((JSONObject)TempjosnArry.get(i)).getString("lost_place"));
                map.put("url", ((JSONObject)TempjosnArry.get(i)).getString("url"));
                map.put("child_feature", ((JSONObject)TempjosnArry.get(i)).getString("child_feature"));
                list.add(map);
            }
        }
        Map<String,Object> allResult = new HashMap<String, Object>();
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("tel",jsonConsObject.getString("tel"));
        conMap.put("consult_mail",jsonConsObject.getString("consult_mail"));
        allResult.put("childrens", list);
        allResult.put("cons", conMap);
        return allResult;
    }
}
