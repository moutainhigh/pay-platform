package com.pay.platform.common.util;

import com.pay.platform.common.util.encrypt.AESEncryptUtil;
import com.pay.platform.common.util.encrypt.Base64Util;
import org.springframework.security.crypto.codec.Base64;

/**
 * User:
 * DateTime: 2019/1/14 14:58
 */
public class AESUtil {

    /**
     * eas 数据加密
     *
     * @param data
     * @param secret
     * @return
     */
    public static String encrypt(String data, String secret) throws Exception {

        //1,进行aes加密
        byte[] bytes = AESEncryptUtil.encrypt(data.getBytes("utf-8"), secret);

        //2,再进行base64编码,将字节转换成字符串
        return Base64Util.encode(bytes);

    }

    /**
     * eas 数据解密
     *
     * @param data
     * @param secret
     * @return
     */
    public static String decrypt(String data, String secret) throws Exception {

        //1,进行base64解码
        byte[] bytes = Base64.decode(data.getBytes("utf-8"));

        //2,进行aes解密
        byte[] result = AESEncryptUtil.decrypt(bytes, secret);

        return new String(result, "utf-8");

    }

}