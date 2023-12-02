package org.example.server.io;

import org.example.server.message.Message;
import org.example.server.parser.MessageParser;

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
        StringBuilder builder = new StringBuilder();
        try
        {
            String message = reader.readLine();//读取数据
            if(message == null) return null;
            builder.append(message).append("\r\n");
            //如果读到空说明已经把首部读完了
            do {
                message = reader.readLine();
                if (message == null) return null;
                builder.append(message).append("\r\n");
            } while (!message.isEmpty());
            //开始读实体主体
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] b =new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(b))!=-1)
            {
                byteArrayOutputStream.write(b,0,bytesRead);
            }
            inputStream.close();
            byteArrayOutputStream.close();
            //构建一个报文
            return new MessageParser().parseMessage(builder, Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
