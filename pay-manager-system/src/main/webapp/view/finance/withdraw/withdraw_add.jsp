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
    <h4 class="modal-title">提现申请</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addWithdrawForm" action="${baseURL}/finance/withdraw/addWithdraw" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">提现金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addWithdrawAmount" name="withdrawAmount" type="text" class="form-control" check-type="required" maxlength="10" placeholder="请输入提现金额"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">真实姓名：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addRealName" name="realName" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入真实姓名"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">银行名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addBankName" name="bankName" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入银行名称"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">银行卡号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addBankCard" name="bankCard" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入银行卡号"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">开户银行所在省：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addProvinceName" name="provinceName" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入开户银行所在省"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">开户银行所在市：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addCityName" name="cityName" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入开户银行所在市"/>
                </div>
            </div>


        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#addWithdrawForm').validation();            //表单验证初始化


    });

</script>