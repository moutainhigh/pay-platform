package com.pay.platform.common.socket.server;

import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.socket.data.ClientSocket;
import com.pay.platform.common.socket.data.ClientSocketList;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.common.websocket.config.SocketMessageType;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

/**
 * 客户端socket消息处理任务
 */
public class ClientSocketMessageTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ClientSocketMessageTask.class);

    private UnifiedPayService unifiedPayService;

    private OrderService orderService;

    private String id = null;
    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public ClientSocketMessageTask(String id, Socket socket, BufferedWriter writer) {
        this.socket = socket;
        this.writer = writer;
        this.id = id;
    }

    @Override
    public void run() {

        try {

            //获取客户端输入流
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (true) {
                if (this.socket.isConnected()) {

                    //监听客户端发送的每一行消息
                    String lineString = this.reader.readLine();
                    if (lineString != null) {

                        try {

                            logger.info(this.id + "，" + this.socket + "socket接收到客户端消息：" + lineString);

                            //代表不是json格式; 不进行处理;
                            if (!lineString.contains("{") || !lineString.contains("messageType")) {
                                continue;
                            }

                            JSONObject reqJson = new JSONObject(lineString);
                            String messageType = reqJson.getString("messageType");
                            //心跳检测,返回响应,以便提高连接保持率s
                            if (SocketMessageType.MESSAGE_HEARTBEAT.equalsIgnoreCase(messageType)) {
                                SocketWrite.write(this.id, this.socket, this.writer, reqJson.toString());
                                continue;
                            }

                            String codeNum = reqJson.getString("codeNum");
                            Map<String, Object> tradeCodeInfo = getUnifiedPayService().queryTradeCodeByCudeNum(codeNum);
                            if (tradeCodeInfo == null) {
                                logger.info("无效的设备编码：" + codeNum);
                                continue;
                            }

                            String secret = tradeCodeInfo.get("secret").toString();
                            String sign = reqJson.getString("sign").trim();
                            String currentSign = AppSignUtil.buildAppSign(JsonUtil.parseToMapString(lineString), secret);
                            if (!sign.equalsIgnoreCase(currentSign)) {
                                logger.info("签名错误：" + codeNum);
                                continue;
                            }

                            //登录操作：指定唯一标识,便于后续发送消息
                            if (SocketMessageType.MESSAGE_LOGIN.equalsIgnoreCase(messageType)) {
                                ClientSocketList.add(codeNum , new ClientSocket(this.id, this.socket, this.writer));
                                SocketWrite.write(this.id, this.socket, this.writer, reqJson.toString());       //返回响应,以便提高连接保持率
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
                                SocketWrite.write(this.id, this.socket, this.writer, reqJson.toString());       //返回响应,以便提高连接保持率
                            }

                        } catch (Exception e) {
                            logger.error(this.id + "，" + this.socket.toString() + "接收消息出现故障：" + e.getMessage(), e);
                        }

                    }

                } else {
                    break;              //客户端停止连接,跳出循环,之后关闭连接,线程终止;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.id + "，" + this.socket.toString() + "客户端socket连接出现故障：" + e.getMessage(), e);
        } finally {

            ClientSocketList.remove(socket);

            //连接关闭时;
            logger.info("socket断开连接,从list中移除！当前数量: " + ClientSocketList.getConnectCount());

            try {
                if (this.reader != null) {
                    this.reader.close();
                }
            } catch (Exception var32) {
                logger.error(this.id + "，" + this.socket.toString() + "关闭读取流出现故障：" + var32.getMessage(), var32);
            }

            try {
                if (this.writer != null) {
                    this.writer.close();
                }
            } catch (Exception var31) {
                logger.error(this.id + "，" + this.socket.toString() + "关闭写入流出现故障：" + var31.getMessage(), var31);
            }

            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (Exception var30) {
                logger.error(this.id + "，" + this.socket.toString() + "关闭Socket出现故障：" + var30.getMessage(), var30);
            }

        }
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
