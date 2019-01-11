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
    <h4 class="modal-title">用户详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="detailUserForm" action="${baseURL}/sysmgr/user/updateUser" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" id="detailId" />

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">用户昵称：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailNickName" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">登录账号：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailAccount" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手机号码：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailPhone" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">组织机构：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailOrgName" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">用户头像：</label>
                <div class="col-md-8 col-sm-8 detail-content">
                    <div class="kv-avatar"  >
                        <input id="file" name="file" type="file" class="file-loading">
                    </div>
                </div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>