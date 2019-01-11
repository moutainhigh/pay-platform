/**
 * User: zjt
 * DateTime: 2018/2/28 14:57
 */
var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */

(function () {

    /**
     * 初始化字典树
     */
    pageScope.initDictionaryTree = function () {

        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                },
                key: {
                    name: "displayName"
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
            url: baseURL + "/sysmgr/dictionary/queryDictionaryList",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "dictType": $("#dictType").val(), "dictDesc": $("#dictDesc").val()},
            success: function (response) {

                //初始化树
                var orgNodes = response;
                $.fn.zTree.init($('#dictionaryTree'), setting, orgNodes);

                //展开所有节点
                var dictionaryTree = $.fn.zTree.getZTreeObj("dictionaryTree");
                dictionaryTree.expandAll(true);

            }
        });

    };

    /**
     * 在节点后增加添加按钮
     * @param treeId
     * @param treeNode
     */
    pageScope.addHoverDom = function (treeId, treeNode) {

        if (treeNode.level != 2) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_" + treeNode.tId);
            if (btn) btn.bind("click", function () {
                pageScope.beforeAdd(treeNode);
            });
        }

    };

    /**
     * 移除新增按钮
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
            url: baseURL + "/view/sysmgr/dictionary/dictionary_add.jsp?_csrf=" + token + "&parentId=" + treeNode.id + "&level=" + level
            + "&dictDesc=" + treeNode.dictDesc + "&dictType=" + treeNode.dictType,
            onLoad: function () {

            },
            buttonEvents: {
                success: function () {

                    if (!$("#addDictionaryForm").valid()) {             //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");              //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#addDictionaryForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.initDictionaryTree();
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
                url: baseURL + "/sysmgr/dictionary/deleteDictionary",
                type: "post",
                dataType: "json",
                data: {"id": treeNode.id, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.initDictionaryTree();
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
            url: baseURL + "/view/sysmgr/dictionary/dictionary_edit.jsp?_csrf=" + token + "&id=" + treeNode.id + "&level=" + treeNode.level
            + "&dictDesc=" + treeNode.dictDesc + "&dictType=" + treeNode.dictType + "&itemDesc=" + treeNode.itemDesc + "&itemCode=" + treeNode.itemCode,
            onLoad: function () {

            },
            buttonEvents: {
                success: function () {

                    if (!$("#editDictionaryForm").valid()) {             //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");              //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editDictionaryForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.initDictionaryTree();
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
            url: baseURL + "/sysmgr/dictionary/updatePosition",
            type: "post",
            dataType: "json",
            data: {"id1": treeNodes[0].id, "seq1": treeNodes[0].seq, "id2": targetNode.id, "seq2": targetNode.seq, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    pageScope.initDictionaryTree();
                } else {
                    $.msg.error(response.msg);
                }

            }
        });

        return false;

    };

})();