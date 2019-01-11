package com.pay.platform.modules.sysmgr.log.model;


import com.pay.platform.modules.base.model.BaseModel;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 26//22 6:2
 *
 * 操作日志实体
 */
public class OperationLogModel extends BaseModel {

    private String 	id;
    private String 	userId;						    //操作用户id
    private String 	userName;						//操作用户名
    private String 	ip;						        //操作用户ip
    private String 	module;						    //操作模块
    private String  operation;						//执行操作
    private String 	description;					//描述信息
    private Integer status;						    //状态（0:失败 1:成功）
    private Date createTime;					    //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}