package cn.report.controller;

import cn.report.api.UserInfoService;
import cn.report.domain.UserInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户图表配置信息controller
 *
 * @author pang
 */

@Controller
@RequestMapping(value = "/userInfo")

public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    // 后台管理系统查询用户的所有配置信息
    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public String questUserInfo(HttpServletRequest request, HttpServletResponse response) {

        List<UserInfo> list = userInfoService.questUserInfo();

        String userInfo = JSONArray.fromObject(list).toString();

        return userInfo;

    }

    // 添加图表
    @RequestMapping(value = "/insertUserInfo")
    @ResponseBody
    public String insertUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "report_name") String reportName,
                                 @RequestParam(value = "catalog_name") String catalogName, @RequestParam(value = "query") Integer query,
                                 @RequestParam(value = "report_sql") String reportSql,
                                 @RequestParam(value = "chart_sql") String chartSql,
                                 @RequestParam(value = "series_name") String seriesName,
                                 @RequestParam(value = "series_value") String seriesValue,
                                 @RequestParam(value = "class_name") String className,
                                 @RequestParam(value = "pie_name") String pieName,
                                 @RequestParam(value = "pie_value") String pieValue,
                                 @RequestParam(value = "chartId") Integer chartId,
                                 @RequestParam(value = "lineClassName")String lineClassName,
                                 @RequestParam(value = "lineSeriesName")String lineSeriesName,
                                 @RequestParam(value = "lineSeriesValue")String lineSeriesValue) {

        UserInfo userInfo = new UserInfo();
        userInfo.setName(reportName);
        userInfo.setTableColumn(catalogName);
        userInfo.setQueryId(query);
        userInfo.setReportSql(reportSql);
        userInfo.setChartSql(chartSql);
        userInfo.setChartId(chartId);
        /*柱状图*/
        userInfo.setSeriesName(seriesName);
        userInfo.setSeries(seriesValue);
        userInfo.setClassName(className);
        /*饼状图*/
        userInfo.setPieName(pieName);
        userInfo.setPieValue(pieValue);
        /*折线图*/
        userInfo.setLineClassName(lineClassName);
        userInfo.setLineSeriesName(lineSeriesName);
        userInfo.setLineSeriesValue(lineSeriesValue);

        System.out.println(userInfo.toString());
        userInfoService.insertUserInfo(userInfo);

        return null;
    }

    //删除图表
    @RequestMapping(value = "/deleteUserInfo")
    @ResponseBody
    public String deleteUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userInfoId") Long userInfoId) {

        userInfoService.deleteUserInfo(userInfoId);
        System.out.println(userInfoId);
        return "2";

    }


}
