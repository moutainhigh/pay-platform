var mecrchantTable = {};

(function(){

    $(document).ready(function(){
    	mecrchantTable = $('#merchant_list_table').bootstrapTable({
            url:baseURL + '/merchant/getMerchantList?' + _csrf + '=' + token
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
            ,toolbar:'#merchant_toolbar'
            ,queryParams:function(params){
                $.extend(params,{
            		//分页参数
                    'offset':params.offset,			//从那条记录开始取数据,例如limit 0,10
                    'limit':params.limit,			//每页显示条数
                    'sort':params.sort,				//排序字段
                    'order':params.order,			//排序方式
                    'isFrozen':$('#isFrozen').val(),			//排序方式
                    'isContainChildren':$("input[name='isContainChildren']:checked").val(),	//是否包含下级0:不是 1:是
                    //查询字段
                    'status':$('#status').val(),
                    'merchantName':$('#merchantName').val(),
                    'merchantPhone':$('#merchantPhone').val(),
                    'receiveAccount':$('#receiveAccount').val(),
                    'agentId':$('#agentId').val()
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
                {
                    title: '全选'
                    , checkbox:true
                }
                , {
                    title:'编号'
                    , formatter:function(){
                        return arguments[2]+1;
                    }
                }
                , {
                    field: 'MERCHANT_NAME'
                    , title: '商家名称'
                    , sortable:true
                }
                ,{
                    field: 'PHONE'
                    , title:'商家手机'
                    , sortable:true
                    , formatter:function(value,row,index){
                        return value;
                    }
                }
                ,{
                    field: 'ROOT_NAME'
                    , title:'所属平台'
                    , sortable:true
                    , formatter:function(value,row,index){
                        return value;
                    }
                }
                ,{
                    field: 'AGENT_NAME'
                    , title:'所属代理商'
                    , sortable:true
                    , formatter:function(value,row,index){
                        return value;
                    }
                }
                // ,{
                //     field: 'CARD_NUMBER'
                //     ,title:'收款银行账户'
                //     , sortable:true
                //     , formatter:function(value,row,index){
                //         return value;
                //     }
                // }
                ,{
                    field: 'createTime'
                    ,title:'注册时间'
                    , sortable:true
                    , formatter:function(value,row,index){
                        return value;
                    }
                }
                ,{
                    field: 'STATUS'
                    ,title:'审核状态'
                    , sortable:false
                    , formatter:function(value,row,index){
                        switch (value) {
                            case 0:
                                return "审核中";
                            case 1:
                                return "审核通过";
                            case 2:
                                return "审核不通过";
                            case 4:
                                return "等待资料完善";
                            default:
                                return value;
                        }
                    }
                }
                ,{
                    title:'操作'
                    ,width:300
                    , align:'center'
                    , formatter:function(value,row,index){
                    	/*var operationButton = '<a href="javascript:void(0)" class="glyphicon glyphicon-pencil" onclick="updateMerchant(\''+row.ID+'\')" ></a>'+
                        '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  class="glyphicon glyphicon-file" onclick="showMerchantDetail(\''+row.ID+'\')"></a>' +*/
                        '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  class="glyphicon glyphicon-remove" onclick="deleteMerchant(\''+row.ID+'\')"></a>';

                        //'&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  onclick="showMerchantCommission(\''+row.ID+'\')">佣金</a>';
                        return operationButton;

                    }

                }
            ]
        });

    });

})();

/*
 * 查询用户,刷新表格即可
 */
function searchMerchant(){
	mecrchantTable.bootstrapTable('refresh');
}

/*
 * 重置查询
 */
function resetSearch(){
	$("#merchant_search")[0].reset();
	$('#agentId').val("");
	searchMerchant();
}

/*
 * 查看商家详情
 */
function showMerchantDetail(id){
	var dlg = bootbox.dialog({
         title:'查看商家信息'
         ,url:baseURL + "/jsp/merchant/merchant_view.jsp?" + _csrf + "=" + token
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
                 label: "确定"
                 , className: "btn-success"    // 按钮的类名
                 , callback: function() {  // 点击按钮时的回调函数
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
function addMerchant(){

	bootbox.dialog({
         title:'添加商家信息'
         ,url:baseURL + "/jsp/merchant/merchant_add.jsp"
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
                     if ($("#merchant_addform").valid() == false){
                    	 btn.removeAttr("disabled");
                         return false;
                     }

                     $('#merchant_addform').ajaxSubmit({
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
function updateMerchant(){
	bootbox.dialog({
         title:'修改商家信息'
         ,url:baseURL + "/jsp/merchant/merchant_edit.jsp"
          ,size:"large"		//small
         //,message:addrolediv
         // 是否显示关闭按钮，默认true
         ,closeButton: true
         ,className: "my-modal"
         , // dialog底端按钮配置
         buttons: {


             //其中一个按钮配置
             button: {

                 // 按钮显示的名称
                 label: "修改银行卡信息"
                 , className: "btn-danger"    // 按钮的类名
                 , callback: function() {  // 点击按钮时的回调函数
                     $("#merchant_add").empty();
                     //window.location.href = "merchant_bankCard_edit.jsp";
                     bootbox.dialog({
                         title:'修改银行卡信息'
                         ,url:baseURL + "/jsp/merchant/merchant_bankCard_edit.jsp?"
                         ,size:"large"		//small
                         //,message:addrolediv
                         // 是否显示关闭按钮，默认true
                         ,closeButton: true
                         ,className: "my-modal",
                     });



                 }
             },




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

                     if ($("#merchant_updateform").valid() == false){
                    	 btn.removeAttr("disabled");
                         return false;
                     }

                     $('#merchant_updateform').ajaxSubmit({
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
function batchDeleteMerchant(){
   var ids = $('#merchant_list_table').find('input[name=btSelectItem]:checked').strval();
   if(!ids){
       $.msg.toast();
       return;
   }
   deleteMerchant(ids);

}

/*
 * 删除商家
 */
function deleteMerchant(ids){
    $.msg.confirm(function(){
    	$.ajax({
    		url:baseURL + '/merchant/delMerchant?' + _csrf + '=' + token + '&ids=' + ids,
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

   var ids = $('#merchant_list_table').find('input[name=btSelectItem]:checked').strval();

   if(!ids){
       $.msg.toast();
       return;
   }

   updateMerchantFrozenStatus(ids,1);

}
function batchBind(){
	var ids = $('#merchant_list_table').find('input[name=btSelectItem]:checked').strval();
    if(!ids){
       $.msg.toast();
       return;
    }
    $.ajax({
		url:baseURL + '/merchant/unBindMerchantAgentStatus?' + _csrf + '=' + token + '&ids=' + ids,
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

   var ids = $('#merchant_list_table').find('input[name=btSelectItem]:checked').strval();

   if(!ids){
       $.msg.toast();
       return;
   }

   updateMerchantFrozenStatus(ids,0);

}

/*
 * 修改冻结状态
 */
function updateMerchantFrozenStatus(ids,isFrozen){

	$.ajax({
		url:baseURL + '/merchant/updateMerchantFrozenStatus?' + _csrf + '=' + token + '&ids=' + ids + '&isFrozen=' + isFrozen,
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
        url:baseURL + '/merchant/syncAccount?' + _csrf + '=' + token+"&id="+id,
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
        url:baseURL + '/merchant/syncUpdateRate?' + _csrf + '=' + token + '&id=' + id+'&ebaoCustomerId='+ebid,
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
function showMerchantCommission(id){
    var dlg = bootbox.dialog({
        title:'查看佣金信息'
        ,url:baseURL + "/jsp/merchant/merchant_commission.jsp?" + _csrf + "=" + token
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
                        var value0 = data.rate[i].merchantInterestRate_0;
                        var value1 = data.rate[i].merchantInterestRate_1
                        var value2 = data.rate[i].merchantInterestRate_2;
                        if(data.rate[i].channelType==1&&data.rate[i].rateType==1){
                            $('#merchant_commission_form').append(unionpay_div);
                            $("#unionpay1").val(toDecimal2Point(multiple(value0)));
                            $("#unionpay2").val(toDecimal2Point(multiple(value1)));
                            $("#unionpay3").val(toDecimal2Point(multiple(value2)));
                        }else if(data.rate[i].channelType==2&&data.rate[i].rateType==1){
                            $('#merchant_commission_form').append(alipay_div);
                            $("#alipay1").val(toDecimal2Point(multiple(value0)));
                            $("#alipay2").val(toDecimal2Point(multiple(value1)));
                            $("#alipay3").val(toDecimal2Point(multiple(value2)));
                        }else if(data.rate[i].channelType==3&&data.rate[i].rateType==1){
                            $('#merchant_commission_form').append(weixin_div);
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
        ,url:baseURL + "/jsp/merchant/gallery.jsp"
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
