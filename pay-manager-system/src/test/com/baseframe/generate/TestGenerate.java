package com.pay.platform.generate;

import com.pay.platform.modules.generate.model.ColumnModel;
import com.pay.platform.modules.generate.model.GenerateModel;
import com.pay.platform.modules.generate.service.DBMetaDataService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User:
 * DateTime: 2017/1/22 14:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/applicationContext.xml"})
@ActiveProfiles("development")
public class TestGenerate {

    @Autowired
    private DBMetaDataService dbMetaDataService;

    /**
     * 生成单表模块
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testGenerateSingleTableModule() throws Exception {

        GenerateModel generateModel = new GenerateModel();
        generateModel.setTableName("tb_goods");
        generateModel.setModuleName("城市");
        generateModel.setRequestUrl("/test/goods");
        generateModel.setRootPath("/Users//project/github/javaweb/baseframe");
        generateModel.setPackageName("com.pay.platform.modules.test.goods");
        generateModel.setClassPrefix("Goods");

        List<ColumnModel> columnModelList = dbMetaDataService.getColumnListByTableName(generateModel.getTableName());
        generateModel.setColumnList(columnModelList);

        //设置属性前缀
        String classPrefix = generateModel.getClassPrefix();
        generateModel.setPropertyPrefix(classPrefix.substring(0, 1).toLowerCase() + classPrefix.substring(1, classPrefix.length()));

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

        //字段内容设置
        for (int i = 0; i < columnModelList.size(); i++) {

            ColumnModel columnModel = columnModelList.get(i);
            if (1 == i) {
                columnModel.set_isQueryField(true);
                columnModel.setMatchPattern("=");
                columnModel.set_isAdd(true);
                columnModel.set_isEdit(true);

                columnModel.set_isShowList(true);
                columnModel.setFormType("text");
            } else if (2 == i) {
                columnModel.set_isQueryField(true);
                columnModel.setMatchPattern("like");
                columnModel.set_isAdd(true);
                columnModel.set_isEdit(true);

                columnModel.set_isShowList(true);
                columnModel.setFormType("datetime");
            } else {
                columnModel.set_isAdd(false);
                columnModel.set_isEdit(false);
                columnModel.setFormType("text");
//                columnModel.set_isShowList(true);
            }

        }

//        GenerateTableCodeUtil.generateController(generateModel);
//        GenerateTableCodeUtil.generateService(generateModel);
//        GenerateTableCodeUtil.generateDao(generateModel);
//        GenerateTableCodeUtil.generateModel(generateModel);
//        GenerateTableCodeUtil.generateView(generateModel);

    }


}