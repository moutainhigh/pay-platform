<%--
Created by IntelliJ IDEA.
User:
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
    <h4 class="modal-title">编辑手机校验配置</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editPhoneConfigForm" action="${baseURL}/phoneConfig/updatePhoneConfig" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="editId" name="id"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">操作类型：</label>
                <div class="col-md-8 col-sm-8">
                    <select id="editType" name="type" class="form-control"></select>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">使用顺序：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editSequence" name="sequence" check-type="required"
                           type="text" class="form-control" maxlength="10" placeholder="请输入使用顺序"/>
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

        $('#editPhoneConfigForm').validation();            //表单验证初始化

    });

</script>