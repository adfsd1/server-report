package cn.report.domain;

public class NewReport {
	private Long reportId;
	private String reportName;
	private String reportIcon;
	private String uid;
	private String websrc;
	private String javasrc;
	
	public NewReport() {
		super();
	}
	public NewReport(Long reportId, String reportName, String reportIcon, String uid, String websrc, String javasrc) {
		super();
		this.reportId = reportId;
		this.reportName = reportName;
		this.reportIcon = reportIcon;
		this.uid = uid;
		this.websrc = websrc;
		this.javasrc = javasrc;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportIcon() {
		return reportIcon;
	}
	public void setReportIcon(String reportIcon) {
		this.reportIcon = reportIcon;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getWebsrc() {
		return websrc;
	}
	public void setWebsrc(String websrc) {
		this.websrc = websrc;
	}
	public String getJavasrc() {
		return javasrc;
	}
	public void setJavasrc(String javasrc) {
		this.javasrc = javasrc;
	}
	
	
}
