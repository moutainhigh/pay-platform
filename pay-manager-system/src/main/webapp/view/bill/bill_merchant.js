var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    queryMerchantIdAndNameList();

    function queryMerchantIdAndNameList() {
        $.ajax({
            type: "post",
            url: baseURL + "/merchant/queryMerchantIdAndNameList?_csrf=" + token,
            dataType: "json",
            success: function (response) {
                if (response && response.success == true) {
                    var str = "";

                    for (var i = 0; i < response.merchantIdList.length; i++) {
                        str += "  <option  value='" + response.merchantIdList[i].id + "'>" + response.merchantIdList[i].merchant_name + " </option> ";
                    }
                    $("#merchantId").html(str);

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
            url: baseURL + '/bill/merchant/queryMerchantEveryDayBill?_csrf=' + token,
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
                {title: '商家编号', field: 'merchantNo', align: 'center', sortable: true},
                {
                    title: '日期', field: 'create_time', align: 'center', sortable: true,
                    formatter: function (value) {
                        return $.date.formatToDate(value);
                    }
                },
                {title: '收款总金额(元)', field: 'day_Order_Amount', align: 'center', sortable: true},
                {title: '实收金额(元)', field: 'day_actual_amount', align: 'center', sortable: true},
                {title: '交易手续费(元)', field: 'day_Handling_Fee', align: 'center', sortable: true},
                {title: '通道总收入(元)', field: 'day_channel_amount', align: 'center', sortable: true},
                {title: '平台总收入(元)', field: 'day_platform_amount', align: 'center', sortable: true},
                {title: '代理总收入(元)', field: 'day_agent_amount', align: 'center', sortable: true},
            ], onLoadSuccess: function () {

                if (roleCode == "ROLE_AGENT") {
                    pageScope.billTable.bootstrapTable('hideColumn', 'day_channel_amount');
                    pageScope.billTable.bootstrapTable('hideColumn', 'day_platform_amount');
                } else if (roleCode == "ROLE_MERCHANT") {
                    pageScope.billTable.bootstrapTable('hideColumn', 'day_channel_amount');
                    pageScope.billTable.bootstrapTable('hideColumn', 'day_platform_amount');
                    pageScope.billTable.bootstrapTable('hideColumn', 'day_agent_amount');
                }

            }
        });

    }

    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.billTable.bootstrapTable('refresh');
    };

})();