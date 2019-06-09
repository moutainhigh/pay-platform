package com.pay.platform.modules.sysmgr.permission.model;
import com.pay.platform.modules.base.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * User:
 * DateTime: 16/10/5 14:13
 *
 * 权限实体
 */
public class PermissionModel extends BaseModel implements Serializable {

    private String id;                          //资源ID
    private String name;                        //权限名称
    private String permissionUrl;                //权限路径 , 例如/sysmgr/permission, /sysmgr/user的访问路径
    private String linkUrl;                      //首页地址 , 例如/view/sysmgr/permission/permission_list.jsp
    private String parentId;                    //父节点id
    private Integer seq;                         //排序字段
    private Integer level;                       //级别（0: 根   1：目录  2：链接）
    private String icon;                        //图标
    private String creatorId;                   //创建人
    private Date createTime;                     //创建时间
    private Integer isDel;                       //是否删除（0:不删除  1:删除）
    private Date deleteTime;                    //删除时间

    /**
     * 前端显示字段,无需关联表字段
     */
    private boolean checked;                //回显权限树是否选中

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}