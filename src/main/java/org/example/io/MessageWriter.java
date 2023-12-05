package org.example.io;

import org.example.message.Message;

import java.io.OutputStream;

public class MessageWriter {
    /**
     * 将Message报文对象拆成原始的报文形式（字符串+字节流），并传给输出流
     * @param outputStream 套接字的输出流
     * @param messageToWrite 需要转换并输出的报文对象
     */
    public void writeMessage(OutputStream outputStream, Message messageToWrite)
    {
        // TODO
    }
}
