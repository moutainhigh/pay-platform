var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

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
            {title: '商家名称', field: 'merchantName', align: 'center', sortable: true},
            {
                title: '日期', field: 'create_time', align: 'center', sortable: true,
                formatter: function (value) {
                    var statisticsWay = $("#queryStatisticsWay").val();
                    if ("day" == statisticsWay) {
                        return $.date.formatToDate(value);
                    } else if ("timeLine" == statisticsWay) {
                        return $("#beginTime").val() + " - " + $("#endTime").val();
                    }
                }
            },
            {title: '收款总金额(元)', field: 'day_Order_Amount', align: 'center', sortable: true},
            {title: '实收金额(元)', field: 'day_actual_amount', align: 'center', sortable: true},
            {title: '交易手续费(元)', field: 'day_Handling_Fee', align: 'center', sortable: true},
            {title: '通道总收入(元)', field: 'day_channel_amount', align: 'center', sortable: true},
            {title: '平台总收入(元)', field: 'day_platform_amount', align: 'center', sortable: true},
            {title: '代理总收入(元)', field: 'day_agent_amount', align: 'center', sortable: true},
            {
                title: '成功率', field: 'create_time', align: 'center', sortable: true,
                formatter: function (value, row, index) {
                    var day_all_order_num = row.day_all_order_num;
                    var day_payed_order_num = row.day_payed_order_num;
                    if (parseInt(day_payed_order_num) != 0) {
                        var result = (parseInt(day_payed_order_num) / parseInt(day_all_order_num)) * 100;
                        return result.toFixed(2) + "%";
                    } else {
                        return "0%";
                    }
                }
            },
        ], onLoadSuccess: function () {

            if (roleCode == "ROLE_AGENT") {
                pageScope.billTable.bootstrapTable('hideColumn', 'day_channel_amount');
                pageScope.billTable.bootstrapTable('hideColumn', 'day_platform_amount');
            } else if (roleCode == "ROLE_MERCHANT") {
                pageScope.billTable.bootstrapTable('hideColumn', 'day_channel_amount');
                pageScope.billTable.bootstrapTable('hideColumn', 'day_platform_amount');
                pageScope.billTable.bootstrapTable('hideColumn', 'day_agent_amount');

                queryMerchantAmountOfNotifyWithdraw();
            }

        }
    });

    /**
     * 查询
     */
    pageScope.search = function () {

        var statisticsWay = $("#queryStatisticsWay").val();
        if ("timeLine" == statisticsWay) {
            var beginTime = $("#beginTime").val();
            var endTime = $("#endTime").val();
            if($.validate.isEmpty(beginTime)){
                $.msg.toast("请选择开始时间");
                return;
            }
            if($.validate.isEmpty(endTime)){
                $.msg.toast("请选择结束时间");
                return;
            }
            if(endTime < beginTime){
                $.msg.toast("开始时间不可大于结束时间!");
                return;
            }
        }

        pageScope.billTable.bootstrapTable('refresh');
    };

})();