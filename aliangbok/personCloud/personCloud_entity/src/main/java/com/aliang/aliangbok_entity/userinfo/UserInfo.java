package com.aliang.aliangbok_entity.userinfo;

import com.aliang.aliangbok_entity.bozhuskill.BoZhuSkillEntity;
import com.aliang.aliangbok_entity.headerpicture.HeaderPicture;

import java.util.List;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UserInfo {
    private String userId; // 博主id
    private String userName; // 博主的登录名
    private String password; // 博主登录密
    private String bigName; // 微博的名称显示
    private String userTel; //  博主电话
    private String aboutMyself; // 介绍自己
    private String qqNumber; // qq号码
    private String sex; // 性别
    private String hobby; // 爱好
    private String userAddress; // 住址
    private  Integer limitLoginNum; // 登录次数
    private String showMainPage; // 是否在页面展示
    private HeaderPicture bokHeaderPic; // 博主头像
    private String bokFlag; // 博主立的flag
    private String bokSkill; // 博主的技能
    private List<BoZhuSkillEntity> skillList; // 博主技能掌握

    public UserInfo() {
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBigName() {
        return bigName;
    }

    public void setBigName(String bigName) {
        this.bigName = bigName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getAboutMyself() {
        return aboutMyself;
    }

    public void setAboutMyself(String aboutMyself) {
        this.aboutMyself = aboutMyself;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Integer getLimitLoginNum() {
        return limitLoginNum;
    }

    public void setLimitLoginNum(Integer limitLoginNum) {
        this.limitLoginNum = limitLoginNum;
    }

    public String getShowMainPage() {
        return showMainPage;
    }

    public void setShowMainPage(String showMainPage) {
        this.showMainPage = showMainPage;
    }

    public HeaderPicture getBokHeaderPic() {
        return bokHeaderPic;
    }

    public void setBokHeaderPic(HeaderPicture bokHeaderPic) {
        this.bokHeaderPic = bokHeaderPic;
    }

    public String getBokFlag() {
        return bokFlag;
    }

    public void setBokFlag(String bokFlag) {
        this.bokFlag = bokFlag;
    }

    public String getBokSkill() {
        return bokSkill;
    }

    public void setBokSkill(String bokSkill) {
        this.bokSkill = bokSkill;
    }

    public List<BoZhuSkillEntity> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<BoZhuSkillEntity> skillList) {
        this.skillList = skillList;
    }
}
