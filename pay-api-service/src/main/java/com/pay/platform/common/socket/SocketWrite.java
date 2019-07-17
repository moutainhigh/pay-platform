package com.pay.platform.common.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.net.Socket;

public class SocketWrite {

    private static final Logger log = LoggerFactory.getLogger(SocketWrite.class);

    public SocketWrite() {
    }

    public static boolean write(String id, Socket socket, BufferedWriter writer, String str) {
        try {

            if (socket.isConnected()) {
                log.info(id + "，" + socket + "发送给客户端数据：{}", str);
                writer.write(str + " ");
                writer.newLine();
                writer.flush();
                return true;
            } else {
                ClientSocketList.remove(socket);            //断开连接的移除

                //连接关闭时;
                log.info("socket断开连接,从list中移除！当前数量: " + ClientSocketList.getConnectCount());

            }

            log.info(id + "，" + socket + "连接已中断，无法发送：{}", str);
        } catch (Exception var5) {
            log.error(id + "，" + socket.toString() + "发送消息出现故障：" + var5.getMessage(), var5);
        }

        return false;
    }

}