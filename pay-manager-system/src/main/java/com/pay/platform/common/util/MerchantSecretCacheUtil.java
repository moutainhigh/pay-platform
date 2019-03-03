package com.pay.platform.common.util;

import com.pay.platform.common.context.AppContext;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/14 14:37
 * <p>
 * 商户密钥缓存
 */
public class MerchantSecretCacheUtil {

    private static final Logger logger = LoggerFactory.getLogger(MerchantSecretCacheUtil.class);

    private static MerchantService merchantService = null;

    /**
     * 商家密钥：key:商家编号 value:商家密钥
     */
    private final static Map<String, String> merchantSecretMap = new HashMap<>();

    /**
     * 回调密钥：key:商家编号 value:回调密钥
     */
    private final static Map<String, String> notifySecretMap = new HashMap<>();

    /**
     * 获取商户密钥
     *
     * @param merchantNo
     */
    public static String getMerchantSecret(String merchantNo) {

        if (merchantService == null) {
            merchantService = AppContext.getApplicationContext().getBean(MerchantService.class);
        }

        //懒加载: 商户密钥获取
        if (StringUtil.isEmpty(merchantSecretMap.get(merchantNo))) {
            MerchantModel merchantModel = merchantService.queryMerchantSecretByIMerchantNo(merchantNo);
            if (merchantModel != null) {
                merchantSecretMap.put(merchantNo, merchantModel.getMerchantSecret());
                notifySecretMap.put(merchantNo, merchantModel.getNotifySecret());
            }
        }

        return merchantSecretMap.get(merchantNo);

    }

    /**
     * 获取商户密钥
     *
     * @param merchantNo
     */
    public static String getNotifySecret(String merchantNo) {

        if (merchantService == null) {
            merchantService = AppContext.getApplicationContext().getBean(MerchantService.class);
        }

        //懒加载: 商户密钥获取
        if (StringUtil.isEmpty(notifySecretMap.get(merchantNo))) {
            MerchantModel merchantModel = merchantService.queryMerchantSecretByIMerchantNo(merchantNo);
            if (merchantModel != null) {
                merchantSecretMap.put(merchantNo, merchantModel.getMerchantSecret());
                notifySecretMap.put(merchantNo, merchantModel.getNotifySecret());
            }
        }

        return notifySecretMap.get(merchantNo);

    }


}