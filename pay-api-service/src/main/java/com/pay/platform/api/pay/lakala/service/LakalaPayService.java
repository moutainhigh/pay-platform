package com.pay.platform.api.pay.lakala.service;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:02
 */
public interface LakalaPayService {

    /**
     * 拉卡拉固码下单
     * @param merchantNo
     * @param merchantOrderNo
     * @param orderAmount
     * @param payWay
     * @param notifyUrl
     * @param returnUrl
     * @param tradeCodeId
     * @return
     */
    String createOrderByLklFixed(String merchantNo, String merchantOrderNo, String orderAmount, String payWay, String notifyUrl, String returnUrl, String tradeCodeId) throws Exception;

}