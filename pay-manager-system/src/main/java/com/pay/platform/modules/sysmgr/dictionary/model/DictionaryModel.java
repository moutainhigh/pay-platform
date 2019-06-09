package com.pay.platform.modules.sysmgr.dictionary.model;

import com.pay.platform.modules.base.model.BaseModel;

import java.util.Date;

/**
 * User:
 * DateTime: 2016/10/19 20:24
 *
 * 字典实体
 */
public class DictionaryModel extends BaseModel {

    private String id;                  //字典id
    private String dictType;            //字典类型
    private String dictDesc;            //字典描述
    private String itemCode;            //分类码
    private String itemDesc;            //分类描述
    private String parentId;                     //父节点id
    private Integer level;                       //级别
    private Integer seq;                         //排序字段
    private Date createTime;                     //创建时间 , createTime create_time
    private Integer isDel;                       //是否删除（0:不删除  1:删除）
    private Date deleteTime;                     //删除时间

    /**
     * 用于显示字段,不作为持久化存储
     */
    private String displayName;     //显示名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}