package com.pay.platform.modules.payChannel.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

import com.pay.platform.modules.payChannel.model.PayChannelModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface PayChannelService {

    /**
     * 分页查询通道
     *
     * @param payChannel
     * @return
     */
    PageInfo<PayChannelModel> queryPayChannelList(PayChannelModel payChannel);

    /**
     * 根据id查询通道
     *
     * @param id
     * @return
     */
    PayChannelModel queryPayChannelById(String id);

    /**
     * 新增通道
     *
     * @param payChannel
     * @return
     */
    Integer addPayChannel(PayChannelModel payChannel);

    /**
     * 删除通道
     *
     * @param ids
     * @return
     */
    Integer deletePayChannel(String[] ids);

    /**
     * 逻辑删除通道
     *
     * @param ids
     * @return
     */
    Integer deletePayChannelByLogic(String[] ids);

    /**
     * 修改通道
     *
     * @param payChannel
     * @return
     */
    Integer updatePayChannel(PayChannelModel payChannel);

}