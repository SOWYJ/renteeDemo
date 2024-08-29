package com.example.activityservices.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 设置允许跨域访问的源
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 设置允许跨域访问的方法
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        // 设置允许跨域访问的头部
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        // 设置是否允许发送Cookie等凭证信息
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        // 设置预检请求的有效期，单位为秒
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(request, response);
    }

    // 其他方法略...
}

