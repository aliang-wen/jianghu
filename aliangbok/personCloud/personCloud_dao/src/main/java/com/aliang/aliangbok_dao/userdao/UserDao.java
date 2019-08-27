package com.aliang.aliangbok_dao.userdao;

import com.aliang.aliangbok_entity.userinfo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Repository
public interface UserDao {
    // 通过密码查询用户信息
    public UserInfo findUserInfo(@Param(value = "userInfo") UserInfo userInfo);
    // 通过id查询用户信息
    public UserInfo findUserInfoById(@Param(value = "userId") String userId);
    // 通过userId 进行数据修改
    public boolean updateUserInfoById(@Param(value = "userInfo") UserInfo userInfo);
    // 进行输入错误计数
    public boolean updateUserInfoByIdLimit(@Param(value = "userInfo") UserInfo userInfo);

    // 查询可以展示的人员的信息
    public UserInfo findShowUserMainPage();

    // 查询可以展示的人员的信息
    public UserInfo findShowUserMainPageAboutMyself();
}
