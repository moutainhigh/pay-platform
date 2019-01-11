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
                <h3 class="panel-title">字典管理</h3>
            </div>

            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchDictionaryForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <td width="80" align="right">字典描述：</td>
                            <td width="150"><input type="text" id="dictDesc" name="dictDesc" class="form-control btn-block" placeholder="" aria-describedby="basic-addon1"></td>

                            <td width="80" align="right">字典类型：</td>
                            <td width="150"><input ype="text" id="dictType" name="dictType" class="form-control btn-block" placeholder="" aria-describedby="basic-addon1"></td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.initDictionaryTree()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchDictionaryForm').reset(); pageScope.initDictionaryTree();">
                            </td>
                        </tr>

                    </table>
                </form>

                <%-- 树区域 --%>
                <div class="not-detail-tree">
                    <ul id="dictionaryTree" class="ztree"></ul>
                </div>

            </div>

        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/sysmgr/dictionary/dictionary.js" ></script>
<script type="text/javascript">

    $(function(){
        pageScope.initDictionaryTree();
    });

</script>