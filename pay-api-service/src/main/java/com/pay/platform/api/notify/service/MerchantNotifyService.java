package com.pay.platform.api.notify.service;

/**
 * User: zjt
 * DateTime: 2019/1/14 11:09
 * <p>
 * 商家通知
 */
public interface MerchantNotifyService {

    /**
     * 推送支付成功消息 -> 商家
     *
     * @param orderNo
     */
    boolean pushPaySuccessInfoToMerchant(String orderNo);

}