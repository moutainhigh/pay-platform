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
            {title: '商家编号', field: 'merchantNo', align: 'center', sortable: true},
            {title: '商家名称', field: 'merchantName', align: 'center', sortable: false},
            {title: '商户订单号', field: 'merchantOrderNo', align: 'center', sortable: true},
            {title: '平台订单号', field: 'platformOrderNo', align: 'center', sortable: true},
            // {title: '支付单号', field: 'payCode', align: 'center', sortable: true},
            {title: '码编号', field: 'tradeCodeNum', align: 'center', sortable: true},
            {title: '订单金额(元)', field: 'orderAmount', align: 'center', sortable: true},
            {
                title: '支付浮动金额(元)', field: 'payFloatAmount', align: 'center', sortable: true,
                formatter: function (value, row, index) {
                    return Number(value).toFixed(2);
                }
            },
            // {title: '商家实收(元)', field: 'actualAmount', align: 'center', sortable: true},
            // {title: '手续费(元)', field: 'handlingFee', align: 'center', sortable: true},
            // {title: '通道收入', field: 'channelAmount', align: 'center', sortable: true},
            // {title: '平台收入', field: 'platformAmount', align: 'center', sortable: true},
            // {title: '代理收入', field: 'agentAmount', align: 'center', sortable: true},
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
                title: '下单时间',
                field: 'createTime',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    if ($.validate.isNotEmpty(value)) {
                        return $.date.formatToDateTime(value);
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showOrderDetail()' ><i class='glyphicon glyphicon-file'></i></button>";

                    var platformOrderNo = row.platformOrderNo;

                    //make
                    if (roleCode == "ROLE_ADMIN" || roleCode == "ROLE_CODE_TRADER") {
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.makeOrderPaySuccess(\"" + row.platformOrderNo + "\")' >补单回调</button>";
                    }

                    return html;
                }
            }
        ], onLoadSuccess: function () {

            if (roleCode == "ROLE_CODE_TRADER") {
                pageScope.orderTable.bootstrapTable('hideColumn', 'channelAmount');
                pageScope.orderTable.bootstrapTable('hideColumn', 'platformAmount');
                pageScope.orderTable.bootstrapTable('hideColumn', 'agentAmount');
            }
            else if (roleCode == "ROLE_AGENT") {
                pageScope.orderTable.bootstrapTable('hideColumn', 'channelAmount');
                pageScope.orderTable.bootstrapTable('hideColumn', 'platformAmount');
            }
            else if (roleCode == "ROLE_MERCHANT") {
                pageScope.orderTable.bootstrapTable('hideColumn', 'channelAmount');
                pageScope.orderTable.bootstrapTable('hideColumn', 'platformAmount');
                pageScope.orderTable.bootstrapTable('hideColumn', 'agentAmount');

                queryMerchantAmountOfNotifyWithdraw();
            }


        }
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
            size: "large",
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailMerchantOrderNo").val(pageScope.currentrow.merchantOrderNo);
                $("#detailPlatformOrderNo").val(pageScope.currentrow.platformOrderNo);
                $("#detailPayCode").val(pageScope.currentrow.payCode);
                $("#detailGoodsName").val(pageScope.currentrow.goodsName);
                $("#detailOrderAmount").val(pageScope.currentrow.orderAmount.toFixed(2));

                $("#detailCostRate").val((pageScope.currentrow.costRate * 100).toFixed(2) + "%");
                $("#detailAgentRate").val((pageScope.currentrow.agentRate * 100).toFixed(2) + "%");
                $("#detailMerchantRate").val((pageScope.currentrow.merchantRate * 100).toFixed(2) + "%");

                $("#detailChannelAmount").val(pageScope.currentrow.channelAmount);


                $("#detailPlatformAmount").val(pageScope.currentrow.platformAmount);
                $("#detailAgentAmount").val(pageScope.currentrow.agentAmount);

                $("#detailHandlingFee").val(pageScope.currentrow.handlingFee);
                $("#detailActualAmount").val(pageScope.currentrow.actualAmount);
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

    /**
     * 手动补单-回调商家
     */
    pageScope.makeOrderPaySuccess = function (orderNo) {

        var dialog = $.dialog.show({
            url: baseURL + "/view/order/order_bu_dan_confirm.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#budan_orderNo").val(pageScope.currentrow.platformOrderNo);
            },
            buttonEvents: {
                success: function () {

                    if (!$("#budanForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#budanForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.orderTable.bootstrapTable('refresh');
                                $(".modal-footer .btn-danger").trigger("click");
                            }
                            else {
                                $.msg.error(response.msg);
                            }

                        }, error: function (e) {
                            btn.removeAttr("disabled");
                        }

                    });

                }
            }
        });

    }

})();