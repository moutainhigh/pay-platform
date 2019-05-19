var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.tradeCodeTable = $('#tradeCodeTable').initBootstrapTable({
        url: baseURL + '/loopMgr/tradeCode/queryTradeCodeList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#tradeCodePager',
        queryParams: function (params) {
            $.extend(params, $('#searchTradeCodeForm').serializeObject());
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
            {title: '收款码编号', field: 'codeNum', align: 'center', sortable: true},
            {title: '设备回调密钥', field: 'secret', align: 'center', sortable: true},
            {title: '登录账号', field: 'loginAccount', align: 'center', sortable: true},
            // {title: '号主姓名', field: 'realName', align: 'center', sortable: true},
            {title: '收款码链接', field: 'codeLink', align: 'center', sortable: true},
            {
                title: '创建时间',
                field: 'createTime',
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
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.editTradeCode()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showTradeCodeDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteTradeCode(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.tradeCodeTable.bootstrapTable('refresh');
    };

    /**
     * 新增交易码
     */
    pageScope.addTradeCode = function () {

        var merchantId = $("#merchantId").val();
        var channelCode = $("#channelCode").val();

        var dialog = $.dialog.show({
            url: baseURL + "/view/loopMgr/tradeCode/tradeCode_add.jsp?" + _csrf + "=" + token + "&merchantId=" + merchantId + "&channelCode=" + channelCode,
            buttonEvents: {
                success: function () {

                    if (!$("#addTradeCodeForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");


                    $('#addTradeCodeForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.tradeCodeTable.bootstrapTable('refresh');
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

    };

    /**
     * 删除交易码
     */
    pageScope.deleteTradeCode = function (id) {

        var ids = $("#tradeCodeTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/loopMgr/tradeCode/deleteTradeCode",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.tradeCodeTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除交易码
     */
    pageScope.deleteTradeCodeByLogic = function (id) {

        var ids = $("#tradeCodeTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/loopMgr/tradeCode/deleteTradeCodeByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.tradeCodeTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑交易码
     */
    pageScope.editTradeCode = function () {

        var channelCode = $("#channelCode").val();

        $.dialog.show({
            url: baseURL + "/view/loopMgr/tradeCode/tradeCode_edit.jsp?" + _csrf + "=" + token + "&channelCode=" + channelCode,
            onLoad: function () {
                $("#editId").val(pageScope.currentrow.id);
                $("#editChannelId").val(pageScope.currentrow.channelId);
                $("#editChannelCode").val(pageScope.currentrow.channelCode);
                $("#editMerchantId").val(pageScope.currentrow.merchantId);
                $("#editMinAmount").val(pageScope.currentrow.minAmount);
                $("#editMaxAmount").val(pageScope.currentrow.maxAmount);
                $("#editDayAmountLimit").val(pageScope.currentrow.dayAmountLimit);
                $("#editCodeNum").val(pageScope.currentrow.codeNum);
                $("#editSecret").val(pageScope.currentrow.secret);
                $("#editLoginAccount").val(pageScope.currentrow.loginAccount);
                $("#editRealName").val(pageScope.currentrow.realName);
                $("#editCodeLink").val(pageScope.currentrow.codeLink);
                $("#editZfbUserId").val(pageScope.currentrow.zfbUserId);
                $("#editCreateTime").val(pageScope.currentrow.createTime);
            },
            buttonEvents: {
                success: function () {

                    if (!$('#editTradeCodeForm').valid()) {
                        return false;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editTradeCodeForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.tradeCodeTable.bootstrapTable('refresh');
                            } else {
                                $.msg.fail(response.msg);
                                return false;
                            }

                        },
                        error: function () {
                            $.msg.fail('修改失败，可能是由网络原因引起的，请稍候再试');
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
     * 查看交易码详情
     * @param id
     */
    pageScope.showTradeCodeDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/loopMgr/tradeCode/tradeCode_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailChannelId").val(pageScope.currentrow.channelId);
                $("#detailChannelCode").val(pageScope.currentrow.channelCode);
                $("#detailMerchantId").val(pageScope.currentrow.merchantId);
                $("#detailMinAmount").val(pageScope.currentrow.minAmount);
                $("#detailMaxAmount").val(pageScope.currentrow.maxAmount);
                $("#detailDayAmountLimit").val(pageScope.currentrow.dayAmountLimit);
                $("#detailCodeNum").val(pageScope.currentrow.codeNum);
                $("#detailSecret").val(pageScope.currentrow.secret);
                $("#detailLoginAccount").val(pageScope.currentrow.loginAccount);
                $("#detailRealName").val(pageScope.currentrow.realName);
                $("#detailCodeLink").val(pageScope.currentrow.codeLink);
                $("#detailZfbUserId").val(pageScope.currentrow.zfbUserId);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);

            }
        });

    };

})();