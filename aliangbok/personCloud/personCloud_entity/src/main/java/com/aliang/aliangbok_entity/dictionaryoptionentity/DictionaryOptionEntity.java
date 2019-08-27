package com.aliang.aliangbok_entity.dictionaryoptionentity;

import java.util.Date;
import java.util.List;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class DictionaryOptionEntity {
    private String value;
    private String label;
    private String description ;
    private String dicPid;
    private Date updateTime;
    private Date creatTime;

    public List<DictionaryOptionEntity> getChildren() {
        return children;
    }

    public void setChildren(List<DictionaryOptionEntity> children) {
        this.children = children;
    }

    private List<DictionaryOptionEntity> children;

    public DictionaryOptionEntity() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String key) {
        this.value = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDicPid() {
        return dicPid;
    }

    public void setDicPid(String dicPid) {
        this.dicPid = dicPid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
