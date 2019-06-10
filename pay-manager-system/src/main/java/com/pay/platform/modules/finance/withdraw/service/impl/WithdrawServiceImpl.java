package com.pay.platform.modules.finance.withdraw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.WithdrawStatusEnum;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.OrderNoUtil;
import com.pay.platform.modules.order.dao.AccountAmountDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.finance.withdraw.service.WithdrawService;
import com.pay.platform.modules.finance.withdraw.dao.WithdrawDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private WithdrawDao withdrawDao;

    @Autowired
    private AccountAmountDao accountAmountDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<WithdrawModel> queryWithdrawList(WithdrawModel withdraw, String merchantId, String beginTime, String endTime) {
        return new PageInfo(withdrawDao.queryWithdrawList(withdraw, merchantId, beginTime, endTime));
    }

    @Override
    public WithdrawModel queryWithdrawById(String id) {
        return withdrawDao.queryWithdrawById(id);
    }

    @Override
    public Integer addWithdraw(String userId, WithdrawModel withdraw) throws Exception {

        int count = 0;

        RedisLock lock = null;

        try {

            //根据用户ID加上分布式锁,避免重复发起提现,造成资金不一致的情况
            lock = new RedisLock(redisTemplate, "applyWithdrawLock::" + userId);
            if (lock.lock()) {

                //校验是否有剩余可提现的资金
                Map<String, Object> accountAmountInfo = accountAmountDao.queryAccountAmountInfo(userId);
                double accountAmount = Double.parseDouble(accountAmountInfo.get("account_amount").toString());
                double freezeAmount = Double.parseDouble(accountAmountInfo.get("freeze_amount").toString());
                double avaiabledWithdrawAmount = DecimalCalculateUtil.sub(accountAmount, freezeAmount);

                double withdrawAmount = withdraw.getWithdrawAmount();
                if (withdrawAmount > avaiabledWithdrawAmount) {
                    throw new Exception("申请提现失败,可提现余额不足！");
                }

                //1、提现记录
                withdraw.setActualAmount(withdraw.getWithdrawAmount());
                String orderNo = OrderNoUtil.getOrderNoByUUId();
                withdraw.setOrderNo(orderNo);
                count += withdrawDao.addWithdraw(withdraw);

                //2、增加冻结资金
                count += accountAmountDao.addFreezeAmount(userId, withdraw.getWithdrawAmount());

                //3、记录冻结资金操作记录
                count += accountAmountDao.addFreezeAmountBillLog(userId, orderNo, WithdrawStatusEnum.withdrawApply.getCode(), withdraw.getWithdrawAmount());

            }

        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        if (count != 3) {
            throw new Exception("发起提现失败,请稍后再试！");
        }

        return count;
    }

    @Override
    public Integer deleteWithdraw(String[] ids) {
        return withdrawDao.deleteWithdraw(ids);
    }

    @Override
    public Integer deleteWithdrawByLogic(String[] ids) {
        return withdrawDao.deleteWithdrawByLogic(ids);
    }

    @Override
    public Integer updateWithdraw(WithdrawModel withdraw) {
        return withdrawDao.updateWithdraw(withdraw);
    }

    @Override
    public Integer initWithdrawPassword(String userId, String withdrawPassword) {
        return withdrawDao.initWithdrawPassword(userId, withdrawPassword);
    }

    @Override
    public Map<String, Object> queryAccountAmountInfo(String userId) {
        return accountAmountDao.queryAccountAmountInfo(userId);
    }

}