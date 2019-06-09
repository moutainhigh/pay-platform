package com.pay.platform.modules.sysmgr.role.service.impl;

import com.pay.platform.modules.sysmgr.permission.dao.PermissionDao;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.role.dao.RoleDao;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.role.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Integer addRole(RoleModel role) {
        return roleDao.addRole(role);
    }

    @Override
    public Integer deleteRole(String[] ids) {
        return roleDao.deleteRole(ids);
    }

    @Override
    public Integer updateRole(RoleModel role) {
        return roleDao.updateRole(role);
    }

    @Override
    public PageInfo<RoleModel> queryRoleList(String name , String code) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name",name);
        params.put("code",code);

        return new PageInfo(roleDao.queryRoleList(params));
    }

    @Override
    public RoleModel queryRoleById(String id) {
        return roleDao.queryRoleById(id);
    }

    @Override
    public Integer grantPermission(String roleId, String[] permissionIds) {

        Integer count = 0;

        //删除角色的所有权限
        count += roleDao.deletePermissionByRoleId(roleId);

        //重新授予权限
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("roleId",roleId);
        params.put("permissionIds",permissionIds);
        count += roleDao.addPermissionToRole(params);

        return count;
    }

    @Override
    public List<PermissionModel> queryPermissionListForGrant(String roleId) {

        //查询所有权限
        List<PermissionModel> permissionList = permissionDao.queryPermissionList();

        //查询角色所拥有权限
        List<PermissionModel> rolePermissionList = roleDao.queryPermissionListByRoleId(roleId);

        for(PermissionModel permission : permissionList){

            permission.setChecked(false);

            for(PermissionModel rolePermission : rolePermissionList){

                if(permission.getId().equals(rolePermission.getId())){
                    permission.setChecked(true);            //回显
                }

            }

        }

        return permissionList;
    }

}