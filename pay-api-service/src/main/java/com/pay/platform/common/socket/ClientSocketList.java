package com.pay.platform.common.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存储客户端连接的socket
 */
public class ClientSocketList {

    private static final Logger logger = LoggerFactory.getLogger(ClientSocketList.class);

    //由于可能出现多台手机登录同一个号; 因此此处1:多存储
    private static final Map<String, List<ClientSocket>> clientSocketMap = new HashMap<String, List<ClientSocket>>();

    /**
     * 保存客户端socket
     *
     * @param codeNum
     * @param clientSocket
     */
    public static synchronized void add(String codeNum, ClientSocket clientSocket) {

        List<ClientSocket> list = clientSocketMap.get(codeNum);

        //第一次为null,则初始化集合
        if (list == null) {
            list = new ArrayList<>();
            list.add(clientSocket);
            clientSocketMap.put(codeNum, list);
        } else {

            boolean isAdd = true;

            //存在重复的客户端socket,不进行添加;
            for (int i = 0; i < list.size(); i++) {
                ClientSocket existsClient = list.get(i);
                if (existsClient == clientSocket) {
                    isAdd = false;
                }
            }

            if (isAdd) {
                list.add(clientSocket);
                clientSocketMap.put(codeNum, list);
            }

        }

        logger.info("socket当前连接数:" + ClientSocketList.getConnectCount());

    }

    /**
     * 获取客户端连接数量
     */
    public static synchronized int getConnectCount() {

        int count = 0;

        for (Map.Entry<String, List<ClientSocket>> entry : clientSocketMap.entrySet()) {
            if (entry.getValue() != null) {
                count += entry.getValue().size();
            }
        }

        return count;
    }

    /**
     * 获取客户端连接的socket
     *
     * @param codeNum
     */
    public static synchronized List<ClientSocket> get(String codeNum) {
        return clientSocketMap.get(codeNum);
    }

    /**
     * 删除客户端socket
     *
     * @param socket
     * @param socket
     */
    public static synchronized void remove(Socket socket) {

        for (Map.Entry<String, List<ClientSocket>> entry : clientSocketMap.entrySet()) {
            if (entry.getValue() != null) {

                List<ClientSocket> list = entry.getValue();

                ClientSocket delClientSocket = null;

                //遍历,找到对应的socket; 根据hashCode判断是否为同一个socket; 并完成删除
                for (int i = 0; i < list.size(); i++) {
                    delClientSocket = list.get(i);
                    if (delClientSocket != null && delClientSocket.getSocket() == socket) {
                        break;
                    }
                }

                //根据equals; hashcode删除
                if (delClientSocket != null) {
                    list.remove(delClientSocket);
                }

            }
        }

    }

}