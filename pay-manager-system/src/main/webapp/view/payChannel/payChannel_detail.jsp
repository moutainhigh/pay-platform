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
    <h4 class="modal-title">通道详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addPayChannelForm" action="" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通道编码：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailChannelCode" name="channelCode" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通道名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailChannelName" name="channelName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">成本费率：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailCostRate" name="costRate" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>