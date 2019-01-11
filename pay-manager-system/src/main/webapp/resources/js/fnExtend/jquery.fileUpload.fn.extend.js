/**
 * User: zjt
 * DateTime: 2016/10/13 19:41
 *
 * 扩展jquery实例对象方法 , 主要用于文件上传的简化操作
 *
 * 涉及部分源码的修改:
 *   //自定义previewId,在编辑时回显传入,用于实现上传文件id和路径的关联,后续用于删除
 *   if($.validate.isNotEmpty(previewCache.data[id].config) && $.validate.isNotEmpty(previewCache.data[id].config[i].previewId)){
 *     previewId = previewCache.data[id].config[i].previewId;
 *   }
 *
 *   $thumb.attr({
 *        //'id': 'uploaded-' + uniqId(),
 *       'id': self.previewInitId + '-' + i,
 *        'data-fileindex': '-1'
 *     });
 *
 */
$.fn.extend({

    /**
     * 单图片上传 - 返回图片路径到隐藏域
     * 具体例子可参考用户管理模块的头像上传
     *
     * @param option
     *  option.hiddenField: 隐藏域ID(必选)
     *  option.uploadExtraData: 上传扩展参数(可选)
     *  option.defaultPreviewContent: 默认图片(可选),
     *  option.imgUrl: 图片路径,编辑时回显(可选)
     *  option.selectAutoUpload: 是否选择图片后自动上传,true:是 false:否
     */
    singleImageUpload: function (option) {

        var hiddenField = option.hiddenField;           //必填项,隐藏域ID,上传成功后将路径赋值给组件
        var imgUrl = option.imgUrl;                     //图片路径,可选编辑时回显

        //编辑时回显
        var initialPreview = [];
        var initialPreviewConfig = [];
        if ($.validate.isNotEmpty(imgUrl)) {
            $(hiddenField).val(imgUrl);
            initialPreview = [
                "<img src='" + baseURL + imgUrl + "' style='width: 160px;' class='file-preview-image' />"
            ];
            initialPreviewConfig = [
                {caption: baseURL + imgUrl, url:baseURL + "/common/fileUpload/virtualDeleteFile?_csrf=" + token}
            ];
        }

        //文件上传相关配置
        var fileObj = $(this).fileinput({
            uploadUrl: option.uploadUrl || baseURL + "/common/fileUpload/imageUpload?_csrf=" + token,      //上传路径
            uploadAsync: true,
            uploadExtraData: option.uploadExtraData || {"module": "default"},                                    //扩展提交参数
            allowedFileExtensions: option.allowedFileExtensions || ["jpg", "png", "gif"],                        //允许的文件格式
            showCaption: option.showCaption || false,                                     //是否显示文件名称
            showRemove: true,
            showUpload: true,
            defaultPreviewContent: option.defaultPreviewContent || "<img src='"+baseURL+"/images/default/default_avatar_male.jpg' style='width:160px' >",   //默认图片

            //编辑时,回显
            allowedPreviewTypes: ['image'],
            initialPreview: initialPreview,
            initialPreviewConfig: initialPreviewConfig,
        });

        //文件选择改变时: 自动上传
        if($.validate.isNotEmpty(option.selectAutoUpload) && option.selectAutoUpload){
            fileObj.on("filebatchselected", function (event, files) {
                $(this).fileinput("upload");
            });
        }

        //上传成功回调: 将上传文件路径存储到隐藏域
        fileObj.on("fileuploaded", function (event, data) {
            if (data.response && data.response.success) {
                var saveFilePath = data.response.data[0];
                $(hiddenField).val(saveFilePath);
            }
        });

        //全部清空 , 上传前的删除 , 上传后的删除:  单文件直接清空隐藏域即可
        fileObj.on('filecleared', function (event) {
            $(hiddenField).val("");
        });
        fileObj.on('filesuccessremove', function (event, id) {
            $(hiddenField).val("");
        });
        fileObj.on('filedeleted', function (event, id) {
            $(hiddenField).val("");
        });

    },

    /**
     * 多图片上传 , 按逗号分隔存储到隐藏域
     * @param option
     */
    multiImageUpload: function (option) {

        var fileList = new Array();                      //用于保存上传图片的id和文件名
        var hiddenField = option.hiddenField;            //隐藏域,上传成功后将路径赋值给组件

        //编辑时回显
        var initialPreview = [];
        var initialPreviewConfig = [];
        if ($.validate.isNotEmpty(option.imgUrl)) {
            $(hiddenField).val(option.imgUrl);
            var imgUrlArray = option.imgUrl.split(",");
            for(var i = 0; i < imgUrlArray.length; i++){

                var previewId = $.string.getUUID();

                initialPreview[i] = "<img src='" + baseURL + imgUrlArray[i] + "' style='width: 160px;' class='file-preview-image' />";
                initialPreviewConfig[i] = {
                    caption: baseURL + imgUrlArray[i] ,
                    key: previewId ,
                    previewId: previewId ,
                    url:baseURL + "/common/fileUpload/virtualDeleteFile?_csrf=" + token
                };

                //保存上传图片信息
                var fileInfo = new Object();
                fileInfo.id = previewId;
                fileInfo.imgUrl = imgUrlArray[i];
                fileList.push(fileInfo);

            }
        }

        //文件上传相关配置
        var fileObj = $(this).fileinput({
            uploadUrl: option.uploadUrl || baseURL + "/common/fileUpload/imageUpload?_csrf=" + token,      //上传路径
            uploadAsync: true,
            uploadExtraData: option.uploadExtraData || {"module": "default"},                                    //扩展提交参数
            allowedFileExtensions: option.allowedFileExtensions || ["jpg", "png", "gif"],                        //允许的文件格式
            showCaption: option.showCaption || false,                                     //是否显示文件名称
            defaultPreviewContent: option.defaultPreviewContent || '',                    //默认图片
            maxFileCount: option.maxFileCount || 10,                                      //最大上传数量

            //编辑时,回显
            allowedPreviewTypes: ['image'],
            initialPreview: initialPreview,
            initialPreviewConfig: initialPreviewConfig,
            overwriteInitial: false,            //不进行覆盖
        });

        //上传成功回调,内部也是逐个上传
        fileObj.on("fileuploaded", function (event, data , previewId , index) {

            if (data.response && data.response.success) {

                //保存上传图片信息
                var fileInfo = new Object();
                fileInfo.id = previewId;
                fileInfo.imgUrl = data.response.data[0];
                fileList.push(fileInfo);

                //将路径拼接到隐藏域中
                var imgUrls = "";
                for (var i = 0; i < fileList.length; i++) {
                    if($.validate.isNotEmpty(imgUrls)){
                        imgUrls += ",";
                    }
                    imgUrls += fileList[i].imgUrl;
                }
                $(hiddenField).val(imgUrls);

            }

        });

        //上传成功后的删除文件, 需修改源码 , 保证返回的id与上传成功时的previewId一致
        fileObj.on('filesuccessremove', function (event, id) {
            handlerMultiFileDelete(fileList , id , hiddenField);
        });

        //预览时的删除文件
        fileObj.on('filedeleted', function(event, key) {
            handlerMultiFileDelete(fileList , key , hiddenField);
        });

        //全部清空
        fileObj.on('filecleared', function (event) {
            fileList = [];
            $(hiddenField).val("");
        });

    } ,

    /**
     * 显示图片
     * @param option
     */
    showImage: function (option) {

        //编辑时回显
        var initialPreview = [];
        var initialPreviewConfig = [];

        //为空时,随便给个默认值,避免页面空白,无照片展示框
        if ($.validate.isEmpty(option.imgUrl)) {
            option.imgUrl = "aaaa.jpg"
        }

        var imgUrlArray = option.imgUrl.split(",");
        for(var i = 0; i < imgUrlArray.length; i++){
            initialPreview[i] = "<img src='" + baseURL + imgUrlArray[i] + "' style='width: 160px;' class='file-preview-image' />";
            initialPreviewConfig[i] = {caption: baseURL + imgUrlArray[i] , showDelete: false};
        }

        //文件上传相关配置,禁用相关操作按钮,只能查看
        $(this).fileinput({
            showCaption: false,
            showBrowse: false,
            showRemove: false,
            showPreview: true,
            showUpload: false,
            showCancel: false,
            showClose: false,
            //编辑时,回显
            allowedPreviewTypes: ['image'],
            initialPreview: initialPreview,
            initialPreviewConfig: initialPreviewConfig,
        });

    }


});

/**
 * 处理多文件的删除 , 刷新Array , 隐藏域内容
 * @param fileList: 集合,存储了previewId和imgUrl的关联
 * @param val: 要删除的previewId
 * @param hiddenField: 隐藏域内容
 */
function handlerMultiFileDelete(fileList , val , hiddenField){

    var deleteIndex = 0;

    //从数组中删除元素
    for (var i = 0; i < fileList.length; i++) {
        if(fileList[i].id == val){
            deleteIndex = i;
            break;
        }
    }
    fileList.remove(i);

    //重新将路径拼接到隐藏域中
    var imgUrls = "";
    for (var i = 0; i < fileList.length; i++) {
        if($.validate.isNotEmpty(imgUrls)){
            imgUrls += ",";
        }
        imgUrls += fileList[i].imgUrl;
    }
    $(hiddenField).val(imgUrls);

}
