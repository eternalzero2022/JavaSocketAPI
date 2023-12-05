package org.example.client.event.object;

import java.net.InetAddress;

public class ConnectionEvent extends SocketEvent{
    /**
     * 建立连接的服务器地址
     */
    private InetAddress address;
    /**
     * 建立连接的服务器端口号
     */
    private int port;

    /**
     * 套接字事件的构造函数
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ConnectionEvent(Object source, InetAddress address, int port) {
        super(source);
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
