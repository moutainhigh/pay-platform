package com.pay.platform.modules.payChannel.dao;

import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.payChannel.model.PayChannelModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface PayChannelDao {

    /**
     * 分页查询通道列表
     *
     * @param payChannel
     * @return
     */
    List<PayChannelModel> queryPayChannelList(PayChannelModel payChannel);

    /**
     * 根据id查询通道信息
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


    /**
     * 根据通道编码查询编号信息
     * @param channelCode
     * @return
     */
    PayChannelModel queryInfoByChannelCode(String channelCode);

}