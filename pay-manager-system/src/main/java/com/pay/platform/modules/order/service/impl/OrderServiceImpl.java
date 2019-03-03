package com.pay.platform.modules.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.plugins.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.order.model.OrderModel;
import com.pay.platform.modules.order.service.OrderService;
import com.pay.platform.modules.order.dao.OrderDao;
import org.springframework.transaction.annotation.Transactional;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<OrderModel> queryOrderList(OrderModel order , String beginTime , String endTime) {
        return new PageInfo(orderDao.queryOrderList(order , beginTime , endTime));
    }

    @Override
    public List<OrderModel> queryTimerPushSuccessInfoOrderList() {
        return orderDao.queryTimerPushSuccessInfoOrderList();
    }

    @Override
    public OrderModel queryOrderByOrderNo(String orderNo) {
        return orderDao.queryOrderByOrderNo(orderNo);
    }

    /**
     * 支付成功后 - 业务处理
     *
     * @param platformOrderNo
     * @param payNo
     * @param payTime
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean paySuccessBusinessHandle(String platformOrderNo, String payNo, String payTime , String channelActuatAmount) throws Exception {

        int count = 0;

        RedisLock lock = null;

        try {

            lock = new RedisLock(redisTemplate, "patSuccessNotifyLock::" + platformOrderNo);

            //加上分布式锁,避免重复回调执行
            if (lock.lock()) {

                OrderModel orderModel = orderDao.queryOrderByOrderNo(platformOrderNo);

                //修改支付状态、支付单号
                if (!(PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus()))) {
                    count += orderDao.updateOrderPayInfo(platformOrderNo, payNo, PayStatusEnum.payed.getCode(), payTime , channelActuatAmount);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;                        //抛出异常,回滚事务
        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        return count == 1;

    }

}