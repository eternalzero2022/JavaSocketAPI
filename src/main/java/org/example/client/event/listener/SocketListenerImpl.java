package org.example.client.event.listener;
import org.example.client.connection.SocketConnection;
import org.example.client.event.object.CloseEvent;
import org.example.client.event.object.SocketEvent;
import org.example.client.event.object.SendMessageEvent;
import org.example.client.event.source.SocketManager;

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
        if(CloseEvent.class.isInstance(event)){
            SocketManager source = (SocketManager) event.getSource();
            if(SocketConnection.getInstance().close()){
                source.setState(false);
            } // 关闭套接字

        }
        else if(SendMessageEvent.class.isInstance(event)){

        }
        else {//ConnectionEvent


        }


    }

}
