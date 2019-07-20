package com.pay.platform.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User:
 * DateTime: 2016/12/30 10:18
 * <p>
 * 监听应用启动/销毁 , 用于在程序中执行各种异步任务
 */
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    private static final String EXECUTOR_NAME = "executor";

    /**
     * 初始化时 - 创建线程池
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //无界限线程池, 线程不定的线程池，只有非核心线程，会自动回收
        ExecutorService executor = Executors.newCachedThreadPool();
        servletContextEvent.getServletContext().setAttribute(EXECUTOR_NAME, executor);
    }

    /**
     * 销毁时 - 释放线程池
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ExecutorService executor = (ExecutorService) servletContextEvent.getServletContext().getAttribute(EXECUTOR_NAME);
        executor.shutdown();
    }

}