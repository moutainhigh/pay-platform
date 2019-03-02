package com.pay.platform.modules.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.SysUserUtil;
import com.pay.platform.modules.merchant.service.MerchantNotifyService;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
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
 * User: zjt
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantNotifyService merchantNotifyService;

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
    public PageInfo<OrderModel> queryOrderList(HttpServletRequest request, HttpServletResponse response, OrderModel order) throws Exception {
        setPageInfo(request);

        UserModel userModel = AppContext.getCurrentUser();

        //超级管理员：可查看到所有的商家,接收前端传递的商家id
        if (SysUserUtil.isAdminRole(userModel)) {
            return orderService.queryOrderList(order);
        }
        //代理管理员：可查到下级商家的流水,接收前端传递的商家id
        else if (SysUserUtil.isAgentRole(userModel)) {
            order.setAgentId(userModel.getAgentId());
            return orderService.queryOrderList(order);
        }
        //商家管理员：只能查看自身,不接受前端传递参数
        else if (SysUserUtil.isMerchantRole(userModel)) {
            order.setMerchantId(userModel.getMerchantId());
            return orderService.queryOrderList(order);
        }

        return null;
    }

    /**
     * 手动补单-回调商家
     *
     * @param response
     * @param orderNo
     * @throws Exception
     */
    @RequestMapping(value = "/pushPaySuccessInfo", produces = "application/json")
    @SystemControllerLog(module = "商家管理", operation = "手动补单-回调商家")
    public void pushPaySuccessInfo(HttpServletResponse response, String orderNo) throws Exception {

        JSONObject json = new JSONObject();

        boolean flag = merchantNotifyService.pushPaySuccessInfo(orderNo);

        if (flag) {
            json.put("success", true);
            json.put("msg", "回调成功");
        } else {
            json.put("success", false);
            json.put("msg", "回调失败,未收到商家响应");
        }

        writeJson(response, json.toString());

    }

}