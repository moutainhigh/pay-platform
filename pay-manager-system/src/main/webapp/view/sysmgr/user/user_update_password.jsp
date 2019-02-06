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
    <h4 class="modal-title">首次登录请先修改密码</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editUserForm" action="${baseURL}/sysmgr/user/updateUserPassword" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">用户昵称：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="nickname" value="${currentUser.nickname}" readonly="readonly" type="text" class="form-control" check-type="required" maxlength="20" placeholder="请输入用户昵称"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="account" value="${currentUser.account}" readonly="readonly" type="text" class="form-control" check-type="required" maxlength="20" placeholder="请输入登录账号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">旧密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="oldPassword" id="oldPassword" check-type="required" placeholder="请填写旧密码" class="form-control" maxlength="20" autocomplete="old-password">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="password" id="password" check-type="required"  placeholder="请填写新密码,不填写则默认保持原密码" class="form-control" maxlength="20" autocomplete="new-password">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">确认密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="confirmPassword" check-type="required"  id="confirmPassword" placeholder="请填写确认密码" class="form-control" maxlength="20" autocomplete="new-password">
                </div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success" onclick="updateUserPassword()">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#editUserForm').validation();

    });


    /**
     * 修改用户信息
     */
   function updateUserPassword() {

        //表单验证
        if (!$("#editUserForm").valid()) {
            return;
        }

        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        if ($.validate.isNotEmpty(password)) {

            if ($.validate.isEmpty(confirmPassword)) {
                $.msg.error("请输入确认密码!");
                return;
            }

            if (password != confirmPassword) {
                $.msg.error("两次输入密码不一致!");
                return;
            }

        }

        var btn = $(".modal-footer .btn-success");              //防止重复提交
        btn.attr("disabled", "disabled");

        $('#editUserForm').ajaxSubmit({
            dataType: 'json',
            type: "post",
            success: function (response) {

                btn.removeAttr("disabled");

                if (response && response.success) {
                    $.msg.success(response.msg);
                    window.location.href = baseURL + "/console/index";
                }
                else {
                    $.msg.error(response.msg);
                }

            }, error: function (e) {
                btn.removeAttr("disabled");
            }

        });

    };

</script>