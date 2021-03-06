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

            {title: '商家名称', field: 'merchantName', align: 'center', sortable: true},
            {title: '商家号', field: 'merchantNo', align: 'center', sortable: true},
            {title: '提现单号', field: 'orderNo', align: 'center', sortable: true},
            {title: '提现金额', field: 'withdrawAmount', align: 'center', sortable: true},
            {title: '真实姓名', field: 'realName', align: 'center', sortable: true},
            {title: '银行卡号', field: 'bankCard', align: 'center', sortable: true},
            {
                title: '申请时间',
                field: 'createTime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return $.date.formatToDateTime(value);
                }
            },
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
                    if (row.withdrawStatus == "withdrawSuccess") {
                        return "转账成功";
                    } else if (row.withdrawStatus == "withdrawFail") {
                        return "转账失败";
                    } else {
                        return "代付处理中";
                    }
                }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {

                    var html = "";

                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showWithdrawDetail()' ><i class='glyphicon glyphicon-file'></i></button>";

                    //待审核状态才能审核
                    if (row.checkStatus == 'waitCheck') {
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.review()' >审核</button>";
                    }
                    //审核通过后,才能进行修改转账成功、转账失败状态
                    else {

                        //避免重复修改状态：转账成功、转账失败
                        if (row.withdrawStatus == 'withdrawApply') {
                            html += "<button type='button' class='btn btn-link' onclick='pageScope.updateWithdrawStatusToSuccess(\"" + row.id + "\" , \"" + row.checkStatus + "\")' >转账成功</i></button>";
                            html += "<button type='button' class='btn btn-link' onclick='pageScope.updateWithdrawStatusToFail(\"" + row.id + "\" , \"" + row.checkStatus + "\")' >转账失败</button>";
                        }

                    }

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
                if (pageScope.currentrow.withdrawStatus == "withdrawSuccess") {
                    $("#detailWithdrawStatus").val("转账成功");
                } else if (pageScope.currentrow.withdrawStatus == "withdrawFail") {
                    $("#detailWithdrawStatus").val("转账失败");
                } else {
                    $("#detailWithdrawStatus").val("代付处理中");
                }
                $("#detailBillNo").val(pageScope.currentrow.billNo);
                $("#detailNotifyResponse").val(pageScope.currentrow.notifyResponse);
                $("#detailRate").val(pageScope.currentrow.rate);
                $("#detailHandlingFee").val(pageScope.currentrow.handlingFee);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);
                $("#reviewId").val(pageScope.currentrow.id);
                $("#checkDesc").html(pageScope.currentrow.checkDesc);

                $("#detailMerchantName").val(pageScope.currentrow.merchantName);
                $("#detailMerchantNo").val(pageScope.currentrow.merchantNo);


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

        if (checkStatus == 'waitCheck') {
            $.msg.toast("请先审核后再进行！");
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


    /**
     * 查看提现申请详情
     * @param id
     */
    pageScope.showWithdrawDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/finance/payFor/payFor_detail.jsp?" + _csrf + "=" + token,
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

                if (pageScope.currentrow.withdrawStatus == "withdrawSuccess") {
                    $("#detailWithdrawStatus").val("转账成功");
                } else if (pageScope.currentrow.withdrawStatus == "withdrawFail") {
                    $("#detailWithdrawStatus").val("转账失败");
                } else {
                    $("#detailWithdrawStatus").val("代付处理中");
                }

                $("#detailBillNo").val(pageScope.currentrow.billNo);
                $("#detailNotifyResponse").val(pageScope.currentrow.notifyResponse);
                $("#detailRate").val(pageScope.currentrow.rate);
                $("#detailHandlingFee").val(pageScope.currentrow.handlingFee);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);
                $("#checkDesc").html(pageScope.currentrow.checkDesc);

            }
        });

    };


})();