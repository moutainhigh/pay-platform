var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.payChannelTable = $('#payChannelTable').initBootstrapTable({
        url: baseURL + '/payChannel/queryPayChannelList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#payChannelPager',
        queryParams: function (params) {
            $.extend(params, $('#searchPayChannelForm').serializeObject());
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
            {title: '通道编码', field: 'channelCode', align: 'center', sortable: true},
            {title: '通道名称', field: 'channelName', align: 'center', sortable: true},
            {
                title: '成本费率', field: 'costRate', align: 'center', sortable: true,
                formatter: function (value) {
                    return value + "%";
                }
            },
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
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.editPayChannel()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showPayChannelDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.deletePayChannelByLogic(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.payChannelTable.bootstrapTable('refresh');
    };

    /**
     * 新增通道
     */
    pageScope.addPayChannel = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/payChannel/payChannel_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addPayChannelForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");


                    $('#addPayChannelForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.payChannelTable.bootstrapTable('refresh');
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
     * 删除通道
     */
    pageScope.deletePayChannel = function (id) {

        var ids = $("#payChannelTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/payChannel/deletePayChannel",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.payChannelTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除通道
     */
    pageScope.deletePayChannelByLogic = function (id) {

        var ids = $("#payChannelTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/payChannel/deletePayChannelByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.payChannelTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑通道
     */
    pageScope.editPayChannel = function () {

        $.dialog.show({
            url: baseURL + "/view/payChannel/payChannel_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editId").val(pageScope.currentrow.id);
                $("#editChannelCode").val(pageScope.currentrow.channelCode);
                $("#editChannelName").val(pageScope.currentrow.channelName);
                $("#editCostRate").val(pageScope.currentrow.costRate);
                $("#editIsDel").val(pageScope.currentrow.isDel);
                $("#editCreateTime").val(pageScope.currentrow.createTime);
            },
            buttonEvents: {
                success: function () {

                    if (!$('#editPayChannelForm').valid()) {
                        return false;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editPayChannelForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.payChannelTable.bootstrapTable('refresh');
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
     * 查看通道详情
     * @param id
     */
    pageScope.showPayChannelDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/payChannel/payChannel_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailChannelCode").val(pageScope.currentrow.channelCode);
                $("#detailChannelName").val(pageScope.currentrow.channelName);
                $("#detailCostRate").val(pageScope.currentrow.costRate + "%");
                $("#detailIsDel").val(pageScope.currentrow.isDel);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);

            }
        });

    };

})();