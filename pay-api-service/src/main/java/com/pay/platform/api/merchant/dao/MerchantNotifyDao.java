package com.pay.platform.api.merchant.dao;

import com.pay.platform.api.merchant.model.MerchantModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * User:
 * DateTime: 2019/1/13 20:54
 */
@MapperScan
public interface MerchantNotifyDao {

    /**
     * 更新回调商户状态
     *
     * @param orderNo
     * @param notifyStatus
     * @return
     */
    int updateOrderNotifyStatus(@Param("orderNo") String orderNo, @Param("notifyStatus") String notifyStatus);

}