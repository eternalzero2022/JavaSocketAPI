package org.example.client.event.listener;
import org.example.client.connection.SocketConnection;
import org.example.client.event.object.CloseEvent;
import org.example.client.event.object.ConnectionEvent;
import org.example.client.event.object.SocketEvent;
import org.example.client.event.object.SendMessageEvent;
import org.example.client.event.source.SocketManager;
import org.example.message.Message;

import java.net.InetAddress;
import java.util.Map;

/**
 * 事件监听类的实现类，用于监听事件源发送的事件
 */
public class SocketListenerImpl implements SocketListener{
    /**、
     * 执行事件操作的方法的实现类
     * 针对三种类型的Event进行不同操作
     * @param event 事件信息
     */
    @Override
    public void doEvent(SocketEvent event) {
        // TODO
        SocketConnection socketConnection = SocketConnection.getInstance();
        if(event instanceof CloseEvent){
            SocketManager source = (SocketManager) event.getSource();
            if(socketConnection.close()){
                source.setState(false);
            } // 关闭套接字
            else{
                source.setState(true);// 关闭失败
            }
        }
        else if(event instanceof SendMessageEvent){
            SocketManager source = (SocketManager) event.getSource();
            Message response = socketConnection.sendMessage(source.getMessage());//发送报文后收到的回复报文
            if(response == null){
                source.setState(false);//没有收到回复报文
            }
            else {
                source.setState(true);//收到回复报文
                if(response.getLine()[1].equals("301")||response.getLine()[1].equals("302")){
                    System.out.println("接收到响应报文：" + "\n" + "-".repeat(20));
                    response.printMessage();
                    System.out.println("-".repeat(20));
                    Message now = source.getMessage();//修改前的发送报文
                    now.getLine()[1] = response.getHeaders().get("Location");//修改发送报文的路径
                    response = socketConnection.sendMessage(now);//重新发送报文
                    if(response == null){
                        source.setState(false);
                        return;//没有收到回复报文
                    }
                    else {
                        source.setState(true);//收到回复报文
                    }
                }
                source.setMessage(response);
            }
        }
        else {//ConnectionEvent
            ConnectionEvent connectionEvent = (ConnectionEvent) event;
            InetAddress add = connectionEvent.getAddress();
            int port = connectionEvent.getPort();
            SocketManager source = (SocketManager) event.getSource();
            source.setState(socketConnection.connect(add,port));
        }
    }

}
