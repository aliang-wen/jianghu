package com.aliang.aliangbok_entity.aseentit;

import javafx.scene.chart.PieChart;

import java.util.Date;

/**
 * @Title：${enclosing_method}
 * @Description: 加密的实体类
 */
public class AseEntity {
    private String aseId; // 加密id
    private String aseKey; // 加密key
    private String aseIv; // 加密aseiv
    private Date aseTime; // 创建时间

    public AseEntity() {
    }

    public String getAseId() {
        return aseId;
    }

    public void setAseId(String aseId) {
        this.aseId = aseId;
    }

    public String getAseKey() {
        return aseKey;
    }

    public void setAseKey(String aseKey) {
        this.aseKey = aseKey;
    }

    public String getAseIv() {
        return aseIv;
    }

    public void setAseIv(String aseIv) {
        this.aseIv = aseIv;
    }

    public Date getAseTime() {
        return aseTime;
    }

    public void setAseTime(Date aseTime) {
        this.aseTime = aseTime;
    }
}
