package com.pay.platform.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.concurrent.ExecutorService;

/**
 * User:
 * DateTime: 2016/10/16 14:07
 * <p>
 * 应用上下文, 在运行期间为其它组件提供通用的一些服务
 */
@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;           //spring容器应用上下文

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 获取spring容器 上下文环境
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取线程池 - 用于执行异步任务
     *
     * @return
     */
    public static ExecutorService getExecutorService() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        return (ExecutorService) servletContext.getAttribute("executor");
    }

}