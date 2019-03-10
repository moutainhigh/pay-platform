package com.pay.platform.modules.sysmgr.user.model;

import com.pay.platform.modules.base.model.BaseModel;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * UserModel: zjt
 * DateTime: 16/9/25 15:11
 * <p>
 * 用户实体 , 需实现UserDetails接口, 返回spring security所需信息
 */
public class UserModel extends BaseModel implements UserDetails {

    private String id;                      //用户id
    private String account;                 //登录账号
    private String password;                //密码
    private String nickname;                //昵称
    private String avatar;                  //头像
    private String gender;                  //性别
    private String lastLoginTime;           //最后登录时间
    private String creatorId;               //创建人
    private Date createTime;                        //创建时间
    private Integer isDel;                          //是否删除（0:不删除  1:删除）
    private Date deleteTime;                        //删除时间
    private String phone;                           //手机号码
    private String orgId;                   //组织机构ID

    private String agentId;                //绑定的代理ID,根据此标识和角色码,确定当前用户是否为商家
    private String merchantId;              //绑定的商户ID,根据此标识和角色码,确定当前用户是否为商家
    private int needInitPassword;           //是否需要初始化密码（0:不需要 1:需要）; 代理和商户首次登陆需要修改密码;

    private String withdrawPassword;        //提现密码

    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();     //用户的角色列表(即以ROLE_开头的角色)

    private List<PermissionModel> permissionList;                        //用户的权限列表(加载菜单用)

    private String roleNames;                                            //显示角色名称

    private String orgName;                                             //组织机构名称

    //扩展图片路径参数,仅做多图片上传测试用,无任何业务意义
    private String extendImgUrl;

    public String getExtendImgUrl() {
        return extendImgUrl;
    }

    public void setExtendImgUrl(String extendImgUrl) {
        this.extendImgUrl = extendImgUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public List<PermissionModel> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionModel> permissionList) {
        this.permissionList = permissionList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * spring security所需数据
     *
     * @return
     */
    @Override
    public String getUsername() {
        return account;                     //用户名
    }

    public String getPassword() {
        return password;                    //密码
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;                        //是否未过时
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;                        //是否没有冻结
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;                          //是否启用
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;                 //权限信息
    }

    public int getNeedInitPassword() {
        return needInitPassword;
    }

    public void setNeedInitPassword(int needInitPassword) {
        this.needInitPassword = needInitPassword;
    }

    public String getWithdrawPassword() {
        return withdrawPassword;
    }

    public void setWithdrawPassword(String withdrawPassword) {
        this.withdrawPassword = withdrawPassword;
    }
}