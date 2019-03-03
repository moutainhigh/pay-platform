package com.pay.platform.api.order.dao;

import com.pay.platform.api.order.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface OrderDao {

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
     * 更新回调商户状态
     *
     * @param orderNo
     * @param notifyStatus
     * @return
     */
    int updateOrderNotifyStatus(@Param("orderNo") String orderNo, @Param("notifyStatus") String notifyStatus);

    /**
     * 更新支付信息
     *
     * @param platformOrderNo
     * @param payNo
     * @param payStatus
     * @param payTime
     * @return
     */
    int updateOrderPayInfo(@Param("platformOrderNo") String platformOrderNo, @Param("payNo") String payNo, @Param("payStatus") String payStatus, @Param("payTime") String payTime);

}