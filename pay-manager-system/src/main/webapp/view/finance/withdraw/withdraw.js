var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.withdrawTable = $('#withdrawTable').initBootstrapTable({
        url: baseURL + '/finance/withdraw/queryWithdrawList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#withdrawPager',
        queryParams: function (params) {
            $.extend(params, $('#searchWithdrawForm').serializeObject());
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
                title: '提现状态',
                field: 'withdrawStatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.withdrawStatusDictDesc;
                }
            },
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
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    // html += "<button type='button' class='btn btn-link' onclick='pageScope.editWithdraw()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showWithdrawDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    // html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteWithdraw(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ], onLoadSuccess: function () {

            if (roleCode == "ROLE_MERCHANT") {
                queryMerchantAmountOfNotifyWithdraw();
            }

        }
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.withdrawTable.bootstrapTable('refresh');
    };

    /**
     * 新增提现申请
     */
    pageScope.addWithdraw = function () {

        //判断是否设置过提现密码,首次需要设置提现密码
        $.ajax({
            url: baseURL + "/finance/withdraw/queryIsInitWithdrawPassword",
            type: "post",
            dataType: "json",
            data: {"_csrf": token},
            success: function (response) {

                //输入提现密码
                if (response && response.success == true) {
                    pageScope.checkWithdrawPassword();
                }
                //初始化提现密码
                else {
                    pageScope.initWithdrawPassword();
                }

            }
        });

    };

    /**
     * 初始化提现密码
     */
    pageScope.initWithdrawPassword = function () {
        var dialog = $.dialog.show({
            url: baseURL + "/view/finance/withdraw/init_withdraw_password.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {
                    
                    if (!$("#initWithdrawPasswordForm").valid()) {                   //表单验证
                        return;
                    }

                    var withdrawPassword = $("#withdrawPassword").val();
                    var confirmWithdrawPassword = $("#confirmWithdrawPassword").val();
                    if ($.validate.isNotEmpty(withdrawPassword)) {

                        if ($.validate.isEmpty(confirmWithdrawPassword)) {
                            $.msg.error("请输入确认密码!");
                            return;
                        }

                        if (withdrawPassword != confirmWithdrawPassword) {
                            $.msg.error("两次输入密码不一致!");
                            return;
                        }

                    }
                    if(!$.validate.checkPassWord(withdrawPassword)){
                        $.msg.error("提现密码必须为8位的英文与数字组合!");
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#initWithdrawPasswordForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.showAddWithdrawDialog();
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

    /**
     * 输入提现密码
     */
    pageScope.checkWithdrawPassword = function () {
        var dialog = $.dialog.show({
            url: baseURL + "/view/finance/withdraw/check_withdraw_password.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#checkWithdrawPassword").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#checkWithdrawPassword').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.showAddWithdrawDialog();
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

    /**
     * 显示发起提现的对话框
     */
    pageScope.showAddWithdrawDialog = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/finance/withdraw/withdraw_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addWithdrawForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");


                    $('#addWithdrawForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.withdrawTable.bootstrapTable('refresh');
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.queryAccountAmount();
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

    /**
     * 删除提现申请
     */
    pageScope.deleteWithdraw = function (id) {

        var ids = $("#withdrawTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/finance/withdraw/deleteWithdraw",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.withdrawTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除提现申请
     */
    pageScope.deleteWithdrawByLogic = function (id) {

        var ids = $("#withdrawTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/finance/withdraw/deleteWithdrawByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.withdrawTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 查看提现申请详情
     * @param id
     */
    pageScope.showWithdrawDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/finance/withdraw/withdraw_detail.jsp?" + _csrf + "=" + token,
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

            }
        });

    };

    /**
     * 查询账户资金情况
     * @param id
     */
    pageScope.queryAccountAmount = function () {

        $.ajax({
            url: baseURL + "/finance/withdraw/queryAccountAmount?" + _csrf + "=" + token,
            type: "post",
            dataType: "json",
            success: function (response) {

                if (response && response.success == true) {
                    $("#accountAmount").html(response.data.accountAmount);
                    $("#freezeAmount").html(response.data.freezeAmount);
                    $("#withdrawableAmount").html(response.data.withdrawableAmount);
                } else {
                    $.msg.fail(response.msg);
                }

            }
        });

    };

})();