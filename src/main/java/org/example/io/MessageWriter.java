package org.example.io;

import org.example.message.Message;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MessageWriter {
    /**
     * 将Message报文对象拆成原始的报文形式（字符串+字节流），并传给输出流
     * @param outputStream 套接字的输出流
     * @param messageToWrite 需要转换并输出的报文对象
     */
    public void writeMessage(OutputStream outputStream, Message messageToWrite)
    {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));
        StringBuilder message = new StringBuilder();
        for(int i=0;i<messageToWrite.getLine().length;i++){
            message.append(messageToWrite.getLine()[i]);
            if(i!=messageToWrite.getLine().length-1){
                message.append(" ");
            }
            else{
                message.append("\r\n");
            }
        }
        for(String name:messageToWrite.getHeaders().keySet()){
            message.append(name).append(":").append(" ");
            message.append(messageToWrite.getHeaders().get(name)).append("\r\n");
        }
        message.append("\r\n");
        writer.write(message.toString());
        writer.write(messageToWrite.getEntityBody());
        writer.write("\r\n");
        writer.flush();
        //writer.close();
    }
}
