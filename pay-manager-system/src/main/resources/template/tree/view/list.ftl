<%--
Created by IntelliJ IDEA.
User: zjt
Date: 16/10/5
Time: 14:13
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

<script type="text/javascript">

    var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

    /**
     * 初始化${moduleName}树
     */
    pageScope.init${classPrefix}Tree = function() {

        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                },
                key: {
                name: <#list columnList as column><#if column._isTreeShowField >"${column.fieldName}"</#if></#list>
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
                onClick : pageScope.onClick,                           //显示节点信息
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
            url: "<@jspEl "baseURL" />${requestUrl}/query${classPrefix}List?_csrf=" + token,
            type: "post",
            dataType: "json",
            data: $('#search${classPrefix}Form').serializeObject(),
            success: function (response) {

                //初始化树
                var orgNodes = response;
                $.fn.zTree.init($('#${propertyPrefix}Tree'), setting, orgNodes);

                //展开所有节点
                var ${propertyPrefix}Tree = $.fn.zTree.getZTreeObj("${propertyPrefix}Tree");
				${propertyPrefix}Tree.expandAll(true);

            }
        });

    };

    /**
     * 查看节点信息
     * @param event
     * @param treeId
     * @param treeNode
     */
    pageScope.onClick = function(event, treeId, treeNode) {

        //不显示根信息
        if(treeNode.level == 0){
            return;
        }

        $(".right-content").css("visibility","visible");

        pageScope.loadDetailContent(treeNode.id);

    };

    /**
     * 加载详情内容
     */
    pageScope.loadDetailContent = function(id){

        $.ajax({
            url : "<@jspEl "baseURL" />${requestUrl}/query${classPrefix}ById",
            type : "post",
            dataType : "json",
            data : {"id" : id , "_csrf" : token},
            success : function(response) {

                if(response && response.success == true){

                    var ${propertyPrefix} = JSON.parse(response.data);

                <#list columnList as column>
                <#-- 普通文本,日期,单选/下拉/复选,富文本 -->
                    <#if column.formType?? && (column.formType == "text" || column.formType == "datetime" || column.formType == "radio"  || column.formType == "select"  || column.formType == "checkbox"  || column.formType == "richText")  >
                        $("#detail${column.fieldName?cap_first}").html(${propertyPrefix}.${column.fieldName});
                    <#-- 单图片 -->
                    <#elseif column.formType?? && column.formType == "singleImage" >
                        $("#detail${column.fieldName?cap_first}").html("<div class='kv-avatar'><input id='detailFile${column.fieldName?cap_first}' type='file' class='file-loading'/></div>");
                        $("#detailFile${column.fieldName?cap_first}").showImage({
                            imgUrl: ${propertyPrefix}.${column.fieldName}
                    });

                    <#-- 多图片上传 -->
                    <#elseif column.formType?? && column.formType == "multiImage" >
                        $("#detail${column.fieldName?cap_first}").html("<input id='detailMultiFile${column.fieldName?cap_first}' multiple type='file' class='file-loading' />");
                        $("#detailMultiFile${column.fieldName?cap_first}").showImage({
                            imgUrl: ${propertyPrefix}.${column.fieldName}
                    });
                    </#if>
                </#list>

                }

            }
        });

    };

    /**
     * 在节点后增加添加按钮
     * @param treeId
     * @param treeNode
     */
     pageScope.addHoverDom = function(treeId, treeNode) {

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
    pageScope.removeHoverDom = function(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#addBtn_space_" + treeNode.id).unbind().remove();
    };

    /**
     * 新增
     */
    pageScope.beforeAdd = function(treeNode) {

        var level = parseInt(treeNode.level) + 1;

        $.dialog.show({
            url: "<@jspEl "baseURL" />/view${requestUrl}/${propertyPrefix}_add.jsp",
            params: {"_csrf": token, "parentId": treeNode.id, "level": level}
        });

        return false;
    };

    /**
     * 删除
     */
    pageScope.beforeRemove = function(treeId, treeNode) {

        if (treeNode.level == 0) {
            $.msg.error("根节点不允许删除");
            return false;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: "<@jspEl "baseURL" />${requestUrl}/delete${classPrefix}",
                type: "post",
                dataType: "json",
                data: {"ids": treeNode.id, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.init${classPrefix}Tree();
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
    pageScope.beforeEdit = function(treeId, treeNode) {

        if (treeNode.level == 0) {
            $.msg.error("根节点不允许修改");
            return false;
        }

        $.dialog.show({
            url: "<@jspEl "baseURL" />${requestUrl}/toEdit${classPrefix}",
            params: {"_csrf": token, "id": treeNode.id}
        });

        return false;
    };

    /**
     * 准备拖拽
     * @param treeId
     * @param treeNodes
     * @returns {boolean}
     */
    pageScope.beforeDrag = function(treeId, treeNodes) {

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
    pageScope.beforeDrop = function(treeId, treeNodes, targetNode, moveType) {

        //只能移动同级节点
        if ("inner" == moveType || treeNodes[0].level != targetNode.level) {
            return false;
        }

        $.ajax({
            url: "<@jspEl "baseURL" />${requestUrl}/updatePosition",
            type: "post",
            dataType: "json",
            data: {"id1": treeNodes[0].id, "seq1": treeNodes[0].seq, "id2": targetNode.id, "seq2": targetNode.seq, "_csrf": token},
            success: function (response) {

                if (response && response.success == true) {
                    $.msg.success(response.msg);
                    pageScope.init${classPrefix}Tree();
                } else {
                    $.msg.error(response.msg);
                }

            }
        });

        return false;

    };

    $(function () {
        pageScope.init${classPrefix}Tree();
    });

</script>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">

            <div class="panel-heading">
                <h3 class="panel-title">${moduleName}管理</h3>
            </div>

            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="search${classPrefix}Form" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>

						<#list columnList as column>
							<#if column._isQueryField>
								<#if column.formType?? && column.formType == "text" >
                                    <td width="80" align="right">${column.remarks}：</td>
                                    <td width="150">
                                        <input type="text" name="${column.fieldName}" id="query${column.fieldName?cap_first}" class="form-control btn-block" aria-describedby="basic-addon1">
                                    </td>

								<#elseif column.formType?? && column.formType == "datetime" >
                                    <td width="80" align="right">${column.remarks}：</td>
                                    <td width="180">
                                        <div class='input-group date' id="query${column.fieldName?cap_first}">
                                            <input type='text' name="${column.fieldName}" class="form-control" data-date-format="YYYY-MM-DD HH:mm"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </td>

								</#if>
							</#if>
						</#list>
                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.init${classPrefix}Tree()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置" onclick="javascript:document.getElementById('search${classPrefix}Form').reset(); pageScope.init${classPrefix}Tree();">
                            </td>

                        </tr>

                    </table>
                </form>

                <div class="left-tree" >
                    <ul id="${propertyPrefix}Tree" class="ztree"></ul>
                </div>

                <div class="tree-content-middle-line"></div>

                <div class="right-content" style="visibility: hidden">

                    <form class="form-horizontal">

                        <#list columnList as column>
                            <#if column._isShowDetail && column.fieldName != "id" && column.formType??  >
                                <#-- 普通文本,日期,单选/下拉/复选,富文本 -->
                                <#if column.formType == "text" || column.formType == "datetime" || column.formType == "radio"  || column.formType == "select"  || column.formType == "checkbox"  || column.formType == "richText"  >
                                    <div class="form-group">
                                        <label class="col-md-2 col-sm-2 control-label tree-detail-label">${column.remarks}：</label>
                                        <div class="col-md-8 col-sm-8 detail-content" id="detail${column.fieldName?cap_first}" ></div>
                                    </div>

                                <#-- 单图片 -->
                                <#elseif column.formType == "singleImage" >
                                    <div class="form-group">
                                        <label class="col-md-2 col-sm-2 control-label tree-detail-label">${column.remarks}：</label>
                                        <div class="col-md-8 col-sm-8 detail-content" id="detail${column.fieldName?cap_first}" >
                                        </div>
                                    </div>

                                <#-- 多图片上传 -->
                                <#elseif column.formType == "multiImage" >
                                    <div class="form-group">
                                        <label class="col-md-2 col-sm-2 control-label tree-detail-label">${column.remarks}：</label>
                                        <div class="col-md-9 col-sm-9 detail-content" style="padding-right:40px;" id="detail${column.fieldName?cap_first}" >
                                        </div>
                                    </div>

                                </#if>
                            </#if>
                        </#list>

                    </form>
                </div>

            </div>

        </div>

    </div>
</div>