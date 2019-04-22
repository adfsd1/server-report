package cn.report.mapper;

import cn.report.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 用户Dao
 * @author pang
 *
 */
public interface UserDao {

	// 查询用户信息
	public List<User> selectUser();
	//微信小程序用户登录
	public User loginUser(@Param("username") String username, @Param("password") String password);
	public User getUser(Long id);
	//增加用户
	public void insertUser(@Param("username") String username, @Param("md5") String md5, @Param("cid") Long cid);
	//删除用户
	public void deleteUser(Long userId);


	public int checkPW(@Param("uid") Long uid, @Param("md5") String md5);

	public void changePW(@Param("uid") Long uid,@Param("newPW") String newPW);

    public User byIdUser(Long uid);

    public void resetPW(@Param(value = "userId") Long userId,@Param(value = "md5") String md5);
    // 查询用户信息根据openid
    public List<User> findUserByOpenid(String openid);
    // 更改用户openid根据uid
    public void updateUserByOpenid(@Param(value = "uid")String uid,@Param(value = "openid")String openid);
}
