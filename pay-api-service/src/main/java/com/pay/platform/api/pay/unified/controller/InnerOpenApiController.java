package com.pay.platform.api.pay.unified.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.socket.data.ClientSocketList;
import com.pay.platform.common.util.DateUtil;
import com.pay.platform.common.util.QrcodeUtil;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.encrypt.Base64Util;
import com.pay.platform.common.websocket.service.AppWebSocketService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 一些针对内部页面开放的接口
 *
 * 通常是前端h5页面,发起调用; 例如查询订单状态、将url转换成二维码图片
 *
 */
@Controller
public class InnerOpenApiController extends BaseController {

    @Autowired
    private UnifiedPayService unifiedPayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AppWebSocketService appWebSocketService;

    /**
     * 查询订单状态: 根据订单id（内部前端页面使用）
     * <p>
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/queryOrderStatus", method = RequestMethod.POST)
    public void queryOrderStatus(HttpServletRequest request, HttpServletResponse response, String orderId) throws Exception {

        JSONObject json = new JSONObject();

        try {

            Map<String, Object> payPageData = unifiedPayService.queryPayPageData(orderId);
            if (payPageData == null) {
                json.put("status", "0");
                json.put("msg", "订单不存在！");
                writeJson(response, json.toString());
                return;
            }

            String payStatus = payPageData.get("payStatus").toString();
            if (PayStatusEnum.payed.getCode().equalsIgnoreCase(payStatus)) {

                Map<String, Object> data = new HashMap();
                data.put("merchantOrderNo", payPageData.get("merchantOrderNo").toString());           //商户订单号
                data.put("platformOrderNo", payPageData.get("platformOrderNo").toString());              //平台订单号
                data.put("payStatus", payStatus);                    //支付状态(waitPay:待支付 payed:已支付 payFail:支付失败)
                data.put("payWay", payPageData.get("payWay").toString());                        //支付方式（参照通道类型）
                data.put("payTime", payPageData.get("payTime").toString());                              //支付时间

                json.put("status", "1");
                json.put("msg", "查询成功！");
                json.put("data", data);
                writeJson(response, json.toString());
                return;
            }

            String createTime = payPageData.get("createTime").toString();
            long orderTimeStamp = DateUtil.getTimeStamp(createTime);
            long nowTimeStamp = DateUtil.getTimeStamp(new Date());
            if (Math.abs(nowTimeStamp - orderTimeStamp) > (5 * 60)) {      //有效期5分钟,单位秒
                json.put("status", "0");
                json.put("msg", "订单已超时！");
                writeJson(response, json.toString());
            }

            json.put("status", "2");
            json.put("msg", "支付等待中！");
            writeJson(response, json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "0");
            json.put("msg", "服务器内部错误：" + e.getMessage());
            writeJson(response, json.toString());
        } finally {
            getCurrentLogger().info("响应报文：{}", json.toString());
        }


    }

    /**
     * 将url转换成二维码图片（内部前端页面使用）
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping({"/openApi/getPayQrcode"})
    public void getPayQrcode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        String url = request.getParameter("payUrl");

        //如果前端传递的是base64; 则进行解码
        if (StringUtil.isNotEmpty(request.getParameter("isBase64"))) {
            url = Base64Util.parseBase64(url);
        }

        QrcodeUtil.drawQrcodeImg(url, response);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
    }


    /**
     * 测试接口是否可用（内部前端页面使用）
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping({"/openApi/ping"})
    public void ping(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();

        json.put("status", "1");
        json.put("msg", "请求成功");
        writeJson(response, json.toString());

    }

    /**
     * 获取在线的设备：成功建立socket连接的（内部前端页面使用）
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping({"/openApi/getOnLineDevice"})
    public void getOnLineDevice(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        json.put("status", "1");
        json.put("msg", "请求成功");
        json.put("data", ClientSocketList.getOnLineSocketDevice());
        writeJson(response, json.toString());
    }

    /**
     * 根据id获取二维码：（内部前端页面使用）
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping({"/openApi/queryQrCodeByOrderId"})
    public void queryQrCodeByOrderId(HttpServletResponse response, HttpServletRequest request, String orderId) throws Exception {
        JSONObject json = new JSONObject();

        Map<String, Object> orderInfo = orderService.queryOrderById(orderId);
        if (orderInfo.get("pay_qr_code_link") != null && StringUtil.isNotEmpty(orderInfo.get("pay_qr_code_link").toString())) {
            json.put("status", "1");
            json.put("msg", "请求成功");
            json.put("data", orderInfo.get("pay_qr_code_link").toString());
        } else {
            json.put("status", "0");
            json.put("msg", "暂无可用收款码！");
            json.put("data", appWebSocketService.getOnLineSocketDevice());
        }

        writeJson(response, json.toString());
    }


}