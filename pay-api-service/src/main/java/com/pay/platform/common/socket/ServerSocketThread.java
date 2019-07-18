package com.pay.platform.common.socket;

import com.pay.platform.common.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

/**
 * 服务端socket
 */
public class ServerSocketThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ServerSocketThread.class);

    //服务端socket
    private ServerSocket serverSocket = null;

    /**
     * step1：ServletContext容器启动时,将启动服务端socket
     */
    public ServerSocketThread() {
        try {
            serverSocket = new ServerSocket(30098);
            logger.info("服务端socket启动");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("服务端socket启动出错：" + e.getMessage());
        }
    }

    @Override
    public void run() {
        super.run();

        //当前线程没有中断,就一直监听
        while (!this.isInterrupted()) {
            try {

                //step2、服务端socket启动后,将开启线程,等待客户端连接
                Socket socket = this.serverSocket.accept();

                if (null != socket && !socket.isClosed()) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String id = UUID.randomUUID().toString();

                    logger.info("连接成功：" + socket.hashCode());

                    //step3：处理客户端的交互消息
                    new Thread(new ClientSocketMessageTask(id, socket, writer)).start();
                }

            } catch (Exception var4) {
                logger.error("服务端主线程出现故障：" + var4.getMessage(), var4);
            }
        }

    }

    /**
     * 给某个用户发送消息
     *
     * @param codeNum
     * @param message
     */
    public static void sendMessageToUser(String codeNum, String message) {
        List<ClientSocket> list = ClientSocketList.get(codeNum);

        if (list != null && list.size() > 0) {

            //可能会同时登录多个; 因此遍历循环;
            for(ClientSocket clientSocket : list){
                AppContext.getExecutorService().submit(new Runnable() {
                    @Override
                    public void run() {
                        SocketWrite.write(clientSocket.getId(), clientSocket.getSocket(), clientSocket.getWriter(), message);
                    }
                });
            }

        }

    }

    /**
     * 关闭服务端socket
     */
    public void closeSocketServer() {
        try {
            if (null != this.serverSocket && !this.serverSocket.isClosed()) {
                this.serverSocket.close();
                logger.info("服务端socket关闭");
            }
        } catch (IOException var2) {
            logger.error("服务端关闭Socket出现故障：" + var2.getMessage(), var2);
        }
    }

}