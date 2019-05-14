package com.pay.platform.modules.merchant.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.math.BigDecimal;


public class MerchantRateListModel extends BaseModel {

    private String channelName;                //通道名称

    private BigDecimal rate;                //商家费率

    private String enabled;     //启用状态（0:关闭 1:启用）

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}