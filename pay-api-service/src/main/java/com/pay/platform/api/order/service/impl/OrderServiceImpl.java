package com.pay.platform.api.order.service.impl;

import com.pay.platform.api.merchant.dao.MerchantDao;
import com.pay.platform.api.order.dao.AccountAmountDao;
import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.common.enums.AccountAmountType;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.plugins.redis.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/1/14 11:42
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private AccountAmountDao accountAmountDao;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderModel queryOrderByOrderNo(String orderNo) {
        return orderDao.queryOrderByOrderNo(orderNo);
    }

    @Override
    public List<OrderModel> queryWaitPushMerchantOrder() {
        return orderDao.queryWaitPushMerchantOrder();
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

                if (!(PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus()))) {
                    //1、修改支付状态、支付单号
                    count += orderDao.updateOrderPayInfo(platformOrderNo, payNo, PayStatusEnum.payed.getCode(), payTime , channelActuatAmount);

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

        } catch (Exception e) {
            e.printStackTrace();
            throw e;                        //抛出异常,回滚事务
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

}