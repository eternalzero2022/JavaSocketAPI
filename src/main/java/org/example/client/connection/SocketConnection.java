package org.example.client.connection;

import org.example.io.MessageReader;
import org.example.message.Message;


import java.io.DataOutputStream;
import java.io.IOException;
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
     * @throws IOException 
     */
    public boolean connect(InetAddress inetAddress,int port)
    {
        try {
            Socket connectsocket = new Socket(inetAddress, port);
            getInstance().socket = connectsocket;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * 向服务器发送报文
     * @param message 发送的报文
     * @return 接收到的报文，如果没有收到就返回null
     */
    public Message sendMessage(Message message)
    {
        try {
            //获取客户端的输出流
            DataOutputStream clientoutputstream = new DataOutputStream(getInstance().socket.getOutputStream());
            //将报文写入客户端输出流
            String messagestr = message.toString();
            clientoutputstream.writeBytes(messagestr);
            //获取客户端输入流查看接收到的报文
            MessageReader reader = new MessageReader();
            return reader.readMessage(getInstance().socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于控制与服务器的连接关闭的函数，在退出程序时调用
     * @return 是否关闭成功
     */
    public boolean close()
    {
        try {
            getInstance().socket.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
