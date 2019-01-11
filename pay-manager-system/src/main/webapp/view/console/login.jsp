<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/4
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>欢迎登录快速开发平台</title>

    <!-- CSS -->
    <link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="${baseURL}/resources/fonts/Font-Awesome-master/css/font-awesome.min.css">
    <link rel="stylesheet" href="${baseURL}/resources/css/login-style.css">
    <link rel="stylesheet" href="${baseURL}/resources/plugins/sweetalert/css/sweetalert.css" />

    <!-- Javascript -->
    <script src="${baseURL}/resources/plugins/jquery/jquery2.1.4/jquery-2.1.4.js"></script>
    <script src="${baseURL}/resources/plugins/bootstrap/bootstrap-3.3.5/js/bootstrap.js"></script>
    <script src="${baseURL}/resources/plugins/jquery/jquery-backstretch/jquery.backstretch.min.js"></script>
    <script src="${baseURL}/resources/plugins/sweetalert/js/sweetalert.js"></script>
    <script src="${baseURL}/resources/js/extend/jquery.msg.extend.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript">

        $(function(){

            $.backstretch("${baseURL}/resources/img/backgrounds/1.jpg");

            $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
                $(this).removeClass('input-error');
            });

            $('.login-form').on('submit', function(e) {

                $(this).find('input[type="text"], input[type="password"], textarea').each(function(){
                    if( $(this).val() == "" ) {
                        e.preventDefault();
                        $(this).addClass('input-error');
                    }
                    else {
                        $(this).removeClass('input-error');
                    }
                });

            });

            <c:if test="${not empty msg}">
                $.msg.error("${msg}");
            </c:if>

        });

    </script>

</head>

<body>
<div class="top-content">

    <div class="inner-bg">
        <div class="container">

            <%-- 描述信息 --%>
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1>欢迎登录快速开发平台</h1>
                    <div class="description">
                    </div>
                </div>
            </div>

            <%-- 表单区域 --%>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <p>请输入您的用户名和密码</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">

                        <form action="${baseURL}/j_spring_security_check" method="post" class="login-form" >

                            <div class="form-group">
                                <label class="sr-only" for="form-username">用户名</label>
                                <input name="username" type="text" placeholder="请输入您的用户名" class="form-username form-control" id="form-username" >
                            </div>

                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input name="password" type="password" placeholder="请输入您的密码" class="form-password form-control" id="form-password" >
                            </div>

                            <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}" />

                            <button type="submit" class="btn">登录</button>

                        </form>

                    </div>
                </div>
            </div>

        </div>
    </div>

</div>

</body>

</html>