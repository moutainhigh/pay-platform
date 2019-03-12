package com.pay.platform.modules.finance.payFor.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.finance.payFor.dao.PayForDao;
import com.pay.platform.modules.finance.payFor.service.PayForService;
import com.pay.platform.modules.finance.withdraw.dao.WithdrawDao;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.order.dao.AccountAmountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * User: zjt
 * DateTime: 2019/3/12 20:39
 */
@Service
public class PayForServiceImpl implements PayForService {

    @Autowired
    private PayForDao payForDao;

    @Autowired
    private AccountAmountDao accountAmountDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<WithdrawModel> queryPayForList(WithdrawModel withdraw, String agentId, String merchantId, String beginTime, String endTime) {
        return new PageInfo(payForDao.queryPayForList(withdraw, agentId, merchantId, beginTime, endTime));
    }

}