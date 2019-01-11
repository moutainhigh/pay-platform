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
    <h4 class="modal-title">查看日志</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form  class="form-horizontal" >

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">操作用户：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailUserName" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">IP地址：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailIp" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">操作模块：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailModule" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">操作动作：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailOperation" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">描述信息：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailDescription" ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">操作结果：</label>
                <div class="col-md-8 col-sm-8 detail-content" id="detailStatus"  ></div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">创建时间：</label>
                <div class="col-md-8 col-sm-8 detail-content"  id="detailCreateTime" ></div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button" >
    <button data-bb-handler="cancel" type="button" class="btn btn-danger" >关闭</button>
</div>