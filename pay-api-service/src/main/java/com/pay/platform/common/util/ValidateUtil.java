package com.pay.platform.common.util;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * User: zjt
 * DateTime: 2017/10/16 10:21
 */
public class ValidateUtil {

    /**
     * 校验对象属性是否为空
     *
     * @param sourceObj：校验对象
     * @param notEmptyFieldName：非空字段名称
     * @throws Exception
     */
    public static ValidateResult isEmpty(Object sourceObj, List<String> notEmptyFieldName) throws Exception {

        ValidateResult result = new ValidateResult();
        result.setSuccess(true);
        result.setMsg("校验成功");

        //得到类字节码对象
        Class userCla = (Class) sourceObj.getClass();

        //得到类中的所有属性集合
        Field[] fields = userCla.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            Field field = fields[i];
            field.setAccessible(true);

            String fieldName = field.getName();
            if (notEmptyFieldName.contains(fieldName)) {

                Object fieldValue = field.get(sourceObj);
                if (fieldValue == null || fieldValue.toString().trim().length() == 0) {
                    result.setSuccess(false);
                    result.setMsg("参数错误: " + fieldName + "不可为空");
                    break;
                }

            }

        }

        return result;

    }


}