package com.pay.platform.modules.generate.util;

import com.pay.platform.modules.generate.model.GenerateModel;

/**
 * User: zjt
 * DateTime: 2017/1/2 10:28
 * <p>
 * 用于生成表格模块的CRUD代码
 */
public class GenerateCodeUtil {

    /**
     * 生成Controller
     * @param generateModel: 渲染模板所需数据
     * @param templatePath: controlelr模板路径
     */
    public static void generateController(GenerateModel generateModel , String templatePath) {

        //输出文件路径
        String packagePath = generateModel.getPackageName().replaceAll("\\.", "/");
        String outFilePath = generateModel.getRootPath() + "/src/main/java/" + packagePath + "/" + "controller" + "/" + generateModel.getClassPrefix() + "Controller.java";

        FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

    }

    /**
     * 生成service
     *
     * @param generateModel: 渲染模板所需数据
     * @param templatePath: service模板路径
     */
    public static void generateService(GenerateModel generateModel , String templatePath) {

        //输出文件路径
        String packagePath = generateModel.getPackageName().replaceAll("\\.", "/");
        String outFilePath = generateModel.getRootPath() + "/src/main/java/" + packagePath + "/" + "service" + "/" + generateModel.getClassPrefix() + "Service.java";

        //输出service
        FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

    }

    /**
     * 生成service 实现类
     *
     * @param generateModel: 渲染模板所需数据
     * @param templatePath: service impl 模板路径
     */
    public static void generateServiceImpl(GenerateModel generateModel , String templatePath) {

        //输出文件路径
        String packagePath = generateModel.getPackageName().replaceAll("\\.", "/");
        String outFilePath = generateModel.getRootPath() + "/src/main/java/" + packagePath + "/" + "service" + "/impl/" + generateModel.getClassPrefix() + "ServiceImpl.java";

        //输出service Impl
        FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

    }

    /**
     * 生成dao接口
     *
     * @param generateModel: 渲染模板所需数据
     * @param templatePath: dao 模板路径
     */
    public static void generateDao(GenerateModel generateModel , String templatePath) {

        //输出文件路径
        String packagePath = generateModel.getPackageName().replaceAll("\\.", "/");
        String outFilePath = generateModel.getRootPath() + "/src/main/java/" + packagePath + "/" + "dao" + "/" + generateModel.getClassPrefix() + "Dao.java";

        //输出dao
        FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

    }

    /**
     * 生成dao - xmm sql
     *
     * @param generateModel: 渲染模板所需数据
     * @param templatePath: dao xml 模板路径
     */
    public static void generateDaoXml(GenerateModel generateModel , String templatePath) {

        //输出文件路径
        String packagePath = generateModel.getPackageName().replaceAll("\\.", "/");
        String outFilePath = generateModel.getRootPath() + "/src/main/java/" + packagePath + "/" + "dao" + "/" + generateModel.getClassPrefix() + "Dao.xml";

        //输出dao xml
        FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

    }

    /**
     * 生成Model
     */
    public static void generateModel(GenerateModel generateModel) {

        //模板路径
        String templatePath = "/template/model/Model.ftl";

        //输出文件路径
        String packagePath = generateModel.getPackageName().replaceAll("\\.", "/");
        String outFilePath = generateModel.getRootPath() + "/src/main/java/" + packagePath + "/" + "model" + "/" + generateModel.getClassPrefix() + "Model.java";

        //输出Model
        FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

    }

    /**
     * 生成视图
     *
     * @param generateModel: 所需路径
     * @param templatePathArr: 模板路径
     * @param templateSuffixArr: 模板后缀名
     */
    public static void generateView(GenerateModel generateModel , String[] templatePathArr , String[] templateSuffixArr) {

        for (int i = 0; i < templatePathArr.length; i++) {

            String templatePath = templatePathArr[i];
            String templateType = templateSuffixArr[i];

            //输出文件路径
            String outFilePath = generateModel.getRootPath() + "/src/main/webapp/view" + generateModel.getRequestUrl() + "/" + generateModel.getPropertyPrefix() + "_" + templateType + ".jsp";
            FreeMarkerUtil.writeToFile(templatePath, generateModel, outFilePath);

        }

    }

    /**
     * 生成单表的crud代码
     *
     * @param generateModel
     */
    public static void generateSingleTableModuleCode(GenerateModel generateModel) {

        generateController(generateModel , "/template/table/java/controller/Controller.ftl");
        generateService(generateModel , "/template/table/java/service/Service.ftl");
        generateServiceImpl(generateModel , "/template/table/java/service/impl/ServiceImpl.ftl");
        generateDao(generateModel , "/template/table/java/dao/Dao.ftl");
        generateDaoXml(generateModel , "/template/table/java/dao/Dao.xml.ftl");
        generateModel(generateModel);

        //模板路径
        String[] templatePathArr = {
                "/template/table/view/list.ftl",
                "/template/table/view/detail.ftl",
                "/template/table/view/add.ftl",
                "/template/table/view/edit.ftl"
        };
        String[] templateSuffixArr = {"list", "detail", "add", "edit"};
        generateView(generateModel , templatePathArr , templateSuffixArr);

    }

    /**
     * 生成树组件的crud代码
     *
     * @param generateModel
     */
    public static void generateTreeModuleCode(GenerateModel generateModel) {

        generateController(generateModel , "/template/tree/java/controller/Controller.ftl");
        generateService(generateModel , "/template/tree/java/service/Service.ftl");
        generateServiceImpl(generateModel , "/template/tree/java/service/impl/ServiceImpl.ftl");
        generateDao(generateModel , "/template/tree/java/dao/Dao.ftl");
        generateDaoXml(generateModel , "/template/tree/java/dao/Dao.xml.ftl");
        generateModel(generateModel);

        //模板路径
        String[] templatePathArr = {
                "/template/tree/view/list.ftl",
                "/template/tree/view/add.ftl",
                "/template/tree/view/edit.ftl"
        };
        String[] templateSuffixArr = {"list", "add", "edit"};
        generateView(generateModel , templatePathArr , templateSuffixArr);

    }

}