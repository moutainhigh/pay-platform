package com.pay.platform.common.socket.handler;

import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * app socket处理器
 */
public class AppWebSocketHandler extends TextWebSocketHandler {

    /**
     * 存储建立连接的用户
     */
    public static final ArrayList<WebSocketSession> users;
    private static Logger logger = LoggerFactory.getLogger(AppWebSocketHandler.class);

    //登录用户标识（根据设备编号）
    public static final String LOGIN_ID = "codeNum";

    /**
     * 消息类型配置
     */
    private static final String MESSAGE_LOGIN = "MESSAG_LOGIN";                           //登录
    private static final String MESSAGE_UPLOAD_QR_CODE = "MESSAG_UPLOAD_QR_CODE";         //上传收款码
    private static final String MESSAGE_GET_QR_CODE = "MESSAG_GET_QR_CODE";               //获取收款码

    static {
        users = new ArrayList<WebSocketSession>();
    }

    @Autowired
    private UnifiedPayService unifiedPayService;

    @Autowired
    private OrderService orderService;

    public AppWebSocketHandler() {

    }

    /**
     * 连接成功时候触发
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("websocket连接成功......当前数量:" + users.size());
        users.add(session);
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
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(LOGIN_ID).equals(codeNum)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
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
            logger.debug("接收到客户端消息：" + reqJsonStr);

            //校验签名
            JSONObject reqJson = new JSONObject(reqJsonStr);
            String codeNum = reqJson.getString("codeNum");
            Map<String, Object> tradeCodeInfo = unifiedPayService.queryTradeCodeByCudeNum(codeNum);
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
            if (MESSAGE_LOGIN.equalsIgnoreCase(messageType)) {
                session.getAttributes().put(LOGIN_ID, codeNum);    //将设备编码作为登录标识;存储到socket session; 便于后续发送指定用户消息
            }
            //上传收款码操作
            else if (MESSAGE_UPLOAD_QR_CODE.equalsIgnoreCase(messageType)) {
                String amount = reqJson.getString("amount");
                String remarks = reqJson.getString("remarks");
                String codeUrl = reqJson.getString("codeUrl");

                //查询匹配的订单,并更新收款链接
                Map<String, Object> orderInfo = unifiedPayService.queryOrderByQrCodeInfo(codeNum, amount, codeUrl);
                if (orderInfo != null) {
                    String id = orderInfo.get("id").toString();
                    orderService.updateOrderPayQrCodeLink(id, codeUrl);
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

}