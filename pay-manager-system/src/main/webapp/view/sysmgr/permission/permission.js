/**
 * User: zjt
 * DateTime: 2018/2/28 15:43
 */

var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */

(function () {

    /**
     * 初始化权限树
     */
    pageScope.initPermissionTree = function () {

        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                },
                key: {
                    name: "name"
                }
            },
            view: {
                showIcon: true,
                selectedMulti: false,
                addHoverDom: pageScope.addHoverDom,                   //新增 (需自定义,ztree没有提供支持)
                removeHoverDom: pageScope.removeHoverDom
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: true,
                showRenameBtn: true
            },
            callback: {
                onClick: pageScope.onClick,                           //显示节点信息
                beforeRemove: pageScope.beforeRemove,                 //删除事件
                beforeEditName: pageScope.beforeEdit,                 //修改事件
                beforeDrag: pageScope.beforeDrag,
                beforeDrop: pageScope.beforeDrop,
            },
            check: {
                enable: false
            }
        };

        $.ajax({
            url: baseURL + "/sysmgr/permission/queryPermissionList",
            type: "post",
            dataType: "json",
            data: {"_csrf": token},
            success: function (response) {

                //初始化树
                var orgNodes = response;
                $.fn.zTree.init($('#permissionTree'), setting, orgNodes);

                //展开所有节点
                var permissionTree = $.fn.zTree.getZTreeObj("permissionTree");
                permissionTree.expandAll(true);

            }
        });

    };

    /**
     * 查看节点信息
     * @param event
     * @param treeId
     * @param treeNode
     */
    pageScope.onClick = function (event, treeId, treeNode) {

        //不显示根信息
        if (treeNode.level == 0) {
            return;
        }

        $(".right-content").css("visibility", "visible");

        if (treeNode.level == 1) {
            $("#td-name").html("目录名称：");
            $("#tr-link-url").hide();
            $("#tr-permission-url").hide();
            $("#tr-icon").show();
        }
        else if (treeNode.level == 2) {
            $("#td-name").html("链接名称：");
            $("#tr-icon").hide();
            $("#tr-link-url").show();
            $("#tr-permission-url").show();
        }

        pageScope.loadDetailContent(treeNode.id);

    };

    /**
     * 加载详情内容
     */
    pageScope.loadDetailContent = function (id) {

        $.ajax({
            url: baseURL + "/sysmgr/permission/queryPermissionById",
            type: "post",
            dataType: "json",
            data: {"id": id, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {

                    var permission = JSON.parse(response.data);
                    $("#detailPermissionName").html(permission.name);
                    $("#detailPermissionUrl").html(permission.permissionUrl);
                    $("#detailLinkUrl").html(permission.linkUrl);
                    $("#detailPermissionIcon").html("<i class='" + permission.icon + "' ></i>");

                }

            }
        });

    };

    /**
     * 在节点后增加添加按钮
     * @param treeId
     * @param treeNode
     */
    pageScope.addHoverDom = function (treeId, treeNode) {

        //最多只能两级(目录 - 链接)
        if (treeNode.level == 2) {
            return;
        }

        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            pageScope.beforeAdd(treeNode);
        });

    };

    /**
     *  移除新增按钮
     */
    pageScope.removeHoverDom = function (treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#addBtn_space_" + treeNode.id).unbind().remove();
    };

    /**
     * 新增
     */
    pageScope.beforeAdd = function (treeNode) {
        var level = parseInt(treeNode.level) + 1;

        $.dialog.show({
            url: baseURL + "/view/sysmgr/permission/permission_add.jsp?_csrf=" + token + "&parentId=" + treeNode.id + "&level=" + level,
            buttonEvents: {
                success: function () {

                    if (!$("#addPermissionForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");            //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#addPermissionForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.initPermissionTree();
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

        return false;
    };

    /**
     * 删除
     */
    pageScope.beforeRemove = function (treeId, treeNode) {

        if (treeNode.level == 0) {
            $.msg.error("根节点不允许删除");
            return false;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/sysmgr/permission/deletePermission",
                type: "post",
                dataType: "json",
                data: {"ids": treeNode.id, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.initPermissionTree();
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        });

        return false;

    };

    /**
     * 修改
     */
    pageScope.beforeEdit = function (treeId, treeNode) {

        if (treeNode.level == 0) {
            $.msg.error("根节点不允许修改");
            return false;
        }

        $.dialog.show({
            url: baseURL + "/view/sysmgr/permission/permission_edit.jsp?_csrf=" + token + "&id=" + treeNode.id + "&level=" + treeNode.level,
            onLoad: function () {
                $("#editIcon").val(treeNode.icon);
                $("#editName").val(treeNode.name);
                $("#editLinkUrl").val(treeNode.linkUrl);
                $("#editPermissionUrl").val(treeNode.permissionUrl);
            },
            buttonEvents: {
                success: function () {

                    if (!$("#editPermissionForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");            //防止重复提交
                    btn.attr("disabled", "disabled");

                    if ($('#editPermissionForm').valid()) {

                        $('#editPermissionForm').ajaxSubmit({
                            dataType: 'json',
                            type: "post",
                            success: function (response) {

                                btn.removeAttr("disabled");

                                if (response && response.success) {
                                    $.msg.success(response.msg);

                                    //重新加载树
                                    pageScope.initPermissionTree();

                                    //重新加载详情内容
                                    pageScope.loadDetailContent(treeNode.id);

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
            }
        });

        return false;
    };

    /**
     * 准备拖拽
     * @param treeId
     * @param treeNodes
     * @returns {boolean}
     */
    pageScope.beforeDrag = function (treeId, treeNodes) {

        //根节点不允许移动
        if (treeNodes.level == 0) {
            return false;
        }

    };

    /**
     * 拖拽到目标前触发 - 修改位置
     * @param treeId
     * @param treeNodes
     * @param targetNode
     * @param moveType
     */
    pageScope.beforeDrop = function (treeId, treeNodes, targetNode, moveType) {

        //只能移动同级节点
        if ("inner" == moveType || treeNodes[0].level != targetNode.level) {
            return false;
        }

        $.ajax({
            url: baseURL + "/sysmgr/permission/updatePosition",
            type: "post",
            dataType: "json",
            data: {"id1": treeNodes[0].id, "seq1": treeNodes[0].seq, "id2": targetNode.id, "seq2": targetNode.seq, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    pageScope.initPermissionTree();
                } else {
                    $.msg.error(response.msg);
                }

            }
        });

        return false;

    };


})();