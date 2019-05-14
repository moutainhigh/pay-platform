var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.codeTraderTable = $('#codeTraderTable').initBootstrapTable({
        url: baseURL + '/codeTrader/queryCodeTraderList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#codeTraderPager',
        queryParams: function (params) {
            $.extend(params, $('#searchCodeTraderForm').serializeObject());
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
            {title: '码商名称', field: 'name', align: 'center', sortable: true},
            {
                title: '创建时间',
                field: 'createTime',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return $.date.formatToDateTime(value);
                }
            }
            , {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    // html += "<button type='button' class='btn btn-link' onclick='pageScope.editCodeTrader()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    // html += "<button type='button' class='btn btn-link' onclick='pageScope.showCodeTraderDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteCodeTrader(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.codeTraderTable.bootstrapTable('refresh');
    };

    /**
     * 新增码商
     */
    pageScope.addCodeTrader = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/codeTrader/codeTrader_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addCodeTraderForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");


                    $('#addCodeTraderForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.codeTraderTable.bootstrapTable('refresh');
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
     * 删除码商
     */
    pageScope.deleteCodeTrader = function (id) {

        var ids = $("#codeTraderTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/codeTrader/deleteCodeTrader",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.codeTraderTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除码商
     */
    pageScope.deleteCodeTraderByLogic = function (id) {

        var ids = $("#codeTraderTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/codeTrader/deleteCodeTraderByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.codeTraderTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑码商
     */
    pageScope.editCodeTrader = function () {

        $.dialog.show({
            url: baseURL + "/view/codeTrader/codeTrader_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editId").val(pageScope.currentrow.id);
                $("#editName").val(pageScope.currentrow.name);
                $("#editCreateTime").val(pageScope.currentrow.createTime);
            },
            buttonEvents: {
                success: function () {

                    if (!$('#editCodeTraderForm').valid()) {
                        return false;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editCodeTraderForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.codeTraderTable.bootstrapTable('refresh');
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
     * 查看码商详情
     * @param id
     */
    pageScope.showCodeTraderDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/codeTrader/codeTrader_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailName").val(pageScope.currentrow.name);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);

            }
        });

    };

})();