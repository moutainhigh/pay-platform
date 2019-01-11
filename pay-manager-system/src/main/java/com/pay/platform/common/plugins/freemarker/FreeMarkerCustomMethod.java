package com.pay.platform.common.plugins.freemarker;

import com.pay.platform.common.util.FieldConvertUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2017/1/2 16:56
 *
 * freemarker自定义方法,主要用于字段转换,提供将驼峰命名法 转成 下划线命名
 * 在生成mybatis的mapper.xml的insert/update语句使用
 *
 */
public class FreeMarkerCustomMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List args) throws TemplateModelException {

        if(args != null && args.size() == 1){
            String fieldName = (String) args.get(0);
            return FieldConvertUtil.parseCamelToUnderline(fieldName);
        }

        return null;
    }

}