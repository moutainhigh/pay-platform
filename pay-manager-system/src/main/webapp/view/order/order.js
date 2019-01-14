var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.orderTable = $('#orderTable').initBootstrapTable({
        url: baseURL + '/order/queryOrderList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#orderPager',
        queryParams: function (params) {
            $.extend(params, $('#searchOrderForm').serializeObject());
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
            {title: '商户订单号', field: 'merchantOrderNo', align: 'center', sortable: true},
            {title: '平台订单号', field: 'platformOrderNo', align: 'center', sortable: true},
            {title: '支付单号', field: 'payCode', align: 'center', sortable: true},
            {title: '订单金额(元)', field: 'orderAmount', align: 'center', sortable: true},
            {title: '商户金额(元)', field: 'merchantAmount', align: 'center', sortable: true},
            {
                title: '费率', field: 'rate', align: 'center', sortable: true, formatter: function (value) {
                    return value + "%";
                }
            },
            {title: '手续费(元)', field: 'handlingFee', align: 'center', sortable: true},
            {title: '商家编号', field: 'merchantNo', align: 'center', sortable: true},
            {
                title: '支付方式 ',
                field: 'payWay',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.payWayDictDesc;
                }
            },
            {
                title: '支付状态',
                field: 'payStatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.payStatusDictDesc;
                }
            },
            {
                title: '支付时间',
                field: 'payTime',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return $.date.formatToDateTime(value);
                }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showOrderDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.orderTable.bootstrapTable('refresh');
    };

    /**
     * 查看订单详情
     * @param id
     */
    pageScope.showOrderDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/order/order_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailMerchantOrderNo").val(pageScope.currentrow.merchantOrderNo);
                $("#detailPlatformOrderNo").val(pageScope.currentrow.platformOrderNo);
                $("#detailPayCode").val(pageScope.currentrow.payCode);
                $("#detailGoodsName").val(pageScope.currentrow.goodsName);
                $("#detailOrderAmount").val(pageScope.currentrow.orderAmount);
                $("#detailRate").val(pageScope.currentrow.rate + "%");
                $("#detailHandlingFee").val(pageScope.currentrow.handlingFee);
                $("#detailMerchantAmount").val(pageScope.currentrow.merchantAmount);
                $("#detailMerchantId").val(pageScope.currentrow.merchantId);
                $("#detailMerchantNo").val(pageScope.currentrow.merchantNo);
                $("#detailChannelId").val(pageScope.currentrow.channelId);
                $("#detailPayWay").val(pageScope.currentrow.payWayDictDesc);
                $("#detailPayStatus").val(pageScope.currentrow.payStatusDictDesc);
                $("#detailPayTime").val(pageScope.currentrow.payTime);
                $("#detailNotifyUrl").val(pageScope.currentrow.notifyUrl);
                $("#detailNotifyStatus").val(pageScope.currentrow.notifyStatusDictDesc);
                $("#detailnotifyNum").val(pageScope.currentrow.notifyNum);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);

            }
        });

    };

})();