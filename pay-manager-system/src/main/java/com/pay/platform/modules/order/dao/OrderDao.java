package com.pay.platform.modules.order.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.order.model.OrderModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface OrderDao {

    /**
     * 分页查询订单列表
     *
     * @param order
     * @return
     */
    List<OrderModel> queryOrderList(@Param("order") OrderModel order, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 根据订单号查询订单：商家单号、平台单号
     *
     * @param orderNo
     * @return
     */
    OrderModel queryOrderByOrderNo(String orderNo);

    /**
     * 更新回调商户状态
     *
     * @param orderNo
     * @param notifyStatus
     * @return
     */
    int updateOrderNotifyStatus(@Param("orderNo") String orderNo, @Param("notifyStatus") String notifyStatus);

    /**
     * 查询出需要定时推送支付信息的订单
     * <p>
     * 作为补偿机制,定时回调给商家
     *
     * @return
     */
    List<OrderModel> queryTimerPushSuccessInfoOrderList();

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
            , @Param("payStatus") String payStatus, @Param("payTime") String payTime, @Param("channelActuatAmount") String channelActuatAmount);

    /**
     * 根据代理Id查询用户id
     *
     * @param agentId
     * @return
     */
    String queryUserIdByAgentId(@Param("agentId") String agentId);

    /**
     * 根据商家Id查询用户id
     *
     * @param merchantId
     * @return
     */
    String queryUserIdByMerchantId(@Param("merchantId") String merchantId);

    /**
     * 增加账户余额
     *
     * @param userId
     * @param amount
     * @return
     */
    int addAccountAmount(@Param("userId") String userId, @Param("amount") Double amount);

    /**
     * 增加账户余额-流水记录
     *
     * @param userId
     * @param refOrderNo
     * @param type
     * @param amount
     * @return
     */
    int addAccountAmountBillLog(@Param("userId") String userId, @Param("refOrderNo") String refOrderNo, @Param("type") String type, @Param("amount") Double amount);

}