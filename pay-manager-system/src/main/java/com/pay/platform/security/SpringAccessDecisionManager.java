package com.pay.platform.security;

import com.pay.platform.common.util.GetAnonationUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

/**
 * User:
 * DateTime: 2016/10/16 11:05
 *
 * 3、验证器,负责验证用户是否拥有当前请求资源的权限
 */
public class SpringAccessDecisionManager  implements AccessDecisionManager {

    /**
     * 验证用户是否拥有该请求资源的权限
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        //请求资源无需权限
        if(configAttributes == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUrl = request.getRequestURL().toString();
        if(requestUrl.contains("?")){
            requestUrl = requestUrl.substring(0,requestUrl.indexOf("?"));
        }
        //判断是否为通用请求
        for(String commonUrl : GetAnonationUtil.getCommonRequestUrl()){
            if(requestUrl.contains(commonUrl)){
                return;
            }
        }

        //请求资源所需角色
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();

        while(iterator.hasNext()) {

            //访问所请求资源所需要的权限
            ConfigAttribute configAttribute = iterator.next();
            String needRole = configAttribute.getAttribute();
            System.out.println("needRole is " + needRole);

            //用户所拥有的权限
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if(needRole.equals(ga.getAuthority())) {
                    return;
                }
            }

        }

        throw new AccessDeniedException(" 没有权限访问！ ");
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

}
