package com.pay.platform.common.util;

/**
 * User: zjt
 * DateTime: 2016/10/20 23:46
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}