package com.pay.platform.api.base.controller;

import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.socket.server.ServerSocketThread;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.common.websocket.service.AppWebSocketService;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

public class TestController  extends BaseController {

    @Autowired
    private UnifiedPayService unifiedPayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AppWebSocketService appWebSocketService;


    /**
     * 测试发送app消息
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping({"/openApi/testSendAppMessage"})
    public void testSendAppMessage(HttpServletResponse response, HttpServletRequest request, String codeNum) throws Exception {

        String nonce = UUID.randomUUID().toString();

        Map<String, Object> tradeCode = unifiedPayService.queryTradeCodeByCudeNum(codeNum);
        if(tradeCode == null){
            return;
        }

        String secret = tradeCode.get("secret").toString();

        JSONObject reqJson = new JSONObject();
        reqJson.put("nonce", nonce);
        reqJson.put("messageType", "test");
        reqJson.put("content", "测试消息");
        String sign = AppSignUtil.buildAppSign(JsonUtil.parseToMapString(reqJson.toString()), secret);
        reqJson.put("sign", sign);

        //线路1：发送socket消息
        AppContext.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                ServerSocketThread.sendMessageToUser(codeNum, reqJson.toString());
            }
        });

//        //线路2：发送websocket消息
//        AppContext.getExecutorService().submit(new Runnable() {
//            @Override
//            public void run() {
//                appWebSocketService.sendMessageToUser(codeNum, reqJson.toString());
//            }
//        });

    }


    /**
     * 模拟测试商家回调
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping({"/openApi/testMerchantNotify"})
    public void testMerchantNotify(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.getWriter().write("SUCCESS");
        response.getWriter().flush();
    }

}