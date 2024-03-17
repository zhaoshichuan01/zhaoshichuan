package com.example.zhaoshichuan.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 获取请求的url
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();
        log.info("请求的url:{}", url);

        // 排除掉login请求，因为登录不拦截
        if (url.contains("login")){
            log.info("登录请求，放行");
            chain.doFilter(request, response);
            return;
        }

        // 获取请头中的token
        String jwt = req.getHeader("token");

        // 判断令牌是否有效
        if (! StringUtils.hasLength(jwt)){
            log.info("请求头中token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }

        // 解析token，如果解析失败，返回未登录状态
        try {
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            log.info("解析令牌失败，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        log.info("令牌有效，放行");
        chain.doFilter(request, response);

    }
}
