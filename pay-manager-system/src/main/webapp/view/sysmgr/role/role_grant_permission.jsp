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
    <h4 class="modal-title">授予权限</h4>
</div>

<div class="modal-body">
    <div class="bootbox-body">

        <div class="left-tree">
            <ul id="permissionTree" class="ztree"></ul>
        </div>

    </div>
</div>

<div class="modal-footer operation-button">
    <button data-bb-handler="success" type="button" class="btn btn-success">保存</button>
    <button data-bb-handler="cancel" type="button" class="btn btn-danger">取消</button>
</div>

<script type="text/javascript">

    $(function () {

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
            url: "${baseURL}/sysmgr/role/queryPermissionListForGrant",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "roleId": "${param.roleId}"},
            success: function (response) {

                //初始化树
                var orgNodes = response;
                $.fn.zTree.init($('#permissionTree'), setting, orgNodes);

                //展开所有节点
                var permissionTree = $.fn.zTree.getZTreeObj("permissionTree");
                permissionTree.expandAll(true);

            }
        });

    });

</script>