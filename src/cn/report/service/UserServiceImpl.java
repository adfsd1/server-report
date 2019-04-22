package cn.report.service;

import cn.report.api.UserService;
import cn.report.domain.User;
import cn.report.mapper.UserDao;
import cn.report.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 用户service实现类
 * @author pang
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	
	//查询所有的用户
	@Override
	public List<User> selectUser() {
		List<User> list = userDao.selectUser();
		return list;
	}

	//微信小程序用户登录
	@Override
	public User loginUser(String username, String password) {
		String md5 = MD5Utils.md5(password);
		System.out.println(md5);
		User user = userDao.loginUser(username,md5);
		return user;
	}

	@Override
	public User getUser(Long id) {
		User user = userDao.getUser(id);
		return user;
	}

	//增加用户
	@Override
	public void insertUser(String username, String password,Long cid) {
		String md5 = MD5Utils.md5(password);
		userDao.insertUser(username,md5,cid);
		
	}

	//删除用户u
	@Override
	public void deleteUser(Long userId) {
		userDao.deleteUser(userId);
		
	}

    @Override
    public int checkPW(Long uid, String checkPW) {
		String md5 = MD5Utils.md5(checkPW);
		int i = userDao.checkPW(uid,md5);
        return i;
    }

	@Override
	public void changePW(Long uid, String newPW) {
		String md5 = MD5Utils.md5(newPW);
		userDao.changePW(uid,md5);
	}

    @Override
    public User byIdUser(Long uid) {
	    User user = userDao.byIdUser(uid);
        return user;
    }

	@Override
	public void resetPW(Long userId) {
		String md5 = MD5Utils.md5("123");
		userDao.resetPW(userId,md5);
	}

	@Override
	public User findUserByOpenid(String openid) {
		List<User> users = userDao.findUserByOpenid(openid);
		User result = null;
		for(User user:users) {
			if(user == null) {

			}else {
				result = user;
			}
		}
		return result;
	}

	@Override
	public void updateUserByOpenid(String uid, String openid) {
		userDao.updateUserByOpenid(uid, openid);
	}
}
