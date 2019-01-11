package com.pay.platform.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * User: zjt
 * DateTime: 2016/10/16 14:07
 *
 * 应用上下文, 在运行期间为其它组件提供通用的一些服务
 */
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;           //spring容器应用上下文

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}