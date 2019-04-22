package cn.report.api;

import cn.report.domain.UserInfo;

import java.util.List;
/**
 * 用户的配置信息
 * @author pang
 *
 */
public interface UserInfoService {

	//查询用户的所有配置信息
	public List<UserInfo> questUserInfo();

	//添加图表配置信息
	public void insertUserInfo(UserInfo userInfo);

	//删除图表
    public void deleteUserInfo(Long userInfoId);
}
