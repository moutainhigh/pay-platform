package com.pay.platform.api.pay.charge.service.impl;

import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.charge.service.PayChargeService;
import com.pay.platform.api.pay.charge.util.PayUtil;
import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.OrderNoUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * User:
 * DateTime: 2019/3/2 21:42
 */
@Service
public class PayChargeServiceImpl implements PayChargeService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Override
    public String createOrderByCharge(String merchantNo, String merchantOrderNo, String orderAmount
            , String payWay, String notifyUrl, String clientIp, String baseUrl) throws Exception {

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
        orderService.rateHandle(orderModel);

        //2、创建订单
        int count = orderDao.createOrder(orderModel);

        //3,调用第四方话冲接口
        if (count > 0) {
            String platformNotifyUrl = baseUrl + "/openApi/payNotifyOfCharge";

            //将支付方式转换成第三方接口所需的
            String payType = null;
            if (PayChannelEnum.hcWechat.getCode().equalsIgnoreCase(payWay)) {
                payType = "1";
            } else if (PayChannelEnum.hcZfb.getCode().equalsIgnoreCase(payWay)) {
                payType = "2";
            }

            String result = PayUtil.charge(platformOrderNo, orderAmount, payType, platformNotifyUrl, clientIp);
            JSONObject resultJson = new JSONObject(result);
            if (resultJson.has("resultCode") && 200 == resultJson.getInt("resultCode")) {
                //更新支付链接,返回订单id
                orderService.updateOrderPayQrCodeLink(orderModel.getId(), resultJson.getString("data"));
                return orderModel.getId();
            } else {
                throw new Exception(resultJson.getString("message"));
            }

        }

        return null;
    }

}