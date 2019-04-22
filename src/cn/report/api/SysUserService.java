package cn.report.api;

import cn.report.domain.SysUser;
/**
 * 管理员登录
 * @author pang
 *
 */
public interface SysUserService {

	//登录
	public SysUser loginUser(SysUser sysUser);

}
