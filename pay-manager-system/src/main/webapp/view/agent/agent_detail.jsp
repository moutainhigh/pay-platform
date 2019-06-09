<%--
Created by IntelliJ IDEA.
User:
Date: 2016/10/6
Time: 15:21
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">代理详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addAgentForm" action="" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">代理编号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailAgentNo" name="agentNo" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">代理名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailAgentName" name="agentName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手机号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailPhone" name="phone" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">真实姓名：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailRealName" name="realName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证号码：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailIdentityCode" name="identityCode" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证-正面：</label>
                <div class="col-md-8 col-sm-8 detail-content">
                    <div class="kv-avatar">
                        <input id="fileIdCardImg1" type="file" class="file-loading"/>
                    </div>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证-反面：</label>
                <div class="col-md-8 col-sm-8 detail-content">
                    <div class="kv-avatar">
                        <input id="fileIdCardImg2" type="file" class="file-loading"/>
                    </div>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">icp证件：</label>
                <div class="col-md-8 col-sm-8 detail-content">
                    <div class="kv-avatar">
                        <input id="fileIcpImg" type="file" class="file-loading"/>
                    </div>
                </div>
            </div>


        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>