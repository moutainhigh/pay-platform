package com.pay.platform.modules.agent.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 * <p>
 * 代理实体
 */
public class AgentModel extends BaseModel {

    private String id;

    private String agentNo;                //代理编号

    private String agentName;                //代理名称

    private String phone;                //手机号

    private String realName;                //真实姓名

    private String identityCode;                //身份证号码

    private String idCardImg1;                //身份证-正面

    private String idCardImg2;                //身份证-反面

    private String icpImg;                //icp证件

    private Integer isDel;                //是否删除(0:否 1:是)

    private String createTime;                //创建时间


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAgentNo() {
        return this.agentNo;
    }

    public void setAgentNo(String agentNo) {
        this.agentNo = agentNo;
    }


    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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


}