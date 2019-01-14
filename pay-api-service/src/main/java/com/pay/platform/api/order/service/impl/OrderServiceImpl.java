package com.pay.platform.api.order.service.impl;

import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/14 11:42
 */
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    public OrderModel queryOrderByOrderNo(String orderNo) {
        return orderDao.queryOrderByOrderNo(orderNo);
    }

}