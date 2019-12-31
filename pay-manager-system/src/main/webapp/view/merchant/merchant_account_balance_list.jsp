<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=UTF-8" %>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">商家账户余额</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchmerchantAccountBalanceForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>

                            <td width="80" align="right">商家编号：</td>
                            <td width="150">
                                <input type="text" name="merchantNo" id="querymerchantAccountBalanceNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">商户名称：</td>
                            <td width="150">
                                <input type="text" name="merchantName" id="querymerchantAccountBalanceName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchmerchantAccountBalanceForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="merchantAccountBalanceTable"></table>
                    <div id="merchantAccountBalancePager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript">

    var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

    $(function () {

        pageScope.merchantAccountBalanceTable = $('#merchantAccountBalanceTable').initBootstrapTable({
            url: baseURL + '/merchant/queryMerchanAccountBalancetList?_csrf=' + token,
            method: 'post',
            dataType: "json",
            toolbar: '#merchantAccountBalancePager',
            queryParams: function (params) {
                $.extend(params, $('#searchmerchantAccountBalanceForm').serializeObject());
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
                {title: '商家编号', field: 'merchantNo', align: 'center', sortable: true,},
                {title: '商户名称', field: 'merchantName', align: 'center', sortable: true},
                {
                    title: '账户余额', field: 'accountAmount', align: 'center', sortable: true, formatter: function (value) {
                    return value.toFixed(2);
                }
                },
                {
                    title: '冻结余额', field: 'freezeAmount', align: 'center', sortable: true, formatter: function (value) {
                    return value.toFixed(2);
                }
                },
                {
                    title: '可提现金额', field: 'avaiabledWithdrawAmount', align: 'center', sortable: true, formatter: function (value) {
                    return value.toFixed(2);
                }
                }
            ]
        });

        /**
         * 查询
         */
        pageScope.search = function () {
            pageScope.merchantAccountBalanceTable.bootstrapTable('refresh');
        };

    });

</script>