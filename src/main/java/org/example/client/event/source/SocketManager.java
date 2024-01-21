package org.example.client.event.source;

import org.example.client.event.listener.SocketListener;
import org.example.client.event.object.CloseEvent;
import org.example.client.event.object.ConnectionEvent;
import org.example.client.event.object.SendMessageEvent;
import org.example.message.Message;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * socket相关事件的数据源抽象类，需要被其他具体的事件处理的抽象类继承
 * 包含了用于处理相关需要使用socket服务的方法，用于生成事件，并调用Listener中的事件处理函数处理事件。
 */
public class SocketManager {


    /**
     * 数据源对象所拥有的报文对象，用于保存需要被接收/发送的报文
     */
    private Message message;
    /**
     * 表示当前与服务器的交互是否成功（能不能取得与服务器的联系）
     */
    private boolean state;
    /**
     * 用于存储所有事件监听对象的集合
     */
    private Set<SocketListener> listeners;

    /**
     * 套接字事件源类的无参构造函数
     */
    public SocketManager() { listeners = new HashSet<SocketListener>();}

    /**
     * 套接字事件源类的有参构造函数，在创建时向监听器表中添加一个监听器
     */
    public SocketManager(SocketListener listener)
    {
        listeners = new HashSet<SocketListener>();
        listeners.add(listener);
    }

    /**
     * 获取本类中存储的报文对象
     * @return 报文对象
     */
    public Message getMessage() { return message;}

    public void setMessage(Message message) { this.message = message;}

    public void addListener(SocketListener listener) { listeners.add(listener);}

    /**
     * 数据源中用于建立连接的方法
     * 会发送事件给Listener，事件中包含需要的服务器信息
     * 可以在state成员变量中获取处理结果
     * @param address 服务器地址
     * @param port 服务器端口号
     */
    public void startConnection(InetAddress address,int port)
    {
        for(SocketListener listener:listeners)
            listener.doEvent(new ConnectionEvent(this,address,port));
    }

    /**
     * 数据源中用于发送报文的方法
     * 会发送事件给Listener，本对象中保存报文信息
     * 可以在message成员变量中获取处理结果
     * @param message 发送的报文
     */
    public void sendMessage(Message message)
    {
        this.message = message;
        for(SocketListener listener:listeners)
            listener.doEvent(new SendMessageEvent(this));
    }

    /**
     * 数据源中用于关闭与服务器的连接的方法
     * 会发送事件给Listener
     * 可以在state成员变量中获取处理的结果
     */
    public void closeConnection()
    {
        for(SocketListener listener:listeners)
            listener.doEvent(new CloseEvent(this));
    }

    public void setState(boolean state) { this.state = state;}

}
