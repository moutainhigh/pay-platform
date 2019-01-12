package com.pay.platform.modules.order.dao;

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
    List<OrderModel> queryOrderList(OrderModel order);

}