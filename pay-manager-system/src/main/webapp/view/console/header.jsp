<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/9/30
  Time: 22:29
  通用的头部栏
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 头部 -->
<header class="main-header">

    <!-- Logo -->
    <a class="logo" style="cursor: default;" >
        <span class="logo-mini"><b>A</b>LT</span>
        <span class="logo-lg"><b>快速开发平台</b></span>
    </a>

    <!-- 开关按钮 -->
    <nav class="navbar navbar-static-top" role="navigation">

        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>

        <!-- 右侧自定义按钮 -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">

                <%--<!-- 消息按钮以及下拉情况 -->--%>
                <%--<li class="dropdown messages-menu">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
                        <%--<i class="fa fa-envelope-o"></i>--%>
                        <%--<span class="label label-success">2</span>--%>
                    <%--</a>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li class="header">You have 4 messages</li>--%>
                        <%--<li>--%>
                            <%--<!-- inner menu: contains the actual data -->--%>
                            <%--<ul class="menu">--%>
                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<img src="${baseURL}/resources/img/user1-128x128.jpg" class="img-circle" alt="User Image">--%>
                                        <%--</div>--%>
                                        <%--<h4>--%>
                                            <%--AdminLTE Design Team--%>
                                            <%--<small><i class="fa fa-clock-o"></i> 2 hours</small>--%>
                                        <%--</h4>--%>
                                        <%--<p>Why not buy a new awesome theme?</p>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="footer"><a href="#">See All Messages</a></li>--%>
                    <%--</ul>--%>
                <%--</li>--%>

                <%--<!--通知信息-->--%>
                <%--<li class="dropdown notifications-menu">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
                        <%--<i class="fa fa-bell-o"></i>--%>
                        <%--<span class="label label-warning">10</span>--%>
                    <%--</a>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li class="header">You have 10 notifications</li>--%>
                        <%--<li>--%>
                            <%--<ul class="menu">--%>
                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<i class="fa fa-users text-aqua"></i> 5 new members joined today--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="footer"><a href="#">View all</a></li>--%>
                    <%--</ul>--%>
                <%--</li>--%>

                <%--<!-- 任务列表 -->--%>
                <%--<li class="dropdown tasks-menu">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
                        <%--<i class="fa fa-flag-o"></i>--%>
                        <%--<span class="label label-danger">9</span>--%>
                    <%--</a>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li class="header">You have 9 tasks</li>--%>
                        <%--<li>--%>
                            <%--<!-- inner menu: contains the actual data -->--%>
                            <%--<ul class="menu">--%>
                                <%--<li><!-- Task item -->--%>
                                    <%--<a href="#">--%>
                                        <%--<h3>--%>
                                            <%--Design some buttons--%>
                                            <%--<small class="pull-right">20%</small>--%>
                                        <%--</h3>--%>
                                        <%--<div class="progress xs">--%>
                                            <%--<div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">--%>
                                                <%--<span class="sr-only">20% Complete</span>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--<!-- end task item -->--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="footer">--%>
                            <%--<a href="#">View all tasks</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
               <%--</li>--%>

                <!-- 用户信息 -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">

                        <c:choose>
                            <c:when test="${not empty currentUser.avatar and '' != currentUser.avatar}">
                                <img src="${baseURL}${currentUser.avatar}" class="user-image"alt="User Image" id="topSmallAvatar" >
                            </c:when>
                            <c:otherwise>
                                <img src="${baseURL}/resources/img/default/default_avatar_male.jpg" class="user-image" alt="User Image" id="topSmallAvatar" >
                            </c:otherwise>
                        </c:choose>

                        <span class="hidden-xs" id="topNickname" >${currentUser.nickname}</span>
                    </a>

                    <ul class="dropdown-menu">

                        <!-- 用户头像 -->
                        <li class="user-header">

                            <c:choose>
                                <c:when test="${not empty currentUser.avatar and '' != currentUser.avatar}">
                                    <img src="${baseURL}${currentUser.avatar}" class="img-circle" alt="User Image" id="topLargeAvatar" >
                                </c:when>
                                <c:otherwise>
                                    <img src="${baseURL}/resources/img/default/default_avatar_male.jpg" class="img-circle" alt="User Image"  id="topLargeAvatar" >
                                </c:otherwise>
                            </c:choose>
                            <p>
                                <span id="topLargeNickname" >${currentUser.nickname} - ${currentUser.roleNames}</span>
                                <small>Member since Nov. 2012</small>
                            </p>

                        </li>

                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a id="btnPersonalInfo" onclick="loadMenuContent('/view/sysmgr/user/user_personal_center.jsp');" class="btn btn-default btn-flat"  >个人信息</a>
                            </div>
                            <div class="pull-right">
                                <a href="${baseURL}/console/logout" class="btn btn-default btn-flat">注销</a>
                            </div>
                        </li>

                    </ul>
                </li>

                <!-- 控制面板,主题设置等-->
                <li>
                    <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                </li>

            </ul>
        </div>
    </nav>
</header>