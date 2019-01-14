package com.pay.platform.api.order.model;


import com.pay.platform.api.base.model.BaseModel;

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

    private Double rate;                //费率

    private Double handlingFee;                //手续费(元)

    private Double merchantAmount;                //商户金额(元)

    private String merchantId;                //商家ID

    private String merchantNo;                //商家编号

    private String channelId;                //通道ID

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


    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }


    public Double getHandlingFee() {
        return this.handlingFee;
    }

    public void setHandlingFee(Double handlingFee) {
        this.handlingFee = handlingFee;
    }


    public Double getMerchantAmount() {
        return this.merchantAmount;
    }

    public void setMerchantAmount(Double merchantAmount) {
        this.merchantAmount = merchantAmount;
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

    public Integer getNotifyNum() {
        return notifyNum;
    }

    public void setNotifyNum(Integer notifyNum) {
        this.notifyNum = notifyNum;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}