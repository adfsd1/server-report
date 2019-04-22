package cn.report.controller;

import cn.report.api.SysUserService;
import cn.report.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;


/**
 * 管理员登录controller
 * 
 * @author pang
 *
 */

@Controller
@RequestMapping(value = "/sysUser")

public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	// 管理员登录,produces= MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"
	@RequestMapping(value = "/login")
	@ResponseBody
	public  Object loginUser(HttpServletRequest request, HttpServletResponse response,HttpSession session,
					 @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser.setPassword(password);


        SysUser ss = sysUserService.loginUser(sysUser);
		String methodStr = request.getParameter("callback");

		HashMap map= new HashMap<String,Object>();


		/*if (ss != null) {
			map.put("result","0");
			return "uu("+JSONArray.fromObject(map).toString()+")";
		} else {
			map.put("result","1");
			return "uu("+JSONArray.fromObject(map).toString()+")";
		}*/
		if (ss != null) {	
			session.setAttribute("username",username);
			//如果登陆成功，向服务器缓存用户信息，以备拦截器区分
			/*if(request.getServletContext().getAttribute(username) == null) {
				request.getServletContext().setAttribute(username, "0");
			}*/
			return  "0";
		} else {
			request.getServletContext().setAttribute(username, "1");
			return  "1";
		}

	}
	@RequestMapping(value = "/loginSys")
	public void loginSys(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {


		response.sendRedirect("index.html");
	}


}
