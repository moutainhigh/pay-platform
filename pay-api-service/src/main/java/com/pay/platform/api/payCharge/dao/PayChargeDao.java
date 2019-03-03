package com.pay.platform.api.payCharge.dao;

import com.pay.platform.api.order.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/3/2 21:45
 */
@MapperScan
public interface PayChargeDao {

    /**
     * 查询支付通道信息
     * @param code
     * @return
     */
    Map<String,Object> queryPayChannelByCode(@Param("code") String code);

    /**
     * 根据商家编号查询代理信息
     * @param merchantNo
     * @return
     */
    Map<String,Object> queryAgentRateByMerchantNo(@Param("merchantNo") String merchantNo , @Param("payChannelId") String payChannelId);

    /**
     * 根据商家编号查询商家信息及费率
     * @param merchantNo
     * @param payChannelId
     * @return
     */
    Map<String,Object> queryMerchantRateByMerchantNo(@Param("merchantNo") String merchantNo , @Param("payChannelId") String payChannelId);

    /**
     * 创建订单
     * @param orderModel
     * @return
     */
    int createOrder(OrderModel orderModel);

}