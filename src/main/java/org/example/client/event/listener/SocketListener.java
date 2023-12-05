package org.example.client.event.listener;

import org.example.client.event.object.SocketEvent;

import java.util.EventListener;

/**
 * 事件中用于监听事件的类
 * 监听由事件源（SourceManager）发来的事件，并根据事件内容执行相应的操作
 */
public interface SocketListener extends EventListener {
    /**
     * 执行事件操作的方法，当事件源(SourceManager)发送事件时会主动调用这个方法，并发送事件信息
     * @param event 事件信息
     */
    public void doEvent(SocketEvent event);
}
