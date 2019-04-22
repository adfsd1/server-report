package cn.report.mapper;

import cn.report.domain.UserInfo;

import java.util.List;
/**
 * 用户配置信息dao
 * @author pang
 *
 */
public interface UserInfoDao {

	//查询用户配置信息
	public List<UserInfo> questUserInfo();

	//增加图表配置
	public void insertInfoUser(UserInfo userInfo);

	//删除图表
    public void deleteUserInfo(Long userInfoId);
}
