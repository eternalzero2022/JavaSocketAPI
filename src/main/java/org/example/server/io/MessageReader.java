package org.example.server.io;

import org.example.server.message.Message;
import org.example.server.parser.MessageParser;

import java.io.*;

public class MessageReader {
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
            return new MessageParser().parseMessage(builder,byteArrayOutputStream.toByteArray());
        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
