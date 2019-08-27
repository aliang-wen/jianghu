package com.aliang.aliangbok_entity.essayentity;

import com.aliang.aliangbok_entity.userinfo.UserInfo;

import java.util.Date;

/**
 * @Title：${enclosing_method}
 * @Description: 文章的实体类
 */
public class EssayEntity {
    private String essayId; // 文章id
    private String essayFCuuId;
    private String userId; // 关联的人id
    private String essayNum; // 文章编号
    private String conText; // 文章内容
    private String conTitle; // 文章标题
    private String conType; // 文章分类
    private String essaySeries; // 一系列文章的番号
    private String CarouselShow; // 是否在轮播框中显示
    private String showBox; // 是否在展示框中显示
    private Integer readCount; // 文章点击次数
    private double recomStart; // 文章推荐度
    private String conFTitle; // 文章给你副标题
    private String conGText; // 文章概述
    private Date updateData; // 文章更新时间
    private Date createdData; // 文章创建时间
    private String ariticleState; // 文章是否展示被阅读  如果不被阅读 只能在后台查看前台不能够查看
    private String ariticlePrivate; // 如果是隐私文章可以在特殊板块进行查看
    private String oldEssayId; // 之前老的id
    private String titlePpicture; //图片地址
    private UserInfo userInfo; // 关联出作者信息

    public String getEssayFCuuId() {
        return essayFCuuId;
    }

    public void setEssayFCuuId(String essayFCuuId) {
        this.essayFCuuId = essayFCuuId;
    }


    public EssayEntity() {
    }

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEssayNum() {
        return essayNum;
    }

    public void setEssayNum(String essayNum) {
        this.essayNum = essayNum;
    }

    public String getConText() {
        return conText;
    }

    public void setConText(String conText) {
        this.conText = conText;
    }

    public String getConTitle() {
        return conTitle;
    }

    public void setConTitle(String conTitle) {
        this.conTitle = conTitle;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getCarouselShow() {
        return CarouselShow;
    }

    public void setCarouselShow(String carouselShow) {
        CarouselShow = carouselShow;
    }

    public String getShowBox() {
        return showBox;
    }

    public void setShowBox(String showBox) {
        this.showBox = showBox;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public double getRecomStart() {
        return recomStart;
    }

    public void setRecomStart(double recomStart) {
        this.recomStart = recomStart;
    }

    public String getConFTitle() {
        return conFTitle;
    }

    public void setConFTitle(String conFTitle) {
        this.conFTitle = conFTitle;
    }

    public String getConGText() {
        return conGText;
    }

    public void setConGText(String conGText) {
        this.conGText = conGText;
    }

    public Date getUpdateData() {
        return updateData;
    }

    public void setUpdateData(Date updateData) {
        this.updateData = updateData;
    }

    public Date getCreatedData() {
        return createdData;
    }

    public void setCreatedData(Date createdData) {
        this.createdData = createdData;
    }

    public String getAriticleState() {
        return ariticleState;
    }

    public void setAriticleState(String ariticleState) {
        this.ariticleState = ariticleState;
    }

    public String getAriticlePrivate() {
        return ariticlePrivate;
    }

    public void setAriticlePrivate(String ariticlePrivate) {
        this.ariticlePrivate = ariticlePrivate;
    }

    public String getOldEssayId() {
        return oldEssayId;
    }

    public void setOldEssayId(String oldEssayId) {
        this.oldEssayId = oldEssayId;
    }

    public String getTitlePpicture() {
        return titlePpicture;
    }

    public void setTitlePpicture(String titlePpicture) {
        this.titlePpicture = titlePpicture;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getEssaySeries() {
        return essaySeries;
    }

    public void setEssaySeries(String essaySeries) {
        this.essaySeries = essaySeries;
    }
}
