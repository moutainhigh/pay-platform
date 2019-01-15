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
     * 支持失败重试机制：连续推送6次
     *
     * @param orderNo
     */
    boolean pushPaySuccessInfoByRetry(String orderNo);

}