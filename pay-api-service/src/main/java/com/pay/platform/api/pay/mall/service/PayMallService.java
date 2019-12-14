package com.pay.platform.api.pay.mall.service;

public interface PayMallService {

    String createOrderByMall(String merchantNo, String merchantOrderNo, String orderAmount
            , String payWay, String notifyUrl, String clientIp, String baseUrl , String returnUrl) throws Exception;

}