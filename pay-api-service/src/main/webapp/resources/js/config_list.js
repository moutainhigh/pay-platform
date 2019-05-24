var mecrchantTable = {};

(function(){
    $(document).ready(function(){
        $.post(baseURL+"/merchant/checkAdmin",{'_csrf':token},function (obj) {
            var visible = obj.res + "" == "true";
            mecrchantTable = $('#config_list_table').bootstrapTable({
                url:baseURL + '/config/query?' + _csrf + '=' + token
                ,method:'post'
                ,dataType: "json"
                ,cache:false
                ,striped:true
                , pagination: true
                ,"queryParamsType": "limit"
                ,singleSelect: false
                ,contentType: "application/x-www-form-urlencoded"
                ,pageSize: 10
                ,pageNumber:1
                ,pageList:[10]
                ,search: false
                ,showColumns: true
                ,showRefresh:true
                ,showPaginationSwitch:false
                ,smartDisplay:false
                ,maintainSelected:true
                ,showToggle:false
                ,idField:'ID'
                , sidePagination: "server"
                ,toolbar:'#config_toolbar'
                ,queryParams:function(params){
                    $.extend(params,{
                        //分页参数
                        'offset':params.offset,			//从那条记录开始取数据,例如limit 0,10
                        'limit':params.limit,			//每页显示条数
                        'sort':params.sort,				//排序字段
                        'order':params.order,			//排序方式
                        'root_id':$('#root_id').val(),
                        'name':$('#name').val(),
                        'read_only':$('#read_only').val()
                    });
                    return params;
                }
                ,responseHandler:function(response){
                    var griddata = {};		//表格数据
                    try {
                        griddata.rows = response.rows || [];			//当前页的数据
                        griddata.total = response.total;				//总记录数
                    }catch(e){
                        griddata.rows = [];
                        griddata.total = 0;
                    }
                    return griddata;
                },

                onClickRow:function(row,tr){
                    mecrchantTable.currentrow = row;
                }
                ,columns:[
                    // {
                    //     title: '全选'
                    //     , checkbox:true
                    // }
                    // ,
                    {
                        title:'编号'
                        , formatter:function(){
                        return arguments[2]+1;
                    }
                    }
                    ,{
                        field: 'TYPE',
                        visible:visible
                        ,title:'产品类型'
                        , sortable:true
                        , formatter:function(value,row,index){
                            return value==1?"酷卡":(value==2?"收银台":"无卡支付");
                        }
                    }
                    // ,{
                    //     field: 'CONFIG_KEY',
                    //     visible:visible
                    //     ,title:'密匙'
                    //     , sortable:true
                    //     , formatter:function(value,row,index){
                    //         return value;
                    //     }
                    // }
                    , {
                        field: 'ROOT_NAME'
                        ,visible:visible
                        , title: '零级代理'
                        , sortable:true
                    }
                    ,{
                        field: 'DESCRIPTION'
                        , title:'名称'
                        , sortable:true
                        , formatter:function(value,row,index){
                            return value;
                        }
                    }
                    // ,{
                    //     field: 'CONFIG_KEY'
                    //     , title:'键'
                    //     , sortable:true
                    //     , formatter:function(value,row,index){
                    //         return value;
                    //     }
                    // }
                    ,{
                        field: 'CONFIG_VALUE',
                        width:200
                        , title:'值'
                        , sortable:true
                        , formatter:function(value,row,index){
                            if(row.DESCRIPTION.indexOf("费率")>-1){

                            }
                            return value;
                        }
                    }
                    ,{
                        field: 'READ_ONLY'
                        ,title:'状态'
                        , sortable:true
                        , formatter:function(value,row,index){
                            return value+""=="false"?"<span style='color:green'>可修改</span>":"<span style='color:red'>只读</span>";
                        }
                    }
                    // ,{
                    //     field: 'UPDATE_TIME'
                    //     ,title:'最近修改'
                    //     , sortable:false
                    //     , formatter:function(value,row,index){
                    //         return value;
                    //     }
                    // }
                    // ,{
                    //     field: 'UPDATE_NAME'
                    //     ,title:'修改人'
                    //     , sortable:false
                    //     , formatter:function(value,row,index){
                    //     	return value;
                    //     }
                    // }
                    ,{
                        title:'操作'
                        , align:'center'
                        , formatter:function(value,row,index){
                            var operationButton = "";
                            if(row.READ_ONLY+""=="false") {
                                operationButton+='<a href="javascript:void(0)" class="glyphicon glyphicon-pencil" onclick="updateconfigDetail()" ></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                            }
                            operationButton += '<a href="javascript:void(0)"  class="glyphicon glyphicon-file" onclick="showconfigDetail()"></a>';
                            return operationButton;
                        }
                    }
                ]
            });
        },"json");
    });
})();

/*
 * 查询用户,刷新表格即可
 */
function searchconfig(){
	mecrchantTable.bootstrapTable('refresh');
}

/*
 * 重置查询
 */
function resetSearch(){
	$("#config_search")[0].reset();
	$('#agentId').val("");
	searchconfig();
}

/*
 * 查看商家详情
 */
function showconfigDetail(){
	var dlg = bootbox.dialog({
         title:'查看配置'
         ,url:baseURL + "/jsp/common/config_view.jsp?" + _csrf + "=" + token
          ,size:"large"		//small
         //,message:addrolediv
         // 是否显示关闭按钮，默认true
         ,closeButton: true
         ,className: "my-modal",
         buttons: {
             // 另一个按钮配置
             cancel:{
                 label:'关闭'
                 , className: "btn-danger"
                 , callback: function() {}
             }
         }
     });
	dlg.on('loaded.bs.modal', function(){
	});
}
function updateconfigDetail(){
    var dlg = bootbox.dialog({
        title:'修改配置'
        ,url:baseURL + "/jsp/common/config_update.jsp?" + _csrf + "=" + token
        ,size:"large"		//small
        //,message:addrolediv
        // 是否显示关闭按钮，默认true
        ,closeButton: true
        ,className: "my-modal",
        buttons: {
            // 另一个按钮配置
            cancel:{
                label:'关闭'
                , className: "btn-danger"
                , callback: function() {}
            },success: {
                // 按钮显示的名称
                label: "确定"
                , className: "btn-success"    // 按钮的类名
                , callback: function() {  // 点击按钮时的回调函数
                    var type = $("#value-area").attr("value-type");
                    var value = $("[name=value]").val();
                    if(type==4){
                        value = $("[name=value]:checked").val();
                    }
                    if(type==1){
                        if((/^\d+$/.test(value)&&(value.length==1||(value.substr(0,1)!="0")))==false){
                            $.msg.fail("值为整数类型，请认真核对格式！");
                            return false;
                        }
                    }
                    if(type==3){
                        if((value.indexOf("http:")==0||value.indexOf("https:")==0)==false){
                            $.msg.fail("值为HTTP链接格式，请认真核对格式！");
                            return false;
                        }
                    }
                    $('#config_update_view>form').ajaxSubmit({
                        dataType:'json',
                        success:function (response) {
                            if(response.res+"" == "true") {
                                bootbox.hideAll();
                                $.msg.success("修改成功！");
                                mecrchantTable.bootstrapTable('refresh');	//刷新页面.
                            } else {
                                $.msg.fail(response.msg);
                                return false;
                            }
                        },
                        error:function () {
                            $.msg.fail("修改配置信息失败，请稍后重试");
                            return false;
                        }
                    });
                }
            }
        }
    });
    dlg.on('loaded.bs.modal', function(){
    });
}
/*
 * 添加商家
 */
function addconfig(){

	bootbox.dialog({
         title:'添加商家信息'
         ,url:baseURL + "/jsp/config/config_add.jsp"
          ,size:"large"		//small
         //,message:addrolediv
         // 是否显示关闭按钮，默认true
         ,closeButton: true
         ,className: "my-modal"
         , // dialog底端按钮配置
         buttons: {
             // 另一个按钮配置
             cancel:{
                 label:'取消'
                 , className: "btn-danger"
                 , callback: function() {}
             },
             //其中一个按钮配置
             success: {
                 // 按钮显示的名称
                 label: "保存"
                 , className: "btn-success"    // 按钮的类名
                 , callback: function() {  // 点击按钮时的回调函数
                	 var btn = $(".modal-footer .btn-success");
                	 btn.attr("disabled","disabled")
                     if ($("#config_addform").valid() == false){
                    	 btn.removeAttr("disabled");
                         return false;
                     }

                     $('#config_addform').ajaxSubmit({
                         dataType:'json',
                         success:function (response) {
                        	btn.removeAttr("disabled");
             				if(response && response.success == true) {
             					bootbox.hideAll();								//关闭窗口
             					$.msg.success(response.msg);
             					mecrchantTable.bootstrapTable('refresh');		//刷新页面
	        				} else {
	        					$.msg.fail(response.msg);
	        					return false;
	        				}
                         },
                         error:function () {
                        	 btn.removeAttr("disabled");
                             $.msg.fail("新增商家信息失败，请稍后再试");
                             return false;
                         }
                     });
                     return false;
                 }
              }
         }
     });
}


/*
 * 修改商家信息
 */
function updateconfig(){
	bootbox.dialog({
         title:'修改商家信息'
         ,url:baseURL + "/jsp/config/config_edit.jsp"
          ,size:"large"		//small
         //,message:addrolediv
         // 是否显示关闭按钮，默认true
         ,closeButton: true
         ,className: "my-modal"
         , // dialog底端按钮配置
         buttons: {
             // 另一个按钮配置
             cancel:{
                 label:'取消'
                 , className: "btn-danger"
                 , callback: function() {}
             },
             //其中一个按钮配置
             success: {
                 // 按钮显示的名称
                 label: "保存"
                 , className: "btn-success"    // 按钮的类名
                 , callback: function() {  // 点击按钮时的回调函数

                	 var btn = $(".modal-footer .btn-success");
                	 btn.attr("disabled","disabled");

                     if ($("#config_updateform").valid() == false){
                    	 btn.removeAttr("disabled");
                         return false;
                     }

                     $('#config_updateform').ajaxSubmit({
                         dataType:'json',
                         success:function (response) {
                        	btn.removeAttr("disabled");
                        	if(response && response.success == true) {
                        		bootbox.hideAll();
             					$.msg.success(response.msg);
             					mecrchantTable.bootstrapTable('refresh');	//刷新页面.
                        	} else {
	        					$.msg.fail(response.msg);
	        					return false;
	        				}
                         },
                         error:function () {
                        	 btn.removeAttr("disabled");
                             $.msg.fail("修改代理商信息失败，请稍后重试");
                             return false;
                         }
                     });
                     return false;
                 }
              }
         }
     });
}

/*
 * 批量删除
 */
function batchDeleteconfig(){
   var ids = $('#config_list_table').find('input[name=btSelectItem]:checked').strval();
   if(!ids){
       $.msg.toast();
       return;
   }
   deleteconfig(ids);

}

/*
 * 删除商家
 */
function deleteconfig(ids){
    $.msg.confirm(function(){
    	$.ajax({
    		url:baseURL + '/config/delconfig?' + _csrf + '=' + token + '&ids=' + ids,
    		type:"post",
    		dataType:"json",
    		success:function(response){
    			if(response && response.success == true) {
    				$.msg.success(response.msg);
    				mecrchantTable.bootstrapTable('refresh');	//刷新页面
    			} else {
    				$.msg.fail(response.msg);
    			}
    		},error:function(e){
    			$.msg.fail(response.msg);
    		}
    	});
    });

}


//批量冻结
function batchFrozen(){

   var ids = $('#config_list_table').find('input[name=btSelectItem]:checked').strval();

   if(!ids){
       $.msg.toast();
       return;
   }

   updateconfigFrozenStatus(ids,1);

}
function batchBind(){
	var ids = $('#config_list_table').find('input[name=btSelectItem]:checked').strval();
    if(!ids){
       $.msg.toast();
       return;
    }
    $.ajax({
		url:baseURL + '/config/unBindconfigAgentStatus?' + _csrf + '=' + token + '&ids=' + ids,
		type:"post",
		dataType:"json",
		success:function(response){
			if(response && response.success == true) {
				$.msg.success(response.msg);
				mecrchantTable.bootstrapTable('refresh');	//刷新页面
			} else {
				$.msg.fail(response.msg);
			}
		},error:function(e){
			$.msg.fail(e.responseJSON.msg);
		}
	});
}

//批量解冻
function batchCancelFrozen(){

   var ids = $('#config_list_table').find('input[name=btSelectItem]:checked').strval();

   if(!ids){
       $.msg.toast();
       return;
   }

   updateconfigFrozenStatus(ids,0);

}

/*
 * 修改冻结状态
 */
function updateconfigFrozenStatus(ids,isFrozen){

	$.ajax({
		url:baseURL + '/config/updateconfigFrozenStatus?' + _csrf + '=' + token + '&ids=' + ids + '&isFrozen=' + isFrozen,
		type:"post",
		dataType:"json",
		success:function(response){
			if(response && response.success == true) {
				$.msg.success(response.msg);
				mecrchantTable.bootstrapTable('refresh');	//刷新页面
			} else {
				$.msg.fail(response.msg);
			}

		},error:function(e){
			$.msg.fail(response.msg);
		}

	});

}

/*
 * 得到银行卡信息,并加入到下拉框中
 */
function getBankInfos(obj){

	$.ajax({
		url:baseURL + '/common/getBankInfos?' + _csrf + '=' + token ,
		type:"post",
		dataType:"json",
		success:function(response){

			if(response && response.success == true) {

				for(var i = 0 ; i < response.data.length ; i ++){

					var bankId = response.data[i].ID;
					var bankName = response.data[i].BANKCARD_NAME;
					obj.append("<option value='"+bankId+"' >"+bankName+"</option>");

				}
			}
		},error:function(e){
		}
	});
}
//同步账户信息
function syncAccount(id){
    $.ajax({
        url:baseURL + '/config/syncAccount?' + _csrf + '=' + token+"&id="+id,
        type:"post",
        dataType:"json",
        success:function(response){
            if(response && response.success == true) {
                $.msg.success(response.msg);
                mecrchantTable.bootstrapTable('refresh');	//刷新页面
            }else{
                $.msg.fail(response.msg);
            }

        },error:function(e){
            $.msg.fail(e.responseJSON.msg);
        }
    });
}
 //同步费率
function syncUpdateRate(id,ebid){
    $.ajax({
        url:baseURL + '/config/syncUpdateRate?' + _csrf + '=' + token + '&id=' + id+'&ebaoCustomerId='+ebid,
        type:"post",
        dataType:"json",
        success:function(response){
            if(response && response.success == true) {
                $.msg.success(response.msg);
                ebrefresh();
            } else {
                $.msg.fail(response.msg);
            }
        },error:function(e){
            $.msg.fail("同步数据失败，请稍后重试");
        }
    });
}

/*
 * 查看分佣利率详情
 */
function showconfigCommission(id){
    var dlg = bootbox.dialog({
        title:'查看佣金信息'
        ,url:baseURL + "/jsp/config/config_commission.jsp?" + _csrf + "=" + token
        //,size:"large"		//small
        //,message:addrolediv
        // 是否显示关闭按钮，默认true
        ,closeButton: true
        ,className: "my-modal"
        , // dialog底端按钮配置
        buttons: {
            //cancel:{
            //    label:'取消'
            //    , className: "btn-danger"
            //    , callback: function() {}
            //},
            //其中一个按钮配置
            success: {
                label: "确定"
                , className: "btn-success"    // 按钮的类名
                , callback: function() {  // 点击按钮时的回调函数
                }

            }

        }

    });
    dlg.on('loaded.bs.modal', function(){
        $.ajax({
            type: "post",
            url: baseURL+"/commission/rateSearch",
            data: {'_csrf':token,'id':id},
            dataType: "json",
            success: function(data){
                if(data.rate!=null&&data.rate!=""){
                    for(var i=0;i<data.rate.length;i++){
                        var value0 = data.rate[i].configInterestRate_0;
                        var value1 = data.rate[i].configInterestRate_1
                        var value2 = data.rate[i].configInterestRate_2;
                        if(data.rate[i].channelType==1&&data.rate[i].rateType==1){
                            $('#config_commission_form').append(unionpay_div);
                            $("#unionpay1").val(toDecimal2Point(multiple(value0)));
                            $("#unionpay2").val(toDecimal2Point(multiple(value1)));
                            $("#unionpay3").val(toDecimal2Point(multiple(value2)));
                        }else if(data.rate[i].channelType==2&&data.rate[i].rateType==1){
                            $('#config_commission_form').append(alipay_div);
                            $("#alipay1").val(toDecimal2Point(multiple(value0)));
                            $("#alipay2").val(toDecimal2Point(multiple(value1)));
                            $("#alipay3").val(toDecimal2Point(multiple(value2)));
                        }else if(data.rate[i].channelType==3&&data.rate[i].rateType==1){
                            $('#config_commission_form').append(weixin_div);
                            $("#weixin1").val(toDecimal2Point(multiple(value0)));
                            $("#weixin2").val(toDecimal2Point(multiple(value1)));
                            $("#weixin3").val(toDecimal2Point(multiple(value2)));
                        }
                    }
                    $('#commission_from').validation();
                }
            }
        });
    });

}

/*
 * 一码付
 */
function nayouyigPay(){

    bootbox.dialog({
        title:'酷卡'
        ,url:baseURL + "/jsp/config/gallery.jsp"
        ,size:"large"		//small
        //,message:addrolediv
        // 是否显示关闭按钮，默认true
        ,closeButton: true
        ,className: "my-modal"
        , // dialog底端按钮配置
        buttons: {
            // 另一个按钮配置
            cancel:{
                label:'取消'
                , className: "btn-danger"
                , callback: function() {}
            },
            //其中一个按钮配置
            success: {
                label: "保存"
                , className: "btn-success"    // 按钮的类名
                , callback: function() {  // 点击按钮时的回调函数
                    var btn = $(".modal-footer .btn-success");
                    btn.attr("disabled","disabled");
                    if ($("#gallery_form").valid() == false){
                        btn.removeAttr("disabled");
                        return false;
                    }

                    $('#gallery_form').ajaxSubmit({
                        dataType:'json',
                        success:function (response) {
                            btn.removeAttr("disabled");
                            if(response && response.success == true) {
                                bootbox.hideAll();								//关闭窗口
                                $.msg.success(response.msg);
                                mecrchantTable.bootstrapTable('refresh');		//刷新页面
                            } else {
                                $.msg.fail(response.msg);
                                return false;
                            }
                        },
                        error:function () {
                            btn.removeAttr("disabled");
                            $.msg.fail("新增商家信息失败，请稍后再试");
                            return false;
                        }
                    });
                    return false;
                }
            }
        }
    });
}
