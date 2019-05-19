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
    <h4 class="modal-title">编辑收款码</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editTradeCodeForm" action="${baseURL}/loopMgr/tradeCode/updateTradeCode" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="editId" name="id"/>

            <input type="hidden" name="channelCode" id="channelCode" value="${param.channelCode}" />

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">交易码的编号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editCodeNum" name="codeNum" readonly check-type="required"
                           type="text" class="form-control" maxlength="50" placeholder="请输入交易码的编号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">设备回调密钥：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editSecret" name="secret" check-type="required"
                           type="text" class="form-control" readonly maxlength="30" placeholder="请输入设备回调密钥"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editLoginAccount" name="loginAccount" check-type="required"
                           type="text" class="form-control" maxlength="50" placeholder="请输入登录账号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">收款码链接：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editCodeLink" name="codeLink" check-type="required"
                           type="text" class="form-control" maxlength="65535" placeholder="请输入收款码链接"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">最小金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editMinAmount" name="minAmount" check-type="required number"
                           type="text" class="form-control" maxlength="20" placeholder="请输入最小金额"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">最大金额：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editMaxAmount" name="maxAmount" check-type="required number"
                           type="text" class="form-control" maxlength="20" placeholder="请输入最大金额"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">单日收款金额限制：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editDayAmountLimit" name="dayAmountLimit" check-type="required number"
                           type="text" class="form-control" maxlength="20" placeholder="请输入单日收款金额限制"/>
                </div>
            </div>

            <%--<div class="form-group">--%>
                <%--<label class="col-md-3 col-sm-3 control-label">支付宝PID：</label>--%>
                <%--<div class="col-md-8 col-sm-8">--%>
                    <%--<input id="editZfbUserId" name="zfbUserId" check-type="required"--%>
                           <%--type="text" class="form-control" maxlength="255" placeholder="请输入支付宝PID"/>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
                <%--<label class="col-md-3 col-sm-3 control-label">号主姓名：</label>--%>
                <%--<div class="col-md-8 col-sm-8">--%>
                    <%--<input id="editRealName" name="realName" check-type="required"--%>
                           <%--type="text" class="form-control" maxlength="50" placeholder="请输入号主姓名"/>--%>
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

        $('#editTradeCodeForm').validation();            //表单验证初始化

    });

</script>