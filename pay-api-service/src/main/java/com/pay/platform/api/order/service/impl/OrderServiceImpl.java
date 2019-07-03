package com.pay.platform.api.order.service.impl;

import com.pay.platform.api.merchant.dao.MerchantDao;
import com.pay.platform.api.order.dao.AccountAmountDao;
import com.pay.platform.api.order.dao.OrderDao;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.common.enums.AccountAmountType;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/14 11:42
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private AccountAmountDao accountAmountDao;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 根据订单号查询订单：商家单号、平台单号
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderModel queryOrderByOrderNo(String orderNo) {
        return orderDao.queryOrderByOrderNo(orderNo);
    }

    /**
     * 查询需要推送支付回调给商家的订单
     *
     * @return
     */
    @Override
    public List<OrderModel> queryWaitPushMerchantOrder() {
        return orderDao.queryWaitPushMerchantOrder();
    }

    /**
     * 费率计算
     *
     * @param orderModel
     * @throws Exception
     */
    @Override
    public void rateHandle(OrderModel orderModel) throws Exception {

        //1,计算商家手续费
        Map<String, Object> payChannelInfo = orderDao.queryPayChannelByCode(orderModel.getPayWay());
        String payChannelId = payChannelInfo.get("id").toString();
        Map<String, Object> merchantInfo = orderDao.queryMerchantRateByMerchantNo(orderModel.getMerchantNo(), payChannelId);
        String merchantId = merchantInfo.get("id").toString();
        double merchantRate = Double.parseDouble(merchantInfo.get("merchantRate").toString());             //商家费率
        double handlingFee = DecimalCalculateUtil.mul(orderModel.getOrderAmount(), merchantRate);          //商家手续费 = 订单金额 * 商家费率
        double actualAmount = DecimalCalculateUtil.sub(orderModel.getOrderAmount(), handlingFee);          //商家实收款 = 订单金额 - 商家手续费

        //2、计算代理费率及其收入
        Map<String, Object> agentInfo = orderDao.queryAgentRateByMerchantNo(orderModel.getMerchantNo(), payChannelId);
        Map<String, Object> parentAgentInfo = null;
        String agentId = agentInfo.get("id").toString();
        if (agentInfo != null && agentInfo.get("parent_id") != null && StringUtil.isNotEmpty(agentInfo.get("parent_id").toString())) {
            parentAgentInfo = orderDao.queryAgentRateInfo(agentInfo.get("parent_id").toString(), payChannelId);
        }

        //商家直属代理的费率及利润
        double agentRate = Double.parseDouble(agentInfo.get("agentRate").toString());                    //代理费率
        double agentProfitRate = DecimalCalculateUtil.sub(merchantRate, agentRate);                      //代理利润费率（例如代理为1.6,下级商家为2.0,则利润空间为0.4）
        double agentAmount = DecimalCalculateUtil.mul(orderModel.getOrderAmount(), agentProfitRate);     //代理收入 = 订单金额 * 代理利润空间

        //存在二级代理;则需要计算上级代理的分润;
        if ("2".equalsIgnoreCase(agentInfo.get("level").toString())) {
            String parentAgentId = parentAgentInfo.get("id").toString();
            double parentAgentRate = Double.parseDouble(parentAgentInfo.get("agentRate").toString());                     //上级代理的费率
            double parentAgentProfitRate = DecimalCalculateUtil.sub(agentRate, parentAgentRate);                          //上级代理的利润比例（一级为1.4;二级代理1.6,则利润空间为0.2）
            double parentAgentAmount = DecimalCalculateUtil.mul(orderModel.getOrderAmount(), parentAgentProfitRate);      //上级代理的利润空间                                                                             //上级代理的利润金额
            orderModel.setParentAgentId(parentAgentId);
            orderModel.setParentAgentAmount(parentAgentAmount);
            orderModel.setParentAgentRate(parentAgentRate);
        }

        //3、计算平台的费率及收入
        double costRate = Double.parseDouble(payChannelInfo.get("cost_rate").toString());       //通道综合成本费率s
        double platformProfitRate = 0;
        double platformAmount = 0;
        if ("1".equalsIgnoreCase(agentInfo.get("level").toString())) {
            platformProfitRate = DecimalCalculateUtil.sub(agentRate, costRate);                             //平台利润费率（例如成本为1.2，放给代理为1.6，则利润空间为0.4））
            platformAmount = DecimalCalculateUtil.mul(orderModel.getOrderAmount(), platformProfitRate);     //平台收入
        } else if ("2".equalsIgnoreCase(agentInfo.get("level").toString())) {
            //平台利润费率（例如成本为1.2，放给一级代理为1.4，二级代理为1.8，则平台利润空间为0.2）
            double parentAgentRate = Double.parseDouble(parentAgentInfo.get("agentRate").toString());
            platformProfitRate = DecimalCalculateUtil.sub(parentAgentRate, costRate);
            platformAmount = DecimalCalculateUtil.mul(orderModel.getOrderAmount(), platformProfitRate);     //平台收入
        }

        //4,计算通道收入（即码商、上游渠道的综合收入）
        double channelAmount = DecimalCalculateUtil.mul(orderModel.getOrderAmount(), costRate);     //订单金额 * 成本费率

        //5、将参数设置到订单实体
        orderModel.setChannelId(payChannelId);
        orderModel.setMerchantId(merchantId);
        orderModel.setMerchantRate(merchantRate);
        orderModel.setHandlingFee(handlingFee);
        orderModel.setActualAmount(actualAmount);
        orderModel.setAgentId(agentId);
        orderModel.setAgentRate(agentRate);
        orderModel.setAgentAmount(agentAmount);
        orderModel.setCostRate(costRate);
        orderModel.setPlatformAmount(platformAmount);
        orderModel.setChannelAmount(channelAmount);

    }


    /**
     * 支付成功后 - 业务处理
     * <p>
     * 注意：当redis与mysql事务同时存在时，需手动添加@Transactional注解
     *
     * @param platformOrderNo
     * @param payNo
     * @param payTime
     * @return
     * @throws Exception
     */
    @Override
    public boolean paySuccessBusinessHandle(String platformOrderNo, String payNo, String payTime) throws Exception {

        int count = 0;
        boolean existsParentAgent = false;

        RedisLock lock = null;

        try {

            lock = new RedisLock(redisTemplate, "paySuccessNotifyLock::" + platformOrderNo);

            //加上分布式锁,避免重复回调执行
            if (lock.lock()) {

                OrderModel orderModel = orderDao.queryOrderByOrderNo(platformOrderNo);

                if (!(PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus()))) {
                    //1、修改支付状态、支付单号
                    count += orderDao.updateOrderPayInfo(platformOrderNo, payNo, PayStatusEnum.payed.getCode(), payTime);

                    //2、增加代理的账户余额,并记录流水
                    String agentId = orderModel.getAgentId();
                    String agentUserId = accountAmountDao.queryUserIdByAgentId(agentId);
                    count += accountAmountDao.addAccountAmount(agentUserId, orderModel.getAgentAmount());
                    count += accountAmountDao.addAccountAmountBillLog(agentUserId, orderModel.getPlatformOrderNo(), AccountAmountType.paySuccess.getCode(), orderModel.getAgentAmount());

                    //存在二级代理,则需要增加上级代理的账户余额,并记录流水
                    if(StringUtil.isNotEmpty(orderModel.getParentAgentId())){
                        existsParentAgent = true;
                        String parentAgentId = orderModel.getParentAgentId();
                        String parentAgentUserId = accountAmountDao.queryUserIdByAgentId(parentAgentId);
                        count += accountAmountDao.addAccountAmount(parentAgentUserId, orderModel.getParentAgentAmount());
                        count += accountAmountDao.addAccountAmountBillLog(parentAgentUserId, orderModel.getPlatformOrderNo(), AccountAmountType.paySuccess.getCode(), orderModel.getParentAgentAmount());
                    }

                    //3、增加商家的账户余额,并记录流水
                    String merchantId = orderModel.getMerchantId();
                    String merchantUserId = accountAmountDao.queryUserIdByMerchantId(merchantId);
                    count += accountAmountDao.addAccountAmount(merchantUserId, orderModel.getActualAmount());
                    count += accountAmountDao.addAccountAmountBillLog(merchantUserId, orderModel.getPlatformOrderNo(), AccountAmountType.paySuccess.getCode(), orderModel.getActualAmount());

                }

            }

        } finally {
            if (lock != null) {
                lock.unlock();              //释放分布式锁
            }
        }

        if (existsParentAgent && count == 7) {
            return true;
        } else if (!existsParentAgent && count == 5) {
            return true;
        } else {
            throw new Exception("订单:" + platformOrderNo + "支付回调业务处理失败,回滚事务!");
        }

    }

    @Override
    public Map<String, Object> queryPayChannelByCode(String payWay) {
        return orderDao.queryPayChannelByCode(payWay);
    }

    @Override
    public int updateOrderPayQrCodeLink(String id, String qrCodeLink) {
        return orderDao.updateOrderPayQrCodeLink(id, qrCodeLink);
    }

    @Override
    public Map<String, Object> queryOrderById(String tradeId) {
        return orderDao.queryOrderById(tradeId);
    }

}