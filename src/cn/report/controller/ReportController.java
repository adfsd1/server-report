package cn.report.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.report.api.ReportService;
import cn.report.domain.NewReport;
import cn.report.domain.ParamJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
@RequestMapping(value = "/report")

public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private CompController compController;

    // 查询用户所有的报表名字
    @RequestMapping(value = "/catalog")
    @ResponseBody
    public String userCatalog(HttpServletRequest request, HttpServletResponse response1,@RequestParam(value = "id")Long id) throws Exception{
    	//根据用户的id查询出用户所在企业的域名
        String address = compController.getAddress(id);

        String string = "";
        // 请求的url(前面加http：// 不然会报错)
        String url = address+"/cloud-report/report/catalog";
        // 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
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
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        /*List<UserInfo> list = reportService.userCatalog();
        String string = JSONArray.fromObject(list).toString();*/

        return string;
    }
    
    // 用来连接客户端请求接口的方法
    /*
     * @RequestParam(value="interfaceName") String interfaceName,@RequestParam(value="uid")Long uid,
    		@RequestParam(value="databaseName")String databaseName,
    		@RequestParam(value="startDate")String startDate,
    		@RequestParam(value="endDate")String endDate,
    		@RequestParam(value="code")String code,
    		@RequestParam(value="keywords")String keywords,
     */
    @RequestMapping(value = "/connectionClient")
    @ResponseBody
    public Map<String,Object> connectionClient(HttpServletRequest request,HttpServletResponse response1,
    		ParamJson jsonObject){
    	Long uid1 = jsonObject.getUid();
    	HashMap<String, Object> resultMap = new HashMap<String,Object>();
    	//根据用户的id查询出用户所在企业的域名
        String address = compController.getAddress(uid1);
        String string = "";
        // 请求的url(前面加http：// 不然会报错)
        String url = address+"/"+jsonObject.getInterfaceName()+"?databaseName="+jsonObject.getDatabaseName()
        +"&code="+jsonObject.getCode()+"&endDate="+jsonObject.getEndDate()+"&keywords=" +jsonObject.getKeywords();
        System.out.println("地址是："+url);
        // 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
                string = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);

        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
            resultMap.put("code", "999");
        	resultMap.put("msg", "查无数据");
        	resultMap.put("data", null);
        	return resultMap;
        } finally {
            // 6.关闭连接，释放资源（很重要）
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 如果返回null就是没有数据
        if(string==null || string.equals("")) {
        	resultMap.put("code", "999");
        	resultMap.put("msg", "查无数据");
        	resultMap.put("data", null);
        	return resultMap;
        }
        resultMap.put("code", "0");
        resultMap.put("msg", "success");
        
        //JSONArray fromObject = JSONArray.fromObject(string);
	        resultMap.put("data", string);
       /*
        	JSONArray fromObject = JSONArray.fromObject(string);
            ArrayList list = (ArrayList) JSONArray.toCollection(fromObject);
            resultMap.put("data", list);*/
        
            return resultMap;
		
        /*List<UserInfo> list = reportService.userCatalog();
        String string = JSONArray.fromObject(list).toString();*/
        
    }
    
    // 柱状图 报表的表头信息 和 详细信息
    @RequestMapping(value = "/parInfo")
    @ResponseBody
    public String parInfo(HttpServletRequest request, HttpServletResponse response1,@RequestParam(value = "uid") Long uid, @RequestParam(value = "id") Long id,
                          @RequestParam(value = "queryId") int queryId, @RequestParam(value = "startDate") String startDate,
                          @RequestParam(value = "endDate") String endDate, @RequestParam(value = "dates") String dates,
                          @RequestParam(value = "queryCriterica") String queryCriterica) throws Exception {
        System.out.println(uid+" par");
        String address = compController.getAddress(uid);

        String string = "";

        // 请求的url(前面加http：// 不然会报错)
        String url = address+"/cloud-report/report/parInfo?id="+id+"&queryId="+queryId+"&startDate="+startDate+"&endDate="+endDate+"&dates="+dates+"&queryCriterica="+queryCriterica;
        System.out.println(url);
        // 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
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
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





        return string;
    }

    //饼图处理方法
    @RequestMapping(value = "/pieInfo")
    @ResponseBody
    public String pieInfo(HttpServletRequest request, HttpServletResponse response1, @RequestParam(value = "uid") Long uid,@RequestParam(value = "id") Long id,
                          @RequestParam(value = "queryId") int queryId, @RequestParam(value = "startDate") String startDate,
                          @RequestParam(value = "endDate") String endDate, @RequestParam(value = "dates") String dates,
                          @RequestParam(value = "queryCriterica") String queryCriterica) throws Exception {
        System.out.println(uid+" pie");

        String address = compController.getAddress(uid);

        String string = "";

        // 请求的url(前面加http：// 不然会报错)
        String url = address+"/cloud-report/report/pieInfo?id="+id+"&queryId="+queryId+"&startDate="+startDate+"&endDate="+endDate+"&dates="+dates+"&queryCriterica="+queryCriterica;
        System.out.println(url);
        // 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
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
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        return string;
    }
    // 折线图 报表的表头信息 和 详细信息
    @RequestMapping(value = "/lineInfo")
    @ResponseBody
    public String lineInfo(HttpServletRequest request, HttpServletResponse response1,@RequestParam(value = "uid") Long uid, @RequestParam(value = "id") Long id,
                          @RequestParam(value = "queryId") int queryId, @RequestParam(value = "startDate") String startDate,
                          @RequestParam(value = "endDate") String endDate, @RequestParam(value = "dates") String dates,
                          @RequestParam(value = "queryCriterica") String queryCriterica) throws  Exception{

        System.out.println(uid+" line");
        String address = compController.getAddress(uid);

        String string = "";

        // 请求的url(前面加http：// 不然会报错)
        String url = address+"/cloud-report/report/lineInfo?id="+id+"&queryId="+queryId+"&startDate="+startDate+"&endDate="+endDate+"&dates="+dates+"&queryCriterica="+queryCriterica;
        System.out.println(url);
        // 1.创建HttpClient对象 这里使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.创建某种请求方法的实例。这里使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面，这里使用EntityUtils（也可以用inputStream）
            if (entity != null) {
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
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
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return string;
    }
    
    /*
     * 后台管理查询所有报表信息的接口
     */
    @RequestMapping("/getAllReportContent")
    @ResponseBody
    public String getAllReportContent() {
    	List<NewReport> reports = reportService.getAllReportContent();
    	JSONArray list = JSONArray.fromObject(reports);
    	return list.toString();
    }
    
    /*
     * 后台管理删除报表的接口
     */
    @RequestMapping("/deleteReport")
    @ResponseBody
    public String deleteReport(Long reportId) {
    	try {
	    	reportService.deleteReport(reportId);
	    	return "0";
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    /*
     * 后台管理修改报表的接口
     */
    @RequestMapping("/updateReport")
    @ResponseBody
    public String updateReport(@RequestParam(value="reportId")Long reportId,
    		@RequestParam(value="reportName")String reportName,
    		@RequestParam(value="reportIcon")String reportIcon,
    		@RequestParam(value="uid")String uid,
    		@RequestParam(value="websrc")String websrc,
    		@RequestParam(value="javasrc")String javasrc) {
    	try {
	    	NewReport nr = new NewReport(reportId, reportName, reportIcon, uid, websrc, javasrc);
	    	reportService.updateReport(nr);
	    	return "0";
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    /*
     * 后台管理新增报表的接口
     */
    @RequestMapping("/insertReport")
    @ResponseBody
    public String insertReport(String reportName,String reportIcon,String uid,String websrc,String javasrc) {
    	try {
	    	reportService.insertReport(reportName, reportIcon, uid, websrc, javasrc);
	    	return "0";
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    // 读取{}中内容的方法
    //条件替换
    //日期转化
    public static String formatDate(String ss) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(ss);
        String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);

        return now;
    }


}
