package com.pay.platform.modules.payChannel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.payChannel.model.PayChannelModel;
import com.pay.platform.modules.payChannel.service.PayChannelService;
import com.pay.platform.modules.payChannel.dao.PayChannelDao;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class PayChannelServiceImpl implements PayChannelService {

    @Autowired
    private PayChannelDao payChannelDao;

    @Override
    public PageInfo<PayChannelModel> queryPayChannelList(PayChannelModel payChannel) {
        return new PageInfo(payChannelDao.queryPayChannelList(payChannel));
    }

    @Override
    public PayChannelModel queryPayChannelById(String id) {
        return payChannelDao.queryPayChannelById(id);
    }

    @Override
    public Integer addPayChannel(PayChannelModel payChannel) {
        return payChannelDao.addPayChannel(payChannel);
    }

    @Override
    public Integer deletePayChannel(String[] ids) {
        return payChannelDao.deletePayChannel(ids);
    }

    @Override
    public Integer deletePayChannelByLogic(String[] ids) {
        return payChannelDao.deletePayChannelByLogic(ids);
    }

    @Override
    public Integer updatePayChannel(PayChannelModel payChannel) {
        return payChannelDao.updatePayChannel(payChannel);
    }

}