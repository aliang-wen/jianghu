package com.aliang.aliangbokh_controller.dictionaryoption.biz;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface DictionaryOptionControllerBiz {
    ReturnInfo findDictionaryOptionTableList(String appId, String params);
    ReturnInfo addDictionaryOptionList(String appId, String params);
    ReturnInfo delDictionaryOption(String appId, String params);
}
