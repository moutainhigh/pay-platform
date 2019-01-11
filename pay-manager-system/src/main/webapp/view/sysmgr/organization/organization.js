/**
 * User: zjt
 * DateTime: 2018/2/28 15:31
 */

var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    /**
     * 初始化机构树
     */
    pageScope.initOrganizationTree = function () {

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
                addHoverDom: pageScope.addHoverDom,                    //新增 (需自定义,ztree没有提供支持)
                removeHoverDom: pageScope.removeHoverDom
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: true,
                showRenameBtn: true
            },
            callback: {
                onClick: pageScope.onClick,                           //查看事件
                beforeRemove: pageScope.beforeRemove,                 //删除事件
                beforeEditName: pageScope.beforeEdit,                 //修改事件
                beforeDrag: pageScope.beforeDrag,         //拖拽事件
                beforeDrop: pageScope.beforeDrop,
            },
            check: {
                enable: false
            }
        };

        $.ajax({
            url: baseURL + "/sysmgr/organization/queryOrganizationList",
            type: "post",
            dataType: "json",
            data: {"_csrf": token},
            success: function (response) {

                //初始化树
                var orgNodes = response;
                $.fn.zTree.init($('#organizationTree'), setting, orgNodes);

                //展开所有节点
                var organizationTree = $.fn.zTree.getZTreeObj("organizationTree");
                organizationTree.expandAll(true);

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

        pageScope.loadDetailContent(treeNode.id);
    };

    /**
     * 加载详情内容
     */
    pageScope.loadDetailContent = function (id) {

        $.ajax({
            url: baseURL + "/sysmgr/organization/queryOrganizationById",
            type: "post",
            dataType: "json",
            data: {"id": id, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {

                    var organization = JSON.parse(response.data);
                    $("#detailName").html(organization.name);
                    $("#detailDescription").html(organization.description);
                    $("#detailContactPerson").html(organization.contactPerson);
                    $("#detailTel").html(organization.tel);
                    $("#detailEmail").html(organization.email);
                    $("#detailFax").html(organization.fax);
                    $("#detailAddress").html(organization.address);
                    $("#detailPostCode").html(organization.postCode);

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
     *  添加
     */
    pageScope.beforeAdd = function (treeNode) {
        var level = parseInt(treeNode.level) + 1;

        $.dialog.show({
            url: baseURL + "/view/sysmgr/organization/organization_add.jsp?_csrf=" + token + "&parentId=" + treeNode.id + "&level=" + level,
            buttonEvents: {
                success: function () {

                    if (!$("#addOrganizationForm").valid()) {                //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");              //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#addOrganizationForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.initOrganizationTree();
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
     * 删除节点信息
     */
    pageScope.beforeRemove = function (treeId, treeNode) {

        if (treeNode.level == 0) {
            $.msg.error("根节点不允许删除");
            return false;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/sysmgr/organization/deleteOrganization",
                type: "post",
                dataType: "json",
                data: {"id": treeNode.id, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.initOrganizationTree();
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        });

        return false;

    };

    /**
     * 修改回显
     */
    pageScope.beforeEdit = function (treeId, treeNode) {

        if (treeNode.level == 0) {
            $.msg.error("根节点不允许修改");
            return false;
        }


        var dialog = $.dialog.show({
            url: baseURL + "/view/sysmgr/organization/organization_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editOrganizationId").val(treeNode.id);
                $("#editName").val(treeNode.name);
                $("#editContactPerson").val(treeNode.contactPerson);
                $("#editTel").val(treeNode.tel);
                $("#editAddress").val(treeNode.address);
                $("#editEmail").val(treeNode.email);
                $("#editPostCode").val(treeNode.postCode);
                $("#editFax").val(treeNode.fax);
                $("#editDescription").html(treeNode.description);
            },
            buttonEvents: {
                success: function () {

                    if (!$("#editOrganizationForm").valid()) {                //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");              //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editOrganizationForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);

                                //重新加载树
                                pageScope.initOrganizationTree();

                                //重新加载详情内容
                                pageScope.loadDetailContent($("#editOrganizationId").val());

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
            url: baseURL + "/sysmgr/organization/updatePosition",
            type: "post",
            dataType: "json",
            data: {"id1": treeNodes[0].id, "seq1": treeNodes[0].seq, "id2": targetNode.id, "seq2": targetNode.seq, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    pageScope.initOrganizationTree();
                } else {
                    $.msg.error(response.msg);
                }

            }
        });

        return false;

    };

})();