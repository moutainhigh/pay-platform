package com.pay.platform.modules.merchantRate.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.math.BigDecimal;



public class MerchantRateModel extends BaseModel {

    private String id;

    private String merchantId;                //商家ID

    private String channelId;                //通道ID

    private BigDecimal rate;                //商家费率

    private String createTime;                //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}