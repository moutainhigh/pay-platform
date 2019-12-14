package com.pay.platform.api.pay.mall.service.impl;

import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.charge.util.PayUtil;
import com.pay.platform.api.pay.mall.service.PayMallService;
import com.pay.platform.api.pay.mall.util.PayMallUtil;
import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.util.OrderNoUtil;
import com.pay.platform.common.util.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PayMallServiceImpl implements PayMallService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Value("mall.server.url")
    private String mallServerUrl;

    @Override
    public String createOrderByMall(String merchantNo, String merchantOrderNo, String orderAmount, String payWay
            , String notifyUrl, String clientIp, String baseUrl, String returnUrl) throws Exception {

        //1、订单手续费计算
        String platformOrderNo = OrderNoUtil.getOrderNoByUUId();
        OrderModel orderModel = new OrderModel();
        orderModel.setMerchantNo(merchantNo);
        orderModel.setMerchantOrderNo(merchantOrderNo);
        orderModel.setPlatformOrderNo(platformOrderNo);
        orderModel.setOrderAmount(Double.parseDouble(orderAmount));
        orderModel.setNotifyUrl(notifyUrl);
        orderModel.setPayWay(payWay);
        orderModel.setId(UUID.randomUUID().toString());
        orderModel.setReturnUrl(returnUrl);
        orderService.rateHandle(orderModel);

        //2、创建订单
        int count = orderDao.createOrder(orderModel);

        //3,调用第三方接口
        if (count > 0) {
            String platformNotifyUrl = baseUrl + "/openApi/payNotifyOfMall";

            String result = PayMallUtil.createOrderByMall(platformOrderNo, orderAmount, payWay, platformNotifyUrl, returnUrl, clientIp, mallServerUrl);
            if (StringUtil.isNotEmpty(result)) {
                JSONObject resultJson = new JSONObject(result);

                //更新支付链接,返回订单id
                if ("1".equals(resultJson.getString("status"))) {
                    String data = resultJson.getString("data");
                    orderService.updateOrderPayQrCodeLink(orderModel.getId(), data);
                }

            }

            return orderModel.getId();

        }

        return null;

    }

}