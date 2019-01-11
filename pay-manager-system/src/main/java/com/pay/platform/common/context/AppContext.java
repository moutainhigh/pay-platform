package com.pay.platform.common.context;

import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: zjt
 * DateTime: 2016/10/16 14:07
 *
 * 应用上下文, 在运行期间为其它组件提供通用的一些服务
 */
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;           //spring容器应用上下文

    /**
     * 获取当前登录用户 , 在自定义的UserDetailServiceImpl中获取
     *
     * @return
     */
    public static UserModel getCurrentUser() throws CredentialsExpiredException {

        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj != null && obj instanceof UserModel){
            return (UserModel) obj;
        }else{
            return null;
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}