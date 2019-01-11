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


<script type="text/javascript">

    $(function () {







    });

</script>

<div id="couponCode_addform_div" class="panel panel-fit">

    <form id="addGoodsForm" action="" class="form-horizontal">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


					<div class="form-group">
						<label class="col-md-3 col-sm-3 control-label">商品名称：</label>
						<div class="col-md-8 col-sm-8">
							<input id="detailName" name="name" type="text" class="form-control" readonly="readonly"/>
						</div>
					</div>


					<div class="form-group">
						<label class="col-md-3 col-sm-3 control-label">生产日期：</label>
						<div class="col-md-8 col-sm-8">
							<input id="detailProductionDate" name="productionDate" type="text" class="form-control" readonly="readonly"/>
						</div>
					</div>


                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">商品图标：</label>
                        <div class="col-md-8 col-sm-8 detail-content">
                            <div class="kv-avatar"  >
                                <input id="fileLogo" type="file" class="file-loading" />
                            </div>
                        </div>
                    </div>



                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">商品图片：</label>
                        <div class="col-md-9 col-sm-9 detail-content" style="padding-right:40px;" >
                            <input id="multiFilePicture" multiple type="file" class="file-loading" />
                        </div>
                    </div>





					<div class="form-group">
						<label class="col-md-3 col-sm-3 control-label">内容：</label>
						<div class="col-md-8 col-sm-8" id="detailContent" name="content">
						</div>
					</div>



    </form>

</div>