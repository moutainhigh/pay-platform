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

/**
 * User: zjt
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
            json.put("merchantNo", orderModel.getMerchantNo());              //商家编号
            json.put("merchantOrderNo", orderModel.getMerchantOrderNo());             //商户订单号
            json.put("platformOrderNo", orderModel.getPlatformOrderNo());             //平台订单号
            json.put("orderAmount", String.valueOf(orderModel.getOrderAmount()));               //订单金额(元)
            json.put("merchantAmount", String.valueOf(orderModel.getMerchantAmount()));         //费率
            json.put("rate", String.valueOf(orderModel.getRate()));                             //手续费(元)
            json.put("handlingFee", String.valueOf(orderModel.getHandlingFee()));               //商户金额(元)
            json.put("payStatus", orderModel.getPayStatus());           //支付状态(waitPay:待支付 payed:已支付 payFail:支付失败)
            json.put("payWay", orderModel.getPayWay());                 //支付方式(zfbScanCode:支付宝扫码支付 zfbH5:支付宝h5支付 wxScanCode:微信扫码支付 wxH5:微信H5支付)
            json.put("payTime", orderModel.getPayTime());               //支付时间

            String notifyUrl = orderModel.getNotifyUrl();                                                            //回调地址
            String notifySecret = MerchantSecretCacheUtil.getNotifySecret(orderModel.getMerchantNo());               //回调密钥


            //数据进行AES加密后,再回调给商家
            String responseJson = AESUtil.encrypt(json.toString(), notifySecret);
            String notifyResult = HttpClientUtil.doPost(notifyUrl, responseJson);

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