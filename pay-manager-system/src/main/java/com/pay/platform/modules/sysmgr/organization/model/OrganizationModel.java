package com.pay.platform.modules.sysmgr.organization.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * User: zjt
 * DateTime: 2016/10/19 20:07
 *
 * 组织机构实体
 */
public class OrganizationModel extends BaseModel implements Serializable {

    private String id;                          //机构ID
    private String name;                        //机构名称
    private String description;                 //机构描述
    private String contactPerson;           //联系人
    private String tel;                     //联系电话
    private String email;                   //邮箱
    private String fax;                     //传真
    private String parentId;                    //父节点id
    private Integer seq;                         //排序字段
    private Integer level;                       //级别
    private String creatorId;                   //创建人
    private Date createTime;                     //创建时间
    private Integer isDel;                       //是否删除（0:不删除  1:删除）
    private Date deleteTime;                    //删除时间
    private String address;         //地址
    private String postCode;        //邮编

    private Integer childCount;         //子节点数量
    private boolean checked;            //是否选中

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}