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

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">提现申请详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addWithdrawForm" action="" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">提现金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailWithdrawAmount" name="withdrawAmount" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">真实姓名：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailRealName" name="realName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">银行名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailBankName" name="bankName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">银行卡号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailBankCard" name="bankCard" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">开户银行所在省：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailProvinceName" name="provinceName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">开户银行所在市：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailCityName" name="cityName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">审核状态：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailCheckStatus"  type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">提现状态：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailWithdrawStatus" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">申请时间：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailCreateTime" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>