package com.aliang.aliangbok_entity.bozhuskill;

import java.util.Date;

/**
 * @Description: 博主技能展示
 * @author: 阿良
 */
public class BoZhuSkillEntity {
    private String skId; // 博主id
    private String userId; // 博主id
    private String skName; // 博主id
    private Integer skScore; // 博主id
    private Date creatTime; // 博主id
    private Date updateTime; // 博主id

    public String getSkId() {
        return skId;
    }

    public void setSkId(String skId) {
        this.skId = skId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSkName() {
        return skName;
    }

    public void setSkName(String skName) {
        this.skName = skName;
    }

    public Integer getSkScore() {
        return skScore;
    }

    public void setSkScore(Integer skScore) {
        this.skScore = skScore;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
