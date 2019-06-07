package com.pay.platform.modules.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.AccountAmountType;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.modules.order.dao.AccountAmountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.order.model.OrderModel;
import com.pay.platform.modules.order.service.OrderService;
import com.pay.platform.modules.order.dao.OrderDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AccountAmountDao accountAmountDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<OrderModel> queryOrderList(OrderModel order , String beginTime , String endTime , String[] merchantIdList) {
        return new PageInfo(orderDao.queryOrderList(order , beginTime , endTime , merchantIdList));
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
    public boolean paySuccessBusinessHandle(String platformOrderNo, String payNo, String payTime) throws Exception {

        int count = 0;

        RedisLock lock = null;

        try {

            lock = new RedisLock(redisTemplate, "paySuccessNotifyLock::" + platformOrderNo);

            //加上分布式锁,避免重复回调执行
            if (lock.lock()) {

                OrderModel orderModel = orderDao.queryOrderByOrderNo(platformOrderNo);

                if (!(PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus()))) {
                    //1、修改支付状态、支付单号
                    count += orderDao.updateOrderPayInfo(platformOrderNo, payNo, PayStatusEnum.payed.getCode(), payTime);

                    //2、增加代理的账户余额,并记录流水
                    String agentId = orderModel.getAgentId();
                    String agentUserId = accountAmountDao.queryUserIdByAgentId(agentId);
                    count += accountAmountDao.addAccountAmount(agentUserId, orderModel.getAgentAmount());
                    count += accountAmountDao.addAccountAmountBillLog(agentUserId, orderModel.getPlatformOrderNo(), AccountAmountType.paySuccess.getCode(), orderModel.getAgentAmount());

                    //3、增加商家的账户余额,并记录流水
                    String merchantId = orderModel.getMerchantId();
                    String merchantUserId = accountAmountDao.queryUserIdByMerchantId(merchantId);
                    count += accountAmountDao.addAccountAmount(merchantUserId, orderModel.getActualAmount());
                    count += accountAmountDao.addAccountAmountBillLog(merchantUserId, orderModel.getPlatformOrderNo(), AccountAmountType.paySuccess.getCode(), orderModel.getActualAmount());

                }

            }

        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        if (count == 5) {
            return true;
        } else {
            throw new Exception("订单:" + platformOrderNo + "支付回调业务处理失败,回滚事务!");
        }

    }

    @Override
    public int queryOrderExistsByBuDanInfo(String orderNo, String merchantOrderNo, String payAmount) {
        return orderDao.queryOrderExistsByBuDanInfo(orderNo , merchantOrderNo , payAmount);
    }

}