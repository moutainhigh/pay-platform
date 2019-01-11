<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/5
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">

            <div class="panel-heading">
                <h3 class="panel-title">权限管理</h3>
            </div>

            <div class="panel-body">

                <div class="left-tree">
                    <ul id="permissionTree" class="ztree"></ul>
                </div>

                <div class="tree-content-middle-line"></div>

                <div class="right-content" style="visibility: hidden">

                    <form class="form-horizontal">

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" id="td-name">链接名称：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailPermissionName">
                            </div>
                        </div>

                        <div class="form-group" id="tr-link-url">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label">链接地址：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailLinkUrl" >
                            </div>
                        </div>

                        <div class="form-group" id="tr-permission-url">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label">权限路径：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailPermissionUrl">
                            </div>
                        </div>

                        <div class="form-group" id="tr-icon">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label">目录图标：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailPermissionIcon">
                            </div>
                        </div>

                    </form>

                </div>

            </div>
        </div>

    </div>
</div>


<script type="text/javascript" src="${baseURL}/view/sysmgr/permission/permission.js" ></script>
<script type="text/javascript">

    $(function () {
        pageScope.initPermissionTree();
    });

</script>
