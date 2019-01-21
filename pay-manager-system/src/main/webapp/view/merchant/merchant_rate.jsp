<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">费率</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="reviewMerchantForm" action="${baseURL}/merchant/" class="form-horizontal"  method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="merchantId" name="merchantId"/>
            <input type="hidden" id="rate"/>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">通道：</label>
                <div class="col-md-8 col-sm-8">
                    <select class="form-control" name="channelId" id="channel" onchange="selectChange(this)">
                    </select>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-3 col-sm-3 control-label">费率：</label>
                <div class="col-md-8 col-sm-8">
                    <input id="costRate" name="costRate" type="text" class="form-control" check-type="required number" range="0~1" placeholder="请输入成本费率(千分之5,请输入0.005)"/>
                </div>
            </div>

        </form>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script>


    $(function () {

        $('#reviewMerchantForm').validation();            //表单验证初始化

    });

    /**
     * 改变费率选择
     * @param channelCode
     */
    function selectChange(obj) {
        var rate =  $("#channel").find("option:selected").attr("rate");
        $("#rate").val(rate);
    }
</script>