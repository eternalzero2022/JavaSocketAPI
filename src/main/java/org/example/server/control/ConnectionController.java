package org.example.server.control;

/**
 * Runnable类
 * 相当于一个新的线程
 * 分配给每一个新的连接使用
 */
public class ConnectionController implements Runnable{
    /**
     * 线程启动时会调用的函数
     * 这其中需要完成每个连接需要完成的事情，即“接收报文、处理报文、发送报文”的循环
     * 这三者分别都可以调用其他包中的方法实现
     */
    @Override
    public void run() {
        // TODO
    }
}
