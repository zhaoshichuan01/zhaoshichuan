package com.example.zhaoshichuan.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// @WebFilter(urlPatterns = "/*")
// 过滤器需要实现Filter接口并重写三个方法，分别是init,doFilter,destroy
public class DemoFilter implements Filter {


    @Override //初始方法，springboot启动时只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("init 方法调用了");
    }

    @Override // 拦截请求后调用，多次调用
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 拦截
        System.out.println("拦截到了请求");
        // 放行
        chain.doFilter(request,response);
        System.out.println("放行了请求");
        // 放行后，需要执行的逻辑
        System.out.println("放行后，需要执行的逻辑");
    }

    @Override //销毁方法，springboot关闭时只调用一次
    public void destroy() {
        Filter.super.destroy();
        System.out.println("destroy 方法调用了");
    }
}
