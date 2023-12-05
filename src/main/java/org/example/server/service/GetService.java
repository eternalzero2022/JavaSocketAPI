package org.example.server.service;

import org.example.message.Message;

/**
 * 当报文是GET报文时处理报文的业务类，需要包含处理报文的方法。主要用于请求资源
 * 可以自行添加其他函数
 */
public class GetService implements MethodService{
    /**
     * 处理GET报文，提供报文需要的东西
     * @param requestMessage 需要处理的报文对象
     * @return 需要发送回去的响应报文
     */
    @Override
    public Message serve(Message requestMessage) {
        // TODO
        return null;
    }
}
