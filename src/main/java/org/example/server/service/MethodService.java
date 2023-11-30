package org.example.server.service;

import org.example.server.message.Message;

/**
 * 用于处理报文的接口类，包含了针对不同报文方法的响应的处理方式。
 */
public interface MethodService {
    /**
     * 报文处理方法，用于根据请求报文的内容进行相应业务
     * @param requestMessage 需要处理的报文
     * @return 需要发送回去的响应报文对象
     */
    public Message serve(Message requestMessage);
}
