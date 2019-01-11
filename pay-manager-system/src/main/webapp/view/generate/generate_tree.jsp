<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 16/10/1
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

    var dbTable = {};            //数据库属性 - 表格
    var viewTable = {};          //页面属性 - 表格
    var columnList = {};         //字段信息

    var formTypeList = {};        //表单类型

    //默认插入的字段,添加/编辑页面不会出现该表单组件以供输入
    var defaultAddField = ["id", "createTime", "isDel", "parentId", "level", "seq"];
    function isNotDefaultAddField(fieldName) {
        return !defaultAddField.contains(fieldName);
    }

    /**
     * 加载表信息
     */
    function loadAutoCompleteByTableName() {

        var $input = $('#queryTableName');

        $.ajax({
            url: baseURL + "/generate/getAllTableNameList",
            type: "post",
            dataType: "json",
            async: true,
            data: {"_csrf": token},
            success: function (response) {

                if (response && response.success) {
                    $input.typeahead({
                        source: response.data,
                        autoSelect: true,
                        afterSelect: function (item) {
                            $("#tableName").val(item);
                            dbTable.bootstrapTable("refresh");
                            viewTable.bootstrapTable("refresh");
                        }
                    });
                }

            }
        });

    }

    /**
     * 加载表字段信息
     */
    dbTable = $('#dbTable').initBootstrapTable({
        url: '${baseURL}/generate/getColumnListByTableName?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#dbPager',
        pageSize: 50,
        pageNumber: 1,
        pageList: [50, 100],
        queryParams: function (params) {
            $.extend(params, {"tableName": $("#tableName").val()});
            return params;
        },
        responseHandler: function (response) {
            var gridData = {};
            try {
                gridData.rows = response.data || [];
                gridData.total = response.data.length || 0;
                columnList = response.data;
            } catch (e) {
            }
            return gridData;
        },
        columns: [
            {
                title: '序号',
                width: 46,
                formatter: function () {
                    return arguments[2] + 1;
                }
            },
            {
                title: '列名',
                field: 'columnName',
                align: 'center',
                sortable: false
            },
            {
                title: '列类型',
                field: 'columnType',
                align: 'center',
                sortable: false,
                formatter: function (value, row, index) {
                    return row.columnType + "(" + row.size + ")";
                }
            },
            {
                title: '字段说明',
                field: 'remarks',
                align: 'center',
                sortable: false
            },
            {
                title: '主键',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' checked readonly class='generate-checkbox' name='primaryKeyRow' />";
                    } else {
                        return "<input type='hidden' name='primaryKeyRow' class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '父节点Id',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "parentId") {
                        return "<input type='checkbox' checked disabled class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '必填',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' id='selectAllIsRequired' class='generate-checkbox' />";
                    } else if (isNotDefaultAddField(row.fieldName)) {
                        return "<input type='checkbox' name='_isRequired' class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '添加',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' id='selectAllAdd' name='_isAdd' class='generate-checkbox' />";
                    } else if (isNotDefaultAddField(row.fieldName)) {
                        return "<input type='checkbox' name='_isAdd' class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '编辑',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' id='selectAllEdit' name='_isEdit' class='generate-checkbox' />";
                    } else if (isNotDefaultAddField(row.fieldName)) {
                        return "<input type='checkbox' name='_isEdit' class='generate-checkbox' />";
                    }
                }
            }
        ]
    });

    /**
     * 加载页面字段信息
     */
    viewTable = $('#viewTable').initBootstrapTable({
        url: '${baseURL}/generate/getColumnListByTableName?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#viewPager',
        pageSize: 50,
        pageNumber: 1,
        pageList: [50, 100],
        queryParams: function (params) {
            $.extend(params, {"tableName": $("#tableName").val()});
            return params;
        },
        responseHandler: function (response) {
            var gridData = {};
            try {
                gridData.rows = response.data || [];
                gridData.total = response.data.length || 0;
            } catch (e) {
            }
            return gridData;
        },
        columns: [
            {
                title: '序号',
                width: 46,
                formatter: function () {
                    return arguments[2] + 1;
                }
            },
            {
                title: 'java属性名',
                field: 'fieldName',
                align: 'center',
                sortable: false
            },
            {
                title: 'java类型',
                field: 'fieldType',
                align: 'center',
                sortable: false
            },
            {
                title: '表单说明',
                field: 'remarks',
                align: 'center',
                width: 295,
                sortable: false,
                formatter: function (value, row, index) {
                    if (row.fieldName != "id") {
                        return "<input type='text' name='remarks' value='" + value + "' class='form-control btn-block' style='height: 30px; width: 270px;margin-left: 3px;' />";
                    }
                }
            },
            {
                title: '树显示字段',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' id='selectAllTreeShowList' name='_isTreeShowField' class='generate-checkbox' />";
                    } else {
                        return "<input type='checkbox' name='_isTreeShowField' class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '详情',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' id='selectAllShowDetail' name='_isShowDetail' class='generate-checkbox' />";
                    } else {
                        return "<input type='checkbox' name='_isShowDetail' class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '查询',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName == "id") {
                        return "<input type='checkbox' id='selectAllQueryList' name='_isQueryField' class='generate-checkbox' />";
                    } else {
                        return "<input type='checkbox' name='_isQueryField' class='generate-checkbox' />";
                    }
                }
            },
            {
                title: '查询匹配方式',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName != "id") {
                        var html = "<select name='matchPattern' class='form-control' style='width:80px;height:24px;' >";
                        html += "<option value='=' >=</option>";
                        html += "<option value='!=' >!=</option>";
                        html += "<option value='like' >like</option>";
                        html += "<option value='>' >></option>";
                        html += "<option value='>=' >>=</option>";
                        html += "<option value='<' ><</option>";
                        html += "<option value='<=' ><=</option>";
                        html += "</select>";
                        return html;
                    }
                }
            },
            {
                title: '表单类型',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName != "id" && row.fieldName != "createTime" && row.fieldName != "isDel") {
                        var html = "<select name='formType' class='form-control' style='width:130px;height:24px;font-size:12px !important;' >";
                        for (var i = 0; i < formTypeList.length; i++) {
                            html += "<option value='" + formTypeList[i].itemCode + "' >" + formTypeList[i].itemDesc + "</option>";
                        }
                        html += "</select>";
                        return html;
                    }
                }
            },
            {
                title: '字典类型',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.fieldName != "id" && row.fieldName != "createTime" && row.fieldName != "isDel") {
                        return "<input type='text' name='dictType' class='form-control btn-block' style='height: 30px; width: 130px;margin-left: 3px;' />";
                    }
                }
            }
        ]
    });

    $(function () {

        //加载全部表名
        loadAutoCompleteByTableName();

        //获取可用表单类型
        $.getDictionaryInfo("formType", function (data) {
            formTypeList = data;
        });

        //控制全选/全不选
        $(document).on("click", "#selectAllTreeShowList", function () {
            var checked = this.checked;
            $("input[name='_isTreeShowField']").prop("checked", checked);
        });

        $(document).on("click", "#selectAllQueryList", function () {
            var checked = this.checked;
            $("input[name='_isQueryField']").prop("checked", checked);
        });

        $(document).on("click", "#selectAllShowDetail", function () {
            var checked = this.checked;
            $("input[name='_isShowDetail']").prop("checked", checked);
        });

        $(document).on("click", "#selectAllAdd", function () {
            var checked = this.checked;
            $("input[name='_isAdd']").prop("checked", checked);
        });

        $(document).on("click", "#selectAllEdit", function () {
            var checked = this.checked;
            $("input[name='_isEdit']").prop("checked", checked);
        });

        $(document).on("click", "#selectAllIsRequired", function () {
            var checked = this.checked;
            $("input[name='_isRequired']").prop("checked", checked);
        });

    });

    /**
     * 生成树组件代码
     */
    function generateTreeModuleCode() {

        var params = {
            tableName: $("#queryTableName").val(),
            classPrefix: $("#classPrefix").val(),
            rootPath: $("#rootPath").val(),
            packageName: $("#packageName").val(),
            moduleName: $("#moduleName").val(),
            requestUrl: $("#requestUrl").val()
        };

        //字段信息
        var showTreeFieldCount = 0;      //树菜单显示字段数量
        var queryCount = 0;         //作为查询的数量
        var addCount = 0;           //添加数量
        var editCount = 0;          //编辑数量
        $("#dbTable input[name='primaryKeyRow']").each(function (index, obj) {
            if (index != 0) {
                var _isRequired = $(this).parent().parent().find("[name='_isRequired']").prop("checked") || false;
                columnList[index]._isRequired = _isRequired;

                var _isParentId = $(this).parent().parent().find("[name='_isParentId']").prop("checked") || false;
                columnList[index]._isParentId = _isParentId;

                var _isAdd = $(this).parent().parent().find("[name='_isAdd']").prop("checked") || false;
                addCount = (_isAdd ? (addCount + 1) : addCount);
                columnList[index]._isAdd = _isAdd;

                var _isEdit = $(this).parent().parent().find("[name='_isEdit']").prop("checked") || false;
                editCount = (_isEdit ? (editCount + 1) : editCount);
                columnList[index]._isEdit = _isEdit;

            }

        });
        var validateDictFlag = false;
        $("#viewTable input[name='_isTreeShowField']").each(function (index, obj) {
            if (index != 0) {
                var remarks = $(this).parent().parent().find("[name='remarks']").val();
                columnList[index].remarks = remarks;

                var _isTreeShowField = $(this).parent().parent().find("[name='_isTreeShowField']").prop("checked") || false;
                showTreeFieldCount = (_isTreeShowField ? (showTreeFieldCount + 1) : showTreeFieldCount);
                columnList[index]._isTreeShowField = _isTreeShowField;

                var _isQueryField = $(this).parent().parent().find("[name='_isQueryField']").prop("checked") || false;
                queryCount = (_isQueryField ? (queryCount + 1) : queryCount);
                columnList[index]._isQueryField = _isQueryField;

                var _isShowDetail = $(this).parent().parent().find("[name='_isShowDetail']").prop("checked") || false;
                columnList[index]._isShowDetail = _isShowDetail;

                var matchPattern = $(this).parent().parent().find("[name='matchPattern']").val();
                columnList[index].matchPattern = matchPattern;

                var formType = $(this).parent().parent().find("[name='formType']").val();
                columnList[index].formType = formType;

                var dictType = $(this).parent().parent().find("[name='dictType']").val();
                columnList[index].dictType = dictType;

                if (formType == "radio" || formType == "checkbox" || formType == "select") {
                    if ($.validate.isEmpty(dictType)) {
                        validateDictFlag = true;
                    }
                }

            }
        });
        params.columnListJson = JSON.stringify(columnList);

        if (validateDictFlag) {
            $.msg.toast("单选按钮/复选框/下拉框必须输入字典类型!");
            return;
        }

        //相关输入验证
        if ($.validate.isEmpty(params.tableName)) {
            $.msg.toast("请输入表名!");
            return;
        }

        if ($.validate.isEmpty(params.classPrefix)) {
            $.msg.toast("请输入类名!");
            return;
        }

        if (!$.validate.isUpperCaseStart(params.classPrefix)) {
            $.msg.toast("类名必须以大写开头!");
            return;
        }

        if ($.validate.isEmpty(params.rootPath)) {
            $.msg.toast("请输入项目根路径!");
            return;
        }

        if (!$.validate.isJavaPackageName(params.packageName)) {
            $.msg.toast("请输入合法的包名!");
            return;
        }

        if ($.validate.isEmpty(params.moduleName)) {
            $.msg.toast("请输入模块名称");
            return;
        }

        if ($.validate.isEmpty(params.requestUrl)) {
            $.msg.toast("请输入请求路径!");
            return;
        }

        if ($.validate.isEmpty(params.requestUrl)) {
            $.msg.toast("请输入请求路径!");
            return;
        }

        if (addCount == 0) {
            $.msg.toast("最少选择一个添加字段!");
            return;
        }

        if (editCount == 0) {
            $.msg.toast("最少选择一个编辑字段!");
            return;
        }

        if (showTreeFieldCount == 0) {
            $.msg.toast("请选择树显示字段!");
            return;
        }

        if (showTreeFieldCount > 1) {
            $.msg.toast("树形菜单最多只能显示一个字段!");
            return;
        }

        if (queryCount == 0) {
            $.msg.toast("最少选择一个查询字段!");
            return;
        }

        $.ajax({
            url: baseURL + "/generate/generateTreeModuleCode?_csrf=" + token,
            type: "post",
            dataType: "json",
            async: true,
            data: params,
            success: function (response) {

                if (response && response.success) {
                    $.msg.success(response.msg);
                } else {
                    $.msg.error(response.msg);
                }

            }
        });

    }

