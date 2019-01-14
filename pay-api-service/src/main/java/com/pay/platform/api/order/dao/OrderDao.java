package com.pay.platform.api.order.dao;

import com.pay.platform.api.order.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface OrderDao {

    /**
     * 根据订单号查询订单：商家单号、平台单号
     * @param orderNo
     * @return
     */
    OrderModel queryOrderByOrderNo(String orderNo);

    /**
     * 更新回调商户状态
     * @param orderNo
     * @param notifyStatus
     * @return
     */
    int updateOrderNotifyStatus(@Param("orderNo") String orderNo, @Param("notifyStatus") String notifyStatus);

}