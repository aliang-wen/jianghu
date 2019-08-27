package com.aliang.filter;


import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述：通过过滤解决跨域访问问题]
 * @Param:
 * @author: 作者 阿良
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CrossDomainFilter implements Filter {
    private final List<String> allowedOrigins = Arrays.asList("http://localhost:8088","http://localhost:8080","http://192.168.2.100:8088");// 允许跨域的地址

    public void destroy() {
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        配置这里每个方法都会走这里 在这里能不能添加token验证？？？
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String origin = req.getHeader("Origin");
//        resp.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Accept, Origin, verifyToken, loginToken");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println("跨域过滤方法进入"+req.getRequestURI()+req.getHeader("Origin"));

        if ((req.getRequestURI()).indexOf("/get404Info")>=0) {
            chain.doFilter(req, resp);
        } else {
            if (null == req.getHeader("verifyToken") || "null".equals(req.getHeader("verifyToken")) || !("123456789ceshikey".equals(req.getHeader("verifyToken"))) ) {
                JSONObject map = new JSONObject();
                map.put("statusCode","603");
                map.put("message","请携带正确的verifyToken进行数据访问");
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json; charset=utf-8");
                resp.getWriter().append(map.toString());
                if (null != resp.getWriter()) {
                    resp.getWriter().close();
                }
            } else {
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
