package cn.report.api;

import java.util.List;
import java.util.Map;

import cn.report.domain.NewReport;
import cn.report.domain.UserInfo;



public interface ReportService {
	
	//报表分类名
	public List<Object> reportClassName(String className);
	
	//报表信息
	public List<Map<String, Object>>  reportInfo(String sql);

	//用户目录信息
	public List<UserInfo> userCatalog();
	
	//后台管理查询所有报表信息
	public List<NewReport> getAllReportContent();

	//删除报表根据报表ID
	public void deleteReport(Long reportId);
	
	//修改报表根据报表ID
	public void updateReport(NewReport nr);
	
	//新增一个报表
	public void insertReport(String reportName,String reportIcon,String uid,String websrc,String javasrc);
	
	//根据用户ID获取与其相关的报表信息
	public List<NewReport> getReportByUid(String uid);
}
