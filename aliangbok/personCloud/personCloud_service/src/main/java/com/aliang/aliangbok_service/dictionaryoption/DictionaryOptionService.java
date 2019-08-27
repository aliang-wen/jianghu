package com.aliang.aliangbok_service.dictionaryoption;

import com.aliang.aliangbok_dao.dictionaryoptiondao.DictionaryOptionDao;
import com.aliang.aliangbok_entity.dictionaryoptionentity.DictionaryOptionEntity;
import com.aliang.aliangbok_service.dictionaryoption.biz.DictionaryOptionServiceBiz;
import com.aliang.aliangbok_until.getpageparams.PageParams;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class DictionaryOptionService implements DictionaryOptionServiceBiz {
    @Resource
    DictionaryOptionDao dictionaryOption;

    // 这里是表查询的字典项
    public Map<String, Object> finDictionaryOptioinTableSer(String params, String appId) {
        PageParams pageParams = new PageParams();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapreturn = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(params);
        Integer DictionaryOptionCurrent = 0;
        Integer DictionaryOptionPage = 10;
        if (jsonObject.has("type")) {
           if("RC01_AOBPR_".equals(jsonObject.get("type"))) {
               map.put("type",jsonObject.get("type"));
           }
            if("ZY02_AOBPR_".equals(jsonObject.get("type"))) {
                map.put("type",jsonObject.get("type"));
            }
        } else {
            map.put("type","");
        }
        if (jsonObject.has("optionKey")) {
            map.put("optionKey",jsonObject.get("optionKey"));
        } else {
            map.put("optionKey","");
        }
        if (jsonObject.has("optionName")) {
            map.put("optionName",jsonObject.get("optionName"));
        } else {
            map.put("optionName","");
        }
        if (jsonObject.has("DictionaryOptionCurrent")) {
            DictionaryOptionCurrent = jsonObject.getInt("DictionaryOptionCurrent");
        }

        if (jsonObject.has("DictionaryOptionPage")) {
            DictionaryOptionPage = jsonObject.getInt("DictionaryOptionPage");
        }
        Integer dictionaryOptionNum = dictionaryOption.finDictionaryOptioinTableNum(map);
        pageParams.culPage(DictionaryOptionCurrent, dictionaryOptionNum, DictionaryOptionPage);
        map.put("startIndex", pageParams.getStartIndex());
        map.put("endIndex", pageParams.getEndIndex());
        List<DictionaryOptionEntity> list = dictionaryOption.finDictionaryOptioinTable(map);
        mapreturn.put("allDictionaryOptionTable",list);
        mapreturn.put("allDictionaryOptionTableNum",dictionaryOptionNum);
        return mapreturn;
    }
    // 这里是日常查询字典项
    public Map<String,Object> finDictionaryOptioinArr(String params, String appId) {
        JSONObject jsonObject = new JSONObject(params);
        Map<String,Object> map = new HashMap<String, Object>();
        String[] arr = new String[]{};
        if (jsonObject.has("DictionaryOptionList")) {
            map.put("arr", jsonObject.getJSONArray("DictionaryOptionList"));
        } else {
            map.put("arr", arr);
        }
        if (jsonObject.has("dicType")) {
           map.put("dicType", jsonObject.getString("dicType"));
        } else {
            map.put("dicType", "");
        }
        List<DictionaryOptionEntity> list = dictionaryOption.finDictionaryOptioin(map);
        Map<String,Object> mapTemp = new HashMap<String, Object>();
        for(DictionaryOptionEntity j : list) {
            mapTemp.put(j.getValue(), j.getChildren());
        }
        return mapTemp;
    }

    public Map<String, Object> addDictionaryOption(String params, String appId) {
        JSONObject jsonObject = new JSONObject(params);
        Map<String,Object> map = new HashMap<String, Object>();
        Map<String,Object> mapInParams = new HashMap<String, Object>();
        DictionaryOptionEntity dictionaryOptionEntity = new DictionaryOptionEntity();
        // 进来先判断是添加子类还是父类
        String tempOptionType = "";
        if (null == jsonObject.getString("Pid") || "0".equals(jsonObject.getString("Pid")) || "".equals(jsonObject.getString("Pid"))) {
            // 判断类型，没有类型就按照日长类型处理
            if (null == jsonObject.get("optionType") ||"NO".equals(jsonObject.getString("optionType")) ||  "".equals(jsonObject.getString("optionType"))) {
                tempOptionType = "RC01_AOBPR_";
            } else {
                if (jsonObject.get("optionType").equals("RC01_AOBPR_")) {
                    tempOptionType = "RC01_AOBPR_";
                } else if (jsonObject.get("optionType").equals("ZY02_AOBPR_")){
                    tempOptionType = "ZY02_AOBPR_";
                } else {
                    tempOptionType = "RC01_AOBPR_";
                }
            }
            dictionaryOptionEntity.setDicPid("0");
        } else {
            tempOptionType = "";
            dictionaryOptionEntity.setDicPid(jsonObject.getString("Pid"));
        }

        // 这里是编码的情况
        if (null == jsonObject.getString("value") || "".equals(jsonObject.getString("value"))) {
            // 这里的不能为空
            map.put("flag", "fail");
            map.put("message", "请输入编码");
            return map;
        } else {
            // 这里不能包含RC01_AOBPR_  ZY02_AOBPR_ 还要去验重
            if (jsonObject.getString("value").indexOf("RC01_AOBPR_") > -1 || jsonObject.getString("value").indexOf("ZY02_AOBPR_")>-1 ) {
                map.put("flag", "fail");
                map.put("message", "输入的编码不能包含头部编码");
                return map;
            } else {
                mapInParams.put("arr",new String[]{tempOptionType + jsonObject.getString("value")});
                List<DictionaryOptionEntity> list = dictionaryOption.finDictionaryOptioin(mapInParams);
                if (null != list && list.size() > 0) {
                    map.put("flag", "fail");
                    map.put("message", "编码已经存在请重新设置编码");
                    return map;
                } else {
                    dictionaryOptionEntity.setValue(tempOptionType + jsonObject.getString("value"));
                }
            }
        }
        // 编码名称
        if (null == jsonObject.getString("label") || "".equals(jsonObject.getString("label"))) {
            // 这里的不能为空
            map.put("flag", "fail");
            map.put("message", "请输入编码名称");
            return map;
        } else {
            dictionaryOptionEntity.setLabel(jsonObject.getString("label"));
        }

        // 设置编码的描述
        if (null == jsonObject.getString("description")) {
            dictionaryOptionEntity.setDescription("");
        } else {
            dictionaryOptionEntity.setDescription(jsonObject.getString("description"));
        }

        if (dictionaryOption.addDictionaryOption(dictionaryOptionEntity)){
            map.put("flag", "success");
            map.put("message", "添加成功");
            return map;
        } else {
            map.put("flag", "fail");
            map.put("message", "添加失败");
            return map;
        }
    }

    public Map<String, Object> delDictionaryOption(String params, String appId) {
        JSONObject jsonObject = new JSONObject(params);
        Map<String,Object> map = new HashMap<String, Object>();
        DictionaryOptionEntity dictionaryOptionEntity = new DictionaryOptionEntity();
        if (null == jsonObject.getString("value") || "".equals(jsonObject.getString("value"))) {
            dictionaryOptionEntity.setValue("");
        } else {
            dictionaryOptionEntity.setValue(jsonObject.getString("value"));
        }
       if (dictionaryOption.delDictionaryOption(dictionaryOptionEntity)) {
           map.put("flag", "success");
           map.put("message", "信息删除成功");
       } else {
           map.put("flag", "fail");
           map.put("message", "信息删除失败");
       }

        return map;
    }

    public Map<String, Object> finDictionaryOptioinAllListService(String appId, String params) {
        JSONObject jsonObject = new JSONObject(params);
        Map<String, Object> mapreturn = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<DictionaryOptionEntity> list = null;
        String[] arr = new String[]{};
        if (jsonObject.has("DictionaryOptionList")) {
            map.put("arr", jsonObject.getJSONArray("DictionaryOptionList"));
        } else {
            map.put("arr", arr);
        }
        if (jsonObject.has("dicType")) {
            map.put("dicType", jsonObject.getString("dicType"));
        } else {
            map.put("dicType", "");
        }
        list = dictionaryOption.finDictionaryOptioinAllList(map);
        if (null != list) {
            mapreturn.put("flag", "success");
            mapreturn.put("message", "查询成功");
            mapreturn.put("DictionaryOption",list);
        } else {
            mapreturn.put("flag", "fail");
            mapreturn.put("message", "查询失败");
        }

        return mapreturn;
    }


}
