package com.pay.platform.modules.finance.withdraw.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 * <p>
 * 提现申请实体
 */
public class WithdrawModel extends BaseModel {

    private String id;

    private String merchantId;                //商家ID

    private String orderNo;                //提现唯一单号

    private Double withdrawAmount;                //提现金额

    private Double actualAmount;                //实际金额：需扣除提现手续费

    private String realName;                //真实姓名

    private String bankName;                //银行名称

    private String bankCard;                //银行卡号

    private String provinceName;                //开户银行所在省

    private String cityName;                //开户银行所在市

    private String checkStatus;                //审核状态（waitCheck:待审核 checkSuccess:审核成功 checkFail:审核失败）
    private String checkStatusDictDesc;        //审核状态（waitCheck:待审核 checkSuccess:审核成功 checkFail:审核失败） - 字典显示值

    private String remark;                //备注

    private String withdrawStatus;                //提现状态（withdrawApply：提现申请处理中: withdraFail:提现失败 withdrawSuccess:提现成功）
    private String withdrawStatusDictDesc;        //提现状态（withdrawApply：提现申请处理中: withdraFail:提现失败 withdrawSuccess:提现成功） - 字典显示值

    private String billNo;                //提现流水号：支付公司回调返回

    private String notifyResponse;                //支付公司异步回调报文

    private Double rate;                //提现费率

    private Double handlingFee;                //手续费(元)

    private String createTime;                //创建时间

    private String checkDesc;               //审核状态


    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public Double getWithdrawAmount() {
        return this.withdrawAmount;
    }

    public void setWithdrawAmount(Double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }


    public Double getActualAmount() {
        return this.actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }


    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getBankCard() {
        return this.bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }


    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckStatusDictDesc() {
        return this.checkStatusDictDesc;
    }

    public void setCheckStatusDictDesc(String checkStatusDictDesc) {
        this.checkStatusDictDesc = checkStatusDictDesc;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getWithdrawStatus() {
        return this.withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getWithdrawStatusDictDesc() {
        return this.withdrawStatusDictDesc;
    }

    public void setWithdrawStatusDictDesc(String withdrawStatusDictDesc) {
        this.withdrawStatusDictDesc = withdrawStatusDictDesc;
    }

    public String getBillNo() {
        return this.billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }


    public String getNotifyResponse() {
        return this.notifyResponse;
    }

    public void setNotifyResponse(String notifyResponse) {
        this.notifyResponse = notifyResponse;
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


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}