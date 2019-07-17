package com.pay.platform.common.socket;

import java.io.BufferedWriter;
import java.net.Socket;

/**
 * 保存客户端socket
 */
public class ClientSocket {

    private String id = null;
    private Socket socket = null;
    private BufferedWriter writer = null;

    public ClientSocket(String id, Socket socket, BufferedWriter writer) {
        this.id = id;
        this.socket = socket;
        this.writer = writer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

}