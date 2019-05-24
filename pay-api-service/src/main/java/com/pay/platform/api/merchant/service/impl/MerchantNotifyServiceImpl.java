package com.pay.platform.api.merchant.service.impl;

import com.pay.platform.api.merchant.dao.MerchantDao;
import com.pay.platform.api.merchant.dao.MerchantNotifyDao;
import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.AESUtil;
import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.security.util.MerchantSecretCacheUtil;
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
    private MerchantNotifyDao merchantNotifyDao;

    @Autowired
    private OrderDao orderDao;

    private static final Logger logger = LoggerFactory.getLogger(MerchantNotifyServiceImpl.class);

    //最大重试次数
    private static final int MAX_ATTEMPTS = 6;

    //最大重试间隔时间。此处设置为120秒,2分钟。multiplier参数后，下次延迟时间根据是上次延迟时间乘以multiplier得出的，这会导致两次重试间的延迟时间越来越长;
    //该参数限制两次重试的最大间隔时间，当间隔时间大于该值时，计算出的间隔时间将会被忽略，使用上次的重试间隔时间。
    private static final int MAX_DELAY = 1000 * 120;

    //初始值为5秒钟,单位毫秒
    private static final int TIME_UNIT = 5000;

    /**
     * 作为乘数用于计算下次延迟时间
     * 第1次是直接调用1秒，则下次是1 * 5 = 5;
     * 第2次是5秒, 则下次是5 * 2 = 10;
     * 第3次是10秒，则下次是10 * 2 = 20;
     * 第4次则为20秒,第5次为40秒,第6次为80秒,依次类推
     */
    private static final int MULTIPLIER = 2;

    /**
     * 推送支付成功消息 -> 商家
     * <p>
     * 支持失败重试机制：连续推送6次,每隔1秒、5秒、10秒、20秒、40秒、80秒推送一次
     *
     * @param orderNo：商家单号、平台单号
     */
    @Retryable(value = {RemoteAccessException.class}, maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = TIME_UNIT, multiplier = MULTIPLIER, maxDelay = MAX_DELAY))
    public boolean pushPaySuccessInfoByRetry(String orderNo) {

        //1,校验订单是否支付
        OrderModel orderModel = orderDao.queryOrderByOrderNo(orderNo);
        if (orderModel == null || !PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {
            logger.info("订单未支付！");
            return false;
        }
        if ("success".equalsIgnoreCase(orderModel.getNotifyStatus())) {
            logger.info("已推送成功！");
            return false;
        }
        if (orderModel.getNotifyNum() >= MAX_ATTEMPTS) {     //服务器重启时候,会重新执行任务队列,避免重复推送次数过多,加上数据库限制
            logger.info("已达最大推送次数！");
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
        data.put("actualAmount", orderModel.getActualAmount());                     //实际金额
        data.put("handlingFee", orderModel.getHandlingFee());                       //手续费

        json.put("status", "1");
        json.put("msg", "支付回调");
        json.put("data", data);

        String notifyUrl = orderModel.getNotifyUrl();                                                            //回调地址
        String notifySecret = MerchantSecretCacheUtil.getNotifySecret(orderModel.getMerchantNo());               //回调密钥

        //数据进行AES加密后,再回调给商家
        try {

            String responseJson = AESUtil.encrypt(json.toString(), notifySecret);
            String notifyResult = HttpClientUtil.doPost(notifyUrl, responseJson);

            //3,回调成功,收到商家反馈,更新推送次数、推送状态
            if ("SUCCESS".equalsIgnoreCase(notifyResult)) {
                merchantNotifyDao.updateOrderNotifyStatus(orderNo, "success");           //已回调: 并收到商家响应
                return true;
            } else {
                merchantNotifyDao.updateOrderNotifyStatus(orderNo, "notifyed");          //已回调: 但未收到商家响应
                throw new RemoteAccessException("回调商户失败,开启重试模式。");
            }

        } catch (Exception e) {
            logger.error("回调商户失败: " + e.getMessage());
            throw new RemoteAccessException("回调商户失败,开启重试模式。");
        }

    }

}