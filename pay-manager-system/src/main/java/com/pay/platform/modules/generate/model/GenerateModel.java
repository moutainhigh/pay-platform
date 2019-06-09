package com.pay.platform.modules.generate.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.util.List;

/**
 * User:
 * DateTime: 2017/1/8 20:44
 *
 * 生成代码所需数据 - 实体
 */
public class GenerateModel extends BaseModel {

    private String tableName;           //表名
    private String classPrefix;         //类前缀
    private String propertyPrefix;      //属性前缀(首字母小写)
    private String rootPath;            //项目根路径
    private String packageName;         //包名称
    private String moduleName;          //模块名称
    private String requestUrl;          //请求路径

    private boolean containQueryField;              //是否包含查询字段
    private boolean containDel;                   //是否包含逻辑删除字段
    private boolean containRichTextField;         //是否包含富文本字段

    public boolean isContainRichTextField() {
        return containRichTextField;
    }

    public void setContainRichTextField(boolean containRichTextField) {
        this.containRichTextField = containRichTextField;
    }

    private List<ColumnModel> columnList;          //字段信息

    public List<ColumnModel> getColumnList() {
        return columnList;
    }

    public boolean isContainDel() {
        return containDel;
    }

    public void setContainDel(boolean containDel) {
        this.containDel = containDel;
    }

    public void setColumnList(List<ColumnModel> columnList) {
        this.columnList = columnList;
    }

    public boolean isContainQueryField() {
        return containQueryField;
    }

    public void setContainQueryField(boolean containQueryField) {
        this.containQueryField = containQueryField;
    }

    public String getPropertyPrefix() {
        return propertyPrefix;
    }

    public void setPropertyPrefix(String propertyPrefix) {
        this.propertyPrefix = propertyPrefix;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassPrefix() {
        return classPrefix;
    }

    public void setClassPrefix(String classPrefix) {
        this.classPrefix = classPrefix;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

}