<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">新增角色</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editRoleForm" action="${baseURL}/sysmgr/role/updateRole" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" id="editRoleId" />

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">角色名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="name" id="editName" type="text" class="form-control" check-type="required" maxlength="30" placeholder="请输入角色名称"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">角色码：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="code" id="editCode" type="text" class="form-control" check-type="required" maxlength="30" placeholder="请输入角色码"/>
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
        $('#editRoleForm').validation();
    });

</script>
