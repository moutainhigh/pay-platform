<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/9/30
  Time: 22:29
  左侧菜单栏
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 左侧菜单 -->
<aside class="main-sidebar">

    <section class="sidebar">

        <!-- 头像相关 -->
        <div class="user-panel">
            <div class="pull-left image">

                <c:choose>
                    <c:when test="${not empty currentUser.avatar and '' != currentUser.avatar}">
                        <img src="${baseURL}${currentUser.avatar}" class="img-circle" alt="User Image" id="menuAvatar">
                    </c:when>
                    <c:otherwise>
                        <img src="${baseURL}/resources/img/default/default_avatar_male.jpg" class="img-circle" alt="User Image" id="menuAvatar">
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="pull-left info">
                <p id="menuNickname">${currentUser.nickname}</p>
                <a style="cursor:default;">${currentUser.roleNames}</a>
            </div>
        </div>

        <!--菜单列表-->
        <ul class="sidebar-menu">

            <c:forEach var="dirItem" items="${currentUser.permissionList}">

                <%-- 一级目录 --%>
                <c:if test="${dirItem.level == 1}">

                    <li class="treeview">
                        <a href="#">
                            <i class="${dirItem.icon}"></i> <span>${dirItem.name}</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                        <ul class="treeview-menu">

                                <%-- 二级链接 --%>
                            <c:forEach var="linkItem" items="${currentUser.permissionList}">

                                <c:if test="${linkItem.level == 2 && linkItem.parentId == dirItem.id}">
                                    <li><a onclick="loadMenuContent('${linkItem.linkUrl}');"><i class="fa fa-angle-right"></i>${linkItem.name}</a></li>
                                </c:if>

                            </c:forEach>

                        </ul>
                    </li>

                </c:if>

            </c:forEach>

        </ul>

    </section>

</aside>

<!-- 右侧菜单 (用于设置主题,样式等) -->
<aside class="control-sidebar control-sidebar-dark">

    <div class="tab-content">

        <div class="tab-pane" id="control-sidebar-home-tab"></div>

        <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>

        <div class="tab-pane" id="control-sidebar-settings-tab"></div>

    </div>

</aside>

<div class="control-sidebar-bg"></div>
<script type="text/javascript" src="${baseURL}/resources/js/app.style.js"></script>
<script type="text/javascript">

    /**
     * 加载菜单对应内容,通过$.ajax解析返回html,并替换容器内容
     */
    function loadMenuContent(url) {

        $('#content-main').html("");                    //清空内容区域

        $.ajax({
            type: "get",
            url: "${baseURL}" + url + "?_csrf=" + token,
            dataType: "html",
            cache: false,
            success: function (response) {

                var index = response.indexOf("欢迎登录快速开发平台");
                if (index > 0) {
                    window.location.href = baseURL + "/console/login";
                } else {
                    $('#content-main').html(response);       //重新替换
                    listenerContentHeight();
                }

            }
        });

    }

    /**
     * 监听树/内容区域的宽高变化
     */
    function listenerContentHeight() {

        //监听内容的宽高变化
        $(".left-tree").resize(function () {

            var treeHeight = $(".left-tree").css("height");
            var contentHeight = $(".right-content").css("height");

            //修改分割线高度
            var lineHeight = parseInt(treeHeight) > parseInt(contentHeight) ? treeHeight : contentHeight;
            $(".tree-content-middle-line").height(lineHeight);

        });

        $(".right-content").resize(function () {

            var treeHeight = $(".left-tree").css("height");
            var contentHeight = $(".right-content").css("height");

            var lineHeight = parseInt(treeHeight) > parseInt(contentHeight) ? treeHeight : contentHeight;
            $(".tree-content-middle-line").height(lineHeight);

        });

    }

</script>