package com.pay.platform.modules.merchant.service.impl;

import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.AESUtil;
import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.MerchantSecretCacheUtil;
import com.pay.platform.modules.merchant.dao.MerchantDao;
import com.pay.platform.modules.merchant.service.MerchantNotifyService;
import com.pay.platform.modules.order.dao.OrderDao;
import com.pay.platform.modules.order.model.OrderModel;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/14 11:09
 */
@EnableRetry
@Service
public class MerchantNotifyServiceImpl implements MerchantNotifyService {

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private OrderDao orderDao;

    private static final Logger logger = LoggerFactory.getLogger(MerchantNotifyServiceImpl.class);

    /**
     * 推送支付成功消息 -> 商家
     *
     * @param orderNo：商家单号、平台单号
     */
    @Override
    public boolean pushPaySuccessInfo(String orderNo) {

        try {

            //1,校验订单是否支付
            OrderModel orderModel = orderDao.queryOrderByOrderNo(orderNo);
            if (orderModel == null || !PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {
                logger.info("订单未支付！");
                return false;
            }

            //2,发送请求回调商家接口
            JSONObject json = new JSONObject();
            Map<String, Object> data = new HashMap();
            data.put("merchantOrderNo", orderModel.getMerchantOrderNo());           //商户订单号
            data.put("platformOrderNo", orderModel.getPlatformOrderNo());            //平台订单号
            data.put("payStatus", orderModel.getPayStatus());                   //支付状态(waitPay:待支付 payed:已支付 payFail:支付失败)
            data.put("payWay", orderModel.getPayWay());                         //支付方式(1:支付宝 2:微信)
            data.put("payTime", orderModel.getPayTime());                               //支付时间
            data.put("orderAmount", orderModel.getOrderAmount());                        //订单金额(元)
            data.put("payAmount", orderModel.getPayFloatAmount());                      //实际支付金额
            //data.put("actualAmount", orderModel.getActualAmount());                     //实际金额
            //data.put("handlingFee", orderModel.getHandlingFee());                       //手续费

            json.put("status", "1");
            json.put("msg", "支付回调");
            json.put("data", data);

            String notifyUrl = orderModel.getNotifyUrl();                                                            //回调地址
            String notifySecret = MerchantSecretCacheUtil.getNotifySecret(orderModel.getMerchantNo());               //回调密钥

            //部分商家是根据订单号,动态获取密钥信息;不是写死的;
            //因此需要返回一个明文的单号;以供商家查找对于的密钥进行解密
            if (notifyUrl.contains("?")) {
                notifyUrl += "&merchantOrderNo=" + orderModel.getMerchantOrderNo();
            } else {
                notifyUrl += "?merchantOrderNo=" + orderModel.getMerchantOrderNo();
            }

            //数据进行AES加密后,再回调给商家
            String responseJson = AESUtil.encrypt(json.toString(), notifySecret);
            String notifyResult = HttpClientUtil.doPost(notifyUrl, responseJson);

            logger.info("订单" + orderModel.getMerchantOrderNo() + "  " + "回调明文：" + json.toString());
            logger.info("订单" + orderModel.getMerchantOrderNo() + "  " + "回调地址：" + notifyUrl + "回调密文：" + responseJson);
            logger.info("订单" + orderModel.getMerchantOrderNo() + "  " + "回调结果：" + notifyResult);

            //3,回调成功,收到商家反馈,更新推送次数、推送状态
            if ("SUCCESS".equalsIgnoreCase(notifyResult)) {
                orderDao.updateOrderNotifyStatus(orderNo, "success");           //已回调: 并收到商家响应
                return true;
            } else {
                orderDao.updateOrderNotifyStatus(orderNo, "notifyed");          //已回调: 但未收到商家响应
            }


        } catch (Exception e) {
            logger.error("回调商户失败: " + e.getMessage());
        }

        return false;

    }

}