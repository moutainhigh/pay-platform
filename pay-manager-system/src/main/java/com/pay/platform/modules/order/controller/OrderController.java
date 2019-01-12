package com.pay.platform.modules.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.github.pagehelper.PageInfo;
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
        return orderService.queryOrderList(order);
    }

}