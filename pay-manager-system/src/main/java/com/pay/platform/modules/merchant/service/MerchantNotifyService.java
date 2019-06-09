package com.pay.platform.modules.merchant.service;

/**
 * User:
 * DateTime: 2019/1/14 11:09
 * <p>
 * 商家通知
 */
public interface MerchantNotifyService {

    /**
     * 推送支付成功消息 -> 商家
     *
     * @param orderNo：商家单号、平台单号
     */
    boolean pushPaySuccessInfo(String orderNo);

}