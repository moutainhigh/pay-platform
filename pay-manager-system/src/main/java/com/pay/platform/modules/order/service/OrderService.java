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

}