package cn.report.domain;

import java.io.Serializable;

public class Report implements Serializable {
    /**
     * xml文件 对应实体
     */
    private static final long serialVersionUID = 1L;

    // 编号
    private String id;
    // 报表的名字
    private String name;
    // 报表的SQL
    private String sql;
    // 报表的列
    private String column;
    //柱状图
    //柱状图系列名
    private String series;
    private String seriesName;


    //柱状图分类名
    private String className;
    //柱状图SQL
    private String parSql;
    //查询页面渲染数据
    private String query;
    private String controlValue;
    private String controlname;


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public String getControlname() {
        return controlname;
    }

    public void setControlname(String controlname) {
        this.controlname = controlname;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }


    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParSql() {
        return parSql;
    }

    public void setParSql(String parSql) {
        this.parSql = parSql;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