</script>


<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">树形菜单生成</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchGenerateForm" method="post">
                    <table class="search generate" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <input type="hidden" id="tableName" name="tableName"/>

                        <tr>
                            <td width="80" align="right">表名：</td>
                            <td width="200"><input type="text" id="queryTableName" name="tableName" class="form-control btn-block" placeholder="请输入表名称" aria-describedby="basic-addon1"
                                                   autocomplete="on"/></td>
                            <td width="120" align="left" class="remark">tb_banner</td>

                            <td width="80" align="right">类名：</td>
                            <td width="200"><input type="text" id="classPrefix" name="classPrefix" class="form-control btn-block" placeholder="请输入类名" aria-describedby="basic-addon1"
                                                   autocomplete="on"/></td>
                            <td width="120" align="left" class="remark">Banner</td>

                            <td></td>

                        </tr>

                        <tr>
                            <td width="80" align="right">项目根路径：</td>
                            <td width="200"><input type="text" id="rootPath" name="rootPath" class="form-control btn-block" placeholder="请输入项目根路径" aria-describedby="basic-addon1" autocomplete="on"/>
                            </td>
                            <td width="120" align="left" class="remark">d:/project/项目名</td>

                            <td width="80" align="right">包名称：</td>
                            <td width="200"><input ype="text" id="packageName" name="packageName" class="form-control btn-block" placeholder="请输入包名" aria-describedby="basic-addon1"></td>
                            <td width="120" align="left" class="remark">com.example.modules.banner</td>

                            <td></td>
                        </tr>

                        <tr>
                            <td width="80" align="right">模块名称：</td>
                            <td width="200"><input ype="text" id="moduleName" name="moduleName" class="form-control btn-block" placeholder="请输入模块名称" aria-describedby="basic-addon1"></td>
                            <td width="120" align="left" class="remark">广告图片</td>

                            <td width="80" align="right">请求路径：</td>
                            <td width="200"><input ype="text" id="requestUrl" name="requestUrl" class="form-control btn-block" placeholder="请输入请求路径" aria-describedby="basic-addon1"></td>
                            <td width="120" align="left" class="remark">/banner</td>

                            <td></td>
                        </tr>

                    </table>
                </form>

                <button id="add" class="btn btn-success" onclick="generateTreeModuleCode()" style="display: block;">
                    <i class="glyphicon glyphicon-plus"></i> 生成代码
                </button>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <%--tab配置 , 通过href锚链接定义--%>
                    <ul class="nav nav-tabs" style="display: inline-block !important;margin-top: 4px;">
                        <li role="presentation" class="active"><a href="#tabDbInfo" data-toggle="tab">数据库属性</a></li>
                        <li role="presentation"><a href="#tabViewInfo" data-toggle="tab">页面属性</a></li>
                    </ul>

                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">

                    <div class="tab-content">

                        <div class="tab-pane fade in active" id="tabDbInfo">
                            <table id="dbTable"></table>
                            <div id="dbPager"></div>
                        </div>

                        <div class="tab-pane fade" id="tabViewInfo">
                            <table id="viewTable"></table>
                            <div id="viewPager"></div>
                        </div>

                    </div>

                </div>

            </div>
        </div>

    </div>
</div>