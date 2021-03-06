package com.pay.platform.common.websocket.handler;

import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.common.websocket.config.SocketMessageType;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * app socket处理器
 */
@Component
public class AppWebSocketHandler extends TextWebSocketHandler {

    /**
     * 存储建立连接的用户
     */
    public static final ArrayList<WebSocketSession> users;
    private static Logger logger = LoggerFactory.getLogger(AppWebSocketHandler.class);

    //登录用户标识（根据设备编号）
    public static final String LOGIN_ID = "codeNum";

    static {
        users = new ArrayList<WebSocketSession>();
    }

    private UnifiedPayService unifiedPayService;

    private OrderService orderService;

    public AppWebSocketHandler() {

    }

    /**
     * 连接成功时候触发
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        logger.info("websocket连接成功......当前数量:" + users.size());
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        //获取登录用户标识
        String codeNum = null;
        if (session.getAttributes().containsKey(LOGIN_ID)) {
            codeNum = (String) session.getAttributes().get(LOGIN_ID);
        }

        users.remove(session);              //移除当前会话

        logger.info("websocket连接关闭......用户" + codeNum + "已退出！");
        logger.info("websocket连接关闭......剩余数量:" + users.size());
    }

    /**
     * 给某个用户发送消息
     *
     * @param codeNum
     * @param message
     */
    public void sendMessageToUser(String codeNum, TextMessage message) {

        //可能出现多个设备连接同一个号; 此处不进行break操作; 遍历所有匹配的发送消息
        for (WebSocketSession user : users) {
            if (user != null) {
                if (codeNum.equals(user.getAttributes().get(LOGIN_ID))) {
                    try {
                        if (user.isOpen()) {
                            logger.info("发送socket消息:" + codeNum + "  内容:" + message.getPayload());
                            user.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 接收消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            super.handleTextMessage(session, message);

            String reqJsonStr = message.getPayload().toString();
            logger.info("websocket接收到客户端消息：" + reqJsonStr);

            //校验签名
            JSONObject reqJson = new JSONObject(reqJsonStr);
            String codeNum = reqJson.getString("codeNum");
            Map<String, Object> tradeCodeInfo = getUnifiedPayService().queryTradeCodeByCudeNum(codeNum);
            if (tradeCodeInfo == null) {
                logger.info("无效的设备编码：" + codeNum);
                return;
            }

            String secret = tradeCodeInfo.get("secret").toString();
            String sign = reqJson.getString("sign").trim();
            String currentSign = AppSignUtil.buildAppSign(JsonUtil.parseToMapString(reqJsonStr), secret);

            if (!sign.equalsIgnoreCase(currentSign)) {
                logger.info("签名错误：" + codeNum);
                return;
            }

            String messageType = reqJson.getString("messageType");
            //登录操作
            if (SocketMessageType.MESSAGE_LOGIN.equalsIgnoreCase(messageType)) {

                //将设备编码作为登录标识;存储到socket session; 便于后续发送指定用户消息
                session.getAttributes().put(LOGIN_ID, codeNum);

            }
            //上传收款码操作
            else if (SocketMessageType.MESSAGE_UPLOAD_QR_CODE.equalsIgnoreCase(messageType)) {
                String amount = reqJson.getString("amount");
                String remarks = reqJson.getString("remarks");
                String codeUrl = reqJson.getString("codeUrl");

                //查询匹配的订单,并更新收款链接
                Map<String, Object> orderInfo = getUnifiedPayService().queryOrderByQrCodeInfo(codeNum, amount, codeUrl);
                if (orderInfo != null) {
                    String id = orderInfo.get("id").toString();
                    getOrderService().updateOrderPayQrCodeLink(id, codeUrl);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 异常处理
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 删除重复session; 避免多个设备重复登录一个账号;
     *
     * @param session
     * @param codeNum
     */
    public void removeRepeatSession(WebSocketSession session, String codeNum) {

        //避免重复登录,先删除之前存在的session
        ArrayList<WebSocketSession> userRemove = new ArrayList<>();

        for (WebSocketSession user : users) {

            //过滤当前的session;
            if (user == null || user == session || session.getId().equalsIgnoreCase(user.getId())) {
                continue;
            }

            if (codeNum.equals(user.getAttributes().get(LOGIN_ID))) {
                userRemove.add(user);
            }

        }
        users.removeAll(userRemove);

    }

    public UnifiedPayService getUnifiedPayService() {
        if (unifiedPayService == null) {
            unifiedPayService = AppContext.getApplicationContext().getBean(UnifiedPayService.class);
        }
        return unifiedPayService;
    }

    public OrderService getOrderService() {
        if (orderService == null) {
            orderService = AppContext.getApplicationContext().getBean(OrderService.class);
        }
        return orderService;
    }

}