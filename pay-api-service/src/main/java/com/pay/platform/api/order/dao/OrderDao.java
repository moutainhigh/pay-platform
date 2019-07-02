package com.pay.platform.api.order.dao;

import com.pay.platform.api.order.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
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
     * 查询支付通道信息
     *
     * @param code
     * @return
     */
    Map<String, Object> queryPayChannelByCode(@Param("code") String code);

    /**
     * 根据商家编号查询代理信息
     *
     * @param merchantNo
     * @return
     */
    Map<String, Object> queryAgentRateByMerchantNo(@Param("merchantNo") String merchantNo, @Param("payChannelId") String payChannelId);

    /**
     * 根据商家编号查询商家信息及费率
     *
     * @param merchantNo
     * @param payChannelId
     * @return
     */
    Map<String, Object> queryMerchantRateByMerchantNo(@Param("merchantNo") String merchantNo, @Param("payChannelId") String payChannelId);

    /**
     * 创建订单
     *
     * @param orderModel
     * @return
     */
    int createOrder(OrderModel orderModel);

    /**
     * 更新支付信息
     *
     * @param platformOrderNo
     * @param payNo
     * @param payStatus
     * @param payTime
     * @return
     */
    int updateOrderPayInfo(@Param("platformOrderNo") String platformOrderNo, @Param("payNo") String payNo
            , @Param("payStatus") String payStatus, @Param("payTime") String payTime);

    /**
     * 更新支付链接
     * @param id
     * @param qrCodeLink
     * @return
     */
    int updateOrderPayQrCodeLink(@Param("id") String id, @Param("qrCodeLink") String qrCodeLink);

    Map<String,Object> queryOrderById(@Param("tradeId") String tradeId);

    Map<String,Object> queryAgentRateInfo(@Param("agentId") String agentId , @Param("payChannelId") String payChannelId);

}