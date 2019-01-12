package com.pay.platform.modules.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.order.model.OrderModel;
import com.pay.platform.modules.order.service.OrderService;
import com.pay.platform.modules.order.dao.OrderDao;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageInfo<OrderModel> queryOrderList(OrderModel order) {
        return new PageInfo(orderDao.queryOrderList(order));
    }

}