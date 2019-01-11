package com.pay.platform.modules.generate.controller;

import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.generate.model.ColumnModel;
import com.pay.platform.modules.generate.model.GenerateModel;
import com.pay.platform.modules.generate.service.DBMetaDataService;
import com.pay.platform.modules.generate.util.GenerateCodeUtil;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: zjt
 * DateTime: 2017/1/7 10:44
 * <p>
 * 代码生成模块
 */
@Controller
@RequestMapping("/generate")
public class GenerateController extends BaseController {

    @Autowired
    private DBMetaDataService dbMetaDataService;

    private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);

    /**
     * 获取所有表名
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getAllTableNameList", produces = "application/json;charset=UTF-8")
    public void getAllTableNameList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        //获取数据库元信息
        List<String> tableNameList = dbMetaDataService.getAllTableNameList();

        json.put("success", true);
        json.put("data", tableNameList);

        writeJson(response, json.toString());

    }

    /**
     * 获取表字段信息
     *
     * @param request
     * @param response
     * @param tableName
     * @throws Exception
     */
    @RequestMapping(value = "/getColumnListByTableName", produces = "application/json;charset=UTF-8")
    public void getColumnListByTableName(HttpServletRequest request, HttpServletResponse response, String tableName) throws Exception {

        JSONObject json = new JSONObject();

        List<ColumnModel> columnModelList = null;

        if (StringUtil.isNotEmpty(tableName)) {
            columnModelList = dbMetaDataService.getColumnListByTableName(tableName);
        }

        json.put("success", true);
        json.put("data", columnModelList);

        writeJson(response, json.toString());

    }

    /**
     * 生成单表 - CRUD代码
     *
     * @param response
     * @param generateModel:  生成代码所需的数据
     * @param columnListJson: 字段列表信息,json格式
     * @throws Exception
     */
    @RequestMapping(value = "/generateSingleTableCode", produces = "application/json")
    public void generateSingleTableCode(HttpServletResponse response, GenerateModel generateModel, String columnListJson) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //设置属性前缀
            String classPrefix = generateModel.getClassPrefix();
            generateModel.setPropertyPrefix(classPrefix.substring(0, 1).toLowerCase() + classPrefix.substring(1, classPrefix.length()));

            //字段信息,解析json获取
            List<ColumnModel> columnModelList = JsonUtil.gson.fromJson(columnListJson, new TypeToken<List<ColumnModel>>() {
            }.getType());
            generateModel.setColumnList(columnModelList);

            //是否包含查询字段
            generateModel.setContainQueryField(false);
            generateModel.getColumnList().forEach(columnModel -> {
                if (columnModel.is_isQueryField()) {
                    generateModel.setContainQueryField(true);
                }
            });

            //是否包含逻辑删除字段isDel
            generateModel.setContainDel(false);
            generateModel.getColumnList().forEach(columnModel -> {
                if ("isDel".equalsIgnoreCase(columnModel.getFieldName())) {
                    generateModel.setContainDel(true);
                }
            });

            //是否包含富文本字段
            generateModel.setContainRichTextField(false);
            generateModel.getColumnList().forEach(columnModel -> {
                if ("richText".equalsIgnoreCase(columnModel.getFormType())) {
                    generateModel.setContainRichTextField(true);
                }
            });

            //生成单表模块 - 代码
            GenerateCodeUtil.generateSingleTableModuleCode(generateModel);

            json.put("success", true);
            json.put("msg", "代码生成成功");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            json.put("success", false);
            json.put("msg", e.getMessage());
        } finally {
            writeJson(response, json.toString());
        }

    }

    /**
     * 生成树模块 - CRUD代码
     *
     * @param response
     * @param generateModel:  生成代码所需的数据
     * @param columnListJson: 字段列表信息,json格式
     * @throws Exception
     */
    @RequestMapping(value = "/generateTreeModuleCode", produces = "application/json")
    public void generateTreeModuleCode(HttpServletResponse response, GenerateModel generateModel, String columnListJson) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //设置属性前缀
            String classPrefix = generateModel.getClassPrefix();
            generateModel.setPropertyPrefix(classPrefix.substring(0, 1).toLowerCase() + classPrefix.substring(1, classPrefix.length()));

            //字段信息,解析json获取
            List<ColumnModel> columnModelList = JsonUtil.gson.fromJson(columnListJson, new TypeToken<List<ColumnModel>>() {
            }.getType());
            generateModel.setColumnList(columnModelList);

            //是否包含查询字段
            generateModel.setContainQueryField(false);
            generateModel.getColumnList().forEach(columnModel -> {
                if (columnModel.is_isQueryField()) {
                    generateModel.setContainQueryField(true);
                }
            });

            //是否包含逻辑删除字段isDel
            generateModel.setContainDel(false);
            generateModel.getColumnList().forEach(columnModel -> {
                if ("isDel".equalsIgnoreCase(columnModel.getFieldName())) {
                    generateModel.setContainDel(true);
                }
            });

            //是否包含富文本字段
            generateModel.setContainRichTextField(false);
            generateModel.getColumnList().forEach(columnModel -> {
                if ("richText".equalsIgnoreCase(columnModel.getFormType())) {
                    generateModel.setContainRichTextField(true);
                }
            });

            //生成单表模块 - 代码
            GenerateCodeUtil.generateTreeModuleCode(generateModel);

            json.put("success", true);
            json.put("msg", "代码生成成功");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            json.put("success", false);
            json.put("msg", e.getMessage());
        } finally {
            writeJson(response, json.toString());
        }

    }

}