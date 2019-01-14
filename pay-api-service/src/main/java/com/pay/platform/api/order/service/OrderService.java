package com.pay.platform.api.order.service;


import com.pay.platform.api.order.model.OrderModel;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface OrderService {

    /**
     * 根据订单号查询订单：商家单号、平台单号
     * @param orderNo
     * @return
     */
    OrderModel queryOrderByOrderNo(String orderNo);

}