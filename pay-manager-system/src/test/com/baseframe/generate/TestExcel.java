package com.baseframe.generate;

import com.pay.platform.common.util.ExcelUtil;
import com.pay.platform.common.util.JsonUtil;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/5/19 14:03
 */
public class TestExcel {

    @Test
    public void testParseExcel() throws Exception {

        File file = new File("/Users//Downloads/拉卡拉-支付宝固码-批量导入模板.xls");

        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("编号");
        fieldNameList.add("回调密钥");
        fieldNameList.add("登录账号");
        fieldNameList.add("收款链接");

        List<Map<String, String>> list = ExcelUtil.parseExcel(file , fieldNameList);

        System.out.println(JsonUtil.parseToJsonStr(list));

    }

}