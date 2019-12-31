var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.phoneConfigTable = $('#phoneConfigTable').initBootstrapTable({
        url: baseURL + '/phoneConfig/queryPhoneConfigList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#phoneConfigPager',
        queryParams: function (params) {
            $.extend(params, $('#searchPhoneConfigForm').serializeObject());
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
            {title: '手机号', field: 'phone', align: 'center', sortable: true},
            {
                title: '操作类型',
                field: 'type',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.typeDictDesc;
                }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.editPhoneConfig()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showPhoneConfigDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.deletePhoneConfig(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.phoneConfigTable.bootstrapTable('refresh');
    };

    /**
     * 新增手机校验配置
     */
    pageScope.addPhoneConfig = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/phoneConfig/phoneConfig_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addPhoneConfigForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");


                    $('#addPhoneConfigForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.phoneConfigTable.bootstrapTable('refresh');
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
     * 删除手机校验配置
     */
    pageScope.deletePhoneConfig = function (id) {

        var ids = $("#phoneConfigTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/phoneConfig/deletePhoneConfig",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.phoneConfigTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除手机校验配置
     */
    pageScope.deletePhoneConfigByLogic = function (id) {

        var ids = $("#phoneConfigTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/phoneConfig/deletePhoneConfigByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.phoneConfigTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑手机校验配置
     */
    pageScope.editPhoneConfig = function () {

        $.dialog.show({
            url: baseURL + "/view/phoneConfig/phoneConfig_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editId").val(pageScope.currentrow.id);
                $("#editUserId").val(pageScope.currentrow.userId);
                $("#editPhone").val(pageScope.currentrow.phone);
                //加载字典(下拉框)
                $("#editType").loadDictionaryForSelect({
                    "inputName": "type",
                    "dictType": "smsCheckType",
                    "value": pageScope.currentrow.type
                });

                $("#editSequence").val(pageScope.currentrow.sequence);
                $("#editCreateTime").val(pageScope.currentrow.createTime);
            },
            buttonEvents: {
                success: function () {

                    if (!$('#editPhoneConfigForm').valid()) {
                        return false;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editPhoneConfigForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.phoneConfigTable.bootstrapTable('refresh');
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
     * 查看手机校验配置详情
     * @param id
     */
    pageScope.showPhoneConfigDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/phoneConfig/phoneConfig_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailUserId").val(pageScope.currentrow.userId);
                $("#detailPhone").val(pageScope.currentrow.phone);
                $("#detailType").val(pageScope.currentrow.typeDictDesc);
                $("#detailSequence").val(pageScope.currentrow.sequence);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);

            }
        });

    };

})();