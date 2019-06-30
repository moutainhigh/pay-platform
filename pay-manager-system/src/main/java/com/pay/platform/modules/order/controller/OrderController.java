package com.pay.platform.modules.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.DateUtil;
import com.pay.platform.common.util.PayChannelEnum;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.SysUserUtil;
import com.pay.platform.common.util.payCharge.util.PayUtil;
import com.pay.platform.modules.agent.model.AgentModel;
import com.pay.platform.modules.agent.service.AgentService;
import com.pay.platform.modules.codeTrader.service.CodeTraderService;
import com.pay.platform.modules.merchant.service.MerchantNotifyService;
import com.pay.platform.modules.merchant.service.MerchantRateService;
import com.pay.platform.modules.merchant.service.MerchantService;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import javafx.scene.AmbientLight;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;

import com.pay.platform.modules.order.model.OrderModel;
import com.pay.platform.modules.order.service.OrderService;


/**
 * User:
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private CodeTraderService codeTraderService;

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AgentService agentService;

    /**
     * 分页查询订单列表
     *
     * @param request
     * @param response
     * @param order
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrderList", produces = "application/json")
    public PageInfo<OrderModel> queryOrderList(HttpServletRequest request, HttpServletResponse response, OrderModel order, String beginTime, String endTime) throws Exception {

        UserModel userModel = AppContext.getCurrentUser();

        //超级管理员：可查看到所有的商家,接收前端传递的商家id
        if (SysUserUtil.isAdminRole(userModel)) {
            setPageInfo(request);
            return orderService.queryOrderList(order, beginTime, endTime, null);
        }
        //码商管理员：默认可查看到绑定的商家,并接收前端传递的商家id
        else if (SysUserUtil.isCodeTraderRole(userModel)) {
            String codeTraderId = userModel.getCodeTraderId();
            List<String> merchantIdList = codeTraderService.queryMerchantIdCodeTraderId(codeTraderId);
            setPageInfo(request);
            return orderService.queryOrderList(order, beginTime, endTime, merchantIdList.toArray(new String[merchantIdList.size()]));
        }
        //代理管理员：可查到下级商家的流水,接收前端传递的商家id
        else if (SysUserUtil.isAgentRole(userModel)) {

            //一级代理,查询自身的商家以及下级代理的商家
            AgentModel agentModel = agentService.queryAgentById(userModel.getAgentId());
            if ("1".equalsIgnoreCase(agentModel.getLevel())) {
                List<String> merchantIdList = merchantService.queryMerchantIdByAgentId(userModel.getAgentId(), userModel.getAgentId());
                if (merchantIdList != null && merchantIdList.size() > 0) {
                    String[] merchantIds = merchantIdList.toArray(new String[merchantIdList.size()]);
                    setPageInfo(request);
                    return orderService.queryOrderList(order, beginTime, endTime, merchantIds);
                }
            }
            //二级代理：只查询自身的商家
            else {
                order.setAgentId(userModel.getAgentId());
                setPageInfo(request);
                return orderService.queryOrderList(order, beginTime, endTime, null);
            }

        }
        //商家管理员：只能查看自身,不接受前端传递参数
        else if (SysUserUtil.isMerchantRole(userModel)) {
            order.setMerchantId(userModel.getMerchantId());
            setPageInfo(request);
            return orderService.queryOrderList(order, beginTime, endTime, null);
        }

        return null;
    }

    /**
     * 手动补单回调
     *
     * @param response
     * @param orderNo
     * @throws Exception
     */
    @RequestMapping(value = "/makeOrderPaySuccess", produces = "application/json")
    @SystemControllerLog(module = "商家管理", operation = "手动补单回调")
    public void makeOrderPaySuccess(HttpServletResponse response, String orderNo, String merchantOrderNo, String payAmount) throws Exception {

        JSONObject json = new JSONObject();

        OrderModel orderModel = orderService.queryOrderByOrderNo(orderNo);
        if (orderModel == null) {
            json.put("status", "0");
            json.put("msg", "订单不存在");
            writeJson(response, json.toString());
            return;
        }

        //避免恶意攻击，绕过权限过滤器；此处加多一层判断；
        //只有管理员和码商才有权限进行补单回调；
        UserModel userModel = AppContext.getCurrentUser();
        if (SysUserUtil.isAdminRole(userModel) || SysUserUtil.isCodeTraderRole(userModel)) {

            //待支付状态:
            if (PayStatusEnum.waitPay.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {

                //话冲通道：查询第三方接口
                if (PayChannelEnum.hcZfb.getCode().equalsIgnoreCase(orderModel.getPayWay()) || PayChannelEnum.hcWechat.getCode().equalsIgnoreCase(orderModel.getPayWay())) {

                    try {
                        String platformOrderNo = orderModel.getPlatformOrderNo();
                        String result = PayUtil.findOrder(platformOrderNo);
                        if (StringUtil.isNotEmpty(result)) {
                            JSONObject resultJson = new JSONObject(result);
                            if (resultJson.has("resultCode") && 200 == resultJson.getInt("resultCode")) {
                                JSONObject data = resultJson.getJSONObject("data");
                                if ("SUCCESS".equalsIgnoreCase(data.getString("status"))) {                      //支付成功
                                    String payTime = data.getString("finishedDate");
                                    //相关业务处理：更新订单状态等
                                    orderService.paySuccessBusinessHandle(platformOrderNo, null, payTime);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //柳行通道：
                else if (PayChannelEnum.lzyhZfb.getCode().equalsIgnoreCase(orderModel.getPayWay()) || PayChannelEnum.lzyhWechat.getCode().equalsIgnoreCase(orderModel.getPayWay())) {

                    //避免人工回调错误,需要前端手动输入商家单号、支付金额; 后台查询后进行确认
                    int isInputRight = orderService.queryOrderExistsByBuDanInfo(orderNo, merchantOrderNo, payAmount);
                    if (isInputRight > 0) {
                        orderService.paySuccessBusinessHandle(orderModel.getPlatformOrderNo(), null, DateUtil.getCurrentDateTime());
                    } else {
                        json.put("status", "0");
                        json.put("msg", "商家单号或实际支付金额错误,请检查输入！");
                        writeJson(response, json.toString());
                        return;
                    }

                }

            }

        }

        //已支付状态：直接回调给商家
        orderModel = orderService.queryOrderByOrderNo(orderNo);         //获取数据库最新的订单信息
        if (PayStatusEnum.payed.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {
            boolean flag = merchantNotifyService.pushPaySuccessInfo(orderNo);

            if (flag) {
                json.put("success", true);
                json.put("msg", "回调成功");
            } else {
                json.put("success", false);
                json.put("msg", "订单已支付,但未收到商家响应回调成功！");
            }

        }

        writeJson(response, json.toString());

    }

}