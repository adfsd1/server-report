package cn.report.domain;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * 图表配置信息的实体
     */
    private static final long serialVersionUID = 1L;

    //图表id,柱状图和表格用的是一个id,一个实体
    private Long id;

    // 表格的名字
    private String name;

    // 表格的表头
    private String tableColumn;

    // 表格的SQL语句
    private String reportSql;
    //图形的SQL
    private String chartSql;

    public String getChartSql() {
        return chartSql;
    }

    public void setChartSql(String chartSql) {
        this.chartSql = chartSql;
    }

    //高级查询分类
    private int queryId;
    //图形分类
    private int chartId;

    public int getChartId() {
        return chartId;
    }

    public void setChartId(int chartId) {
        this.chartId = chartId;
    }

    //柱状图系列名数据库字段
    private String series;

    //柱状图系列名汉字
    private String seriesName;

    //柱状图分类名
    private String className;

    //饼图名
    private String pieName;
    //饼图值
    private String pieValue;
    //折线图系列名
    private String lineSeriesName;
    //折线图系列值
    private String lineSeriesValue;
    //折线图分类名

    public String getLineSeriesName() {
        return lineSeriesName;
    }

    public void setLineSeriesName(String lineSeriesName) {
        this.lineSeriesName = lineSeriesName;
    }

    public String getLineSeriesValue() {
        return lineSeriesValue;
    }

    public void setLineSeriesValue(String lineSeriesValue) {
        this.lineSeriesValue = lineSeriesValue;
    }

    public String getLineClassName() {
        return lineClassName;
    }

    public void setLineClassName(String lineClassName) {
        this.lineClassName = lineClassName;
    }

    private String lineClassName;

    public String getPieName() {
        return pieName;
    }

    public void setPieName(String pieName) {
        this.pieName = pieName;
    }

    public String getPieValue() {
        return pieValue;
    }

    public void setPieValue(String pieValue) {
        this.pieValue = pieValue;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getReportSql() {
        return reportSql;
    }

    public void setReportSql(String reportSql) {
        this.reportSql = reportSql;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableColumn() {
        return tableColumn;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

}
