<%--
Created by IntelliJ IDEA.
User: zjt
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
    <h4 class="modal-title">批量导入收款码</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <form id="importTradeCodeForm" action="${baseURL}/loopMgr/tradeCode/importTradeCodeByExcel" class="form-horizontal" method="post">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input type="hidden" name="merchantId" id="merchantId" value="${param.merchantId}"/>
            <input type="hidden" name="channelCode" id="channelCode" value="${param.channelCode}"/>

            <p style="color: red;font-weight: bold; font-size: 18px;">请点击选择按钮,上传excel</p>
            <div>
                <input id="uploadExcelFile" name="uploadExcelFile" type="file" data-show-preview="false" class="file"/>
                <input type="hidden" name="excelPath" id="saveFilePath"/>
            </div>

        </form>

        <!-- 数据表格 -->
        <div class="data-table-wrapper">
            <table id="importTradeCodeTable"></table>
            <div id="importTradeCodePager"></div>
        </div>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

        $('#importTradeCodeForm').validation();            //表单验证初始化

        /**
         * 上传excel
         */
        $("#uploadExcelFile").multiDocUpload({
            uploadExtraData: {"module": "excel"},
            showCaption: true,
            selectAutoUpload: true,
            onSuccess: function (data) {

                if (data.response && data.response.success) {
                    var saveFilePath = data.response.data[0];
                    $("#saveFilePath").val(saveFilePath);

                    //刷新表格,显示上传后的内容
                    pageScope.importTradeCodeTable.bootstrapTable('refresh');

                }

            }
        });

        /**
         * 表格初始化-显示上传后的内容
         * @type {*}
         */
        pageScope.importTradeCodeTable = $('#importTradeCodeTable').initBootstrapTable({
            url: baseURL + '/loopMgr/tradeCode/getImportTradeCodeExcelList?_csrf=' + token,
            method: 'post',
            dataType: "json",
            toolbar: '#importTradeCodeForm',
            pageSize: 500,
            pagination : true,                  //是否分页
            queryParams: function (params) {
                $.extend(params, $('#importTradeCodeForm').serializeObject());
                return params;
            },
            onClickRow: function (row, tr) {
                pageScope.currentrow = row;
            },
            responseHandler: function (response) {
                var griddata = {};
                try {
                    griddata.rows = response.rows || [];
                    griddata.total = response.total || 0;
                } catch (e) {
                }
                return griddata;
            },
            columns: [
                {title: '全选', checkbox: true},
                {
                    title: '序号',
                    align: 'center',
                    width: 46,
                    formatter: function () {
                        return arguments[2] + 1;
                    }
                },
                {title: '编号', field: '编号', align: 'center', sortable: true},
                {title: '回调密钥', field: '回调密钥', align: 'center', sortable: true},
                {title: '登录账号', field: '登录账号', align: 'center', sortable: true},
                {title: '收款码链接', field: '收款链接', align: 'center', sortable: true},
                {
                    title: '备注',
                    field: 'exists',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == true || value == "true") {
                            return "<span style='color: red;'>" + row.msg + "</span>";
                        } else {
                            return "<span style='color: green;'>" + row.msg + "</span>";
                        }
                    }
                }
            ]
        });

    });

</script>