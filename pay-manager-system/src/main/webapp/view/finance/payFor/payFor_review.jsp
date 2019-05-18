<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">代付审核</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="reviewPayForForm" action="${baseURL}/finance/payFor/review" class="form-horizontal"  method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="reviewId" name="id"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商家名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMerchantName" type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商家编号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="detailMerchantNo"  type="text" class="form-control" readonly="readonly"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">状态：</label>
                <div class="col-md-8 col-sm-8">
                    <select class="form-control" name="checkStatus" id="checkStatus">
                        <option value="waitCheck">待审核</option>
                        <option value="checkSuccess">审核通过</option>
                        <option value="checkFail">审核失败</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">审核备注：</label>
                <div class="col-md-8 col-sm-8">
                    <textarea id="checkDesc" name="checkDesc" type="text" class="form-control"/>
                </div>
            </div>

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
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>