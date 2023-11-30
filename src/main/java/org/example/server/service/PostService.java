package org.example.server.service;

import org.example.server.message.Message;

/**
 * 当报文是POST报文时处理报文的业务类，需要包含处理报文的方法。主要用于登录或注册。
 * 可以自行添加其他函数
 */
public class PostService implements MethodService{
    /**
     * 处理POST报文。
     * @param requestMessage 需要处理的报文对象
     * @return 需要发送回去的响应报文
     */
    @Override
    public Message serve(Message requestMessage) {
        // TODO
        return null;
    }
}
