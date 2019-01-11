package ${packageName}.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;
import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 *
 * ${moduleName}实体
 */
public class ${classPrefix}Model extends BaseModel{

<#list columnList as column>
    private ${column.fieldType} ${column.fieldName};                <#if column.remarks != "" >//${column.remarks}</#if>
    <#if column.formType?? && (column.formType == "radio" || column.formType == "checkbox" || column.formType == "select") >
    private ${column.fieldType} ${column.fieldName}DictDesc;        <#if column.remarks != "" >//${column.remarks} - 字典显示值</#if>
    </#if>

</#list>

<#list columnList as column>
    public ${column.fieldType} get${column.fieldName?cap_first}() {
        return this.${column.fieldName};
    }

    public void set${column.fieldName?cap_first}(${column.fieldType} ${column.fieldName}) {
        this.${column.fieldName} = ${column.fieldName};
    }

    <#if column.formType?? && (column.formType == "radio" || column.formType == "checkbox" || column.formType == "select") >
    public ${column.fieldType} get${column.fieldName?cap_first}DictDesc() {
        return this.${column.fieldName}DictDesc;
    }

    public void set${column.fieldName?cap_first}DictDesc(${column.fieldType} ${column.fieldName}DictDesc) {
        this.${column.fieldName}DictDesc = ${column.fieldName}DictDesc;
    }
    </#if>

</#list>

}