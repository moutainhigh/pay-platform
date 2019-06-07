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
    <h4 class="modal-title">补单确认</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="budanForm" action="${baseURL}/order/makeOrderPaySuccess" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input type="hidden" name="orderNo" id="budan_orderNo" />

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商户订单号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="budanMerchantOrderNo" name="merchantOrderNo" type="text" class="form-control" check-type="required" maxlength="255" placeholder="请输入商家订单号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">实际支付金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="budanPayAmount" name="payAmount" type="text" class="form-control" check-type="required number 2digit" maxlength="10" placeholder="请输入实际支付金额"/>
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

        $('#budanForm').validation();            //表单验证初始化

    });

</script>