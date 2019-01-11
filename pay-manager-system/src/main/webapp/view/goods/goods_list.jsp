<%@page language="java" contentType="text/html; charset=UTF-8" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/jsp/common/sbadmin_common.jsp" %>
    <script type="text/javascript" src="${baseURL}/jsp/goods/goods.js"></script>
</head>

<script type="text/javascript">

    $(function () {
        pageScope.initGoodsTable();


    });

</script>

<body class="container">
<div class="panel panel-default">

    <div class="panel-heading">
        <h3 class="panel-title">商品管理</h3>
    </div>

    <div class="panel-body">

        <!-- 搜索表单 -->
        <form id="searchGoodsForm" onsubmit="return false;" autocomplete="off">
            <div class="searchDiv">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                                        <tr class="query-row-1">

                                            <td width="80" align="right" height="40">商品名称：</td>
                                            <td width="140">
                                                <input type="text" name="name" id="queryName" class="form-control btn-block" aria-describedby="basic-addon1">
                                            </td>

                                            <td width="80" align="right">生产日期：</td>
                                            <td width="180">
                                                <div class='input-group date' id="queryProductionDate">
                                                    <input type='text' name="productionDate" class="form-control" data-date-format="YYYY-MM-DD HH:mm"/>
                                                    <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                </div>
                                            </td>


                                            <td>&nbsp;</td>

                                            <td colspan="3">
                                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                                       onclick="javascript:document.getElementById('searchGoodsForm').reset(); pageScope.search();">
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>

            </div>
        </form>

        <!--  表格 -->
        <table id="goodsTable"></table>

        <!--  工具栏 -->
        <div id="goodsPager">

            <button id="add" class="btn btn-success" onclick="pageScope.addGoods()">
                <i class="glyphicon glyphicon-plus"></i> 新增
            </button>

            <button id="remove" class="btn btn-danger" onclick="pageScope.deleteGoods()">
                <i class="glyphicon glyphicon-remove"></i> 删除
            </button>

        </div>

    </div>
</div>

</body>
</html>
