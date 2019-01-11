package com.pay.platform.security;


import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * User: zjt
 * DateTime: 2016/10/16 10:00
 *
 * spring security过滤器
 * 必须包含authenticationManager,accessDecisionManager,securityMetadataSource三个核心组件
 *
 */
public class SpringsecurityFilter extends AbstractSecurityInterceptor implements Filter {

    //数据源: 加载所有定义过得权限 , 其它两个组件定义在AbstractSecurityInterceptorz中, 通过xml注入即可
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    public void init(FilterConfig arg0) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);

        /**
         * 内部流程:
         * 1. 获取请求资源所需的权限(或角色): SecurityMetadataSource.getAttributes(object);
         * 2. 进行匹配,判断用户是否拥有该权限(或角色): this.accessDecisionManager.decide(authenticated, object, attributes);
         */
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }

    }

    public void destroy() {

    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

}