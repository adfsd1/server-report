package cn.report.api;

import cn.report.domain.User;

import java.util.List;

/**
 * 用户
 *
 * @author pang
 */
public interface UserService {
    //查询所有用户
    public List<User> selectUser();
    //根据openid查询用户
    public User findUserByOpenid(String openid);

    //微信小程序用户登录
    public User loginUser(String username, String password);

    public User getUser(Long id);

    //增加用户
    public void insertUser(String username, String password, Long cid);

    public void deleteUser(Long userId);

    public int checkPW(Long uid, String checkPW);

    public void changePW(Long uid, String newPW);

    public User byIdUser(Long uid);

    public void resetPW(Long userId);
    
    //更新用户openid根据uid
    public void updateUserByOpenid(String uid,String openid);
}
