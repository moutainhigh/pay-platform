package com.pay.platform.modules.finance.payFor.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.*;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.modules.finance.payFor.dao.PayForDao;
import com.pay.platform.modules.finance.payFor.service.PayForService;
import com.pay.platform.modules.finance.withdraw.dao.WithdrawDao;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.order.dao.AccountAmountDao;
import com.pay.platform.modules.order.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * User: zjt
 * DateTime: 2019/3/12 20:39
 */
@Service
public class PayForServiceImpl implements PayForService {

    @Autowired
    private PayForDao payForDao;

    @Autowired
    private WithdrawDao withdrawDao;

    @Autowired
    private AccountAmountDao accountAmountDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<WithdrawModel> queryPayForList(WithdrawModel withdraw, String agentId, String merchantId, String beginTime, String endTime) {
        return new PageInfo(payForDao.queryPayForList(withdraw, agentId, merchantId, beginTime, endTime));
    }

    @Override
    public Integer payForReview(String id, String checkStatus, String checkDesc) {
        return payForDao.payForReview(id, checkStatus, checkDesc);
    }

    /**
     * 设置为提现成功
     * 注意：当redis与mysql事务同时存在时，需手动添加@Transactional注解
     * @param id
     * @param withdrawStatus
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateWithdrawStatusToSuccess(String id, String withdrawStatus) throws Exception {

        int count = 0;

        RedisLock lock = null;

        try {
            WithdrawModel withdrawModel = withdrawDao.queryWithdrawById(id);

            lock = new RedisLock(redisTemplate, "updateWithdrawStatusLock::" + withdrawModel.getOrderNo());

            //加上分布式锁,避免重复回调执行
            if (lock.lock()) {

                if (!CheckStatusEnum.checkSuccess.getCode().equalsIgnoreCase(withdrawModel.getCheckStatus())) {
                    throw new Exception("请先审核通过后再进行");
                }

                if (WithdrawStatusEnum.withdrawSuccess.getCode().equalsIgnoreCase(withdrawModel.getWithdrawStatus())) {
                    throw new Exception("此订单已转账成功,请勿重复操作！");
                }

                if (WithdrawStatusEnum.withdrawFail.getCode().equalsIgnoreCase(withdrawModel.getWithdrawStatus())) {
                    throw new Exception("此订单已设为转账失败,请勿重复操作！");
                }

                String userId = accountAmountDao.queryUserIdByMerchantId(withdrawModel.getMerchantId());

                //1、减少冻结资金
                count += accountAmountDao.reduceFreezeAmount(userId , withdrawModel.getWithdrawAmount());

                //2、记录冻结资金操作记录
                count += accountAmountDao.addFreezeAmountBillLog(userId , withdrawModel.getOrderNo() , FreezeAmountType.withdrawSuccess.getCode() , withdrawModel.getWithdrawAmount());

                //3、减少账户余额
                count += accountAmountDao.reduceAccountAmount(userId , withdrawModel.getWithdrawAmount());

                //4、商家账户余额操作日志
                count += accountAmountDao.addAccountAmountBillLog(userId , withdrawModel.getOrderNo() , FreezeAmountType.withdrawSuccess.getCode() , withdrawModel.getWithdrawAmount());

                //5、修改提现状态
                count += payForDao.updateWithdrawStatus(id , withdrawStatus);

            }

        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        if (count == 5) {
            return true;
        } else {
            throw new Exception("更改转账状态失败!");
        }

    }

    /**
     * 设置为提现失败
     * 注意：当redis与mysql事务同时存在时，需手动添加@Transactional注解
     * @param id
     * @param withdrawStatus
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateWithdrawStatusToFail(String id, String withdrawStatus) throws Exception{

        int count = 0;

        RedisLock lock = null;

        try {
            WithdrawModel withdrawModel = withdrawDao.queryWithdrawById(id);

            lock = new RedisLock(redisTemplate, "updateWithdrawStatusLock::" + withdrawModel.getOrderNo());

            //加上分布式锁,避免重复回调执行
            if (lock.lock()) {


                if (!CheckStatusEnum.checkSuccess.getCode().equalsIgnoreCase(withdrawModel.getCheckStatus())) {
                    throw new Exception("请先审核通过后再进行");
                }

                if (WithdrawStatusEnum.withdrawSuccess.getCode().equalsIgnoreCase(withdrawModel.getWithdrawStatus())) {
                    throw new Exception("此订单已转账成功,请勿重复操作！");
                }

                if (WithdrawStatusEnum.withdrawFail.getCode().equalsIgnoreCase(withdrawModel.getWithdrawStatus())) {
                    throw new Exception("此订单已设为转账失败,请勿重复操作！");
                }

                String userId = accountAmountDao.queryUserIdByMerchantId(withdrawModel.getMerchantId());

                //1、减少冻结资金
                count += accountAmountDao.reduceFreezeAmount(userId , withdrawModel.getWithdrawAmount());

                //2、记录冻结资金操作记录
                count += accountAmountDao.addFreezeAmountBillLog(userId , withdrawModel.getOrderNo() , FreezeAmountType.withdrawFail.getCode() , withdrawModel.getWithdrawAmount());

                //3、修改提现状态
                count += payForDao.updateWithdrawStatus(id , withdrawStatus);

            }

        }  finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        if (count == 3) {
            return true;
        } else {
            throw new Exception("更改转账状态失败!");
        }

    }

}