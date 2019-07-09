var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    queryAgentIdAndNameList();

    function queryAgentIdAndNameList() {
        $.ajax({
            type: "post",
            url: baseURL + "/agent/queryAgentIdAndNameList?_csrf=" + token,
            dataType: "json",
            success: function (response) {
                if (response && response.success == true) {
                    var str = "<option value=''>请选择</option>";

                    for (var i = 0; i < response.agentIdList.length; i++) {
                        str += "  <option  value='" + response.agentIdList[i].id + "'>" + response.agentIdList[i].agentName + " </option> ";
                    }
                    $("#agentId").html(str);

                    loadBillTale();

                } else {
                    btn.removeAttr("disabled");
                    $.msg.fail(response.msg);
                    return false;
                }
            },
            error: function () {
                return false;
            }
        });
    }

    function loadBillTale() {

        pageScope.billTable = $('#billTable').initBootstrapTable({
            url: baseURL + '/bill/queryAgentProfit?_csrf=' + token,
            method: 'post',
            dataType: "json",
            toolbar: '#billPager',
            queryParams: function (params) {
                $.extend(params, $('#searchBillForm').serializeObject());
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
                {title: '代理名称', field: 'agent_name', align: 'center', sortable: true},
                {title: '商家名称', field: 'merchant_name', align: 'center', sortable: true},
                {title: '平台单号', field: 'platform_order_no', align: 'center', sortable: true},
                {title: '商家单号', field: 'merchant_order_no', align: 'center', sortable: true},
                {
                    title: '订单金额(元)', field: 'order_amount', align: 'center', sortable: true,
                    formatter: function (value) {
                        return value.toFixed(2);
                    }
                },
                {
                    title: '分润金额(元)', field: 'amount', align: 'center', sortable: true,
                    formatter: function (value) {
                        return value.toFixed(3);
                    }
                },
                {
                    title: '日期', field: 'create_time', align: 'center', sortable: true,
                    formatter: function (value) {
                        return $.date.formatToDateTime(value);
                    }
                },
            ], onLoadSuccess: function () {

            }
        });

    }

    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.billTable.bootstrapTable('refresh');
        pageScope.queryTotalAgentProfit();
    };

    /**
     * 统计分润信息
     */
    pageScope.queryTotalAgentProfit = function () {

        var agentId = $("#agentId").find("option:selected").val();
        var beginTime = $("#beginTime").val();
        var endTime = $("#endTime").val();
        var merchantName = $("#queryMerchantName").val();
        var platformOrderNo = $("#queryPlatformOrderNo").val();
        var merchantOrderNo = $("#queryMerchantOrderNo").val();

        $.ajax({
            url: baseURL + "/bill/queryTotalAgentProfit",
            type: "post",
            dataType: "json",
            data: {
                "agentId": agentId,
                "merchantName": merchantName,
                "beginTime": beginTime,
                "endTime": endTime,
                "platformOrderNo": platformOrderNo,
                "merchantOrderNo": merchantOrderNo,
                "_csrf": token
            },
            success: function (response) {

                if (response && response.success == true) {

                    console.log(JSON.stringify(response.data));

                    if (response.data.totalOrderAmount) {
                        $("#totalPayAmount").html(parseFloat(response.data.totalOrderAmount).toFixed(3));
                        $("#totalProfitAmount").html(parseFloat(response.data.totalProfitAmount).toFixed(3));
                    } else {
                        $("#totalPayAmount").html("0.00");
                        $("#totalProfitAmount").html("0.00");
                    }

                } else {
                    $.msg.fail(response.msg);
                }

            }
        });

    }

})();