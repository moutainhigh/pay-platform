<%@page language="java" contentType="text/html; charset=UTF-8" %>


<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">代理管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchAgentForm" method="post">
                    <table class="search" border="0" cellspacing="0" cellpadding="0">

                        <tr>

                            <td width="80" align="right">代理编号：</td>
                            <td width="150">
                                <input type="text" name="agentNo" id="queryAgentNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">代理名称：</td>
                            <td width="150">
                                <input type="text" name="agentName" id="queryAgentName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">手机号：</td>
                            <td width="150">
                                <input type="text" name="phone" id="queryPhone" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchAgentForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                        <tr>
                            <td width="80" align="right">代理级别：</td>
                            <td width="150">
                                <select class="form-control" id="queryLevel" name="level">
                                    <option value=''>请选择</option>
                                    <option value="1">一级代理</option>
                                    <option value="2">二级代理</option>
                                </select>
                            </td>

                            <td width="80" align="right">上级代理：</td>
                            <td width="150">
                                <select class="form-control" id="queryParentId" name="parentId">
                                </select>
                            </td>


                            <td width="80" align="right">真实姓名：</td>
                            <td width="150">
                                <input type="text" name="realName" id="queryRealName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <button id="add" class="btn btn-success" onclick="pageScope.addAgent()">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>

                    <button id="remove" class="btn btn-danger" onclick="pageScope.deleteAgent()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>

                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="agentTable"></table>
                    <div id="agentPager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/agent/agent.js"></script>
<script type="text/javascript">

    $(function () {

        queryOneLevelAgent();

    });

    /**
     * 查询一级代理
     */
    function queryOneLevelAgent(){

        $.ajax({
            type: "post",
            url: baseURL + "/agent/queryOneLevelAgent?_csrf=" + token,
            dataType: "json",
            success: function (response) {
                if (response && response.success == true) {
                    var str = "<option value=''>请选择</option>";
                    for (var i = 0; i < response.data.length; i++) {
                        str += "  <option  value='" + response.data[i].id + "'>" + response.data[i].agentName + " </option> ";
                    }
                    $("#queryParentId").html(str);
                } else {
                    return false;
                }
            },
            error: function () {
                return false;
            }
        });

    }

</script>