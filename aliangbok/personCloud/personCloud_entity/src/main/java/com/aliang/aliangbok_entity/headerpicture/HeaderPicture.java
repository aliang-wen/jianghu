package com.aliang.aliangbok_entity.headerpicture;

import java.sql.Blob;
import java.util.Date;

/**
 * @Description:
 * @author: 阿良
 */
public class HeaderPicture {
    private String smallPicId;
    private String samllPicInfo;
    private Date updateTime;
    private Date creatTime;
    private String userId;

    public String getSmallPicId() {
        return smallPicId;
    }

    public void setSmallPicId(String smallPicId) {
        this.smallPicId = smallPicId;
    }

    public String getSamllPicInfo() {
        return samllPicInfo;
    }

    public void setSamllPicInfo(String samllPicInfo) {
        this.samllPicInfo = samllPicInfo;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
