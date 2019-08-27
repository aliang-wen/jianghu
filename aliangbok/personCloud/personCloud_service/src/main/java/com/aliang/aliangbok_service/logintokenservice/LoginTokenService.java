package com.aliang.aliangbok_service.logintokenservice;

import com.aliang.aliangbok_dao.logintoken.LoginTokenDao;
import com.aliang.aliangbok_entity.logintokenentity.LoginTokenEntity;
import com.aliang.aliangbok_service.logintokenservice.biz.LoginTokenServiceBiz;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class LoginTokenService implements LoginTokenServiceBiz {
    @Resource
    LoginTokenDao loginTokenDao;


    public boolean insertLoginTokeService(LoginTokenEntity loginTokenEntity) {
        return loginTokenDao.insertLoginTokeDao(loginTokenEntity);
    }

    public boolean updateLoginTokenService(LoginTokenEntity loginTokenEntity) {
        return loginTokenDao.updateLoginTokenDao(loginTokenEntity);
    }

    public LoginTokenEntity getLoginTokenService(String loginToken) {
        return loginTokenDao.getLoginTokenDao(loginToken);
    }
}
