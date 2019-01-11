package com.pay.platform.security;

import com.pay.platform.common.util.encrypt.Md5Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/7 10:43
 */
public class ApiSignUtil {

    /**
     * 生成签名
     * @param params：请求参数
     * @param secret：密钥
     * @return
     */
    public static String buildSignByMd5(Map<String, String> params , String secret) {

        params.remove("sign");           //去除sign参数

        StringBuilder formatParams = new StringBuilder("");

        //1, 对请求参数key按ASCII排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        //2, 将参数拼接成“参数=参数值&”的方式
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (formatParams.toString().trim().length() > 0) {
                formatParams.append("&");
            }
            formatParams.append(key + "=" + value);
        }

        //3、请求参数拼接的字符串 + 商家密钥 -> MD5生成sign签名
        return Md5Util.md5_32(formatParams.toString() + secret);

    }



}