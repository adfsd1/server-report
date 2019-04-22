package cn.report.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.report.api.CompService;
import cn.report.api.ReportService;
import cn.report.api.UserService;
import cn.report.domain.Account;
import cn.report.domain.NewReport;
import cn.report.domain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户controller
 */
@Controller
@RequestMapping(value = "/user")

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompService compService;
    @Autowired
    private ReportService reportService;
    // 微信小程序用户登录
    @RequestMapping(value = "/login")
    @ResponseBody
    public String loginUser(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                            @RequestParam(value = "code",required=false) String code) {
    	String appid = "";
    	String secret = "";
    	String flag = "";
    	HashMap<String,Object> result = new HashMap<String,Object>(); // 装有返回的数据
        JSONObject resultJson = null;
        if(code==null || code.equals("")) {
        	result.put("code", 3);
        	result.put("msg", "code为空");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
        }
    	try {
    		appid = readConfigurationByProperties("/resource/wxconfig.properties","appid");
    		secret = readConfigurationByProperties("/resource/wxconfig.properties","secret");
    		flag = readConfigurationByProperties("/resource/wxconfig.properties","flag");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	User user = userService.loginUser(username, password);
    	
    	String string1 = "";// 接收的数据
    	String openid = null;
    	// 请求的url(前面加http：// 不然会报错)
    	String url1 = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code=" + code + "&grant_type=authorization_code";
    	// 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client1 = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet1 = new HttpGet(url1);
        InputStream inputStream1 = null;
        CloseableHttpResponse response2 = null;
        try {
            // 3.执行请求，获取响应
            response2 = client1.execute(httpGet1);
            // 看请求是否成功，这儿打印的是http状态码
             System.out.println("状态码:"+response2.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity1 = response2.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity1 != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
            	//http://16683h67p0.imwork.net:29188
                string1 = EntityUtils.toString(entity1, "utf-8");
                System.out.println(string1);
            }
            EntityUtils.consume(entity1);

        }catch(HttpHostConnectException e) {
        	result.put("code", 2);
        	result.put("msg", "openid不匹配");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
        }catch (UnsupportedOperationException | IOException e) {
        	result.put("code", 2);
        	result.put("msg", "openid不匹配");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
        }catch(Exception e) {
        	result.put("code", 2);
        	result.put("msg", "openid不匹配");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
        }
        finally {
            // 6.关闭连接，释放资源（很重要）
            if (inputStream1 != null) {
                try {
                    inputStream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response2 != null) {
                try {
                    response2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    	
    	JSONObject openidObject = JSONObject.fromObject(string1);
    	try {
    		openid = openidObject.getString("openid");
    	}catch(Exception e) {
    		result.put("code", 4);
        	result.put("msg", "openid出错,过期或者该code不能用");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
    	}
        String string = ""; // 接收的数据
        
        
        if (user != null) {
        	if(user.getOpenid()==null || user.getOpenid().equals("")) {
        		userService.updateUserByOpenid(user.getId().toString(), openid);
        	}else if(flag.equals("true")) {
        		if(!user.getOpenid().equals(openid)) {
	        		result.put("code", 2);
	            	result.put("msg", "openid不匹配");
	            	result.put("data", null);
	            	resultJson = JSONObject.fromObject(result);
	            	return resultJson.toString();
        		}
        	}
        	Map<String, String> typeAndAddress = compService.getAllAccount(user.getId());
        	// 请求的url(前面加http：// 不然会报错)
            String url = typeAndAddress.get("address")+"/cloud-report/report/account?type="+typeAndAddress.get("type");
            System.out.println("url:"+url);
            // 1.创建HttpClient对象 这里使用默认的配置的httpclient
            CloseableHttpClient client = HttpClients.createDefault();
            // 2.创建某种请求方法的实例。这里使用get方法
            HttpGet httpGet = new HttpGet(url);
            InputStream inputStream = null;
            CloseableHttpResponse response1 = null;
            try {
                // 3.执行请求，获取响应
                response1 = client.execute(httpGet);
                // 看请求是否成功，这儿打印的是http状态码
                System.out.println(response1.getStatusLine().getStatusCode());
                // 4.获取响应的实体内容，就是我们所要抓取得网页内容
                HttpEntity entity = response1.getEntity();
                // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
                if (entity != null) {
                    //System.out.println(EntityUtils.toString(entity, "utf-8"));
                	//http://16683h67p0.imwork.net:29188
                    string = EntityUtils.toString(entity, "utf-8");
                }
                EntityUtils.consume(entity);

            } catch (UnsupportedOperationException | IOException e) {
                e.printStackTrace();
            } finally {
                // 6.关闭连接，释放资源（很重要）
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (response1 != null) {
                    try {
                        response1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("返回的内容:"+string);
            JSONArray jsonArray = null;
            try {
	            jsonArray = JSONArray.fromObject(string);
	            ArrayList<Account> list2 = (ArrayList) JSONArray.toCollection(jsonArray);
            }catch(Exception e) {
            	e.printStackTrace();
            }
            // 根据用户ID获取所有报表信息
            List<NewReport> nr = reportService.getReportByUid(user.getId().toString());
            JSONArray nrs = JSONArray.fromObject(nr);
            HashMap<String,Object> resultson = new HashMap<String,Object>();
            resultson.put("uid", user.getId().toString());
            resultson.put("accounts", jsonArray);
            resultson.put("reports", nrs);
        	// 设置状态码,附带信息和数据
        	result.put("code", 0);
        	result.put("msg", "success");
        	result.put("data", resultson);
        	resultJson = JSONObject.fromObject(result);
        } else {
        	result.put("code", 1);
        	result.put("msg", "账号或密码错误");
        	result.put("data", "");
        	resultJson = JSONObject.fromObject(result);
        }
        return resultJson.toString();
    }
    
    /*
     * 登陆前校验openid的接口
     * 若查到相关用户，则返回用户信息
     * 否则，返回无数据信息
     */
    @RequestMapping(value = "/loginBefore")
    @ResponseBody
    public String loginUserBefore(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="code",required=false)String code) throws Exception {
    	HashMap<String, Object> result = new HashMap<String,Object>();
    	JSONObject resultJson = null;
    	if(code==null || code.equals("")) {
    		result.put("code", 3);
        	result.put("msg", "code为空");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
    	}
    	String string1 = "";// 接收的数据
    	String openid = null;
    	String appid = "";
    	String secret = "";
    	try {
    		appid = readConfigurationByProperties("/resource/wxconfig.properties","appid");
    		secret = readConfigurationByProperties("/resource/wxconfig.properties","secret");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	// 请求的url(前面加http：// 不然会报错)
    	String url1 = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code=" + code + "&grant_type=authorization_code";
        // 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client1 = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet1 = new HttpGet(url1);
        InputStream inputStream1 = null;
        CloseableHttpResponse response2 = null;
        try {
            // 3.执行请求，获取响应
            response2 = client1.execute(httpGet1);
            // 看请求是否成功，这儿打印的是http状态码
            // System.out.println(response2.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity1 = response2.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity1 != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
            	//http://16683h67p0.imwork.net:29188
                string1 = EntityUtils.toString(entity1, "utf-8");
                
            }
            EntityUtils.consume(entity1);

        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        } finally {
            // 6.关闭连接，释放资源（很重要）
            if (inputStream1 != null) {
                try {
                    inputStream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response2 != null) {
                try {
                    response2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    	try {
    		JSONObject openidObject = JSONObject.fromObject(string1);
    		openid = (String) openidObject.get("openid");
	    	User user = userService.findUserByOpenid(openid);
	    	if(user != null) {
	    		String string = "";// 接收的数据
	    		Map<String, String> typeAndAddress = compService.getAllAccount(user.getId());
	        	// 请求的url(前面加http：// 不然会报错)
	            String url = typeAndAddress.get("address")+"/cloud-report/report/account?type="+typeAndAddress.get("type");
	            // 1.创建HttpClient对象 这里使用默认的配置的httpclient
	            CloseableHttpClient client = HttpClients.createDefault();
	            // 2.创建某种请求方法的实例。这里使用get方法
	            HttpGet httpGet = new HttpGet(url);
	            InputStream inputStream = null;
	            CloseableHttpResponse response1 = null;
	            try {
	                // 3.执行请求，获取响应
	                response1 = client.execute(httpGet);
	                // 看请求是否成功，这儿打印的是http状态码
	                System.out.println(response1.getStatusLine().getStatusCode());
	                // 4.获取响应的实体内容，就是我们所要抓取得网页内容
	                HttpEntity entity = response1.getEntity();
	                // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
	                if (entity != null) {
	                    //System.out.println(EntityUtils.toString(entity, "utf-8"));
	                	//http://16683h67p0.imwork.net:29188
	                    string = EntityUtils.toString(entity, "utf-8");
	                }
	                EntityUtils.consume(entity);

	            } catch (UnsupportedOperationException | IOException e) {
	                e.printStackTrace();
	            } finally {
	                // 6.关闭连接，释放资源（很重要）
	                if (inputStream != null) {
	                    try {
	                        inputStream.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (response1 != null) {
	                    try {
	                        response1.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	            JSONArray jsonArray = null;
	            try {
		            jsonArray = JSONArray.fromObject(string);
		            ArrayList<Account> list2 = (ArrayList) JSONArray.toCollection(jsonArray);
	            }catch(Exception e) {
	            	e.printStackTrace();
	            }
	            // 根据用户ID获取所有报表信息
	            List<NewReport> nr = reportService.getReportByUid(user.getId().toString());
	            JSONArray nrs = JSONArray.fromObject(nr);
	            
	            HashMap<String,Object> resultson = new HashMap<String,Object>();
	            resultson.put("uid", user.getId().toString());
	            resultson.put("accounts", jsonArray);
	            resultson.put("reports", nrs);
	        	// 设置状态码,附带信息和数据
	        	result.put("code", 0);
	        	result.put("msg", "success");
	        	result.put("data", resultson);
	        	resultJson = JSONObject.fromObject(result);
	    	}else {
	    		result.put("code", "999");
	    		result.put("msg", "查无数据");
	    		result.put("data", null);
	    		resultJson = JSONObject.fromObject(result);
	    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    		result.put("code", 2);
        	result.put("msg", "openid不匹配");
        	result.put("data", null);
        	resultJson = JSONObject.fromObject(result);
        	return resultJson.toString();
    	}
    	return resultJson.toString();
    }

    // 后台管理系统查询所有的用户
    @RequestMapping(value = "/user")
    @ResponseBody
    public String selectUserInfo(HttpServletRequest request, HttpServletResponse response) {

        List<User> list = userService.selectUser();

        String string = JSONArray.fromObject(list).toString();

        return string;
    }

    // 根据id查询用户
    public User getUser(Long id) {
        User user = userService.getUser(id);
        return user;
    }

    // 增加用户
    @RequestMapping(value = "/insertUser")
    public void insertUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "cid") long cid) {
        System.out.println(username);


        userService.insertUser(username, password, cid);


    }

    //根据id删除用户
    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userId") Long userId) throws UnsupportedEncodingException {

        userService.deleteUser(userId);


        return "0";
    }

    //校验密码
    @RequestMapping(value = "/checkPW")
    @ResponseBody
    public int checkPW(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "uid") Long uid, @RequestParam(value = "checkPW") String checkPW,
                       @RequestParam(value = "newPW") String newPW) {

        System.out.println(uid);
        System.out.println(checkPW);

        int i = userService.checkPW(uid, checkPW);
        if (i == 0) {
            return 0;
        } else {
            userService.changePW(uid, newPW);
            return 6;
        }
    }

    //微信小程序个人信息页面根据ID查询个人信息
    @RequestMapping(value = "/byIdUser")
    @ResponseBody
    public User byIdUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "uid") Long uid) {
        User user = userService.byIdUser(uid);
        return user;


    }

    //后台为用户重置密码
    @RequestMapping(value = "/resetPW")
    @ResponseBody
    public Map<String, String> resetPW(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userId") Long userId) {
        HashMap<String,String> map  = new HashMap<>();
        if (userId != 0 && userId != null) {
            userService.resetPW(userId);
            map.put("uu","重置成功");

        } else {
            map.put("uu","用户不存在");
        }
        return map;




    }
    /*
     * 提取配置文件的工具方法
     */
    public static String readConfigurationByProperties(String filePath,String key) throws Exception{

        Properties properties = new Properties();
        //可以用两种不同的流来加载配置文件
        //properties.load(new BufferedReader(new FileReader(filePath)));
        properties.load(UserController.class.getResourceAsStream(filePath));

        //也可以指定键名来获取值
        //String name = properties.getProperty("name");

        Set<Entry<Object,Object>> entrySet = properties.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            if(entry.getKey().equals(key)) {
            	return (String) entry.getValue();
            }
        }
        return null;
    }
	
}
