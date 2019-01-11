<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/5
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-12" >

        <div class="panel panel-default">

            <div class="panel-heading">
                <h3 class="panel-title">机构管理</h3>
            </div>

            <div class="panel-body" >

                <div class="left-tree" >
                    <ul id="organizationTree" class="ztree" ></ul>
                </div>

                <div class="tree-content-middle-line"></div>

                <div class="right-content" style="visibility: hidden">

                    <form class="form-horizontal">

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >机构名称：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailName" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >联系人：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailContactPerson" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >联系电话：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailTel" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >通信地址：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailAddress" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >邮箱：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailEmail" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >邮编：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailPostCode" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >传真：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailFax" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 col-sm-2 control-label tree-detail-label" >备注：</label>
                            <div class="col-md-8 col-sm-8 detail-content" id="detailDescription" >
                            </div>
                        </div>

                    </form>

                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/sysmgr/organization/organization.js" ></script>
<script type="text/javascript" >

    $(function () {
        pageScope.initOrganizationTree();
    });

</script>
