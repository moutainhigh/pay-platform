package com.pay.platform.modules.order.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

import com.pay.platform.modules.order.model.OrderModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface OrderService {

    /**
     * 分页查询订单
     *
     * @param order
     * @return
     */
    PageInfo<OrderModel> queryOrderList(OrderModel order);

    /**
     * 查询出需要定时推送支付信息的订单
     *
     * 作为补偿机制,定时回调给商家
     * @return
     */
    List<OrderModel> queryTimerPushSuccessInfoOrderList();

}