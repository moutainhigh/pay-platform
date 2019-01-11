<%--
Created by IntelliJ IDEA.
User: zjt
Date: 16/10/5
Time: 14:13
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">

            <div class="panel-heading">
                <h3 class="panel-title">${moduleName}管理</h3>
            </div>

            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="search${classPrefix}Form" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>

						<#list columnList as column>
							<#if column._isQueryField>
								<#if column.formType?? && column.formType == "text" >
                                    <td width="80" align="right">${column.remarks}：</td>
                                    <td width="150">
                                        <input type="text" name="${column.fieldName}" id="query${column.fieldName?cap_first}" class="form-control btn-block" aria-describedby="basic-addon1">
                                    </td>

								<#elseif column.formType?? && column.formType == "datetime" >
                                    <td width="80" align="right">${column.remarks}：</td>
                                    <td width="180">
                                        <div class='input-group date' id="query${column.fieldName?cap_first}">
                                            <input type='text' name="${column.fieldName}" class="form-control" data-date-format="YYYY-MM-DD HH:mm"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </td>

								</#if>
							</#if>
						</#list>
                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.init${classPrefix}Tree()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置" onclick="javascript:document.getElementById('search${classPrefix}Form').reset(); pageScope.init${classPrefix}Tree();">
                            </td>

                        </tr>

                    </table>
                </form>

                <div class="left-tree" >
                    <ul id="${propertyPrefix}Tree" class="ztree"></ul>
                </div>

                <div class="tree-content-middle-line"></div>

                <div class="right-content" style="visibility: hidden">

                    <form class="form-horizontal">

                        <#list columnList as column>
                            <#if column._isShowDetail && column.fieldName != "id" && column.formType??  >
                                <#-- 普通文本,日期,单选/下拉/复选,富文本 -->
                                <#if column.formType == "text" || column.formType == "datetime" || column.formType == "radio"  || column.formType == "select"  || column.formType == "checkbox"  || column.formType == "richText"  >
                                    <div class="form-group">
                                        <label class="col-md-2 col-sm-2 control-label tree-detail-label">${column.remarks}：</label>
                                        <div class="col-md-8 col-sm-8 detail-content" id="detail${column.fieldName?cap_first}" ></div>
                                    </div>

                                <#-- 单图片 -->
                                <#elseif column.formType == "singleImage" >
                                    <div class="form-group">
                                        <label class="col-md-2 col-sm-2 control-label tree-detail-label">${column.remarks}：</label>
                                        <div class="col-md-8 col-sm-8 detail-content" id="detail${column.fieldName?cap_first}" >
                                        </div>
                                    </div>

                                <#-- 多图片上传 -->
                                <#elseif column.formType == "multiImage" >
                                    <div class="form-group">
                                        <label class="col-md-2 col-sm-2 control-label tree-detail-label">${column.remarks}：</label>
                                        <div class="col-md-9 col-sm-9 detail-content" style="padding-right:40px;" id="detail${column.fieldName?cap_first}" >
                                        </div>
                                    </div>

                                </#if>
                            </#if>
                        </#list>

                    </form>
                </div>

            </div>

        </div>

    </div>
</div>

<script type="text/javascript" src="<@jspEl "baseURL" />/view${requestUrl}/${propertyPrefix}.js"></script>
<script type="text/javascript">

    $(function () {
        pageScope.init${classPrefix}Tree();
    });

</script>