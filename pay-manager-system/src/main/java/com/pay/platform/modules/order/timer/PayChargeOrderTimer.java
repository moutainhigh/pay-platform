package com.pay.platform.modules.order.timer;

import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.payCharge.util.PayUtil;
import com.pay.platform.modules.merchant.service.MerchantNotifyService;
import com.pay.platform.modules.order.model.OrderModel;
import com.pay.platform.modules.order.service.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/3/3 16:01
 */
@Component
public class PayChargeOrderTimer {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    /**
     * 同步订单状态
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void syncPayChargeOrderStatus() {

        //1、查询30分钟内未推送的订单
        List<OrderModel> orderModelList = orderService.queryNeedSyncPayOrderStatusList();

        //2、遍历并回调给商家
        for (OrderModel orderModel : orderModelList) {

            //待支付状态: 则主动查询通道接口，获取最新状态，再进行回调
            if (PayStatusEnum.waitPay.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {

                try {
                    String platformOrderNo = orderModel.getPlatformOrderNo();
                    String result = PayUtil.findOrder(platformOrderNo);
                    if (StringUtil.isNotEmpty(result)) {
                        JSONObject resultJson = new JSONObject(result);
                        if (resultJson.has("resultCode") && 200 == resultJson.getInt("resultCode")) {
                            JSONObject data = resultJson.getJSONObject("data");
                            //支付成功
                            if ("SUCCESS".equalsIgnoreCase(data.getString("status"))) {
                                String payTime = data.getString("finishedDate");
                                String channelActuatAmount = data.getString("actualPayment");

                                //相关业务处理：更新订单状态等
                                orderService.paySuccessBusinessHandle(platformOrderNo, null, payTime, channelActuatAmount);
                                orderModel = orderService.queryOrderByOrderNo(orderModel.getPlatformOrderNo());
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //已支付状态：直接回调
                if (PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {
                    merchantNotifyService.pushPaySuccessInfo(orderModel.getPlatformOrderNo());
                }

            }


        }


    }

}