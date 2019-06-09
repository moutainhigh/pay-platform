package com.pay.platform.modules.payChannel.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 * <p>
 * 通道实体
 */
public class PayChannelModel extends BaseModel {

    private String id;

    private String channelCode;                //通道编码

    private String channelName;                //通道名称

    private String costRate;                //成本费率

    private Integer isDel;                //是否删除(0:否 1:是)

    private String createTime;                //创建时间


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }


    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCostRate() {
        return costRate;
    }

    public void setCostRate(String costRate) {
        this.costRate = costRate;
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