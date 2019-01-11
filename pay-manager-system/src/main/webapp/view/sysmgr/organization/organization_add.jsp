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
    <h4 class="modal-title">新增机构</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addOrganizationForm" action="${baseURL}/sysmgr/organization/addOrganization" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}" />
            <input type="hidden" name="parentId" value="${param.parentId}" />
            <input type="hidden" name="level" value="${param.level}" />

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">机构名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="name" type="text" class="form-control" check-type="required" placeholder="请输入公司/机构名称" maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">联系人：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="contactPerson" type="text" class="form-control" placeholder="请输入联系人" maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">联系电话：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="tel" type="text" class="form-control" placeholder="请输入联系电话" maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通信地址：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="address" type="text" class="form-control" placeholder="请输入通信地址" maxlength="200" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">邮箱：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="email" type="text" class="form-control" placeholder="请输入邮箱" maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">邮编：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="postCode" type="text" class="form-control" placeholder="请输入邮编" maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">传真：</label>
                <div class="col-md-8 col-sm-8">
                    <input name="fax" type="text" class="form-control" placeholder="请输入传真" maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">备注：</label>
                <div class="col-md-8 col-sm-8">
                    <textarea name="description" class="form-control" rows="3" placeholder="请输入备注" maxlength="500" ></textarea>
                </div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button" >
    <button data-bb-handler="success" type="button" class="btn btn-success" >保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger" >取消</button>
</div>

<script type="text/javascript" >

    $(function(){
        $('#addOrganizationForm').validation();
    });

</script>