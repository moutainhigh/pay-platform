package com.pay.platform.modules.loopMgr.model;

import com.pay.platform.modules.base.model.BaseModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 * <p>
 * 交易码实体
 */
public class TradeCodeModel extends BaseModel {

    private String id;

    private String channelId;                //所属通道ID

    private String channelCode;                //所属通道编码

    private String merchantId;                //所属商家ID

    private Double minAmount;                //最小金额

    private Double maxAmount;                //最大金额

    private Double dayAmountLimit;                //单日收款金额限制

    private String codeNum;                //交易码的编号

    private String secret;                //设备回调密钥

    private String loginAccount;                //登录账号

    private String realName;                //号主姓名

    private String codeLink;                //收款码链接

    private String zfbUserId;                //支付宝PID

    private String createTime;                //创建时间


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }


    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public Double getMinAmount() {
        return this.minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }


    public Double getMaxAmount() {
        return this.maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }


    public Double getDayAmountLimit() {
        return this.dayAmountLimit;
    }

    public void setDayAmountLimit(Double dayAmountLimit) {
        this.dayAmountLimit = dayAmountLimit;
    }


    public String getCodeNum() {
        return this.codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }


    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    public String getLoginAccount() {
        return this.loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }


    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getCodeLink() {
        return this.codeLink;
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
    }


    public String getZfbUserId() {
        return this.zfbUserId;
    }

    public void setZfbUserId(String zfbUserId) {
        this.zfbUserId = zfbUserId;
    }


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}