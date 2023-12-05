package org.example.client.event.object;

public class SendMessageEvent extends SocketEvent{
    /**
     * 套接字事件的构造函数
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public SendMessageEvent(Object source) {
        super(source);
    }
}
