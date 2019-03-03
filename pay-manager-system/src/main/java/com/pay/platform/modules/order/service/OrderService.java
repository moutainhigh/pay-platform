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

    /**
     * 根据订单号查询订单：商家单号、平台单号
     * @param orderNo
     * @return
     */
    OrderModel queryOrderByOrderNo(String orderNo);

    /**
     * 支付成功回调 - 业务处理
     * @param platformOrderNo
     * @param payNo
     * @param payTime
     * @return
     */
    boolean paySuccessBusinessHandle(String platformOrderNo , String payNo , String payTime , String channelActuatAmount) throws Exception;

}