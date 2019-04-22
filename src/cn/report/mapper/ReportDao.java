package cn.report.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.report.domain.NewReport;
import cn.report.domain.UserInfo;



public interface ReportDao {

	//报表信息
	
	public List<Map<String, Object>> reportInfo(String sql);

	//用户目录信息
	public List<UserInfo> userCatalog();

	//报表分类信息
	public List<Object> reportClassName(String className);
	
	//获取所有报表信息
	public List<NewReport> getAllReportContent();
	
	//删除报表根据报表ID
	public void deleteReport(Long reportId);
	
	//更新报表根据报表ID
	public void updateReport(@Param("reportId")Long reportId,
			@Param("reportName")String reportName,
			@Param("reportIcon")String reportIcon,
			@Param("uid")String uid,
			@Param("websrc")String websrc,
			@Param("javasrc")String javasrc);
	
	//新增一个报表
	public void insertReport(@Param("reportName")String reportName,
			@Param("reportIcon")String reportIcon,
			@Param("uid")String uid,
			@Param("websrc")String websrc,
			@Param("javasrc")String javasrc);
	
	//根据用户ID获取与其相关的所有报表信息
	public List<NewReport> getReportByUid(@Param("uid")String uid);
}
