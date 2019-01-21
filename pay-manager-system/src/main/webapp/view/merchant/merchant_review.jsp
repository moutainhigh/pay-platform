<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">商家详情</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="reviewMerchantForm" action="${baseURL}/merchant/review" class="form-horizontal"  method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="reviewId" name="id"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">状态：</label>
                <div class="col-md-8 col-sm-8">
                    <select class="form-control" name="checkStatus">
                        <option value="success">通过</option>
                        <option value="fail">拒绝</option>
                    </select>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">备注：</label>
                <div class="col-md-8 col-sm-8">
                    <textarea id="checkDesc" name="checkDesc" type="text" class="form-control"/>
                </div>
            </div>



        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>