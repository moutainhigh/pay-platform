package com.pay.platform.modules.phoneConfig.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 * <p>
 * 手机校验配置实体
 */
public class PhoneConfigModel extends BaseModel {

    private String id;

    private String userId;                //用户ID

    private String phone;                //手机号

    private String type;                //操作类型
    private String typeDictDesc;        //操作类型 - 字典显示值

    private Integer sequence;                //使用顺序

    private String createTime;                //创建时间


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDictDesc() {
        return this.typeDictDesc;
    }

    public void setTypeDictDesc(String typeDictDesc) {
        this.typeDictDesc = typeDictDesc;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}