<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>

<#if containRichTextField>
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/ueditor.parse.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>

</#if>
<script type="text/javascript" >

    $(function(){

        $('#add${classPrefix}Form').validation();            //表单验证初始化

        <#list columnList as column>
            <#if column._isAdd>
                <#if column.formType == "datetime" >
                    //日期初始化
                    $("#add${column.fieldName?cap_first}Div").datetimepicker();

                <#-- 下拉框 -->
                <#elseif column.formType == "select" >
                    //加载字典(下拉框)
                    $("#add${column.fieldName?cap_first}").loadDictionaryForSelect({
                        "inputName":"${column.fieldName}",
                        "dictType":"${column.dictType}"
                    });

                <#-- 单选按钮 -->
                <#elseif column.formType == "radio" >
                    //加载字典(单选按钮)
                    $("#add${column.fieldName?cap_first}").loadDictionaryForRadio({
                        "inputName":"${column.fieldName}",
                        "dictType":"${column.dictType}"
                    });

                <#-- 复选框 -->
                <#elseif column.formType == "checkbox" >
                    //加载字典(复选框)
                    $("#add${column.fieldName?cap_first}").loadDictionaryForCheckbox({
                        "inputName":"${column.fieldName}",
                        "dictType":"${column.dictType}"
                    });

                <#-- 富文本 -->
                <#elseif column.formType == "richText" >
                    //实例化编辑器
                    var ueditor = UE.getEditor('add${column.fieldName?cap_first}');
                <#elseif column.formType == "singleImage" >
                    //单图片上传 - ${column.remarks}
                    $("#file${column.fieldName?cap_first}").singleImageUpload({
                        uploadExtraData: {"module": "${requestUrl}"} ,                     //上传图片扩展参数,指定所属模块
                        hiddenField:"#add${column.fieldName?cap_first}" ,                   //返回隐藏域路径
                    });

                <#elseif column.formType == "multiImage" >
                    //多图片上传 - ${column.remarks}
                    $("#multiFile${column.fieldName?cap_first}").multiImageUpload({
                        uploadExtraData: {"module": "${requestUrl}"} ,                  //上传图片扩展参数,指定所属模块
                        hiddenField:"#add${column.fieldName?cap_first}" ,              //返回隐藏域路径
                    });

                </#if>
            </#if>
        </#list>

    });

</script>

<div id="couponCode_addform_div" class="panel panel-fit">

    <form id="add${classPrefix}Form" action="<@jspEl "baseURL" />${requestUrl}/add${classPrefix}" class="form-horizontal"  method="post">

        <input type="hidden" name="<@jspEl "_csrf.parameterName" />" value="<@jspEl "_csrf.token" />" />

        <#list columnList as column>
            <#if column._isAdd>

                  <#-- 普通文本 -->
                    <#if column.formType == "text" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                                <input id="add${column.fieldName?cap_first}" name="${column.fieldName}" type="text" class="form-control" <#if column._isRequired >check-type="required"</#if> maxlength="${column.size}" placeholder="请输入${column.remarks}" />
                            </div>
                        </div>
                    <#-- 日期类型 -->
                    <#elseif column.formType == "datetime" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                                <div class='input-group date' id="add${column.fieldName?cap_first}Div" >
                                    <input id="add${column.fieldName?cap_first}" type='text' name="${column.fieldName}" class="form-control" <#if column._isRequired >check-type="required"</#if> data-date-format="YYYY-MM-DD HH:mm" placeholder="请输入${column.remarks}" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                </div>
                            </div>
                        </div>
                    <#-- 下拉框 -->
                    <#elseif column.formType == "select" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                                <select id="add${column.fieldName?cap_first}" name="${column.fieldName}" class="form-control" ></select>
                            </div>
                        </div>
                    <#-- 单选按钮/复选框 -->
                    <#elseif column.formType == "radio" || column.formType == "checkbox" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8" id="add${column.fieldName?cap_first}" name="${column.fieldName}" >
                            </div>
                        </div>
                    <#-- 富文本 -->
                    <#elseif column.formType == "richText" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12 col-sm-12">
                                <script id="add${column.fieldName?cap_first}" type="text/plain" style="width:auto;height:500px;"></script>
                                <input type="hidden" id="addReal${column.fieldName?cap_first}" name="${column.fieldName}" />
                            </div>
                        </div>
                    <#-- 单图片 -->
                    <#elseif column.formType == "singleImage" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                                <div class="kv-avatar"  >
                                    <!-- 上传文件表单组件 -->
                                    <input id="file${column.fieldName?cap_first}" type="file" class="file-loading" >
                                </div>
                            </div>
                            <!-- 隐藏域,保存上传文件的路径  -->
                            <input id="add${column.fieldName?cap_first}" name="${column.fieldName}" type="hidden" />
                        </div>
                    <#-- 多图片上传 -->
                    <#elseif column.formType == "multiImage" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-9 col-sm-9">
                                <!-- 上传文件表单组件 -->
                                <input id="multiFile${column.fieldName?cap_first}" multiple type="file" class="file-loading" >
                            </div>
                            <!-- 隐藏域,保存上传文件的路径  -->
                            <input id="add${column.fieldName?cap_first}" name="${column.fieldName}" type="hidden" />
                        </div>

                    </#if>

            </#if>
        </#list>

    </form>

</div>