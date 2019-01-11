package com.pay.platform.modules.sysmgr.role.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 *
 * 角色实体
 */
public class RoleModel extends BaseModel {

    private String id;                          //角色id
    private String name;                        //角色名称
    private String code;                        //角色码
    private String creatorId;                   //创建人
    private Date createTime;                     //创建时间 , createTime create_time
    private Integer isDel;                       //是否删除（0:不删除  1:删除）
    private Date deleteTime;                    //删除时间

    private boolean checked;            //回显时 - 是否选中

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}