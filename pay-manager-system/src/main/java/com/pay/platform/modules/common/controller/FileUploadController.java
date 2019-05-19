package com.pay.platform.modules.common.controller;

import com.pay.platform.common.util.DateUtil;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * User: zjt
 * DateTime: 2016/12/10 14:10
 * <p>
 * 通用模块 - 文件上传
 */
@Controller
@RequestMapping("/common/fileUpload")
public class FileUploadController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 上传文件的基础路径(从webapps/项目根路径开始)
     */
    private static final String REPOSITORY_PATH = "/resources/upload";

    /**
     * 上传图片的相关限制(大小/格式)
     */
    private static final String UPLOAD_IMAGE_TYPE = ".jpg .png .bmp";           //格式:
    private static final Integer UPLOAD_IMAGE_MAX_SIZE = 1024 * 1024 * 3;       //大小: 最多为3M

    /**
     * 上传文档的相关限制
     */
    private static final String UPLOAD_DOC_TYPE = ".txt .pdf .doc .docx .xls .xlsx .ppt .pptx";         //格式
    private static final Integer UPLOAD_DOC_MAX_SIZE = 1024 * 1024 * 30;

    /**
     * 图片上传 - 支持多图片
     *
     * @param request
     * @param response
     * @param module:  上传文件所属模块 , 按/upload/模块名称/日期分组/文件名的方式存储
     * @throws Exception
     */
    @RequestMapping(value = "/imageUpload", produces = "application/json")
    @SystemControllerLog(module = "文件上传", operation = "多图片上传")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response, String module) throws Exception {

        JSONObject json = new JSONObject();
        List<String> saveFileNameList = new ArrayList<>();

        //1, 将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //2, 检查form中是否有enctype=multipart/form-data
        if (multipartResolver.isMultipart(request)) {

            //3, 获取上传资源 , 遍历上传
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {

                //获取上传文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());

                //获取上传文件名称,后缀名
                String uploadFileName = file.getOriginalFilename();
                String suffixName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);

                //验证上传文件格式
                if (!UPLOAD_IMAGE_TYPE.toLowerCase().contains(suffixName.toLowerCase())) {
                    json.put("success", false);
                    json.put("msg", "上传文件格式必须是以" + UPLOAD_IMAGE_TYPE + "为结尾的,请重新选择文件!");
                    return;
                }

                //验证上传文件大小
                if (UPLOAD_IMAGE_MAX_SIZE < file.getSize()) {
                    json.put("success", false);
                    json.put("msg", "图片大小不能超过" + (UPLOAD_IMAGE_MAX_SIZE / 1024 / 1024) + "M,请重新选择文件!");
                    return;
                }

                //爆粗目录: 上传基础目录 + 模块 + 按月
                String saveDir = REPOSITORY_PATH + "/" + module + "/" + DateUtil.getCurrentYearAndMonth();

                //服务器真实路径
                String serverRealPath = request.getSession().getServletContext().getRealPath(saveDir);

                //保存文件名称 = 上传目录  + 随机文件名  + 后缀
                String saveFileName = UUID.randomUUID().toString() + "." + suffixName;
                File saveFile = new File(serverRealPath , saveFileName);
                if (!saveFile.getParentFile().exists()) {
                    saveFile.getParentFile().mkdirs();
                }

                file.transferTo(saveFile);          //保存

                saveFileNameList.add(saveDir + "/" + saveFileName);

            }

            json.put("success", true);
            json.put("msg", "上传文件成功!");
            json.put("data", saveFileNameList);

        }

        writeJson(response, json.toString());

    }


    /**
     * 多文档上传
     *
     * @param request
     * @param response
     * @param module:  上传文件所属模块 , 按/upload/模块名称/日期分组/文件名的方式存储
     * @throws Exception
     */
    @RequestMapping(value = "/multiDocUpload", produces = "application/json")
    @SystemControllerLog(module = "文件上传", operation = "多文件上传")
    public void multiDocUpload(HttpServletRequest request, HttpServletResponse response, String module) throws Exception {

        JSONObject json = new JSONObject();
        List<String> saveFileNameList = new ArrayList<>();

        //1, 将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //2, 检查form中是否有enctype=multipart/form-data
        if (multipartResolver.isMultipart(request)) {

            //3, 获取上传资源 , 遍历上传
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {

                //获取上传文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());

                //获取上传文件名称,后缀名
                String uploadFileName = file.getOriginalFilename();
                String suffixName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);

                //验证上传文件格式
                if (!UPLOAD_DOC_TYPE.toLowerCase().contains(suffixName.toLowerCase())) {
                    json.put("success", false);
                    json.put("msg", "上传文件格式必须是以" + UPLOAD_DOC_TYPE + "为结尾的,请重新选择文件!");
                    return;
                }

                //验证上传文件大小
                if (UPLOAD_DOC_MAX_SIZE < file.getSize()) {
                    json.put("success", false);
                    json.put("msg", "图片大小不能超过" + (UPLOAD_DOC_MAX_SIZE / 1024 / 1024) + "M,请重新选择文件!");
                    return;
                }

                //爆粗目录: 上传基础目录 + 模块 + 按月
//                String saveDir = REPOSITORY_PATH + "/" + module + "/" + DateUtil.getCurrentYearAndMonth();
                String saveDir = "/WEB-INF/upload/" + module + "/" + DateUtil.getCurrentYearAndMonth();

                //服务器真实路径
                String serverRealPath = request.getSession().getServletContext().getRealPath(saveDir);

                //保存文件名称 = 上传目录  + 随机文件名  + 后缀
                String saveFileName = UUID.randomUUID().toString() + "." + suffixName;
                File saveFile = new File(serverRealPath , saveFileName);
                if (!saveFile.getParentFile().exists()) {
                    saveFile.getParentFile().mkdirs();
                }

                file.transferTo(saveFile);          //保存

                saveFileNameList.add(saveDir + "/" + saveFileName);

            }

            json.put("success", true);
            json.put("msg", "上传文件成功!");
            json.put("data", saveFileNameList);

        }

        writeJson(response, json.toString());

    }

    /**
     * 删除文件 (假删 , bootstrap fileinpu删除需要给个可以调用的地址)
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/virtualDeleteFile", produces = "application/json")
    @SystemControllerLog(module = "文件上传", operation = "删除图片")
    public void virtualDeleteFile(HttpServletRequest request, HttpServletResponse response , String filePath) throws Exception {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("msg", "删除成功!");
        writeJson(response, json.toString());
    }

    /**
     * 删除文件
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFile", produces = "application/json")
    @SystemControllerLog(module = "文件上传", operation = "删除图片")
    public void deleteFile(HttpServletRequest request, HttpServletResponse response , String filePath) throws Exception {

        JSONObject json = new JSONObject();

        //服务器真实路径
        String serverRealPath = request.getSession().getServletContext().getRealPath("/");

        //保存文件名称 = 上传目录  + 随机文件名  + 后缀
        File file = new File(serverRealPath , filePath);
        if(file.exists()){
            file.delete();
        }

        json.put("success", true);
        json.put("msg", "删除成功!");

        writeJson(response, json.toString());

    }

}