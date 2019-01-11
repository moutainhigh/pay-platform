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
    <title>快速开发平台</title>
    <%@include file="common.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%-- 引入头部 --%>
    <%@include file="header.jsp" %>

    <!-- 内容区域-->
    <div class="content-wrapper" id="content-main" >
        <div class="row">
            <div class="col-md-12" >

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
</html>