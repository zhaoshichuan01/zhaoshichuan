package com.example.zhaoshichuan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override //日标资源方法(也就是Controller)运行前运行，返回true: 放行，放回false，不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle...");
        // 获取请求的url
        HttpServletRequest req = request;
        HttpServletResponse resp = response;
        String url = req.getRequestURI();
        log.info("请求的url:{}", url);

        // 排除掉login请求，因为登录不拦截
        if (url.contains("login")){
            return true;
        }

        // 获取请头中的token
        String jwt = req.getHeader("token");

        // 判断令牌是否有效
        if (! StringUtils.hasLength(jwt)){
            log.info("请求头中token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }

        // 解析token，如果解析失败，返回未登录状态
        try {
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            log.info("解析令牌失败，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        log.info("令牌有效，放行");
        return true;
    }

    @Override //标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override //视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
