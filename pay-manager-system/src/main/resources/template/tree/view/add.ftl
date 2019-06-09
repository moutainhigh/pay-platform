<%--
  Created by IntelliJ IDEA.
  User:
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">新增${moduleName}</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="add${classPrefix}Form" action="<@jspEl "baseURL" />${requestUrl}/add${classPrefix}" class="form-horizontal">

            <input type="hidden" name="<@jspEl "_csrf.parameterName" />" value="<@jspEl "_csrf.token" />" />
            <input type="hidden" name="parentId" value="<@jspEl "param.parentId" />" />
            <input type="hidden" name="level" value="<@jspEl "param.level" />" />

            <#list columnList as column>
                <#if column._isAdd>
                    <#-- 普通文本 -->
                    <#if column.formType == "text" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                                <input name="${column.fieldName}" type="text" class="form-control" <#if column._isRequired >check-type="required"</#if> maxlength="${column.size}" placeholder="请输入${column.remarks}" />
                            </div>
                        </div>
                    <#-- 日期类型 -->
                    <#elseif column.formType == "datetime" >
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">${column.remarks}：</label>
                            <div class="col-md-8 col-sm-8">
                                <div class='input-group date' id="add${column.fieldName?cap_first}" >
                                    <input type='text' name="${column.fieldName}" class="form-control" <#if column._isRequired >check-type="required"</#if> data-date-format="YYYY-MM-DD HH:mm" placeholder="请输入${column.remarks}" />
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
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
                            <div class="col-md-8 col-sm-8" >
                                <script id="add${column.fieldName?cap_first}" name="${column.fieldName}" type="text/plain" style="width:auto;height:500px;"></script>
                            </div>
                        </div>
                    </#if>

                </#if>
            </#list>

        </form>

    </div>
</div>

<div class="modal-footer operation-button" >
    <button data-bb-handler="success" type="button" class="btn btn-success" >保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger" >取消</button>
</div>

<#if containRichTextField>
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/resources/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/resources/plugins/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<@jspEl "baseURL" />/resources/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

</#if>