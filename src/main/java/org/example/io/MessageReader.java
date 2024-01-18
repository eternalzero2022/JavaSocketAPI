package org.example.io;

import org.example.message.Message;
import org.example.parser.MessageParser;

import java.io.*;
import java.util.Base64;

public class MessageReader {
    /**
     * 从套接字的输入流中读取一个完整的HTTP报文
     * @param inputStream 套接字的输入流
     * @return 读取到的报文对象
     */
    public Message readMessage(InputStream inputStream)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder entityBuilder = new StringBuilder();
        try
        {
            String message = reader.readLine();//读取数据
            if(message == null) return null;
            headerBuilder.append(message).append("\r\n");
            //如果读到空说明已经把首部读完了
            do {
                message = reader.readLine();
                if (message == null) return null;
                headerBuilder.append(message).append("\r\n");
            } while (!message.isEmpty());
            //开始读实体主体
            while(reader.ready())
                entityBuilder.append(reader.readLine());
            inputStream.close();
            //构建一个报文，其中的实体主体仍然为字符串，并且如果是text的MIME类型则为原来的字符串数据不变，如果是其他MIME类型则为将二进制数据进行base64编码后形成的字符串
            return new MessageParser().parseMessage(headerBuilder, entityBuilder.toString());
        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
