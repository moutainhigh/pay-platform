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
    <h4 class="modal-title">新增用户</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addUserForm" action="${baseURL}/sysmgr/user/addUser" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">用户昵称：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="nickname" type="text" class="form-control" check-type="required" maxlength="20" placeholder="请输入用户昵称"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="account" value="" type="text" class="form-control" check-type="required" maxlength="20" autocomplete="new-password" placeholder="请输入登录账号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="password" id="password" class="form-control" check-type="required" maxlength="20" minlength="6" autocomplete="new-password" placeholder="请输入密码">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">确认密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" check-type="required" maxlength="20" minlength="6" autocomplete="new-password"
                           placeholder="请输入确认密码">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手机号码：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="phone" type="text" class="form-control" check-type="required mobile" placeholder="请输入手机号码"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">组织机构：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="input-group">
                        <input id="addOrgName" name="orgName" type="text" class="form-control" check-type="required" disabled>
                        <input id="addOrgId" name="orgId" type="hidden">

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
                        <input id="file" name="file" type="file" class="file-loading">
                    </div>

                </div>

                <%-- 隐藏域,保存上传文件的路径  --%>
                <input id="avatar" name="avatar" type="hidden"/>

            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success" onclick="pageScope.addUser()">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#addUserForm').validation();             //初始化表单验证

        /**
         * 上传头像
         */
        $("#file").singleImageUpload({
            uploadExtraData: {"module": "user"},               //上传图片扩展参数,指定所属模块
            hiddenField: "#avatar",                             //返回隐藏域路径
            defaultPreviewContent: '<img src="' + baseURL + '/resources/img/default/default_avatar_male.jpg" style="width:160px" >',   //默认显示图片
        });

    });

    /**
     * 新增用户
     */
    pageScope.addUser = function () {

        //表单验证
        if (!$("#addUserForm").valid()) {
            return;
        }

        //验证两次输入密码是否一致
        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        if (password != confirmPassword) {
            $.msg.error("两次输入密码不一致!");
            return;
        }

        var btn = $(".modal-footer .btn-success");              //防止重复提交
        btn.attr("disabled", "disabled");

        $("#addUserForm").ajaxSubmit({
            dataType: 'json',
            type: "post",
            success: function (response) {

                btn.removeAttr("disabled");

                if (response && response.success) {
                    $.msg.success(response.msg);
                    pageScope.userTable.bootstrapTable('refresh');
                    $(".modal-footer .btn-danger").trigger("click");            //关闭模态框
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
            url: "${baseURL}/view/sysmgr/user/user_edit_organization.jsp?" + _csrf + "=" + token,
            size: "small",
            onLoad: function () {

            },
            buttonEvents: {
                success: function () {
                    //获取选中节点
                    var selectOrgTree = $.fn.zTree.getZTreeObj("selectOrgTree");
                    var checkedNodes = selectOrgTree.getCheckedNodes(true);

                    if (checkedNodes.length > 0) {
                        var orgId = checkedNodes[0].id;
                        var orgName = checkedNodes[0].name;

                        $("#addOrgId").val(orgId);
                        $("#addOrgName").val(orgName);

                        dialog.hide();

                    }
                    else {
                        $.msg.toast("请选中授予机构!");
                    }
                }
            }
        });

    };

</script>