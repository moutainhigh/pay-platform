package com.pay.platform.modules.order.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 * <p>
 * 订单实体
 */
public class OrderModel extends BaseModel {

    private String id;

    private String merchantOrderNo;                //商户订单号

    private String platformOrderNo;                //平台订单号

    private String payCode;                //第三方支付单号

    private String goodsName;                //商品名称

    private Double orderAmount;                //订单金额(元)

    private Double actualAmount;                //实际金额(元)

    private Double handlingFee;                //手续费(元)

    private Double costRate;                //成本费率
    private Double channelAmount;           //通道收入
    private Double agentRate;                   //代理费率
    private Double platformAmount;              //平台收入
    private Double merchantRate;            //商家费率
    private Double agentAmount;             //代理收入

    private String merchantId;                //商家ID

    private String merchantNo;                //商家编号
    private String merchantName;                //商家名称

    private String channelId;                //通道ID

    private String agentId;             //代理Id

    private String payWay;                //支付方式(zfbScanCode:支付宝扫码支付 zfbH5:支付宝h5支付 wxScanCode:微信扫码支付 wxH5:微信H5支付) 
    private String payWayDictDesc;        //支付方式(zfbScanCode:支付宝扫码支付 zfbH5:支付宝h5支付 wxScanCode:微信扫码支付 wxH5:微信H5支付)  - 字典显示值

    private String payStatus;                //支付状态(waitPay:待支付 payed:已支付 payFail:支付失败)
    private String payStatusDictDesc;        //支付状态(waitPay:待支付 payed:已支付 payFail:支付失败) - 字典显示值

    private String payTime;                //支付时间

    private String notifyUrl;                //商户回调地址

    private String notifyStatus;                //回调商户状态(notNotify:未通知 notifyed:已通知,并成功收到了商户反馈)
    private String notifyStatusDictDesc;        //回调商户状态(notNotify:未通知 notifyed:已通知,并成功收到了商户反馈) - 字典显示值

    private Integer notifyNum;                //通知次数

    private String createTime;                //创建时间


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMerchantOrderNo() {
        return this.merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }


    public String getPlatformOrderNo() {
        return this.platformOrderNo;
    }

    public void setPlatformOrderNo(String platformOrderNo) {
        this.platformOrderNo = platformOrderNo;
    }


    public String getPayCode() {
        return this.payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }


    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    public Double getOrderAmount() {
        return this.orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getHandlingFee() {
        return this.handlingFee;
    }

    public void setHandlingFee(Double handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public String getMerchantNo() {
        return this.merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }


    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public String getPayWay() {
        return this.payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayWayDictDesc() {
        return this.payWayDictDesc;
    }

    public void setPayWayDictDesc(String payWayDictDesc) {
        this.payWayDictDesc = payWayDictDesc;
    }

    public String getPayStatus() {
        return this.payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusDictDesc() {
        return this.payStatusDictDesc;
    }

    public void setPayStatusDictDesc(String payStatusDictDesc) {
        this.payStatusDictDesc = payStatusDictDesc;
    }

    public String getPayTime() {
        return this.payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }


    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }


    public String getNotifyStatus() {
        return this.notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getNotifyStatusDictDesc() {
        return this.notifyStatusDictDesc;
    }

    public void setNotifyStatusDictDesc(String notifyStatusDictDesc) {
        this.notifyStatusDictDesc = notifyStatusDictDesc;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Double getCostRate() {
        return costRate;
    }

    public void setCostRate(Double costRate) {
        this.costRate = costRate;
    }

    public Double getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(Double channelAmount) {
        this.channelAmount = channelAmount;
    }

    public Double getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(Double agentRate) {
        this.agentRate = agentRate;
    }

    public Double getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(Double platformAmount) {
        this.platformAmount = platformAmount;
    }

    public Double getMerchantRate() {
        return merchantRate;
    }

    public void setMerchantRate(Double merchantRate) {
        this.merchantRate = merchantRate;
    }

    public Double getAgentAmount() {
        return agentAmount;
    }

    public void setAgentAmount(Double agentAmount) {
        this.agentAmount = agentAmount;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Integer getNotifyNum() {
        return notifyNum;
    }

    public void setNotifyNum(Integer notifyNum) {
        this.notifyNum = notifyNum;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}