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
    <h4 class="modal-title">编辑用户</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editUserForm" action="${baseURL}/sysmgr/user/updateUser" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${currentUser.id}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">用户昵称：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="nickname" value="${currentUser.nickname}" type="text" class="form-control" check-type="required" maxlength="20" placeholder="请输入用户昵称"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="account" value="${currentUser.account}" type="text" class="form-control" check-type="required" maxlength="20" placeholder="请输入登录账号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="password" id="password" placeholder="请填写新密码,不填写则默认保持原密码" class="form-control" maxlength="20" autocomplete="new-password">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">确认密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="confirmPassword" id="confirmPassword" placeholder="请填写确认密码" class="form-control" maxlength="20" autocomplete="new-password">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手机号码：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="phone" value="${currentUser.phone}" type="text" class="form-control" check-type="required mobile" placeholder="请输入手机号码"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">组织机构：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="input-group">
                        <input id="editOrgName" name="orgName" type="text" class="form-control" check-type="required" disabled value="${currentUser.orgName}">
                        <input id="editOrgId" name="orgId" type="hidden" value="${currentUser.orgId}">

                        <span class="input-group-btn">
                          <button class="btn btn-default" type="button" onclick="pageScope.selectOrg();">选择</button>
                        </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">用户头像：</label>
                <div class="col-md-8 col-sm-8">

                    <%-- 上传文件表单组件 --%>
                    <div class="kv-avatar">
                        <input id="editImgFile" name="editImgFile" type="file">
                    </div>

                </div>

                <%-- 隐藏域,保存上传文件的路径  --%>
                <input id="avatar" name="avatar" type="hidden" value="${currentUser.avatar}"/>

            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success" onclick="pageScope.updateUser()">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#editUserForm').validation();

        /**
         * 上传头像 - 编辑/回显示
         */
        $("#editImgFile").singleImageUpload({
            uploadExtraData: {"module": "user"},
            hiddenField: "#avatar",
            defaultPreviewContent: '<img src="' + baseURL + '/resources/img/default/default_avatar_male.jpg" style="width:160px" >',
            imgUrl: "${currentUser.avatar}"
        });

    });


    /**
     * 修改用户信息
     */
    pageScope.updateUser = function () {

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
                    $(".modal-footer .btn-danger").trigger("click");

                    //刷新当前用户信息
                    pageScope.refreshCurrentUserInfo();

                }
                else {
                    $.msg.error(response.msg);
                }

            }, error: function (e) {
                btn.removeAttr("disabled");
            }

        });

    };

    /**
     * 选择组织机构
     */
    pageScope.selectOrg = function () {

        var dialog = $.dialog.show({
            url: "${baseURL}/view/sysmgr/user/user_edit_organization.jsp",
            params: {"_csrf": token, "userId": "${currentUser.id}"},
            size: "small",
            confirmCallback: function () {

                //获取选中节点
                var selectOrgTree = $.fn.zTree.getZTreeObj("selectOrgTree");
                var checkedNodes = selectOrgTree.getCheckedNodes(true);

                if (checkedNodes.length > 0) {
                    var orgId = checkedNodes[0].id;
                    var orgName = checkedNodes[0].name;

                    $("#editOrgId").val(orgId);
                    $("#editOrgName").val(orgName);

                    dialog.hide();

                }
                else {
                    $.msg.toast("请选中授予机构!");
                }

            }
        });

    }

</script>