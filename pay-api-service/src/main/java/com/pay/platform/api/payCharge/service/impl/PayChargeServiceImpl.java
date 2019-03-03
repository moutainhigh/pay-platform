package com.pay.platform.api.payCharge.service.impl;

import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.payCharge.dao.PayChargeDao;
import com.pay.platform.api.payCharge.service.PayChargeService;
import com.pay.platform.api.payCharge.util.PayUtil;
import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.IpUtil;
import com.pay.platform.common.util.OrderNoUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/3/2 21:42
 */
@Service
public class PayChargeServiceImpl implements PayChargeService {

    @Autowired
    private PayChargeDao payChargeDao;

    @Override
    public String createOrderByCharge(String merchantNo, String merchantOrderNo, String orderAmount
            , String payWay, String notifyUrl, String clientIp, String baseUrl) throws Exception {

        //1,计算商家手续费
        Map<String, Object> payChannelInfo = payChargeDao.queryPayChannelByCode(PayChannelEnum.JU_FU_BAO_CHARGE.getCode());
        String payChannelId = payChannelInfo.get("id").toString();
        Map<String, Object> merchantInfo = payChargeDao.queryMerchantRateByMerchantNo(merchantNo, payChannelId);
        String merchantId = merchantInfo.get("merchant_id").toString();
        double merchantRate = Double.parseDouble(merchantInfo.get("merchant_rate").toString());                 //商家费率
        double handlingFee = DecimalCalculateUtil.mul(Double.parseDouble(orderAmount), merchantRate);          //商家手续费
        double actualAmount = DecimalCalculateUtil.sub(Double.parseDouble(orderAmount), handlingFee);          //商家实收款

        //2,计算代理费率及其收入
        Map<String, Object> agentInfo = payChargeDao.queryAgentRateByMerchantNo(merchantNo, payChannelId);
        String agentId = agentInfo.get("agent_id").toString();
        double agentRate = Double.parseDouble(agentInfo.get("agent_rate").toString());                  //代理费率
        double agentProfitRate = DecimalCalculateUtil.sub(merchantRate, agentRate);                    //代理利润费率（例如商家2.0，代理为1.6,则利润空间为0.4）
        double agentAmount = DecimalCalculateUtil.mul(Double.parseDouble(orderAmount), agentProfitRate);                   //代理收入

        //3,计算平台收入
        double costRate = Double.parseDouble(payChannelInfo.get("cost_rate").toString());       //通道成本费率
        double platformProfitRate = DecimalCalculateUtil.sub(costRate, agentRate);              //平台利润费率（例如成本为1.3，放给代理为1.6，则利润空间为0.3）
        double platformAmount = DecimalCalculateUtil.mul(Double.parseDouble(orderAmount), platformProfitRate);     //平台收入

        //4,计算通道收入
        double channelAmount = DecimalCalculateUtil.mul(Double.parseDouble(orderAmount), costRate);

        //5,创建订单数据
        String platformOrderNo = OrderNoUtil.getOrderNoByUUId();
        OrderModel orderModel = new OrderModel();
        orderModel.setMerchantId(merchantId);
        orderModel.setMerchantNo(merchantNo);
        orderModel.setMerchantOrderNo(merchantOrderNo);
        orderModel.setPlatformOrderNo(platformOrderNo);
        orderModel.setOrderAmount(Double.parseDouble(orderAmount));
        orderModel.setHandlingFee(handlingFee);
        orderModel.setActualAmount(actualAmount);
        orderModel.setCostRate(costRate);
        orderModel.setChannelAmount(channelAmount);
        orderModel.setAgentRate(agentRate);
        orderModel.setPlatformAmount(platformAmount);
        orderModel.setMerchantRate(merchantRate);
        orderModel.setAgentAmount(agentAmount);
        orderModel.setAgentId(agentId);
        orderModel.setChannelId(payChannelId);
        orderModel.setPayWay(payWay);
        int count = payChargeDao.createOrder(orderModel);

        //6,调用第四方话冲接口
        if (count > 0) {
            String platformNotifyUrl = baseUrl + "/openApi/payNotifyOfCharge";
            return PayUtil.charge(platformOrderNo, orderAmount, payWay, platformNotifyUrl, clientIp);
        }

        return null;
    }

}