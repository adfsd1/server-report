package cn.report.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import javax.json.Json;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestURI = request.getRequestURI();
        String ss = request.getLocalAddr();
        String u2 = request.getMethod();
        System.out.println(requestURI + "]]]]");
        System.out.println(!requestURI.contains("/login"));
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());
        
        /*if(request.getParameter("login").equals("true")) {
        	
        }*/
        
        if (!requestURI.equals("/server-report/")) {
            String username = (String) request.getSession().getAttribute("username");
            
	            if (username == null) {
	                response.sendRedirect(request.getContextPath());
	                return false;
	            }
	            //单点登陆
	            /*System.out.println("服务器中的sessionID："+request.getServletContext().getAttribute(username));
	            System.out.println("当前会话中的sessionID:"+request
	            		.getSession().getId().toString());
	            if(request.getServletContext().getAttribute(username) != request
	            		.getSession().getId().toString()) {
	            	response.sendRedirect(request.getContextPath());
	                return false;
	            }*/
            
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    	/*if(request.getServletPath().contains("/sysUser/login")) {
	    	//单点登陆
	        ServletContext servletContext = request.getServletContext();
	        if(servletContext.getAttribute(request.getAttribute("username").toString()).equals("0")) {
	        	servletContext.setAttribute(request.getAttribute("username").toString(), request.getSession().getId());
	        }
    	}*/
    }
}
