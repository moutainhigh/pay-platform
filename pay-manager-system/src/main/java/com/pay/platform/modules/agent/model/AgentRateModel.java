package com.pay.platform.modules.agent.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.math.BigDecimal;


public class AgentRateModel extends BaseModel {

    private String id;

    private String agentId;                //代理ID

    private String channelId;                //通道ID

    private String rate;                //商家费率

    private String createTime;                //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}