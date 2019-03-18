package com.pay.platform.common.util;


/**
 * User:
 * DateTime: 2016/10/14 22:55
 *
 * 用于类属性 和 数据库字段之间的转换
 * 1, 驼峰名称 和 下划线之间的转换
 * 2, java数据类型 与 数据库数据类型的转换
 *
 */
public class FieldConvertUtil {

    public static final char UNDERLINE = '_';

    /**
     * 驼峰命名转下划线,例如createTime -> create_time
     * @param param
     * @return
     */
    public static String parseCamelToUnderline(String param){

        if (param == null || "".equals(param.trim())){
            return "";
        }

        //如果已经是按下划线分割,则直接返回
        if(param.contains(String.valueOf(UNDERLINE))){
            return param;
        }

        int len = param.length();
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {

            char c = param.charAt(i);

            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }

        }

        return sb.toString();
    }

    /**
     * 下划线转驼峰,例如create_time -> createTime
     * @param param
     * @return
     */
    public static String parseUnderlineToCamel(String param){

        if (param == null || "".equals(param.trim())){
            return "";
        }

        //转成小写
        param = param.toLowerCase();

        int len = param.length();
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {

            char c = param.charAt(i);

            if (c == UNDERLINE){
                if (++i < len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }

        }

        return sb.toString();
    }

    /**
     * 将数据库类型转换成java的数据类型
     * @param param
     * @return
     */
    public static String parseToJavaType(String param){

        if(StringUtil.isEmpty(param)){
            return param;
        }

        //整型
        if("TINYINT".equalsIgnoreCase(param) || ("SMALLINT").equalsIgnoreCase(param)
                || ("MEDIUMINT").equalsIgnoreCase(param) || ("INT").equalsIgnoreCase(param) || ("INTEGER").equalsIgnoreCase(param)){
            return "Integer";
        }
        //长整形
        else if("BIGINT".equalsIgnoreCase(param)){
            return "Long";
        }
        //浮点型
        else if("FLOAT".equalsIgnoreCase(param) || "DOUBLE".equalsIgnoreCase(param) || "DECIMAL".equalsIgnoreCase(param)){
            return "Double";
        }
        //日期类型(暂用String)
        else if("DATETIME".equalsIgnoreCase(param) || "TIMESTAMP".equalsIgnoreCase(param) || "DATE".equalsIgnoreCase(param) || "TIME".equalsIgnoreCase(param) || "YEAR".equalsIgnoreCase(param)){
            return "String";
        }
        //其它类型,统一为字符串
        else{
            return "String";
        }

    }


}