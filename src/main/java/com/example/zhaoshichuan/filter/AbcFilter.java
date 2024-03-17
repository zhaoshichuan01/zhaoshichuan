package com.example.zhaoshichuan.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// @WebFilter(urlPatterns = "/*")
public class AbcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 拦截
        System.out.println("abc拦截到了请求");
        // 放行
        chain.doFilter(request,response);
        System.out.println("abc放行了请求");
        // 放行后，需要执行的逻辑
        System.out.println("abc放行后，需要执行的逻辑");
    }
}
