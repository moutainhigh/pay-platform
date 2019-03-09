<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">设置费率</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <div class="modal-footer operation-button">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>渠道</th>
                    <th>费率</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="rateList">


                <%--<td>.active</td>--%>
                <%--<td>表示当前活动的信息</td>--%>
                <%--<td><button>删除</button></td>--%>
                </tbody>
            </table>
        </div>


        <form id="reteMerchantForm" action="${baseURL}/merchant/addMerchantRate" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="merchantId" name="merchantId"/>

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
                    <input id="rate" name="rate" type="text" class="form-control" check-type="required" value="0.00%" />
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

        $('#reteMerchantForm').validation();            //表单验证初始化

    });

    /**
     * 删除费率
     * @param id
     */
    function deleteMerchantRate(id) {

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/merchant/deleteMerchantRate",
                type: "post",
                dataType: "json",
                data: {"_csrf": token, "id": id},
                success: function (response) {
                    if (response && response.success == true) {
                        //删除当前行
                        $("#" + id).remove();
                        $.msg.success('成功');
                    } else {
                        $.msg.error('删除费率失败，可能是由网络原因引起的，请稍候再试');
                    }

                }
            });

        });

    }

    /**
     * 改变费率选择
     * @param channelCode
     */
    function selectChange(obj) {
        var rate = $("#channel").find("option:selected").attr("rate");
        $("#costRate").val(rate);
    }

</script>