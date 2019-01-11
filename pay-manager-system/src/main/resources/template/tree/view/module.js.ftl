<#-- 宏定义,freemarker与mybatis,jsp都采用类似于el表达式的操作,因此需要进行转义 -->
<#macro mapperEl value>${r"#{"}${value}}</#macro>                                       <#-- #{value}-->
<#macro mapperLike value>${r"CONCAT('%','${"}${value}${r"}','%')"}</#macro>             <#-- CONCAT('%','${value}','%' ) -->
<#macro jspEl value>${r"${"}${value}}</#macro>                                          <#-- ${value} -->

var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */

(function () {

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
            url: baseURL + "${requestUrl}/query${classPrefix}List?_csrf=" + token,
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
            url : baseURL + "${requestUrl}/query${classPrefix}ById",
            type : "post",
            dataType : "json",
            data : {"id" : id , "_csrf" : token},
            success : function(response) {

                if(response && response.success == true){

                    var ${propertyPrefix} = JSON.parse(response.data);

                    <#list columnList as column>
                        <#-- 普通文本,日期,富文本 -->
                        <#if column.formType?? && (column.formType == "text" || column.formType == "datetime" || column.formType == "richText")  >
                             $("#detail${column.fieldName?cap_first}").html(${propertyPrefix}.${column.fieldName});
                        <#-- 单选/下拉/复选, -->
                        <#elseif column.formType?? && (column.formType == "radio"  || column.formType == "select"  || column.formType == "checkbox" )  >
                            $("#detail${column.fieldName?cap_first}").html(${propertyPrefix}.${column.fieldName}DictDesc);
                        <#-- 单图片 -->
                        <#elseif column.formType?? && column.formType == "singleImage" >
                            $("#detail${column.fieldName?cap_first}").html("<div class='kv-avatar'><input id='detailFile${column.fieldName?cap_first}' type='file' class='file-loading'/></div>");
                            $("#detailFile${column.fieldName?cap_first}").showImage({
                                imgUrl: ${propertyPrefix}.${column.fieldName}
                            });

                        <#-- 多图片上传 -->
                        <#elseif column.formType?? && column.formType == "multiImage" >
                            $("#detail${column.fieldName?cap_first}").html("<input id='detailMultiFile${column.fieldName?cap_first}' multiple type='file' class='file-loading' />");
                            $("#detailMultiFile${column.fieldName?cap_first}").showFileName({
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
            url: baseURL + "/view${requestUrl}/${propertyPrefix}_add.jsp?" + _csrf + "=" + token + "&parentId=" + treeNode.id + "&level=" + level,
            onLoad: function(){
                $('#add${classPrefix}Form').validation();
                <#list columnList as column>
                    <#if column._isAdd>
                        <#if column.formType == "datetime" >
                            //日期初始化
                            $("#add${column.fieldName?cap_first}").datetimepicker();

                        <#elseif column.formType == "singleImage" >
                            //单图片上传 - ${column.remarks}
                            $("#file${column.fieldName?cap_first}").singleImageUpload({
                                uploadExtraData: {"module": "${requestUrl}"} ,                     //上传图片扩展参数,指定所属模块
                                hiddenField:"#add${column.fieldName?cap_first}" ,                   //返回隐藏域路径
                                defaultPreviewContent: '<img src="'+baseURL+'/resources/img/default/default_avatar_male.jpg" style="width:160px" >' ,   //默认显示图片
                            });

                        <#elseif column.formType == "multiImage" >
                            //多图片上传 - ${column.remarks}
                            $("#multiFile${column.fieldName?cap_first}").multiFileUpload({
                                uploadExtraData: {"module": "${requestUrl}"} ,                  //上传图片扩展参数,指定所属模块
                                hiddenField:"#add${column.fieldName?cap_first}" ,              //返回隐藏域路径
                            });

                        <#-- 下拉框 -->
                        <#elseif column.formType == "select" >
                            //加载字典(下拉框)
                            $("#add${column.fieldName?cap_first}").loadDictionaryForSelect({
                                "inputName":"${column.fieldName}",
                                "dictType":"${column.dictType}"
                            });

                        <#-- 单选按钮 -->
                        <#elseif column.formType == "radio" >
                            //加载字典(单选按钮)
                            $("#add${column.fieldName?cap_first}").loadDictionaryForRadio({
                                "inputName":"${column.fieldName}",
                                "dictType":"${column.dictType}"
                            });

                        <#-- 复选框 -->
                        <#elseif column.formType == "checkbox" >
                            //加载字典(复选框)
                            $("#add${column.fieldName?cap_first}").loadDictionaryForCheckbox({
                                "inputName":"${column.fieldName}",
                                "dictType":"${column.dictType}"
                            });

                        <#-- 富文本 -->
                        <#elseif column.formType == "richText" >
                            //实例化编辑器
                            var ueditor = UE.getEditor('add${column.fieldName?cap_first}');

                        </#if>
                    </#if>
                </#list>
            } ,
            buttonEvents: {
                success: function () {

                    if (!$("#add${classPrefix}Form").valid()) {             //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");              //防止重复提交
                    btn.attr("disabled", "disabled");

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

                    $('#add${classPrefix}Form').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if(response && response.success){
                                $.msg.success(response.msg);
                                pageScope.init${classPrefix}Tree();
                                $(".modal-footer .btn-danger").trigger("click");
                            }
                            else{
                                $.msg.error(response.msg);
                            }

                        }, error: function (e) {
                            btn.removeAttr("disabled");
                        }

                    });

                }
            }
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
                url: baseURL + "${requestUrl}/delete${classPrefix}",
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
            url: baseURL + "/view${requestUrl}/${propertyPrefix}_edit.jsp?" + _csrf + "=" + token + "&id=" + treeNode.id + "&level=" + treeNode.level,
            onLoad: function () {

                //表单验证初始化
                $('#edit${classPrefix}Form').validation();
                <#list columnList as column>
                    <#if column._isEdit>
                        <#if column.formType == "datetime" >
                        //日期初始化
                        $("#edit${column.fieldName?cap_first}").datetimepicker();
                        $("#edit${column.fieldName?cap_first}").val(treeNode.${column.fieldName});

                        <#elseif column.formType == "singleImage" >
                        //单图片上传 - ${column.remarks}
                        $("#file${column.fieldName?cap_first}").singleImageUpload({
                            uploadExtraData: {"module": "${requestUrl}"} ,                     //上传图片扩展参数,指定所属模块
                            hiddenField:"#edit${column.fieldName?cap_first}"  ,                 //返回隐藏域路径
                            imgUrl:  treeNode.${column.fieldName} ,      //图片路径,编辑时回显(可选)
                            defaultPreviewContent: '<img src="'+baseURL+'/resources/img/default/default_avatar_male.jpg" style="width:160px" >' ,   //默认显示图片
                        });

                        <#elseif column.formType == "multiImage" >
                            //多图片上传 - ${column.remarks}
                            $("#multiFile${column.fieldName?cap_first}").multiFileUpload({
                                uploadExtraData: {"module": "${requestUrl}"} ,                       //上传图片扩展参数,指定所属模块
                                hiddenField:"#edit${column.fieldName?cap_first}" ,                  //返回隐藏域路径
                                imgUrl: treeNode.${column.fieldName}       //图片路径,编辑时回显(可选)
                            });

                        <#-- 下拉框 -->
                        <#elseif column.formType == "select" >
                            //加载字典(下拉框)
                            $("#edit${column.fieldName?cap_first}").loadDictionaryForSelect({
                                "inputName":"${column.fieldName}",
                                "dictType":"${column.dictType}",
                                "value":treeNode.${column.fieldName}
                            });

                        <#-- 单选按钮 -->
                        <#elseif column.formType == "radio" >
                            //加载字典(单选按钮)
                            $("#edit${column.fieldName?cap_first}").loadDictionaryForRadio({
                                "inputName":"${column.fieldName}",
                                "dictType":"${column.dictType}",
                                "value":treeNode.${column.fieldName}
                            });

                        <#-- 复选框 -->
                        <#elseif column.formType == "checkbox" >
                            //加载字典(复选框)
                            $("#edit${column.fieldName?cap_first}").loadDictionaryForCheckbox({
                                "inputName":"${column.fieldName}",
                                "dictType":"${column.dictType}",
                                "value":treeNode.${column.fieldName}
                            });

                        <#-- 富文本 -->
                        <#elseif column.formType == "richText" >
                            //实例化编辑器
                            var ueditor = UE.getEditor('edit${column.fieldName?cap_first}');
                            ueditor.ready(function() {
                                ueditor.setContent(treeNode.${column.fieldName});
                            });

                        <#-- 其它 -->
                        <#else >
                            $("#edit${column.fieldName?cap_first}").val(treeNode.${column.fieldName});
                        </#if>

                    </#if>
                </#list>

            },
            buttonEvents: {
                success: function () {

                    if (!$("#edit_${propertyPrefix}_form").valid()) {             //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");              //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#edit${classPrefix}Form').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {

                            btn.removeAttr("disabled");

                            if(response && response.success){
                                $.msg.success(response.msg);

                                //重新加载树
                                pageScope.init${classPrefix}Tree();

                                //重新加载详情内容
                                pageScope.loadDetailContent(treeNode.id);

                                $(".modal-footer .btn-danger").trigger("click");
                            }
                            else{
                                $.msg.error(response.msg);
                            }

                        } , error: function (e) {
                            btn.removeAttr("disabled");
                        }

                    });

                }
            }
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
            url: baseURL + "${requestUrl}/updatePosition",
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

})();