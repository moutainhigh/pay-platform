package com.pay.platform.modules.order.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Map;

/**
 * User:
 * DateTime: 2019/3/10 11:06
 *
 * 账户资金相关
 */
@MapperScan
public interface AccountAmountDao {

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
     * 查询账户资金
     * @param userId
     * @return
     */
    Map<String,Object> queryAccountAmountInfo(@Param("userId") String userId);

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

    /**
     * 增加冻结资金
     *
     * @param userId
     * @param amount
     * @return
     */
    int addFreezeAmount(@Param("userId") String userId, @Param("amount") Double amount);

    /**
     * 增加账户余额-流水记录
     *
     * @param userId
     * @param refOrderNo
     * @param type
     * @param amount
     * @return
     */
    int addFreezeAmountBillLog(@Param("userId") String userId, @Param("refOrderNo") String refOrderNo, @Param("type") String type, @Param("amount") Double amount);

    /**
     * 减少账户余额
     *
     * @param userId
     * @param amount
     * @return
     */
    int reduceAccountAmount(@Param("userId") String userId, @Param("amount") Double amount);

    /**
     * 减少冻结资金
     *
     * @param userId
     * @param amount
     * @return
     */
    int reduceFreezeAmount(@Param("userId") String userId, @Param("amount") Double amount);


}