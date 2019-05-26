package com.pay.platform.api.order.service;


import com.pay.platform.api.order.model.OrderModel;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
public interface OrderService {

    /**
     * 根据订单号查询订单：商家单号、平台单号
     *
     * @param orderNo
     * @return
     */
    OrderModel queryOrderByOrderNo(String orderNo);

    /**
     * 查询待推送商家的订单
     *
     * @return
     */
    List<OrderModel> queryWaitPushMerchantOrder();

    /**
     * 费率计算
     *
     * @param orderModel
     * @throws Exception
     */
    void rateHandle(OrderModel orderModel) throws Exception;

    /**
     * 支付成功回调 - 业务处理
     *
     * @param platformOrderNo
     * @param payNo
     * @param payTime
     * @return
     */
    boolean paySuccessBusinessHandle(String platformOrderNo, String payNo, String payTime, String payFloatAmount) throws Exception;

    /**
     * 根据支付方式查询通道信息
     * @param payWay
     * @return
     */
    Map<String,Object> queryPayChannelByCode(String payWay);

    /**
     * 根据回调金额信息 - 查询匹配的订单
     * @param codeNum
     * @param amount
     * @param orderNo
     * @return
     */
    Map<String,Object> queryOrderInfoPyAppNotifyAmount(String codeNum, String amount, String orderNo);

    int updateOrderPayQrCodeLink(String id, String qrCodeLink);

}