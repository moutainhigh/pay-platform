<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>


<script type="text/javascript" charset="utf-8" src="${baseURL}/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${baseURL}/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${baseURL}/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/ueditor.parse.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${baseURL}/jsp/3rd-plug/ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" >

    $(function(){

        $('#addGoodsForm').validation();            //表单验证初始化

                    //日期初始化
                    $("#addProductionDateDiv").datetimepicker();

                    //单图片上传 - 商品图标
                    $("#fileLogo").singleImageUpload({
                        uploadExtraData: {"module": "/goods"} ,                     //上传图片扩展参数,指定所属模块
                        hiddenField:"#addLogo" ,                   //返回隐藏域路径
                    });

                    //多图片上传 - 商品图片
                    $("#multiFilePicture").multiImageUpload({
                        uploadExtraData: {"module": "/goods"} ,                  //上传图片扩展参数,指定所属模块
                        hiddenField:"#addPicture" ,              //返回隐藏域路径
                    });

                    //实例化编辑器
                    var ueditor = UE.getEditor('addContent');

    });

</script>

<div id="couponCode_addform_div" class="panel panel-fit">

    <form id="addGoodsForm" action="${baseURL}/goods/addGoods" class="form-horizontal"  method="post">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />


                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">商品名称：</label>
                            <div class="col-md-8 col-sm-8">
                                <input id="addName" name="name" type="text" class="form-control" check-type="required" maxlength="255" placeholder="请输入商品名称" />
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">生产日期：</label>
                            <div class="col-md-8 col-sm-8">
                                <div class='input-group date' id="addProductionDateDiv" >
                                    <input id="addProductionDate" type='text' name="productionDate" class="form-control" check-type="required" data-date-format="YYYY-MM-DD HH:mm" placeholder="请输入生产日期" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                </div>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">商品图标：</label>
                            <div class="col-md-8 col-sm-8">
                                <div class="kv-avatar"  >
                                    <!-- 上传文件表单组件 -->
                                    <input id="fileLogo" type="file" class="file-loading" >
                                </div>
                            </div>
                            <!-- 隐藏域,保存上传文件的路径  -->
                            <input id="addLogo" name="logo" type="hidden" />
                        </div>


                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">商品图片：</label>
                            <div class="col-md-9 col-sm-9">
                                <!-- 上传文件表单组件 -->
                                <input id="multiFilePicture" multiple type="file" class="file-loading" >
                            </div>
                            <!-- 隐藏域,保存上传文件的路径  -->
                            <input id="addPicture" name="picture" type="hidden" />
                        </div>



                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">内容：</label>
                            <div class="col-md-8 col-sm-8">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12 col-sm-12">
                                <script id="addContent" type="text/plain" style="width:auto;height:500px;"></script>
                                <input type="hidden" id="addRealContent" name="content" />
                            </div>
                        </div>


    </form>

</div>