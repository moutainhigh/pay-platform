<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">请先设置提现密码</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="initWithdrawPasswordForm" action="${baseURL}/finance/withdraw/initWithdrawPassword" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">提现密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="withdrawPassword" id="withdrawPassword" check-type="required" class="form-control" maxlength="20" autocomplete="new-password">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">确认提现密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="confirmWithdrawPassword" check-type="required"  id="confirmWithdrawPassword" class="form-control" maxlength="20" autocomplete="new-password">
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

        $('#initWithdrawPasswordForm').validation();

    });

</script>