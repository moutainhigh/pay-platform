<%--
Created by IntelliJ IDEA.
User: zjt
Date: 2016/10/6
Time: 15:21
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>
<%
pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>


<script type="text/javascript">

    $(function () {

	<#list columnList as column>
		<#if column._isShowDetail && column.formType?? >

		</#if>
	</#list>

    });

</script>

<div id="couponCode_addform_div" class="panel panel-fit">

    <form id="add${classPrefix}Form" action="" class="form-horizontal">

        <input type="hidden" name="<@jspEl "_csrf.parameterName" />" value="<@jspEl "_csrf.token" />"/>

		<#list columnList as column>
			<#if column._isAdd>
			<#-- 普通文本,日期,单选/下拉/复选,富文本 -->
				<#if column.formType == "text" || column.formType == "datetime" || column.formType == "radio"  || column.formType == "select"  || column.formType == "checkbox"  >
					<div class="form-group">
						<label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
						<div class="col-md-8 col-sm-8">
							<input id="detail${column.fieldName?cap_first}" name="${column.fieldName}" type="text" class="form-control" readonly="readonly"/>
						</div>
					</div>
				<#-- 富文本 -->
				<#elseif column.formType == "richText">
					<div class="form-group">
						<label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
						<div class="col-md-8 col-sm-8" id="detail${column.fieldName?cap_first}" name="${column.fieldName}">
						</div>
					</div>
				<#--单图片 -->
				<#elseif column.formType == "singleImage" >
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                        <div class="col-md-8 col-sm-8 detail-content">
                            <div class="kv-avatar"  >
                                <input id="file${column.fieldName?cap_first}" type="file" class="file-loading" />
                            </div>
                        </div>
                    </div>

				<#-- 多图片上传 -->
				<#elseif column.formType == "multiImage" >
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                        <div class="col-md-9 col-sm-9 detail-content" style="padding-right:40px;" >
                            <input id="multiFile${column.fieldName?cap_first}" multiple type="file" class="file-loading" />
                        </div>
                    </div>

				</#if>

			</#if>

		</#list>

    </form>

</div>