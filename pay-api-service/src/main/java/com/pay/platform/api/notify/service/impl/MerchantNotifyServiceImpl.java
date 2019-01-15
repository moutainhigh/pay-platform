package com.pay.platform.api.notify.service.impl;

import com.pay.platform.api.merchant.dao.MerchantDao;
import com.pay.platform.api.notify.dao.MerchantNotifyDao;
import com.pay.platform.api.notify.service.MerchantNotifyService;
import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.AESUtil;
import com.pay.platform.security.MerchantSecretCacheUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private MerchantNotifyDao merchantNotifyDao;

    @Autowired
    private OrderDao orderDao;

    private static final Logger logger = LoggerFactory.getLogger(MerchantNotifyServiceImpl.class);

    //最大重试次数
    private static final int MAX_ATTEMPTS = 6;

    //最大重试间隔时间。此处设置为120秒,2分钟。multiplier参数后，下次延迟时间根据是上次延迟时间乘以multiplier得出的，这会导致两次重试间的延迟时间越来越长;
    //该参数限制两次重试的最大间隔时间，当间隔时间大于该值时，计算出的间隔时间将会被忽略，使用上次的重试间隔时间。
    private static final int MAX_DELAY = 1000 * 120;

    //初始值为2秒钟,单位毫秒
    private static final int TIME_UNIT = 2000;

    /**
     * 作为乘数用于计算下次延迟时间
     * 第1次是直接调用0秒
     * 第2次是2秒, 则下次是2 * 2 = 4;
     * 第3次是4秒，则下次是4 * 2 = 8;
     * 第4次则为8秒,第5次为16秒,第6次为32秒,依次类推
     */
    private static final int MULTIPLIER = 2;

    /**
     * 推送支付成功消息 -> 商家
     * <p>
     * 支持失败重试机制：连续推送6次
     *
     * @param orderNo
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
        if (orderModel.getNotifyNum() >= MAX_ATTEMPTS) {     //服务器重启时候,会重新执行任务队列,避免重复推送次数过多,加上数据库限制
            logger.info("已达最大推送次数！");
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
        try {

            String responseJson = AESUtil.encrypt(json.toString(), notifySecret);
            String notifyResult = HttpClientUtil.doPost(notifyUrl, responseJson);

            //3,回调成功,收到商家反馈,更新推送次数、推送状态
            if ("SUCCESS".equalsIgnoreCase(notifyResult)) {
                orderDao.updateOrderNotifyStatus(orderNo, "success");           //已回调: 并收到商家响应
                return true;
            } else {
                orderDao.updateOrderNotifyStatus(orderNo, "notifyed");          //已回调: 但未收到商家响应
                throw new RemoteAccessException("回调商户失败,开启重试模式。");
            }

        } catch (Exception e) {
            logger.error("回调商户失败: " + e.getMessage());
            throw new RemoteAccessException("回调商户失败,开启重试模式。");
        }

    }

}