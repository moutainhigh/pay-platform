<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/view/console/common.jsp" %>

<html>
<head>
    <title>500 - 服务器内部错误</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <meta http-equiv="refresh" content="5;url=${baseURL}/console/index">
    <link rel="stylesheet" type="text/css" href="${baseURL}/resources/css/error/error.css" media="screen" />
</head>
<body>

    <div id="container"><img class="png" src="${baseURL}/resources/img/error/500.png" /> <img class="png msg" src="${baseURL}/resources/img/error/500_msg.png" />
        <p><a href="${baseURL}/console/index"><img class="png" src="${baseURL}/resources/img/error/404_to_index.png" /></a> </p>
    </div>

    <div id="cloud" class="png"></div>

    <pre style="DISPLAY: none"></pre>

</body>
</html>
