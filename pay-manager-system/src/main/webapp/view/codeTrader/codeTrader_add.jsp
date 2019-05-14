<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>


<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">新增码商</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addCodeTraderForm" action="${baseURL}/codeTrader/addCodeTrader" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">码商名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addName" name="name" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入码商名称"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addAccount" name="account" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入登录账号"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录密码：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addPassword" name="password" type="password" class="form-control" check-type="required" maxlength="50" placeholder="请输入登录密码"/>
                </div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#addCodeTraderForm').validation();            //表单验证初始化


    });

</script>