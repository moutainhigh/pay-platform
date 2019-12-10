package com.pay.platform.api.pay.lzyh.service.impl;

import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.lzyh.dao.LzyhPayDao;
import com.pay.platform.api.pay.lzyh.service.LzyhPayService;
import com.pay.platform.api.pay.lzyh.util.LzyhUtil;
import com.pay.platform.api.pay.unified.dao.UnifiedPayDao;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.common.socket.server.ServerSocketThread;
import com.pay.platform.common.util.*;
import com.pay.platform.common.websocket.config.SocketMessageType;
import com.pay.platform.common.websocket.service.AppWebSocketService;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * DateTime: 2019/5/22 16:02
 */
@Service
public class LzyhPayServiceImpl implements LzyhPayService {

    private static final Logger logger = LoggerFactory.getLogger(LzyhPayServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UnifiedPayDao unifiedPayDao;

    @Autowired
    private LzyhPayDao lzyhPayDao;

    @Autowired
    private AppWebSocketService appWebSocketService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 轮询查询可用的交易码
     *
     * @param merchantId
     * @param payChannelId
     * @param orderAmount
     * @return
     */
    @Override
    public List<Map<String, Object>> queryLooperTradeCodeByLzyh(String codeNum, String merchantId, String payChannelId, String orderAmount) {
        //1、去除已达到单日收款限制的号
        //2、根据单日收款笔数、单日收款金额排序；从小到大排序；
        List<Map<String, Object>> list = lzyhPayDao.queryLooperTradeCodeByLzyh(merchantId, payChannelId, orderAmount);

        if (list != null && list.size() > 0) {

            //后台测试支付时,可指定收款码进行测试;
            if (StringUtil.isNotEmpty(codeNum)) {
                for (Map<String, Object> map : list) {
                    if (codeNum.equalsIgnoreCase(map.get("code_num").toString())) {
                        List<Map<String,Object>> codeNumList = new ArrayList<>();
                        codeNumList.add(map);
                        return codeNumList;
                    }
                }
                return null;
            }

            //3、获取已经连接socket,在线的号
            //return appWebSocketService.getOnLineSocket(list);
            return list;
        }

        return null;
    }

    /**
     * 柳行下单接口
     *
     * @param merchantNo
     * @param merchantOrderNo
     * @param orderAmount
     * @param payWay
     * @param notifyUrl
     * @param returnUrl
     * @param tradeCodeId
     * @return
     * @throws Exception
     */
    @Override
    public OrderModel createOrderByLzyh(String merchantNo, String merchantOrderNo, String orderAmount, String payWay, String notifyUrl, String returnUrl, String tradeCodeId, String tradeCodeNum) throws Exception {

        //1、基本参数封装
        String platformOrderNo = OrderNoUtil.getOrderNoByUUId();
        OrderModel orderModel = new OrderModel();
        orderModel.setMerchantNo(merchantNo);
        orderModel.setMerchantOrderNo(merchantOrderNo);
        orderModel.setPlatformOrderNo(platformOrderNo);
        orderModel.setOrderAmount(Double.parseDouble(orderAmount));
        orderModel.setNotifyUrl(notifyUrl);
        orderModel.setReturnUrl(returnUrl);
        orderModel.setPayWay(payWay);
        orderModel.setId(UUID.randomUUID().toString());

        RedisLock lock = null;

        try {

            //根据收款码ID,加上锁,避免同一个号同时下单,造成金额重复
            lock = new RedisLock(redisTemplate, "createOrderTradeCodeLock::" + tradeCodeId);
            if (lock.lock()) {

                //2、生成支付浮动金额；
                //总浮动金额池
                List<Double> payFloatAmountList = LzyhUtil.getPayFloatAmountList(Double.parseDouble(orderAmount));

                //过去1小时内,已使用过的金额
                List<Double> usedPayFloatAmountList = lzyhPayDao.queryTradeCodeUsedPayFloatAmount(tradeCodeId);

                //去除已使用过的金额; 只保留过去1小时内未使用过的金额；
                if (usedPayFloatAmountList != null && usedPayFloatAmountList.size() > 0) {
                    payFloatAmountList.removeAll(usedPayFloatAmountList);
                }

                if (payFloatAmountList == null || payFloatAmountList.size() == 0) {
                    return null;
                }

                //从浮动金额池里面,随机产生一个金额作为支付金额
                int randomIndex = new Random().nextInt(payFloatAmountList.size());
                double payFloatAmount = payFloatAmountList.get(randomIndex);

                //为避免出错,造成损失,当浮动的金额超过20元时;拒绝下单; 每次浮动1元；代表浮动了20次；
                double div = Math.abs(DecimalCalculateUtil.sub(Double.parseDouble(orderAmount), payFloatAmount));
                if (div >= 20) {
                    return null;
                }
                orderModel.setPayFloatAmount(String.valueOf(payFloatAmount));
                orderModel.setTradeCodeId(tradeCodeId);
                orderModel.setTradeCodeNum(tradeCodeNum);

                //3、费率计算
                orderService.rateHandle(orderModel);

                //4、创建订单
                int count = orderDao.createOrder(orderModel);
                if (count > 0) {
                    return orderModel;
                } else {
                    return null;
                }

            }

        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        return null;
    }

    @Override
    public Map<String, Object> queryOrderInfoPyLzyhAppNotify(String codeNum, String amount, String payCode) {
        return lzyhPayDao.queryOrderInfoPyLzyhAppNotify(codeNum, amount, payCode);
    }

    @Override
    public int queryPayCodeExists(String payCode) {
        return lzyhPayDao.queryPayCodeExists(payCode);
    }

    /**
     * 发送获取收款码消息
     *
     * @param tradeCodeNum
     * @param secret
     * @param payFloatAmount
     */
    @Override
    public void sendGetQrCodeMessage(String orderId, String tradeCodeNum, String secret, String payFloatAmount) {

        try {

            JSONObject reqJson = new JSONObject();
            reqJson.put("nonce", orderId);
            reqJson.put("messageType", SocketMessageType.MESSAGE_GET_QR_CODE);
            reqJson.put("amount", Math.round(Double.parseDouble(payFloatAmount)));          //去除小数点
            reqJson.put("remarks", "");

            String sign = AppSignUtil.buildAppSign(JsonUtil.parseToMapString(reqJson.toString()), secret);
            reqJson.put("sign", sign);

            //线路1：发送socket消息
            AppContext.getExecutorService().submit(new Runnable() {
                @Override
                public void run() {
                    ServerSocketThread.sendMessageToUser(tradeCodeNum, reqJson.toString());
                }
            });

//            //线路2：发送websocket消息
//            AppContext.getExecutorService().submit(new Runnable() {
//                @Override
//                public void run() {
//                    appWebSocketService.sendMessageToUser(tradeCodeNum, reqJson.toString());
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Map<String, Object>> getWaitQrCodeData(String codeNum) {
        return lzyhPayDao.getWaitQrCodeData(codeNum);
    }

}