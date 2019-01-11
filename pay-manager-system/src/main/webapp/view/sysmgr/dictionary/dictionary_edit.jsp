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
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">
        <c:if test="${param.level == 1}">编辑字典</c:if>
        <c:if test="${param.level == 2}">编辑分类</c:if>
    </h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="editDictionaryForm" action="${baseURL}/sysmgr/dictionary/updateDictionary" class="form-horizontal">

            <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}" />
            <input type="hidden" name="id" value="${param.id}" />
            <input type="hidden" name="level" value="${param.level}" />

            <%-- 一级为字典/二级代表分类 --%>
            <c:if test="${param.level == 1}">

                <div class="form-group">
                    <label class="col-md-3 col-sm-3 control-label">字典描述：</label>
                    <div class="col-md-8 col-sm-8">
                        <input name="dictDesc" value="${param.dictDesc}" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入字典描述" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 col-sm-3 control-label">字典类型：</label>
                    <div class="col-md-8 col-sm-8">
                        <input name="dictType" value="${param.dictType}" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入字典类型" />
                    </div>
                </div>

            </c:if>

            <c:if test="${param.level == 2}">

                <input type="hidden" name="dictDesc" value="${param.dictDesc}" />
                <input type="hidden" name="dictType" value="${param.dictType}" />

                <div class="form-group">
                    <label class="col-md-3 col-sm-3 control-label">分类描述：</label>
                    <div class="col-md-8 col-sm-8">
                        <input name="itemDesc" value="${param.itemDesc}" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入分类描述" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 col-sm-3 control-label">分类类型：</label>
                    <div class="col-md-8 col-sm-8">
                        <input name="itemCode" value="${param.itemCode}" type="text" class="form-control" check-type="required" maxlength="50" placeholder="请输入分类类型" />
                    </div>
                </div>

            </c:if>

        </form>

    </div>
</div>

<div class="modal-footer operation-button" >
    <button data-bb-handler="success" type="button" class="btn btn-success"  >保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger" >取消</button>
</div>

<script type="text/javascript" >

    $(function(){
        $('#editDictionaryForm').validation();
    });

</script>