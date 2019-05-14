package com.pay.platform.modules.codeTrader.model;

import com.pay.platform.modules.base.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 * <p>
 * 码商实体
 */
public class CodeTraderModel extends BaseModel {

    private String id;

    private String name;                //码商名称

    private String createTime;                //创建时间


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}