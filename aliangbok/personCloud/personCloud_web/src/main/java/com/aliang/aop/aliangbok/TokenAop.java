package com.aliang.aop.aliangbok;

import com.aliang.aliangbok_entity.logintokenentity.LoginTokenEntity;
import com.aliang.aliangbok_service.logintokenservice.LoginTokenService;
import com.aliang.aliangbok_until.returnfun.ReturnFun;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 切面判断token的有效性
 * @author: 阿良
 * @CreateDate: ${date} ${time}</p>
 */
@Aspect
@Order(1)
@Component
public class TokenAop extends ReturnFun {
    @Resource
    LoginTokenService loginTokenService;
    /**
     * 定义一个方法，用于声明切入点表达式，方法中一般不需要添加其他代码
     * 使用@Pointcut声明切入点表达式
     * 后面的通知直接使用方法名来引用当前的切点表达式；如果是其他类使用，加上包名即可
     */
    @Pointcut("execution(public * com.aliang.aliangbokh*.*.*.*(..))")
    public void verificationToken(){

    }

    /**
     * 前置通知
     * @param pjp
     */
    @Around("verificationToken()") //该标签声明次方法是一个前置通知：在目标方法开始之前执行
    public Object beforMethod(ProceedingJoinPoint  pjp) throws Throwable{
        LoginTokenEntity loginTokenEntity = null;
        // 请求开始时间
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        if (null == request.getHeader("loginToken") || "null".equals(request.getHeader("loginToken")) || "".equals(request.getHeader("loginToken")) ) {
            // 这里就是登录token不存在的时候
            System.out.println("token不存在");
            return returnInfoObj("604", "您没有访问权限请先登录");
        } else {
           // 这里是token是存在的时候 但是要验证是否失效
            loginTokenEntity = loginTokenService.getLoginTokenService(request.getHeader("loginToken"));
            if (null != loginTokenEntity && loginTokenEntity.getLoginFlag()>=0) {
                loginTokenEntity.setLoginFlag(loginTokenEntity.getLoginFlag()+1);
                loginTokenService.updateLoginTokenService(loginTokenEntity);
                //获取执行方法的参数
                System.out.println("token验证通过");
                return pjp.proceed();
            } else {
                System.out.println("token失效");
                return returnInfoObj("604", "您没有访问权限请先登录");
            }
        }
    }

}

