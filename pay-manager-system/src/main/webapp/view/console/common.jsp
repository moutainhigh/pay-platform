<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/1
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%
    session.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<!--  强制webkit内核打开 -->
<meta name="renderer" content="webkit" >

<!-- 编码 -->
<meta http-equiv="Content-Type"content="textml; charset=utf-8" />

<!-- 适配移动设备 -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" >

<script type="text/javascript">

    //作为全局变量存在, 挂载window下
    window.baseURL = "${baseURL}";
    window._csrf = "${_csrf.parameterName}";
    window.token = "${_csrf.token}";

</script>

<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-3.3.5/css/bootstrap.css">

<!-- 图标 -->
<link rel="stylesheet" href="${baseURL}/resources/fonts/Font-Awesome-master/css/font-awesome.min.css">

<!-- AdminLTE 主题样式 -->
<link rel="stylesheet" href="${baseURL}/resources/css/AdminLTE.css">
<link rel="stylesheet" href="${baseURL}/resources/css/skins/_all-skins.css">

<!-- 插件 -->
<link rel="stylesheet" href="${baseURL}/resources/plugins/iCheck/flat/blue.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/morris/morris.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/metisMenu/css/metisMenu.min.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/sweetalert/css/sweetalert.css" />
<link rel="stylesheet" href="${baseURL}/resources/plugins/ztree3/css/zTreeStyle/zTreeStyle.css"  >
<link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-file-upload/css/fileinput.min.css">
<link rel="stylesheet" href="${baseURL}/resources/plugins/bootstrap/bootstrap-switch/css/bootstrap3/bootstrap-switch.min.css">

<link rel="stylesheet" href="${baseURL}/resources/css/style.css">

<!-- 建议将js放到最后, 因为浏览器在遇到body标签之前,不会渲染任何界面,可将下载js文件的操作放到body后面,提高用户体验 -->
<!-- jQuery 2.1.4 -->
<script src="${baseURL}/resources/plugins/jquery/jquery2.1.4/jquery-2.1.4.js"></script>

<!-- jquery 插件-->
<script src="${baseURL}/resources/plugins/jquery/jquery-serialize-object-2.4.0/jquery.serialize-object.min.js"></script>
<script src="${baseURL}/resources/plugins/jquery/jquery-form/jquery.form.min.js"></script>

<!-- Bootstrap 3.3.5 -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-3.3.5/js/bootstrap.js"></script>
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-validation/bootstrap3-validation.js"></script>

<!-- Morris.js charts -->
<script src="${baseURL}/resources/plugins/morris/morris.min.js"></script>

<!-- Sparkline -->
<script src="${baseURL}/resources/plugins/sparkline/jquery.sparkline.min.js"></script>

<!-- jvectormap -->
<script src="${baseURL}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${baseURL}/resources/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>

<!-- jQuery Knob Chart -->
<script src="${baseURL}/resources/plugins/knob/jquery.knob.js"></script>

<!-- datepicker -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-datetimepicker/js/moment-with-locales.js"></script>
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-datetimepicker/js/moment-locale_zh-CN.js"></script>
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

<!-- Bootstrap WYSIHTML5 -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>

<!-- Slimscroll -->
<script src="${baseURL}/resources/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- FastClick -->
<script src="${baseURL}/resources/plugins/fastclick/fastclick.min.js"></script>

<!-- AdminLTE App -->
<script src="${baseURL}/resources/js/app.js"></script>
<script src="${baseURL}/resources/plugins/metisMenu/js/metisMenu.min.js"></script>

<!-- 弹出框组件 -->
<script src="${baseURL}/resources/plugins/bootbox/bootbox.js"></script>
<script src="${baseURL}/resources/plugins/sweetalert/js/sweetalert.js"></script>

<!-- 金额格式化 -->
<script src="${baseURL}/resources/plugins/accounting/accounting.js"></script>

<!-- 扩展jquery 全局$对象,为其添加一些全局的对象,方法 -->
<script src="${baseURL}/resources/js/extend/jquery.dialog.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.msg.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.string.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.tree.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.validate.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.date.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.array.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.format.extend.js"></script>
<script src="${baseURL}/resources/js/extend/jquery.dictionary.extend.js"></script>

<!-- 扩展jquery 每个实例对象,为自身添加一些方法 -->
<script src="${baseURL}/resources/js/fnExtend/jquery.fileUpload.fn.extend.js"></script>
<script src="${baseURL}/resources/js/fnExtend/jquery.string.fn.extend.js"></script>
<script src="${baseURL}/resources/js/fnExtend/jquery.loadData.fn.extend.js"></script>

<!-- ztree -->
<script src="${baseURL}/resources/plugins/ztree3/js/jquery.ztree.all.min.js" ></script>

<!-- ajaxfileupload -->
<script src="${baseURL}/resources/plugins/ajax/ajaxfileupload.js" ></script>

<!-- bootstrap table -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<!-- bootstrap file upload -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-file-upload/js/fileinput.js"></script>
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-file-upload/js/locales/zh.js"></script>

<!-- bootstrap switch -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-switch/js/bootstrap-switch.js"></script>

<!-- bootstrap 自动搜索 -->
<script src="${baseURL}/resources/plugins/bootstrap/bootstrap-typeahead/bootstrap3-typeahead.min.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

