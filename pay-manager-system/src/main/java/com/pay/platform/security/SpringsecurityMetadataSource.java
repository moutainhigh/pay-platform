package com.pay.platform.security;

import com.pay.platform.modules.sysmgr.permission.dao.PermissionDao;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.role.dao.RoleDao;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

/**
 * User:
 * DateTime: 2016/10/16 10:05
 *
 * 1、数据源 : 从数据库读取系统权限信息,返回格式为权限和角色的对应关系
 */
public class SpringsecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * key: 请求资源的url, 即权限
     * value: 该资源所需的角色, 即以ROLE_为前缀的角色
     */
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 容器启动时候,加载角色和权限的映射信息
     */
    private void loadResourceDefine() {

       if(resourceMap == null) {

            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

            //查询所有的权限
            List<PermissionModel> permissionList =  permissionDao.queryPermissionList();
            for (PermissionModel permission : permissionList) {

                //查询该权限分配了那些角色
                List<RoleModel> roleList = roleDao.queryRoleListByPermissionId(permission.getId());

                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                for(RoleModel role : roleList){
                    ConfigAttribute configAttribute = new SecurityConfig(role.getCode());
                    configAttributes.add(configAttribute);
                }

                //存储权限和角色
                if(StringUtil.isNotEmpty(permission.getPermissionUrl())){
                    String permissionUrl = permission.getPermissionUrl();
                    resourceMap.put(permissionUrl,configAttributes);
                }

            }

        }

    }

    /**
     * 拦截到用户请求后,获取请求资源所需要的角色
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        //获取请求url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        //只匹配前缀 , 例如/sysmgr/user/addUser  /sysmgr/user/deleteUser , 只匹配到/sysmgr/user即可
        if(requestUrl.contains("?")){
            requestUrl = requestUrl.substring(0,requestUrl.indexOf("?"));
        }
        if(requestUrl.substring(1).contains("/")){
            requestUrl = requestUrl.substring(0,requestUrl.lastIndexOf("/"));
        }

        if(resourceMap == null) {
            loadResourceDefine();
        }

        //返回该资源所需权限(角色)
        return resourceMap.get(requestUrl);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}