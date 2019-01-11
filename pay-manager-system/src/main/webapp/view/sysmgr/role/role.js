/**
 * User: zjt
 * DateTime: 2018/2/28 16:05
 */

var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */

(function () {

    /**
     * 加载列表
     */
    pageScope.roleTable = $('#roleTable').initBootstrapTable({
        url: baseURL + '/sysmgr/role/queryRoleList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#rolePager',
        queryParams: function (params) {
            $.extend(params, $('#searchRoleForm').serializeObject());
            return params;
        },
        onClickRow: function (row, tr) {
            pageScope.currentrow = row;
        },
        columns: [
            {title: '全选', checkbox: true},                //正确
            {
                title: '序号',
                width: 46,
                formatter: function () {
                    return arguments[2] + 1;
                }
            },
            {title: '角色名称', field: 'name', align: 'center', sortable: true},
            {title: '角色码', field: 'code', align: 'center', sortable: true},
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
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.editRole()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.grantPermission(\"" + row.id + "\")' >授予权限</button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteRole(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ]
    });

    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.roleTable.bootstrapTable('refresh');
    };

    /**
     * 新增角色
     */
    pageScope.addRole = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/sysmgr/role/role_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addRoleForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#addRoleForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.roleTable.bootstrapTable('refresh');
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
     * 删除角色
     */
    pageScope.deleteRole = function (id) {

        var ids = $("#roleTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/sysmgr/role/deleteRole",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.roleTable.bootstrapTable('refresh');
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑角色
     */
    pageScope.editRole = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/sysmgr/role/role_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editRoleId").val(pageScope.currentrow.id);
                $("#editName").val(pageScope.currentrow.name);
                $("#editCode").val(pageScope.currentrow.code);
            },
            buttonEvents: {
                success: function () {
                    if (!$("#editRoleForm").valid()) {            //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editRoleForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.roleTable.bootstrapTable('refresh');
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
     * 授予权限
     * @param id
     */
    pageScope.grantPermission = function (id) {

        var dialog = $.dialog.show({
            url: baseURL + "/view/sysmgr/role/role_grant_permission.jsp?" + _csrf + "=" + token + "&roleId=" + id,
            size: "small",
            buttonEvents: {
                success: function () {

                    //获取选中节点
                    var permissionTree = $.fn.zTree.getZTreeObj("permissionTree");
                    var checkedNodes = permissionTree.getCheckedNodes(true);

                    if (checkedNodes.length > 0) {

                        //获取按,号分割返回的id
                        var ids = $.string.getIdsFromArray(checkedNodes);

                        var btn = $(".modal-footer .btn-success");        //防止重复提交
                        btn.attr("disabled", "disabled");

                        //授予权限
                        $.ajax({
                            url: baseURL + "/sysmgr/role/grantPermission",
                            type: "post",
                            dataType: 'json',
                            data: {"_csrf": token, "permissionIds": ids, "roleId": id},
                            success: function (response) {

                                btn.removeAttr("disabled");

                                if (response && response.success) {
                                    $.msg.success(response.msg);
                                    pageScope.roleTable.bootstrapTable('refresh');
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
                    else {
                        $.msg.toast("请选中授予权限!");
                    }

                }
            }
        });

    };

})();