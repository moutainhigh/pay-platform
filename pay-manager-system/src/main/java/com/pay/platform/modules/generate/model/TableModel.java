package com.pay.platform.modules.generate.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.util.List;

/**
 * User:
 * DateTime: 2017/1/2 10:37
 *
 * 表信息
 */
public class TableModel extends BaseModel {

    private String tableName;                       //表名称
    private String tableType;                       //表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
    private String remarks;                         //表备注

    private List<ColumnModel> columnList;          //包含的列信息

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnModel> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnModel> columnList) {
        this.columnList = columnList;
    }

}