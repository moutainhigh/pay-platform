package com.pay.platform.common.socket.timer;

import com.pay.platform.common.socket.data.ClientSocket;
import com.pay.platform.common.socket.data.ClientSocketList;
import com.pay.platform.common.socket.server.SocketWrite;
import com.pay.platform.common.websocket.config.SocketMessageType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务端socket心跳服务；定时给客户端发送心跳消息; 确认连接状态
 */
@Component
public class ServerHeartbeatTimer {

    private static final Logger logger = LoggerFactory.getLogger(ServerHeartbeatTimer.class);

    /**
     * 定时发送心跳消息；检测客户端连接状态；
     *
     * 30分钟发送一次;
     *
     */
//    @S cheduled(cron = " */10 * * * * ?")        //暂时10秒执行一次
    @Scheduled(cron = " 0 0/30 * * * ?")
    public void sendHeartbeatMessage() {

        List<ClientSocket> list = ClientSocketList.getAllClientSocket();

        if (list != null && list.size() > 0) {

            logger.info("服务端socket心跳检测");

            JSONObject json = new JSONObject();
            json.put("messageType", SocketMessageType.MESSAGE_HEARTBEAT);
            json.put("heartbeat", "1");
            json.put("server","1");

            for (ClientSocket clientSocket : list) {
                SocketWrite.write(clientSocket.getId(), clientSocket.getSocket(), clientSocket.getWriter(), json.toString());
            }

        }

    }

}