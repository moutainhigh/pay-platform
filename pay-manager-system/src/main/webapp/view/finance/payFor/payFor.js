var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.payForTable = $('#payForTable').initBootstrapTable({
        url: baseURL + '/finance/payFor/queryPayForList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#payForPager',
        queryParams: function (params) {
            $.extend(params, $('#searchPayForForm').serializeObject());
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
            {title: '提现单号', field: 'orderNo', align: 'center', sortable: true},
            {title: '提现金额', field: 'withdrawAmount', align: 'center', sortable: true},
            {title: '真实姓名', field: 'realName', align: 'center', sortable: true},
            {title: '银行卡号', field: 'bankCard', align: 'center', sortable: true},
            {
                title: '审核状态',
                field: 'checkStatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.checkStatusDictDesc;
                }
            },
            {
                title: '代付状态',
                field: 'withdrawStatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.withdrawStatusDictDesc;
                }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.review()' >审核</button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.updateWithdrawStatusToSuccess(\"" + row.id + "\" , \"" + row.checkStatus + "\")' >转账成功</i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.updateWithdrawStatusToFail(\"" + row.id + "\" , \"" + row.checkStatus + "\")' >转账失败</button>";
                    return html;
                }
            }
        ], onLoadSuccess: function () {

            if (roleCode == "ROLE_MERCHANT") {
                queryMerchantAmountOfNotifypayFor();
            }

        }
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.payForTable.bootstrapTable('refresh');
    };

    /**
     * 审核
     * @param id
     */
    pageScope.review = function () {

        $.dialog.show({
            url: baseURL + "/view/finance/payFor/payFor_review.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#detailId").val(pageScope.currentrow.id);
                $("#detailMerchantId").val(pageScope.currentrow.merchantId);
                $("#detailOrderNo").val(pageScope.currentrow.orderNo);
                $("#detailWithdrawAmount").val(pageScope.currentrow.withdrawAmount);
                $("#detailActualAmount").val(pageScope.currentrow.actualAmount);
                $("#detailRealName").val(pageScope.currentrow.realName);
                $("#detailBankName").val(pageScope.currentrow.bankName);
                $("#detailBankCard").val(pageScope.currentrow.bankCard);
                $("#detailProvinceName").val(pageScope.currentrow.provinceName);
                $("#detailCityName").val(pageScope.currentrow.cityName);
                $("#detailCheckStatus").val(pageScope.currentrow.checkStatusDictDesc);
                $("#detailRemark").val(pageScope.currentrow.remark);
                $("#detailWithdrawStatus").val(pageScope.currentrow.withdrawStatusDictDesc);
                $("#detailBillNo").val(pageScope.currentrow.billNo);
                $("#detailNotifyResponse").val(pageScope.currentrow.notifyResponse);
                $("#detailRate").val(pageScope.currentrow.rate);
                $("#detailHandlingFee").val(pageScope.currentrow.handlingFee);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);
                $("#reviewId").val(pageScope.currentrow.id);
                $("#checkDesc").html(pageScope.currentrow.checkDesc);
                $("select[name='checkStatus'] option[value='" + pageScope.currentrow.checkStatus + "']").attr("selected", "selected");
            },
            buttonEvents: {
                success: function () {

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#reviewPayForForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.payForTable.bootstrapTable('refresh');
                            } else {
                                $.msg.error(response.msg);
                                return false;
                            }

                        },
                        error: function () {
                            $.msg.error('修改失败，可能是由网络原因引起的，请稍候再试');
                            btn.removeAttr("disabled");
                            return false;
                        }
                    });

                    return false;

                }
            }
        });

    };

    /**
     * 更新为提现成功
     */
    pageScope.updateWithdrawStatusToSuccess = function (id, checkStatus) {

        if (checkStatus != 'checkSuccess') {
            $.msg.toast("请先审核通过后再进行！");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/finance/payFor/updateWithdrawStatusToSuccess",
                type: "post",
                dataType: "json",
                data: {"id": id, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.payForTable.bootstrapTable('refresh');
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        }, "确定设为转账成功？");


    };


    /**
     * 更新为提现失败
     */
    pageScope.updateWithdrawStatusToFail = function (id, checkStatus) {

        if (checkStatus != 'checkSuccess') {
            $.msg.toast("请先审核通过后再进行！");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/finance/payFor/updateWithdrawStatusToFail",
                type: "post",
                dataType: "json",
                data: {"id": id, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.payForTable.bootstrapTable('refresh');
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        }, "确定设为转账失败？");

    };


})();