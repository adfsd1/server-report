package cn.report.service;

import cn.report.api.SysUserService;
import cn.report.domain.SysUser;
import cn.report.mapper.SysUserDao;
import cn.report.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员登录
 * @author pang
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	// 登录
	@Override
	public SysUser loginUser(SysUser sysUser) {
		
		String password = MD5Utils.md5(sysUser.getPassword());
		sysUser.setPassword(password);
		sysUser = sysUserDao.loginUser(sysUser);
		return sysUser;
	}


}
