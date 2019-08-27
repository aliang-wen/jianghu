package com.aliang.aliangbok_service.loginbok;

import com.aliang.aliangbok_dao.ase_dao.AseDao;
import com.aliang.aliangbok_dao.codetoken.CodeToken;
import com.aliang.aliangbok_dao.userdao.UserDao;
import com.aliang.aliangbok_entity.aseentit.AseEntity;
import com.aliang.aliangbok_entity.logintokenentity.LoginTokenEntity;
import com.aliang.aliangbok_entity.securitycode.SecurityCodeEntity;
import com.aliang.aliangbok_entity.userinfo.UserInfo;
import com.aliang.aliangbok_service.loginbok.biz.LoginBokServiceBiz;
import com.aliang.aliangbok_service.logintokenservice.LoginTokenService;
import com.aliang.aliangbok_until.aseutil.AESutil;
import com.aliang.aliangbok_until.securitycode.SecurityCode;
import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class LoginBokService implements LoginBokServiceBiz {
    @Resource
    UserDao userDao;
    @Resource
    AseDao aseDao;
    @Resource
    CodeToken codeToken;
    @Resource
    LoginTokenService loginTokenService;

    public Map<String, Object> loginBok( String params, String appId) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        String token = null;
        String loginToken = "";
        JSONObject jsonObject = new JSONObject(params);
        UserInfo userInfo = new UserInfo();
        UserInfo us = new UserInfo();
        SecurityCodeEntity securityCodeEntity = new SecurityCodeEntity();
        // 揭秘之前要对验证码
        if (jsonObject.has("SecutityCodeToken")) {
            securityCodeEntity = codeToken.getCode(jsonObject.getString("SecutityCodeToken"));
        }else {
            securityCodeEntity = codeToken.getCode("");
        }
        if (null != securityCodeEntity && securityCodeEntity.getCode() != "null" && securityCodeEntity.getCode() != "") {
            // 这里是查询到验证码进行比较
            if (((securityCodeEntity.getCode()).toLowerCase()).equals(jsonObject.getString("virCode").toLowerCase())) {
                // 这里是验证码输入成功
                // 这里对账号密码进行揭秘
                AseEntity aseEntity = aseDao.getAseInfo(appId);
                String password = "";
                try {
                    password = AESutil.desEncrypt(jsonObject.getString("password").trim(), aseEntity.getAseKey(), aseEntity.getAseIv());
                }catch (Exception e) {
                    System.out.println(e);
                }
                userInfo.setUserName(jsonObject.getString("userName").trim());
                UserInfo userfo = userDao.findUserInfo(userInfo);
                if (userfo != null && userfo.getUserId() != null && userfo.getUserId().length()>0) {
                    if (userfo.getLimitLoginNum() < 10) {
                        if ((userfo.getPassword()).equals(password.trim())) {
                            // 这里是登录成功
                            token = userfo.getUserName() + "-" + userfo.getUserId() + "-" + new Date().getTime() + (int) (Math.random() * 1000000);
                            try {
                                // 这里的token采用再aop中进行五分钟改变一次 加密的字符串改变
                                loginToken = AESutil.encrypt(token);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            LoginTokenEntity loginTokenEntity = new LoginTokenEntity();
                            loginTokenEntity.setLoginFlag(0);
                            loginTokenEntity.setLoginToken(loginToken);
                            // 向数据库添加生成的token
                            if (loginTokenService.insertLoginTokeService(loginTokenEntity)) {
                                us.setUserId(userfo.getUserId());
                                us.setLimitLoginNum(0);
                                if (userDao.updateUserInfoByIdLimit(us)) {
                                    mapResult.put("loginToken", loginToken);
                                    mapResult.put("flag", "success");
                                    mapResult.put("message", "登录成功");
                                } else {
                                    mapResult.put("loginToken", loginToken);
                                    mapResult.put("flag", "success");
                                    mapResult.put("message", "登录成功,但是清空登录失败次数失败");
                                }
                            } else {
                                mapResult.put("loginToken", "");
                                mapResult.put("flag", "error");
                                mapResult.put("message", "登录失败,token存储失败");
                            }
                        } else {
                            // 这里调用修改方法
                            us.setLimitLoginNum(userfo.getLimitLoginNum() + 1);
                            us.setUserId(userfo.getUserId());
                            if (userDao.updateUserInfoByIdLimit(us)) {
                                loginToken = "";
                                mapResult.put("loginToken", loginToken);
                                mapResult.put("message", "账号密码出错,您已经输入"+(userfo.getLimitLoginNum()+1)+"次错误,还有"+(9-userfo.getLimitLoginNum())+"次机会");
                                mapResult.put("flag", "error");
                            } else {
                                loginToken = "";
                                mapResult.put("loginToken", loginToken);
                                mapResult.put("message", "登录失败账号密码不正确且没有进行数据库计数");
                                mapResult.put("flag", "error");
                            }
                        }
                    } else {
                        mapResult.put("loginToken", "");
                        mapResult.put("message", "密码输入错误次数过多请等待30分钟后重新输入");
                        mapResult.put("flag", "error");
                    }
                } else {
                    mapResult.put("loginToken", "");
                    mapResult.put("message", "用户名无效");
                    mapResult.put("flag", "error");
                }
            } else {
                // 这里是验证码输入错误
                mapResult.put("loginToken", "");
                mapResult.put("message", "验证码输入错误");
                mapResult.put("flag", "error");
            }
        } else {
            // 这里是查询不到验证码 相当于过时
            mapResult.put("loginToken", "");
            mapResult.put("message", "验证码过时");
            mapResult.put("flag", "error");
        }
        return mapResult;
    }

    // 这里是获取密钥的方法 超时后就要更新没有超时就使用当前的加密串
    public Map<String, Object> getKeyIv(String appId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String key = "";
        String iv = "";
        // 获取当前的时间戳
        Date TimeTemp = new Date();
        AseEntity aseEntity = aseDao.getAseInfo(appId);
        if (TimeTemp.getTime() - aseEntity.getAseTime().getTime() > 1800) {
            // 这里是超出时间之后
            // 这里的key iv都应是16位
            key = "" + TimeTemp.getTime() + "key";
            iv = "" + TimeTemp.getTime() + "ivv";
            AseEntity ae = new AseEntity();
            ae.setAseId(appId);
            ae.setAseIv(iv);
            ae.setAseKey(key);
            ae.setAseTime(TimeTemp);
            if (aseDao.updateAseInfo(ae)) {
               // 这里是保存成功了
                map.put("key", key);
                map.put("iv", iv);
                map.put("flag", "success");
            } else {
                // 这里是保存失败了
                map.put("key", "");
                map.put("iv", "");
                map.put("flag", "error");
            }
        } else {
            // 这里是时间还没超出
            map.put("key", aseEntity.getAseKey());
            map.put("iv", aseEntity.getAseIv());
            map.put("flag", "success");
        }
        return map;
    }

    // 这里是验证码的生成和向数据库中存数据
    public Map<String, Object> getSecurityCode() {
        Map<String,Object> mapReturn = new HashMap<String, Object>();

        // 这个方法返回一个图片和一个token 并且要存到数据库中去
        Map<String,Object> map = SecurityCode.generateCodeAndPic(36,120, 4, 20, 24, 26);

        // bufferImage->base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write((BufferedImage)map.get("codePic"), "jpg", baos);//写入流中
        } catch (Exception e) {
            System.out.println(e);
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        // 每次进来就要生成一个唯一标识  仅仅是标识  不是token
        String SecutityCodeToken = new Date().getTime() + "aliang"+ (int) (Math.random()*1000000);
        SecurityCodeEntity securityCodeEntity = new SecurityCodeEntity();
        // 这是向数据库存的字段
        securityCodeEntity.setCode(map.get("code").toString());
        securityCodeEntity.setTokenCode(SecutityCodeToken);
        // 如果能够新增就进行 否则失败
        if (codeToken.inertSecurityCode(securityCodeEntity)) {
            mapReturn.put("SecutityCodeToken", SecutityCodeToken);
            mapReturn.put("SecurityPic", "data:image/jpg;base64," + png_base64);
            mapReturn.put("flag", "success");
        } else {
            mapReturn.put("message", "获取验证码失败");
            mapReturn.put("flag", "error");
        }
        return mapReturn;
    }

}
