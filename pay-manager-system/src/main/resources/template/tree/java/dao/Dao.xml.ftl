<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 指定唯一的命名空间 , 如果采用绑定接口方式, 则必须为接口全路径 -->
<mapper namespace="${packageName}.dao.${classPrefix}Dao" >

    <select id="query${classPrefix}List" resultType="${classPrefix}Model" >
        select *
        <#list columnList as column>
            <#if column.formType?? && (column.formType == "radio" || column.formType == "checkbox" || column.formType == "select") >
                , ( select Group_concat(item_desc) from tb_dictionary where DICT_TYPE = '${column.dictType}' and FIND_IN_SET(ITEM_CODE , ${column.columnName}))  as ${column.fieldName}DictDesc
            </#if>
        </#list>
        from ${tableName}
        <#if containDel >
            <where>
                and is_del = 0
                <#if containQueryField >
                    <#list columnList as column>
                        <#if column._isQueryField>
                            <if test="${column.fieldName} != null and ${column.fieldName} != '' " >
                                <#if column.matchPattern == "like" >
                                    and ${column.columnName} ${column.matchPattern} <@mapperLike column.fieldName />
                                <#else>
                                    and ${column.columnName} ${column.matchPattern} <@mapperEl column.fieldName />
                                </#if>
                            </if>
                        </#if>
                    </#list>
                </#if>
            </where>
        </#if>
        order by level,seq asc
    </select>

    <select id="query${classPrefix}ById" parameterType="java.lang.String" resultType="${classPrefix}Model" >
        select *
        <#list columnList as column>
            <#if column.formType?? && (column.formType == "radio" || column.formType == "checkbox" || column.formType == "select") >
                , ( select Group_concat(item_desc) from tb_dictionary where DICT_TYPE = '${column.dictType}' and FIND_IN_SET(ITEM_CODE , ${column.columnName}))  as ${column.fieldName}DictDesc
            </#if>
        </#list>
        from ${tableName} where id = <@mapperEl "id" />
    </select>

    <insert id="add${classPrefix}"  parameterType="${classPrefix}Model" >
        <#assign isInsertNeedComma=false>
        insert into ${tableName}(<#list columnList as column><#if column._isAdd || column.fieldName == "id" || column.fieldName == "createTime" || column.fieldName == "level" || column.fieldName == "seq" || column.fieldName == "parentId"><#if isInsertNeedComma> , </#if>${column.columnName}<#assign isInsertNeedComma=true></#if></#list>)
        <#assign isValueNeedComma=false>
        values(<#list columnList as column><#if column.fieldName == "id" >uuid() , <#elseif column.fieldName == "createTime" > , now()<#elseif column._isAdd || column.fieldName == "level" || column.fieldName == "seq" || column.fieldName == "parentId" ><#if isValueNeedComma> , </#if><#assign isValueNeedComma=true><@mapperEl column.fieldName /></#if></#list>);
    </insert>

    <select id="queryNextSeq" resultType="java.lang.Integer" >
        select IFNULL(max(seq) , 0) + 1 from ${tableName}
        <where>
            and LEVEL = <@mapperEl "level" />
            <if test="parentId != null and parentId != '' ">
                and PARENT_ID = <@mapperEl "parentId" />
            </if>
        </where>
    </select>

    <delete id="delete${classPrefix}" >
        delete from ${tableName} where id in
        <foreach collection="array" item="id" open="(" separator="," close=")">
            <@mapperEl "id" />
        </foreach>
    </delete>

    <delete id="delete${classPrefix}ByLogic" >
        update ${tableName} set is_Del = 1 where id in
        <foreach collection="array" item="id" open="(" separator="," close=")">
            <@mapperEl "id" />
        </foreach>
    </delete>

    <update id="update${classPrefix}"  parameterType="${classPrefix}Model" >
        <#assign isUpdateNeedComma=false>
        update ${tableName}
        set <#list columnList as column><#if column.fieldName != 'id' && column._isEdit ><#if isUpdateNeedComma> , </#if><#assign isUpdateNeedComma=true>${column.columnName} = <@mapperEl column.fieldName /></#if></#list>
        where id = <@mapperEl "id" />
    </update>

    <update id="updatePosition" parameterType="${classPrefix}Model" >
        update ${tableName} set seq = <@mapperEl "seq" /> where id = <@mapperEl "id" />
    </update>

</mapper>