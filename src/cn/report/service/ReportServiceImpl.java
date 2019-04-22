package cn.report.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.report.api.ReportService;
import cn.report.domain.NewReport;
import cn.report.domain.UserInfo;
import cn.report.mapper.ReportDao;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired 
	private ReportDao reportDao;

	//报表信息
	@Override
	public List<Map<String, Object>>  reportInfo(String sql) {
		System.out.println(sql);
		List<Map<String, Object>>  list = reportDao.reportInfo(sql);
		return list;
	}

	//用户目录信息
	@Override
	public List<UserInfo> userCatalog() {
		List<UserInfo> list = reportDao.userCatalog();
		return list;
	}

	//报表分类
	@Override
	public List<Object> reportClassName(String className) {
		List<Object> list = reportDao.reportClassName(className);
		return list;
	}
	
	//后台查询所有报表信息
	@Override
	public List<NewReport> getAllReportContent() {
		List<NewReport> reports = reportDao.getAllReportContent();
		return reports;
	}

	@Override
	public void deleteReport(Long reportId) {
		reportDao.deleteReport(reportId);
	}

	@Override
	public void updateReport(NewReport nr) {
		reportDao.updateReport(nr.getReportId(),nr.getReportName(),nr.getReportIcon(),nr.getUid(),nr.getWebsrc(),nr.getJavasrc());
	}

	@Override
	public void insertReport(String reportName, String reportIcon, String uid, String websrc, String javasrc) {
		reportDao.insertReport(reportName, reportIcon, uid, websrc, javasrc);
	}

	@Override
	public List<NewReport> getReportByUid(String uid) {
		List<NewReport> nr = reportDao.getReportByUid(uid);
		return nr;
	}

}
