<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pay.platform.modules.sysmgr.user.model.UserModel" %>
<%@ page import="com.pay.platform.common.context.AppContext" %>
<%@ page import="com.pay.platform.common.util.SysUserUtil" %>
<%
    {
        UserModel userModel = AppContext.getCurrentUser();
        if (userModel != null) {
            session.putValue("roleCode", SysUserUtil.getRoleCode(userModel));
            session.putValue("merchantId", userModel.getMerchantId());
        }
    }
%>
<script type="text/javascript">
    var roleCode = "${roleCode}";
    var merchantId = "${merchantId}";
</script>

<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/4
  Time: 09:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台管理系统</title>
    <%@include file="common.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%-- 引入头部 --%>
    <%@include file="header.jsp" %>

    <!-- 内容区域-->
    <div class="content-wrapper" id="content-main">
        <div class="row">
            <div class="col-md-12">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Welcome</h3>
                    </div>
                    <%--<div class="panel-body">--%>
                    <%--</div>--%>
                </div>

            </div>
        </div>
    </div>

    <%-- 引入菜单 --%>
    <%@include file="menu.jsp" %>

    <%-- 引入底部版权--%>
    <%@include file="footer.jsp" %>

</div>
</body>
<script type="text/javascript">

    $(function () {

        <%-- 商家和代理首次登陆需要修改密码 --%>
        <%
                UserModel userModel = AppContext.getCurrentUser();
            if (userModel != null) {
                if (SysUserUtil.isAgentRole(userModel) || SysUserUtil.isMerchantRole(userModel)) {
                    if (1 == userModel.getNeedInitPassword()) {
                        out.println("$.dialog.show({");
                        out.println(" url: baseURL + \"/view/sysmgr/user/user_update_password.jsp?_csrf=\" + token, ");
                        out.println(" }); ");
                    }
                }
            }
        %>

        //查询提醒商家及时进行提现
        if (roleCode == "ROLE_MERCHANT") {
            queryMerchantAmountOfNotifyWithdraw();
        }

    });

    /**
     * 轮询,提醒商家及时进行提现
     */
    function queryMerchantAmountOfNotifyWithdraw() {
        $.ajax({
            type: "GET",
            url: baseURL + "/merchant/queryMerchantAmountOfNotifyWithdraw?merchantId=" + merchantId,
            async: true,
            dataType: "json",
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.alert("温馨提示", response.msg);
                }

            }
        });
    }

</script>

</html>