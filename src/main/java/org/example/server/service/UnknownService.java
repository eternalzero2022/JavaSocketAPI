package org.example.server.service;

import org.example.message.Message;

/**
 * 当报文既不是GET方法也不是POST方法时处理报文的业务类，主要用于返回一个405状态码的报文
 * 可以自行添加其他函数
 */
public class UnknownService implements MethodService{
    /**
     * 处理未知方法报文
     * @param requestMessage 需要处理的报文
     * @return 405状态码报文
     */
    @Override
    public Message serve(Message requestMessage) {
        return null;
    }
}
