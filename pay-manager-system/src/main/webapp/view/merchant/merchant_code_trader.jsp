<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">绑定码商</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <input type="hidden" id="merchantId" name="merchantId"/>

        <!-- 数据表格 -->
        <div class="data-table-wrapper">
            <table id="codeTraderTable"></table>
            <div id="codeTraderPager"></div>
        </div>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>
<script type="text/javascript">

    function queryAllCodeTraderByMerchantId() {

        pageScope.codeTraderTable = $('#codeTraderTable').initBootstrapTable({
            url: baseURL + '/codeTrader/queryAllCodeTraderByMerchantId?_csrf=' + token + "&merchantId=" + $("#merchantId").val(),
            method: 'post',
            dataType: "json",
            toolbar: '#codeTraderPager',
            queryParams: function (params) {
                return params;
            },
            onClickRow: function (row, tr) {
                pageScope.currentrow = row;
            },
            responseHandler: function (response) {
                var griddata = {};
                try {
                    griddata.rows = response.list || [];
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
                {title: '码商名称', field: 'name', align: 'center', sortable: true},
                {
                    title: '绑定状态',
                    field: 'isExists',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        if (parseInt(value) > 0) {
                            return "已绑定";
                        } else {
                            return "未绑定";
                        }
                    }
                },
                {
                    title: '创建时间',
                    field: 'create_time',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return $.date.formatToDateTime(value);
                    }
                }
                , {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var html = "";

                        console.log(row.isExists);

                        if (parseInt(row.isExists) > 0) {
                            html += "<button type='button' class='btn btn-link' onclick='deleteMerchantCodeTrader(\"" + row.id + "\")' >取消绑定</button>";
                        } else {
                            html += "<button type='button' class='btn btn-link' onclick='addMerchantCodeTrader(\"" + row.id + "\")' >绑定码商</button>";
                        }

                        return html;
                    }
                }
            ]
        });

    }

    function addMerchantCodeTrader(id) {

        var merchantId = $("#merchantId").val();

        $.ajax({
            url: baseURL + "/codeTrader/addMerchantCodeTrader",
            type: "post",
            dataType: "json",
            data: {"codeTraderId": id,"merchantId": merchantId, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    pageScope.codeTraderTable.bootstrapTable('refresh');
                } else {
                    $.msg.fail(response.msg);
                }

            }
        });

    }

    function deleteMerchantCodeTrader(id) {

        var merchantId = $("#merchantId").val();

        $.ajax({
            url: baseURL + "/codeTrader/deleteMerchantCodeTrader",
            type: "post",
            dataType: "json",
            data: {"codeTraderId": id,"merchantId": merchantId, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    pageScope.codeTraderTable.bootstrapTable('refresh');
                } else {
                    $.msg.fail(response.msg);
                }

            }
        });

    }

</script>