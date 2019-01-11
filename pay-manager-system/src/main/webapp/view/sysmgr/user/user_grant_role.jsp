<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
    <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">授予角色</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <div class="left-tree">
            <ul id="roleTree" class="ztree"></ul>
        </div>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success" onclick="pageScope.grantRole()">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    /**
     * 初始化树
     */
    pageScope.initRoleTree = function(){

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
            check: {
                enable: true
            }
        };

        $.ajax({
            url: "${baseURL}/sysmgr/user/queryRoleListForGrant",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "userId": "${param.userId}"},
            success: function (response) {

                //初始化树
                var orgNodes = response;
                $.fn.zTree.init($('#roleTree'), setting, orgNodes);

                //展开所有节点
                var roleTree = $.fn.zTree.getZTreeObj("roleTree");
                roleTree.expandAll(true);

            }
        });

    };

    /**
     * 授予角色
     */
    pageScope.grantRole = function () {

        //获取选中节点
        var roleTree = $.fn.zTree.getZTreeObj("roleTree");
        var checkedNodes = roleTree.getCheckedNodes(true);

        if (checkedNodes.length > 0) {

            //获取按,号分割返回的id
            var ids = $.string.getIdsFromArray(checkedNodes);

            var btn = $(".modal-footer .btn-success");              //防止重复提交
            btn.attr("disabled", "disabled");

            //授予角色
            $.ajax({
                url: "${baseURL}/sysmgr/user/grantRole",
                type: "post",
                dataType: 'json',
                data: {"_csrf": token, "roleIds": ids, "userId": "${param.userId}"},
                success: function (response) {

                    btn.removeAttr("disabled");

                    if (response && response.success) {
                        $.msg.success(response.msg);
                        pageScope.userTable.bootstrapTable('refresh');
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
            $.msg.toast("请选中授予角色!");
        }

    };

    $(function () {
        pageScope.initRoleTree();
    });

</script>
