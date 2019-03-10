package com.pay.platform.modules.finance.withdraw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.finance.withdraw.service.WithdrawService;
import com.pay.platform.modules.finance.withdraw.dao.WithdrawDao;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private WithdrawDao withdrawDao;

    @Override
    public PageInfo<WithdrawModel> queryWithdrawList(WithdrawModel withdraw) {
        return new PageInfo(withdrawDao.queryWithdrawList(withdraw));
    }

    @Override
    public WithdrawModel queryWithdrawById(String id) {
        return withdrawDao.queryWithdrawById(id);
    }

    @Override
    public Integer addWithdraw(WithdrawModel withdraw) {
        return withdrawDao.addWithdraw(withdraw);
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
        return withdrawDao.queryAccountAmountInfo(userId);
    }

}