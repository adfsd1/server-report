package cn.report.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myFilter")
public class MyFilter implements Filter {

    public void destroy() {
        // System.out.println("过滤器销毁");
    }

    public void doFilter(ServletRequest request, ServletResponse response1, FilterChain chain) throws IOException,
            ServletException {
        // System.out.println("执行过滤操作");

        HttpServletResponse response = (HttpServletResponse) response1;

        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Headers",
                "User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken");

        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        response.setHeader("Access-Control-Max-Age", "1209600");

        response.setHeader("Access-Control-Expose-Headers", "accesstoken");

        response.setHeader("Access-Control-Request-Headers", "accesstoken");

        response.setHeader("Expires", "-1");

        response.setHeader("Cache-Control", "no-cache");

        response.setHeader("pragma", "no-cache");

        chain.doFilter(request, response);
    }

    public void init(FilterConfig arg0) throws ServletException {
         System.out.println("过滤器初始化");
    }

}
