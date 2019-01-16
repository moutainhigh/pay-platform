package com.pay.platform.modules.merchant.timer;

import com.pay.platform.modules.merchant.service.MerchantNotifyService;
import com.pay.platform.modules.order.model.OrderModel;
import com.pay.platform.modules.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/1/16 14:24
 * <p>
 * 商家回调通知-补偿机制：每隔2个小时查询一次订单,回调给商家
 */
@Component
public class MerchantNotifyTimer {

    private static final Logger logger = LoggerFactory.getLogger(MerchantNotifyTimer.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    /**
     * 推送支付成功消息 -> 商家
     * <p>
     * 2小时执行一次,针对那些通过任务调度回调了6次还是失败的订单
     * <p>
     * 最多补偿2次,次数累加到8
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void pushPaySuccessInfo() {

        //查询出需要定时推送支付信息的订单
        List<OrderModel> orderModelList = orderService.queryTimerPushSuccessInfoOrderList();

        if (orderModelList != null && orderModelList.size() > 0) {
            for (OrderModel orderModel : orderModelList) {
                merchantNotifyService.pushPaySuccessInfo(orderModel.getMerchantOrderNo());
            }
        }

    }

}