package com.pay.platform.modules.order.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

import com.pay.platform.modules.order.model.OrderModel;

/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
public interface OrderService {

    /**
     * 分页查询订单
     *
     * @param order
     * @return
     */
    PageInfo<OrderModel> queryOrderList(OrderModel order, String beginTime, String endTime, String[] merchantIdList);

    /**
     * 查询出需要定时推送支付信息的订单
     * <p>
     * 作为补偿机制,定时回调给商家
     *
     * @return
     */
    List<OrderModel> queryTimerPushSuccessInfoOrderList();

    /**
     * 根据订单号查询订单：商家单号、平台单号
     *
     * @param orderNo
     * @return
     */
    OrderModel queryOrderByOrderNo(String orderNo);

    /**
     * 支付成功回调 - 业务处理
     *
     * @param platformOrderNo
     * @param payNo
     * @param payTime
     * @return
     */
    boolean paySuccessBusinessHandle(String platformOrderNo, String payNo, String payTime) throws Exception;

    /**
     * 根据补单提供信息,查询订单是否存在
     * @param orderNo
     * @param merchantOrderNo
     * @param payAmount
     * @return
     */
    int queryOrderExistsByBuDanInfo(String orderNo, String merchantOrderNo, String payAmount);

}