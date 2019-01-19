package com.pay.platform.modules.merchant.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 * <p>
 * 商家实体
 */
public class MerchantModel extends BaseModel {

    private String id;

    private String merchantNo;                //商家编号

    private String merchantName;                //商户名称

    private String phone;                //手机号

    private String realName;                //真实姓名

    private String identityCode;                //身份证号码

    private String idCardImg1;                //身份证-正面

    private String idCardImg2;                //身份证-反面

    private String icpImg;                //icp证件

    private String merchantSecret;                //商家密钥

    private String checkStatus;                //审核状态(waitCheck:待审核 success:审核通过 fail:审核失败)

    private String checkDesc;                //审核备注

    private Integer isDel;                //是否删除(0:否 1:是)

    private String createTime;                //创建时间

    private String notifySecret;                   //回调密钥

    private String  notifySecret;  //回调密钥

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMerchantNo() {
        return this.merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }


    public String getMerchantName() {
        return this.merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }


    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getIdentityCode() {
        return this.identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }


    public String getIdCardImg1() {
        return this.idCardImg1;
    }

    public void setIdCardImg1(String idCardImg1) {
        this.idCardImg1 = idCardImg1;
    }


    public String getIdCardImg2() {
        return this.idCardImg2;
    }

    public void setIdCardImg2(String idCardImg2) {
        this.idCardImg2 = idCardImg2;
    }


    public String getIcpImg() {
        return this.icpImg;
    }

    public void setIcpImg(String icpImg) {
        this.icpImg = icpImg;
    }


    public String getMerchantSecret() {
        return this.merchantSecret;
    }

    public void setMerchantSecret(String merchantSecret) {
        this.merchantSecret = merchantSecret;
    }


    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }


    public String getCheckDesc() {
        return this.checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }


    public Integer getIsDel() {
        return this.isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNotifySecret() {
        return notifySecret;
    }

    public void setNotifySecret(String notifySecret) {
        this.notifySecret = notifySecret;
    }
}