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
    <h4 class="modal-title">新增通道</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addPayChannelForm" action="${baseURL}/payChannel/addPayChannel" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通道编码：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addChannelCode" name="channelCode" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入通道编码"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通道名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addChannelName" name="channelName" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入通道名称"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">成本费率：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addCostRate" name="costRate" type="text" class="form-control" check-type="required" maxlength="10" placeholder="请输入成本费率"/>
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

        $('#addPayChannelForm').validation();            //表单验证初始化


    });

</script>