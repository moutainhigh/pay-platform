package com.pay.platform.api.pay.lakala.service.impl;

import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.lakala.dao.LakalaPayDao;
import com.pay.platform.api.pay.lakala.service.LakalaPayService;
import com.pay.platform.api.pay.unified.dao.UnifiedPayDao;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:02
 */
@Service
public class LakalaPayServiceImpl implements LakalaPayService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UnifiedPayDao unifiedPayDao;

    @Autowired
    private LakalaPayDao lakalaPayDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String createOrderByLklFixed(String merchantNo, String merchantOrderNo, String orderAmount, String payWay, String notifyUrl, String returnUrl, String tradeCodeId) throws Exception {

        //1、订单手续费计算
        String platformOrderNo = OrderNoUtil.getOrderNoByUUId();
        OrderModel orderModel = new OrderModel();
        orderModel.setMerchantNo(merchantNo);
        orderModel.setMerchantOrderNo(merchantOrderNo);
        orderModel.setPlatformOrderNo(platformOrderNo);
        orderModel.setOrderAmount(Double.parseDouble(orderAmount));
        orderModel.setNotifyUrl(notifyUrl);
        orderModel.setReturnUrl(returnUrl);
        orderModel.setPayWay(payWay);
        orderService.rateHandle(orderModel);

        RedisLock lock = null;

        try {

            //根据交易码id,加上锁,避免同一个号在5分钟内同时下单
            lock = new RedisLock(redisTemplate, "createOrderTradeCodeLock::" + tradeCodeId);
            if (lock.lock()) {

                Map<String, Object> tradeCode = unifiedPayDao.queryTradeCode5MinuteNotUseById(tradeCodeId);
                if (tradeCode == null) {
                    return null;
                }

                //2、获取向下浮动金额,避免重复
                double lastAmount = unifiedPayDao.queryTradeCodeLastPayFloatAmount(tradeCodeId, orderAmount);
                double payFloatAmount;
                if (lastAmount > 0) {
                    //用上次的金额减去0.01
                    payFloatAmount = DecimalCalculateUtil.sub(lastAmount, 0.01);
                } else {
                    //第一次则用订单金额减去0.01即可
                    payFloatAmount = DecimalCalculateUtil.sub(Double.parseDouble(orderAmount), 0.01);
                }

                //为避免出错,造成损失,当浮动的金额超过5毛钱时,拒绝下单；
                double div = DecimalCalculateUtil.sub(Double.parseDouble(orderAmount), payFloatAmount);
                if (div > 0.5) {
                    return null;
                }

                orderModel.setPayFloatAmount(String.valueOf(payFloatAmount));
                orderModel.setTradeCodeId(tradeCodeId);
                orderModel.setTradeCodeNum(tradeCode.get("code_num").toString());

                //3、创建订单
                int count = orderDao.createOrder(orderModel);
                if (count > 0) {
                    return platformOrderNo;
                } else {
                    return null;
                }

            }

        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        return null;
    }

}