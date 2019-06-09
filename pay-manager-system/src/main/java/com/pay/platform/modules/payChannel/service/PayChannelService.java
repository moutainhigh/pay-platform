package com.pay.platform.modules.payChannel.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.payChannel.model.PayChannelModel;

/**
 * User:
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
     * 根据通道编码查询编号信息
     * @param channelCode
     * @return
     */
    PayChannelModel queryInfoByChannelCode(String channelCode);

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

    /**
     * 根据通道编码查询编号信息
     * @return
     */
    List<PayChannelModel> queryAllPayChannelList();

    /**
     * 读取所有的通道费率信息,以及代理费率信息
     * @param agentId
     * @return
     */
    List<Map<String,Object>> queryAllPayChannelListAndAgentRate(String agentId);

}