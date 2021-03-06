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
    <h4 class="modal-title">编辑商家</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editMerchantForm" action="${baseURL}/merchant/updateMerchant" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="editId" name="id"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">商户名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editMerchantName" name="merchantName" check-type="required"
                           type="text" class="form-control" maxlength="255" placeholder="请输入商户名称"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手机号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editPhone" name="phone" check-type="required"
                           type="text" class="form-control" maxlength="255" placeholder="请输入手机号"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">所属代理：</label>
                <div class="col-md-8 col-sm-8">
                    <select name="agentId" id="editAgentId" class="form-control btn-block" check-type="required">
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">开启提现通知：</label>
                <div class="col-md-8 col-sm-8 form-inline">
                    <label class="radio-inline">
                        <input type="radio" name="needNotifyWithdraw" value="1">开启
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="needNotifyWithdraw" value="0">关闭
                    </label>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">真实姓名：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editRealName" name="realName"
                           type="text" class="form-control" maxlength="255"  placeholder="请输入真实姓名"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证号码：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="editIdentityCode" name="identityCode"
                           type="text" class="form-control" maxlength="255" placeholder="请输入身份证号码"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证-正面：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="kv-avatar">
                        <!-- 上传文件表单组件 -->
                        <input id="fileIdCardImg1" type="file" class="file-loading" />
                    </div>
                </div>
                <!-- 隐藏域,保存上传文件的路径  -->
                <input id="editIdCardImg1" name="idCardImg1" type="hidden"/>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证-反面：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="kv-avatar">
                        <!-- 上传文件表单组件 -->
                        <input id="fileIdCardImg2" type="file" class="file-loading"/>
                    </div>
                </div>
                <!-- 隐藏域,保存上传文件的路径  -->
                <input id="editIdCardImg2" name="idCardImg2" type="hidden"/>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">icp证件：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="kv-avatar">
                        <!-- 上传文件表单组件 -->
                        <input id="fileIcpImg" type="file" class="file-loading"/>
                    </div>
                </div>
                <!-- 隐藏域,保存上传文件的路径  -->
                <input id="editIcpImg" name="icpImg" type="hidden"/>
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

        $('#editMerchantForm').validation();            //表单验证初始化

    });

</script>