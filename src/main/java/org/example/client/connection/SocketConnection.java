package org.example.client.connection;

import org.example.message.Message;

import java.net.InetAddress;
import java.net.Socket;

/**
 * 实现使用套接字连接并维护连接的类
 * 使用单例模式
 * 可以创建套接字、读写数据、关闭套接字
 */
public class SocketConnection {
    private static SocketConnection instance;

    private Socket socket;

    /**
     * 连接类的私有构造函数
     */
    private SocketConnection()
    {
        socket = null;
    }

    /**
     * 获取构造类实例的方法
     * @return 构造类实例
     */
    public static SocketConnection getInstance()
    {
        if(instance == null)
            instance = new SocketConnection();
        return instance;
    }


    /**
     * 用于与服务器取得连接
     * 将连接的套接字存入成员变量Socket中
     * @param inetAddress 服务器地址
     * @param port 服务器端口号
     * @return 是否成功连接
     */
    public boolean connect(InetAddress inetAddress,int port)
    {
        // TODO
        return false;
    }

    /**
     * 向服务器发送报文
     * @param message 发送的报文
     * @return 接收到的报文，如果没有收到就返回null
     */
    public Message sendMessage(Message message)
    {
        // TODO
        return null;
    }

    /**
     * 用于控制与服务器的连接关闭的函数，在退出程序时调用
     * @return 是否关闭成功
     */
    public boolean close()
    {
        // TODO
        return false;
    }
}
