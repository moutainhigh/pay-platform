<%@page language="java" contentType="text/html; charset=UTF-8" %>

<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/jsp/common/sbadmin_common.jsp" %>
    <script type="text/javascript" src="<@jspEl "baseURL" />/jsp${requestUrl}/${propertyPrefix}.js"></script>
</head>

<script type="text/javascript">

    $(function(){
        pageScope.init${classPrefix}Table();

        <#list columnList as column>
            <#if column._isQueryField>
                <#if column.formType?? && column.formType == "datetime" >
                    $("#query${column.fieldName?cap_first}").datetimepicker();

                </#if>
            </#if>
        </#list>

    });

</script>

<body class="container">
<div class="panel panel-default">

    <div class="panel-heading">
        <h3 class="panel-title">${moduleName}管理</h3>
    </div>

    <div class="panel-body">

        <!-- 搜索表单 -->
        <form id="search${classPrefix}Form" onsubmit="return false;" autocomplete="off">
            <div class="searchDiv">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                                        <tr class="query-row-1">

										<#list columnList as column>
											<#if column._isQueryField>
                                                <td width="80" align="right" height="40" >${column.remarks}：</td>
                                                <td width="140">
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
										</#list>

                                            <td>&nbsp;</td>

                                            <td colspan="3">
                                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                                       onclick="javascript:document.getElementById('search${classPrefix}Form').reset(); pageScope.search();">
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>

            </div>
        </form>

        <!--  表格 -->
        <table id="${propertyPrefix}Table"></table>

        <!--  工具栏 -->
        <div id="${propertyPrefix}Pager">

            <button id="add" class="btn btn-success" onclick="pageScope.add${classPrefix}()">
                <i class="glyphicon glyphicon-plus"></i> 新增
            </button>

            <button id="remove" class="btn btn-danger" onclick="pageScope.delete${classPrefix}()">
                <i class="glyphicon glyphicon-remove"></i> 删除
            </button>

        </div>

    </div>
</div>

</body>
</html>
