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
    <h4 class="modal-title">收款码详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addTradeCodeForm" action="" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">收款码编号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailCodeNum" name="codeNum" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">设备回调密钥：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailSecret" name="secret" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailLoginAccount" name="loginAccount" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">号主姓名：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailRealName" name="realName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <%--<div class="form-group">--%>
                <%--<label class="col-md-3 col-sm-3 control-label">收款码链接：</label>--%>
                <%--<div class="col-md-8 col-sm-8">--%>
                    <%--<input id="detailCodeLink" name="codeLink" type="text" class="form-control" readonly="readonly"/>--%>
                <%--</div>--%>
            <%--</div>--%>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">最小金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMinAmount" name="minAmount" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">最大金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMaxAmount" name="maxAmount" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">单日收款金额限制：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailDayAmountLimit" name="dayAmountLimit" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <%--<div class="form-group">--%>
                <%--<label class="col-md-3 col-sm-3 control-label">支付宝PID：</label>--%>
                <%--<div class="col-md-8 col-sm-8">--%>
                    <%--<input id="detailZfbUserId" name="zfbUserId" type="text" class="form-control" readonly="readonly"/>--%>
                <%--</div>--%>
            <%--</div>--%>


        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>