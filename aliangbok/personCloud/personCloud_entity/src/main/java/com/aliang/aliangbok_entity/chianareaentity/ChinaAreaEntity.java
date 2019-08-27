package com.aliang.aliangbok_entity.chianareaentity;

import java.util.List;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ChinaAreaEntity {
    private String value;
    private String label;
    private String AreaPid;
    private List<ChinaAreaEntity> children;

    public ChinaAreaEntity() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAreaPid() {
        return AreaPid;
    }

    public void setAreaPid(String areaPid) {
        AreaPid = areaPid;
    }

    public List<ChinaAreaEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChinaAreaEntity> children) {
        this.children = children;
    }
}
