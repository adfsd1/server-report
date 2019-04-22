package cn.report.service;

import cn.report.api.UserInfoService;
import cn.report.domain.UserInfo;
import cn.report.mapper.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 用户配置信息serviceimpl
 * @author pang
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoDao userInfoDao;

	//查询用户的所有的配置信息
	@Override
	public List<UserInfo> questUserInfo() {
		List<UserInfo> list = userInfoDao.questUserInfo();
		return list;
	}

	//添加图表
	@Override
	public void insertUserInfo(UserInfo userInfo) {
		userInfoDao.insertInfoUser(userInfo);
		
	}

	//删除图表
    @Override
    public void deleteUserInfo(Long userInfoId) {
		userInfoDao.deleteUserInfo(userInfoId);

    }

}
