package com.pay.platform.modules.generate.model;
import com.pay.platform.modules.base.model.BaseModel;

/**
 * User: zjt
 * DateTime: 2017/1/2 10:37
 *
 * 字段信息
 */
public class ColumnModel extends BaseModel {

    /**
     * 通过数据库元信息读取
     */
    private String columnName;               //数据库字段名称 (下划线命名 )
    private String columnType;               //数据库字段类型

    private String fieldName;               //属性名称 (驼峰命名)
    private String fieldType;               //属性类型

    private String remarks;                 //描述信息
    private String size;                    //大小,列的大小。对于 char 或 date 类型，列的大小是最大字符数，对于 numeric 和 decimal 类型，列的大小就是精度

    /**
     * 自动生成时,前端传递参数,
     * 注意: freemarker对于变量不可以is开头...
     * @return
     */
    private boolean _isShowList;          //是否列表显示
    private boolean _isQueryField;        //是否查询字段
    private boolean _isShowDetail;        //是否显示详情
    private String matchPattern;          //查询匹配方式：=、!=、like、>、>=、<、<=
    private boolean _isAdd;               //是否新增
    private boolean _isEdit;              //是否编辑
    private boolean _isRequired;          //是否必填项
    private String formType;             //表单类型
    private String dictType;             //字典类型,用于radio/checkbox/select

    private boolean _isParentId;                //是否父节点id
    private boolean _isTreeShowField;           //是否树的显示字段

    public boolean is_isParentId() {
        return _isParentId;
    }

    public void set_isParentId(boolean _isParentId) {
        this._isParentId = _isParentId;
    }

    public boolean is_isTreeShowField() {
        return _isTreeShowField;
    }

    public void set_isTreeShowField(boolean _isTreeShowField) {
        this._isTreeShowField = _isTreeShowField;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public boolean is_isShowList() {
        return _isShowList;
    }

    public void set_isShowList(boolean _isShowList) {
        this._isShowList = _isShowList;
    }

    public boolean is_isQueryField() {
        return _isQueryField;
    }

    public void set_isQueryField(boolean _isQueryField) {
        this._isQueryField = _isQueryField;
    }

    public boolean is_isAdd() {
        return _isAdd;
    }

    public void set_isAdd(boolean _isAdd) {
        this._isAdd = _isAdd;
    }

    public boolean is_isEdit() {
        return _isEdit;
    }

    public void set_isEdit(boolean _isEdit) {
        this._isEdit = _isEdit;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean is_isRequired() {
        return _isRequired;
    }

    public boolean is_isShowDetail() {
        return _isShowDetail;
    }

    public void set_isShowDetail(boolean _isShowDetail) {
        this._isShowDetail = _isShowDetail;
    }

    public void set_isRequired(boolean _isRequired) {
        this._isRequired = _isRequired;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMatchPattern() {
        return matchPattern;
    }

    public void setMatchPattern(String matchPattern) {
        this.matchPattern = matchPattern;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

}