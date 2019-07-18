package com.pay.platform.common.listener;

import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.socket.server.ServerSocketThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
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

    private ServerSocketThread serverSocketThread;

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

        restartPaySuccessInfoService(executor);

        if (null == this.serverSocketThread) {
            this.serverSocketThread = new ServerSocketThread();
            this.serverSocketThread.start();
            logger.info("启动Socket通信成功！");
        }

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

        //关闭服务端socket
        if (null != this.serverSocketThread && !this.serverSocketThread.isInterrupted()) {
            this.serverSocketThread.closeSocketServer();
            this.serverSocketThread.interrupt();
        }

    }

    /**
     * 重新启动推送支付成功回调信息
     *
     * @param executor
     */
    public void restartPaySuccessInfoService(ExecutorService executor) {

        try {

            OrderService orderService = AppContext.getApplicationContext().getBean(OrderService.class);
            MerchantNotifyService merchantNotifyService = AppContext.getApplicationContext().getBean(MerchantNotifyService.class);

            //查询待推送的订单,开启线程回调上架
            List<OrderModel> orderModelList = orderService.queryWaitPushMerchantOrder();
            if (orderModelList != null && orderModelList.size() > 0) {
                for (OrderModel orderModel : orderModelList) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            merchantNotifyService.pushPaySuccessInfoByRetry(orderModel.getMerchantOrderNo());
                        }
                    });
                }
            }

        } catch (Exception e) {
            logger.error("推送失败:" + e.getMessage());
        }

    }

}