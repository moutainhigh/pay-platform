<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.init${classPrefix}Table = function () {

        pageScope.${propertyPrefix}Table = $('#${propertyPrefix}Table').initBootstrapTable({
            url: baseURL + '${requestUrl}/query${classPrefix}List?_csrf=' + token,
            method: 'post',
            dataType: "json",
            toolbar: '#${propertyPrefix}Pager',
            queryParams: function (params) {
                $.extend(params, $('#search${classPrefix}Form').serializeObject());
                return params;
            },
            onClickRow:function(row,tr){
                pageScope.currentrow = row;
            } ,
            responseHandler: function (response) {
                var griddata = {};
                try {
                    griddata.rows = response.list || [];
                    griddata.total = response.total || 0;
                } catch (e) {
                }
                return griddata;
            },
            columns: [
                {title: '全选', checkbox: true},
                {
                    title: '序号',
                    width: 46,
                    formatter: function () {
                        return arguments[2] + 1;
                    }
                },
            <#list columnList as column>
        <#if column._isShowList>
        <#if column.columnType == "datetime" || column.columnType = "DATETIME" >
            {
                title: '${column.remarks}',
                field: '${column.fieldName}',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return $.date.formatToDateTime(value);
                }
            },
            <#elseif column.formType?? && (column.formType == "radio" || column.formType == "checkbox" || column.formType == "select") >
        {
            title: '${column.remarks}',
            field: '${column.fieldName}',
            align: 'center',
            sortable: true,
            formatter: function (value, row, index) {
                return row.${column.fieldName}DictDesc;
            }
        },
        <#else>
        {title: '${column.remarks}', field: '${column.fieldName}', align: 'center', sortable: true},
        </#if>
        </#if>
        </#list>
        {
            title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
                var html = "";
                html += "<button type='button' class='btn btn-link' onclick='pageScope.edit${classPrefix}()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                html += "<button type='button' class='btn btn-link' onclick='pageScope.show${classPrefix}Detail()' ><i class='glyphicon glyphicon-file'></i></button>";
                html += "<button type='button' class='btn btn-link' onclick='pageScope.delete${classPrefix}(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                return html;
            }
        }
        ]
    });

    };

    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.${propertyPrefix}Table.bootstrapTable('refresh');
    };

    /**
     * 删除${moduleName}
     */
    pageScope.delete${classPrefix} = function (id) {

        var ids = $("#${propertyPrefix}Table input[name='btSelectItem']:checked").getCheckedIdsNotQuotes(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "${requestUrl}/delete${classPrefix}",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.${propertyPrefix}Table.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除${moduleName}
     */
    pageScope.delete${classPrefix}ByLogic = function (id) {

        var ids = $("#${propertyPrefix}Table input[name='btSelectItem']:checked").getCheckedIdsNotQuotes(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "${requestUrl}/delete${classPrefix}ByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.${propertyPrefix}Table.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

	/**
	 * 查看${moduleName}详情
	 * @param id
	 */
	pageScope.show${classPrefix}Detail = function () {

        var viewdialog = bootbox.dialog({
            title: '查看${moduleName}详情'
            , url:baseURL + "/jsp${requestUrl}/${propertyPrefix}_detail.jsp?" + _csrf + "=" + token
            , closeButton: true
            , className: "permission-view-modal"
            , buttons: {
                cancel: {
                    label: '取消',
                    className: "btn-danger",
                    callback: function () {

                    }
                }
            }
        });
        viewdialog.on('loaded.bs.modal', function () {
            <#list columnList as column>
                <#if column.formType?? && (column.formType == "radio" || column.formType == "checkbox" || column.formType == "select") >
                    $("#detail${column.fieldName?cap_first}").val(pageScope.currentrow.${column.fieldName}DictDesc);
                <#elseif column.formType?? && column.formType == "richText" >
                    $("#detail${column.fieldName?cap_first}").html(pageScope.currentrow.${column.fieldName});
                <#-- 单图片上传 -->
				<#elseif column.formType?? && column.formType == "singleImage" >
                    //图片显示 - ${column.remarks}
                    $("#file${column.fieldName?cap_first}").showImage({
                   	    imgUrl: pageScope.currentrow.${column.fieldName}
                    });
				<#elseif column.formType?? && column.formType == "multiImage" >
                    //图片显示 - ${column.remarks}
                    $("#multiFile${column.fieldName?cap_first}").showImage({
                    	imgUrl: pageScope.currentrow.${column.fieldName}
                    });
                <#else >
                    $("#detail${column.fieldName?cap_first}").val(pageScope.currentrow.${column.fieldName});
                </#if>
            </#list>
        });

	};

	/**
	 * 新增${moduleName}
	 */
	pageScope.add${classPrefix} = function () {

        var dialog = $.dialog.addoredit({
            title: '添加${moduleName}',
            url: baseURL + "/jsp${requestUrl}/${propertyPrefix}_add.jsp?" + _csrf + "=" + token,
            confirmcallback: function () {

                if (!$('#add${classPrefix}Form').valid()) {
                    return false;
                }

                var btn = $(".modal-footer .btn-success");        //防止重复提交
                btn.attr("disabled","disabled");
                <#list columnList as column>
                    <#-- 富文本 -->
                    <#if column.formType?? && column.formType == "richText" >
                        var content = UE.getEditor('add${column.fieldName?cap_first}').getContent();
                        if ($.validate.isEmpty(content)) {
                            $.msg.fail('请输入${column.remarks}');
                            btn.removeAttr("disabled");
                            return false;
                        } else {
                            $("#addReal${column.fieldName?cap_first}").val(content);
                        }
                    </#if>
                </#list>

                //提交数据
                $('#add${classPrefix}Form').ajaxSubmit({
                    dataType: 'json',
                    success: function (response) {

                        btn.removeAttr("disabled");

                        if (response && response.success) {
                            $.msg.success(response.msg);

                            $(".modal-footer .btn-danger").trigger("click");
                            pageScope.${propertyPrefix}Table.bootstrapTable('refresh');

                        } else {
                            $.msg.fail(response.msg);
                            return false;
                        }

                    },
                    error: function () {
                        $.msg.fail('新增失败，可能是由网络原因引起的，请稍候再试');
                        btn.removeAttr("disabled");
                        return false;
                    }
                });

                return false;
            }
        });

	};

    /**
     * 编辑${moduleName}
     */
    pageScope.edit${classPrefix} = function () {

        $.dialog.addoredit({
            title: "修改${moduleName}",
            url: baseURL + "/jsp${requestUrl}/${propertyPrefix}_edit.jsp?" + _csrf + "=" + token,
            afterloaded: function () {
                <#list columnList as column>
                    <#if column.formType?? && column.formType == "datetime" >
                        //日期初始化
                        $("#edit${column.fieldName?cap_first}Div").datetimepicker();
                        $("#edit${column.fieldName?cap_first}").val(pageScope.currentrow.${column.fieldName});

                    <#-- 下拉框 -->
                    <#elseif column.formType?? && column.formType == "select" >
                        //加载字典(下拉框)
                        $("#edit${column.fieldName?cap_first}").loadDictionaryForSelect({
                            "inputName":"${column.fieldName}",
                            "dictType":"${column.dictType}",
                            "value":pageScope.currentrow.${column.fieldName}
                        });

                    <#-- 单选按钮 -->
                    <#elseif column.formType?? && column.formType == "radio" >
                        //加载字典(单选按钮)
                        $("#edit${column.fieldName?cap_first}").loadDictionaryForRadio({
                            "inputName":"${column.fieldName}",
                            "dictType":"${column.dictType}",
                            "value":pageScope.currentrow.${column.fieldName}
                        });

                    <#-- 复选框 -->
                    <#elseif column.formType?? && column.formType == "checkbox" >
                          //加载字典(复选框)
                        $("#edit${column.fieldName?cap_first}").loadDictionaryForCheckbox({
                            "inputName":"${column.fieldName}",
                            "dictType":"${column.dictType}",
                            "value":pageScope.currentrow.${column.fieldName}
                        });

                    <#-- 富文本 -->
                    <#elseif column.formType?? && column.formType == "richText" >
                        //实例化编辑器
                        var ueditor = UE.getEditor('edit${column.fieldName?cap_first}');

                        ueditor.ready(function() {
                            ueditor.setContent(pageScope.currentrow.${column.fieldName});
                        });

                    <#elseif column.formType?? && column.formType == "singleImage" >
                        //单图片上传 - ${column.remarks}
                        $("#file${column.fieldName?cap_first}").singleImageUpload({
                            uploadExtraData: {"module": "${requestUrl}"} ,                     //上传图片扩展参数,指定所属模块
                            hiddenField:"#edit${column.fieldName?cap_first}"  ,                 //返回隐藏域路径
                            imgUrl: pageScope.currentrow.${column.fieldName}       //图片路径,编辑时回显(可选)
                        });

                    <#elseif column.formType?? && column.formType == "multiImage" >
                        //多图片上传 - ${column.remarks}
                         $("#multiFile${column.fieldName?cap_first}").multiImageUpload({
                            uploadExtraData: {"module": "${requestUrl}"} ,                       //上传图片扩展参数,指定所属模块
                            hiddenField:"#edit${column.fieldName?cap_first}" ,                  //返回隐藏域路径
                            imgUrl: pageScope.currentrow.${column.fieldName}       //图片路径,编辑时回显(可选)
                        });
                    <#-- 其它 -->
                    <#else >
                        $("#edit${column.fieldName?cap_first}").val(pageScope.currentrow.${column.fieldName});

                    </#if>
                </#list>

            }
            //修改事件
            , confirmcallback: function () {

                if (!$('#edit${classPrefix}Form').valid()) {
                    return false;
                }

                var btn = $(".modal-footer .btn-success");        //防止重复提交
                btn.attr("disabled","disabled");
                <#list columnList as column>
                    <#-- 富文本 -->
                    <#if column.formType?? && column.formType == "richText" >
                    var content = UE.getEditor('edit${column.fieldName?cap_first}').getContent();
                    if ($.validate.isEmpty(content)) {
                        $.msg.fail('请输入${column.remarks}');
                        btn.removeAttr("disabled");
                        return false;
                    } else {
                        $("#editReal${column.fieldName?cap_first}").val(content);
                    }
                    </#if>
                </#list>

                $('#edit${classPrefix}Form').ajaxSubmit({
                    dataType: 'json',
                    success: function (response) {

                        btn.removeAttr("disabled");

                        if (response && response.success) {
                            $.msg.success(response.msg);

                            $(".modal-footer .btn-danger").trigger("click");
                            pageScope.${propertyPrefix}Table.bootstrapTable('refresh');

                        } else {
                            $.msg.fail(response.msg);
                            return false;
                        }

                    },
                    error: function () {
                        $.msg.fail('修改失败，可能是由网络原因引起的，请稍候再试');
                        btn.removeAttr("disabled");
                        return false;
                    }

                });

                return false;

            }
        })

    };

})();