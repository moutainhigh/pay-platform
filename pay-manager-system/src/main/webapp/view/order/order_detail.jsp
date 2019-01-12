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
    <h4 class="modal-title">订单详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addOrderForm" action="" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商户订单号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMerchantOrderNo" name="merchantOrderNo" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">平台订单号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailPlatformOrderNo" name="platformOrderNo" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">支付单号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailPayCode" name="payCode" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商品名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailGoodsName" name="goodsName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">订单金额(元)：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailOrderAmount" name="orderAmount" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商户金额(元)：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMerchantAmount" name="merchantAmount" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">费率：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailRate" name="rate" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手续费(元)：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailHandlingFee" name="handlingFee" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商家编号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMerchantNo" name="merchantNo" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通道名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailChannelId" name="channelId" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">支付方式 ：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailPayWay" name="payWay" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">支付状态：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailPayStatus" name="payStatus" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">支付时间：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailPayTime" name="payTime" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商户回调地址：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailNotifyUrl" name="notifyUrl" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">回调商户状态：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailNotifyStatus" name="notifyStatus" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通知次数：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailNotifiyNum" name="notifiyNum" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>


        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>