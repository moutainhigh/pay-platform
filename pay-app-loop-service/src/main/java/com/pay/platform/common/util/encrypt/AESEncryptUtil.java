package com.pay.platform.common.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * AES加密工具类
 */
public class AESEncryptUtil {

    /**
     * 初始化 AES Cipher
     *
     * @param password
     * @param isEncryptMode
     * @return
     */
    public static Cipher initAESCipher(String password, boolean isEncryptMode) {

        try {

            IvParameterSpec zeroIv = new IvParameterSpec(password.getBytes("utf-8"));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            final SecretKeySpec secretKey = new SecretKeySpec(password.getBytes("utf-8"), "AES");

            if (isEncryptMode) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, zeroIv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, zeroIv);
            }

            return cipher;

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | UnsupportedEncodingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密
     *
     * @param bytes    需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(byte[] bytes, String password) {

        try {

            Cipher cipher = initAESCipher(password, true);

            if (null == cipher) {
                return bytes;
            }

            return cipher.doFinal(bytes); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {

        try {

            Cipher cipher = initAESCipher(password, false);

            if (null == cipher) {
                return content;
            }

            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
