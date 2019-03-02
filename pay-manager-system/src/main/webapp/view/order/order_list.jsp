<%@page language="java" contentType="text/html; charset=UTF-8" %>


<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">订单管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchOrderForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <td width="80" align="right">商户订单：</td>
                            <td width="150">
                                <input type="text" name="merchantOrderNo" id="queryMerchantOrderNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">平台订单：</td>
                            <td width="150">
                                <input type="text" name="platformOrderNo" id="queryPlatformOrderNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">支付单号：</td>
                            <td width="150">
                                <input type="text" name="payCode" id="queryPayCode" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">所属代理：</td>
                            <td width="150">
                                <select name="agentId" id="queryAgentId" class="form-control btn-block">
                                </select>
                            </td>

                            <td width="80" align="right">所属商户：</td>
                            <td width="150">
                                <select name="merchantId" id="queryMerchantId" class="form-control btn-block">
                                </select>
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchOrderForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                        <tr>

                            <td width="80" align="right">商家编号：</td>
                            <td width="150">
                                <input type="text" name="merchantNo" id="queryMerchantNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">支付方式：</td>
                            <td width="150">
                                <select name="payWay" class="form-control" id="queryPayWay" class="form-control btn-block" aria-describedby="basic-addon1">
                                </select>
                            </td>

                            <td width="80" align="right">支付状态：</td>
                            <td width="150">
                                <select name="payStatus" class="form-control" id="queryPayStatus" class="form-control btn-block" aria-describedby="basic-addon1">
                                    <option value="">请选择</option>
                                    <option value="waitPay">待支付</option>
                                    <option value="payed">已支付</option>
                                    <option value="payFail">支付失败</option>
                                </select>
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <%--<button id="add" class="btn btn-success" onclick="pageScope.exportOrder()">--%>
                        <%--<i class="glyphicon glyphicon-plus"></i> 导出--%>
                    <%--</button>--%>
                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="orderTable"></table>
                    <div id="orderPager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/order/order.js"></script>
<script type="text/javascript">

    $(function () {

        $("#queryAgentId").loadAgentIdAndNameList();            //加载代理

        //级联操作
        $("#queryAgentId").change(function(){
            var value = $(this).val();
            $("#queryMerchantId").loadMerchantIdAndNameList({agentId:value});
        });

        //加载字典(下拉框)
        $("#queryPayWay").loadDictionaryForSelect({
            "dictType":"payWay",
            "value":""
        });

    });

</script>