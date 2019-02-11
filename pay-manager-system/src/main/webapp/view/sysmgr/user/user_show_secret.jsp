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
    <h4 class="modal-title">用户详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="detailUserForm" action="${baseURL}/sysmgr/user/updateUser" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录密码：</label>
                <div class="col-md-5 col-sm-5 detail-content"  >
                    <input name="password" id="password" type="password" class="form-control" check-type="required" maxlength="20" placeholder="请输入登录密码,确认查看密钥！"/>
                </div>
                <div class="col-md-3 col-sm-3 detail-content"  >
                    <button type="button" class="btn btn-primary" onclick="showMerchantSecret();" >查看密钥</button>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商家密钥：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailMerchantSecret" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">回调密钥：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailNotifySecret" ></div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    /**
     * 查看商家密钥
     */
    function showMerchantSecret(){

        var password = $("#password").val();
        if ($.validate.isEmpty(password)) {
            $.msg.error("请输入确认密码!");
            return;
        }

        $.ajax({
            url: baseURL + "/sysmgr/user/showMerchantSecret",
            type: "post",
            dataType: "json",
            data: {"password": password, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    $("#detailMerchantSecret").html(response.data.merchantSecret);
                    $("#detailNotifySecret").html(response.data.notifySecret);
                } else {
                    $.msg.error(response.msg);
                }

            }
        });

    }

</script>