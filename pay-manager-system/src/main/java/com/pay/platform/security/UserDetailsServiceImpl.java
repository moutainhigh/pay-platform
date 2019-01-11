package com.pay.platform.security;

import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.user.dao.UserDao;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjt
 * DateTime: 2016/10/16 10:43
 *
 * 2、授权管理：spring security所需组件, 用于实现用户认证, 获取用户所拥有角色
 */
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        //1,查询用户信息
        UserModel user = userDao.queryUserByAccount(account);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }

        //2,获取用户的角色列表(即以ROLE_开头的角色)
        List<RoleModel> roleList = userDao.queryRoleListByUserId(user.getId());
        if(roleList != null && roleList.size() > 0){
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            StringBuilder roleNames = new StringBuilder();
            for(RoleModel role : roleList){
                GrantedAuthority ga = new SimpleGrantedAuthority(role.getCode());
                authorities.add(ga);
                roleNames.append(role.getName() + ",");
            }
            user.setAuthorities(authorities);
            user.setRoleNames(roleNames.substring(0,roleNames.length() - 1));
        }

        //3,获取用户的权限列表(主要用于加载菜单权限)
        List<PermissionModel> permissionList = userDao.queryPermissionListByUserId(user.getId());
        user.setPermissionList(permissionList);

        return user;

    }

}