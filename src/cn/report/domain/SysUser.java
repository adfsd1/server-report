package cn.report.domain;

import java.io.Serializable;
/**
 * 管理员实体类
 * @author pang
 *
 */

public class SysUser implements Serializable {

	
	private static final long serialVersionUID = 1L;

	//管理员id
	private Long userId;
	
	//管理员登录名
	private String username;
	
	//管理员登录密码
	private String password;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
