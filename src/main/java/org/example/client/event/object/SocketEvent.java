package org.example.client.event.object;

import java.util.EventObject;

public abstract class SocketEvent extends EventObject {
    /**
     * 套接字事件的构造函数
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public SocketEvent(Object source) {
        super(source);
    }
}

