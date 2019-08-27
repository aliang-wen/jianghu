package com.aliang.aliangbok_until.returnfun;

import com.aliang.aliangbok_until.returninfo.ReturnInfo;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public abstract class ReturnFun {
    // 构造方法
    public ReturnFun() {
    }
    // 没有参数的时候
    public ReturnInfo returnInfoObj() {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("成功");
        returnInfo.setStatusCode("200");
        return returnInfo;
    }
    // 一个参数的时候
    public ReturnInfo returnInfoObj(Object data) {
       ReturnInfo returnInfo = new ReturnInfo();
       returnInfo.setResult(data);
       returnInfo.setMessage("成功");
       returnInfo.setStatusCode("200");
       return returnInfo;
    }
    // 两个参数的时候 状态码 和 信息
    public ReturnInfo returnInfoObj(String code, String message) {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage(message);
        returnInfo.setStatusCode(code);
        return returnInfo;
    }
}
