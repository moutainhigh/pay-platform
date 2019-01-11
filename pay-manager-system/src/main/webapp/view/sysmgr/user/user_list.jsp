<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/1
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">用户管理</h3>
            </div>
            <div class="panel-body">

                <div class="left-tree">
                    <ul id="organizationTree" class="ztree"></ul>
                </div>

                <div class="tree-content-middle-line"></div>

                <div class="right-content">

                    <!-- 搜索条件 -->
                    <form id="searchUserForm" method="post">
                        <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                            <input type="hidden" name="orgId" id="orgId">

                            <tr>
                                <td width="80" align="right">用户昵称：</td>
                                <td width="150"><input type="text" name="nickname" class="form-control btn-block" placeholder="" aria-describedby="basic-addon1"></td>

                                <td width="80" align="right">登录账号：</td>
                                <td width="150"><input ype="text" name="account" class="form-control btn-block" placeholder="" aria-describedby="basic-addon1"></td>

                                <td width="80" align="right">包含下级：</td>
                                <td width="150">

                                    <label class="checkbox-inline">
                                        <input type="radio" name="isContainChildren" value="1" checked > 是
                                    </label>

                                    <label class="checkbox-inline">
                                        <input type="radio" name="isContainChildren" value="0" > 不是
                                    </label>

                                </td>

                                <td colspan="2">
                                    <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                    <input class="btn btn-default btn-reset" type="button" value="重 置" onclick="javascript:document.getElementById('searchUserForm').reset(); pageScope.search();">
                                </td>
                            </tr>

                        </table>
                    </form>

                    <!-- 操作按钮 -->
                    <div class="operation-button columns columns-left bars pull-left">

                        <button id="add" class="btn btn-success" onclick="pageScope.toAddUser()">
                            <i class="glyphicon glyphicon-plus"></i> 新增
                        </button>

                        <button id="remove" class="btn btn-danger" onclick="pageScope.deleteUser()">
                            <i class="glyphicon glyphicon-remove"></i> 删除
                        </button>

                    </div>

                    <!-- 数据表格 -->
                    <div class="data-table-wrapper">
                        <table id="userTable"></table>
                        <div id="userPager"></div>
                    </div>

                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/sysmgr/user/user.js"></script>
<script type="text/javascript">

    $(function () {
        pageScope.initUserTable();
        pageScope.initCurrentUserOrgTree();
    });

</script>
