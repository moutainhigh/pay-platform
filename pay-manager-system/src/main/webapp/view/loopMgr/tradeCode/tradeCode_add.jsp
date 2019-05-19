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
    <h4 class="modal-title">新增收款码</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addTradeCodeForm" action="${baseURL}/loopMgr/tradeCode/addTradeCode" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <%-- 商家id、通道编码 --%>
            <input type="hidden" name="merchantId" id="merchantId" value="${param.merchantId}" />
            <input type="hidden" name="channelCode" id="channelCode" value="${param.channelCode}" />

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">交易码的编号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addCodeNum" name="codeNum" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入交易码的编号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">设备回调密钥：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addSecret" name="secret" type="text" class="form-control" check-type="required" maxlength="30" minlength="8"  placeholder="请输入设备回调密钥"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addLoginAccount" name="loginAccount" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入登录账号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">收款码链接：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addCodeLink" name="codeLink" type="text" class="form-control" check-type="required" maxlength="65535" placeholder="请输入收款码链接"/>
                </div>
            </div>

            <%--<div class="form-group">--%>
                <%--<label class="col-md-3 col-sm-3 control-label">号主姓名：</label>--%>
                <%--<div class="col-md-8 col-sm-8">--%>
                    <%--<input id="addRealName" name="realName" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入号主姓名"/>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
                <%--<label class="col-md-3 col-sm-3 control-label">支付宝PID：</label>--%>
                <%--<div class="col-md-8 col-sm-8">--%>
                    <%--<input id="addZfbUserId" name="zfbUserId" type="text" class="form-control" check-type="required" maxlength="255" placeholder="请输入支付宝PID"/>--%>
                <%--</div>--%>
            <%--</div>--%>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#addTradeCodeForm').validation();            //表单验证初始化

    });

</script>