<%@ page import="com.pay.platform.modules.sysmgr.user.model.UserModel" %>
<%@ page import="com.pay.platform.common.context.AppContext" %>
<%@ page import="com.pay.platform.common.util.SysUserUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User:
  Date: 16/10/1
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">个人中心</h3>
            </div>
            <div class="panel-body">

                <table class="table table-bordered table-info-detail">

                    <tr>
                        <td class="td-label">用户昵称</td>
                        <td class="td-content"> &nbsp;&nbsp;${currentUser.nickname}</td>
                    </tr>

                    <tr>
                        <td class="td-label">登录账号</td>
                        <td class="td-content"> &nbsp;&nbsp;${currentUser.account}</td>
                    </tr>

                    <tr>
                        <td class="td-label">手机号码</td>
                        <td class="td-content"> &nbsp;&nbsp;${currentUser.phone}</td>
                    </tr>

                    <tr>
                        <td class="td-label">组织机构</td>
                        <td class="td-content"> &nbsp;&nbsp;${currentUser.orgName}</td>
                    </tr>

                    <tr>
                        <td class="td-label">用户头像</td>
                        <td class="td-content">
                            <div class="kv-avatar">
                                <input id="imgDetailFile" name="imgDetailFile" type="file" class="file-loading">
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <button type="button" class="btn btn-primary" onclick="pageScope.toEditUser('${currentUser.id}');">
                                <i class="glyphicon glyphicon-pencil"></i>修改
                            </button>
                            <%-- 商家则显示查看密钥按钮 --%>
                            <%
                                UserModel userModel = AppContext.getCurrentUser();
                                if (userModel != null && SysUserUtil.isMerchantRole(userModel)) {
                                    out.print("<button type=\"button\" class=\"btn btn-primary\" onclick=\"pageScope.showMerchantSecret();\">");
                                    out.print("<i class=\"glyphicon glyphicon-file\"></i>查看商家密钥");
                                    out.print("</button>");
                                }
                            %>
                        </td>
                    </tr>

                </table>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript">

    var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

    /**
     * 编辑用户
     */
    pageScope.toEditUser = function (id) {
        $.dialog.show({
            url: "${baseURL}/view/sysmgr/user/user_personal_center_edit.jsp?_csrf=" + token,
        });
    };

    /**
     * 刷新当前界面信息
     */
    pageScope.refreshCurrentUserInfo = function () {

        //触发菜单按钮事件,重新刷新此页
        $("#btnPersonalInfo").trigger("click");

    };

    /**
     * 查看商家密钥
     */
    pageScope.showMerchantSecret = function(){

        $.dialog.show({
            url: baseURL + "/view/sysmgr/user/user_show_secret.jsp?" + _csrf + "=" + token,
            onLoad: function () {

            },
            success: function (response) {

            }
        });

    };

    $(function () {

        //刷新左侧菜单,顶部栏的用户信息
        $("#topNickname").html("${currentUser.nickname}");
        $("#topSmallAvatar").attr("src", "${baseURL}${currentUser.avatar}");

        $("#topLargeNickname").html("${currentUser.nickname}");
        $("#topLargeAvatar").attr("src", "${baseURL}${currentUser.avatar}");

        $("#menuNickname").html("${currentUser.nickname}");
        $("#menuAvatar").attr("src", "${baseURL}${currentUser.avatar}");

        var imgUrl = "${currentUser.avatar}";
        if ($.validate.isEmpty(imgUrl)) {
            imgUrl = "/resources/img/default/default_avatar_male.jpg";
        }

        $("#imgDetailFile").showImage({
            imgUrl: imgUrl
        });

    });

</script>