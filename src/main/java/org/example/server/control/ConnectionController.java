package org.example.server.control;

import org.example.io.MessageReader;
import org.example.io.MessageWriter;
import org.example.message.Message;
import org.example.server.service.GetService;
import org.example.server.service.PostService;

import java.io.IOException;
import java.net.Socket;

/**
 * Runnable类
 * 相当于一个新的线程
 * 分配给每一个新的连接使用
 */
public class ConnectionController implements Runnable {
    /**
     * 线程启动时会调用的函数
     * 这其中需要完成每个连接需要完成的事情，即“接收报文、处理报文、发送报文”的循环
     * 这三者分别都可以调用其他包中的方法实现
     */
    public Socket socket;

    public ConnectionController(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(socket.isClosed())
                    return;
                Message message = new MessageReader().readMessage(socket.getInputStream());
                if (message == null) {
                    //socket.getInputStream().close();
                    socket.close();
                    return;
                }
                if (message.getLine()[0].startsWith("GET")) {
                    new MessageWriter().writeMessage(socket.getOutputStream(),new GetService().serve(message));
                }else {
                    new MessageWriter().writeMessage(socket.getOutputStream(),new PostService().serve(message));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
