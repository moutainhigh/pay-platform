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
    <h4 class="modal-title">新增代理</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="addAgentForm" action="${baseURL}/agent/addAgent" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">代理级别：</label>
                <div class="col-md-8 col-sm-8">
                    <select class="form-control" id="addLevel" name="level">
                        <option value="1">一级代理</option>
                        <option value="2">二级代理</option>
                    </select>
                </div>
            </div>

            <div class="form-group" id="divParentId" style="display: none;">
                <label class="col-md-3 col-sm-3 control-label">上级代理：</label>
                <div class="col-md-8 col-sm-8">
                    <select class="form-control" id="addParentId" name="parentId" >
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">代理名称：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addAgentName" name="agentName" type="text" class="form-control" check-type="required" maxlength="255" placeholder="请输入代理名称"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">手机号：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addPhone" name="phone" type="text" class="form-control" check-type="required" maxlength="255" placeholder="请输入手机号"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">真实姓名：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addRealName" name="realName" type="text" class="form-control" maxlength="255" placeholder="请输入真实姓名"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证号码：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="addIdentityCode" name="identityCode" type="text" class="form-control" maxlength="255" placeholder="请输入身份证号码"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证-正面：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="kv-avatar">
                        <!-- 上传文件表单组件 -->
                        <input id="fileIdCardImg1" type="file" class="file-loading">
                    </div>
                </div>
                <!-- 隐藏域,保存上传文件的路径  -->
                <input id="addIdCardImg1" name="idCardImg1" type="hidden"/>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">身份证-反面：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="kv-avatar">
                        <!-- 上传文件表单组件 -->
                        <input id="fileIdCardImg2" type="file" class="file-loading">
                    </div>
                </div>
                <!-- 隐藏域,保存上传文件的路径  -->
                <input id="addIdCardImg2" name="idCardImg2" type="hidden"/>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">icp证件：</label>
                <div class="col-md-8 col-sm-8">
                    <div class="kv-avatar">
                        <!-- 上传文件表单组件 -->
                        <input id="fileIcpImg" type="file" class="file-loading">
                    </div>
                </div>
                <!-- 隐藏域,保存上传文件的路径  -->
                <input id="addIcpImg" name="icpImg" type="hidden"/>
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

        $('#addAgentForm').validation();            //表单验证初始化

        //单图片上传 - 身份证-正面
        $("#fileIdCardImg1").singleImageUpload({
            uploadExtraData: {"module": "/agent"},                     //上传图片扩展参数,指定所属模块
            hiddenField: "#addIdCardImg1",                   //返回隐藏域路径
        });

        //单图片上传 - 身份证-反面
        $("#fileIdCardImg2").singleImageUpload({
            uploadExtraData: {"module": "/agent"},                     //上传图片扩展参数,指定所属模块
            hiddenField: "#addIdCardImg2",                   //返回隐藏域路径
        });

        //单图片上传 - icp证件
        $("#fileIcpImg").singleImageUpload({
            uploadExtraData: {"module": "/agent"},                     //上传图片扩展参数,指定所属模块
            hiddenField: "#addIcpImg",                   //返回隐藏域路径
        });


        //代理级别改变事件
        $("#addLevel").change(function () {

            var level = $(this).find("option:selected").val();

            if (level === "1" || parseInt(level) == 1) {
                $("#divParentId").hide();

                $("#addParentId").find("option").removeAttr("selected");
                $("#addParentId").find("option:eq(0)").attr("selected" , "selected");

                $("#addParentId").removeAttr("check-type");

            } else {
                $("#addParentId").attr("check-type" , "required");

                $("#divParentId").show();
            }

        });

        queryOneLevelAgent();

    });

    /**
     * 查询一级代理
     */
    function queryOneLevelAgent(){

        $.ajax({
            type: "post",
            url: baseURL + "/agent/queryOneLevelAgent?_csrf=" + token,
            dataType: "json",
            success: function (response) {
                if (response && response.success == true) {
                    var str = "<option value=''>请选择</option>";
                    for (var i = 0; i < response.data.length; i++) {
                        str += "  <option  value='" + response.data[i].id + "'>" + response.data[i].agentName + " </option> ";
                    }
                    $("#addParentId").html(str);
                } else {
                    return false;
                }
            },
            error: function () {
                return false;
            }
        });

    }

</script>