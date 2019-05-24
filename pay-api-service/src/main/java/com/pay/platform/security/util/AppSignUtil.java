package com.pay.platform.security.util;

import com.pay.platform.common.util.AESUtil;
import com.pay.platform.common.util.encrypt.Md5Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/7 10:43
 */
public class AppSignUtil {

    private static final String APP_AES_SECRET = "kx824vgx";        //aes密钥

    /**
     * 生成签名
     * @param params：请求参数
     * @param secret：密钥
     *
     * 1、对所有请求参数和时间戳进行排序
     * 2、并“参数=参数值”的模式用“&”字符拼接成字符串 + 加上商家密钥 -> MD5生成第一遍sign签名
     * 3、再通过aes加密，生成第二遍签名（防止app界面输入的签名泄漏; 因此进行二次加密签名）
     *
     * @return
     */
    public static String buildAppSignByMd5(Map<String, String> params , String secret) throws Exception {

        params.remove("sign");           //去除sign参数

        StringBuilder formatParams = new StringBuilder("");

        //1, 对请求参数key按ASCII排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        //2, 将参数拼接成“参数=参数值&”的方式
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            //为空的参数不参与签名
            if (value != null && value.length() > 0) {
                if (formatParams.toString().trim().length() > 0) {
                    formatParams.append("&");
                }

                formatParams.append(key + "=" + value);
            }

        }

        //3、请求参数拼接的字符串 + 商家密钥 -> MD5生成sign签名
        String firstSign =  Md5Util.md5_32(formatParams.toString() + secret);

        //4、再通过aes加密，生成第二遍签名（防止app界面输入的签名泄漏; 因此进行二次加密签名）
        return AESUtil.encrypt(firstSign , APP_AES_SECRET);

    }



}