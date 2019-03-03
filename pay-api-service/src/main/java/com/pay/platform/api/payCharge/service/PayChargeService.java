package com.pay.platform.api.payCharge.service;

/**
 * User: zjt
 * DateTime: 2019/3/2 21:41
 */
public interface PayChargeService {

    /**
     * 创建充值订单
     * @param merchantNo
     * @param merchantOrderNo
     * @param orderAmount
     * @param payWay
     * @param notifyUrl
     * @param clientIp
     * @return
     */
    String createOrderByCharge(String merchantNo, String merchantOrderNo, String orderAmount, String payWay
            , String notifyUrl, String clientIp , String baseUrl) throws Exception;

}