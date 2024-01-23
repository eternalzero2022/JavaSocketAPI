package org.example.parser;

import org.example.message.Message;
import org.example.message.Request;
import org.example.message.Response;

import java.util.HashMap;
import java.util.Map;

public class MessageParser {
    /**
     * 将ASCII（字符串）+字节流形式的原始报文封装成一个Message报文对象
     * @param request HTTP报文的开始行和首部行
     * @param entityBody HTTP报文的实体主体
     * @return 被封装的Message报文对象
     */
    public Message parseMessage(StringBuilder request,String entityBody) {
        String[] messageLine = request.toString().split("\r\n");
        int lineNum =messageLine.length;
        if(lineNum == 0) return null;
        Map<String,String> headers = new HashMap<String,String>();
        //System.out.println(messageLine[0]+"stop!!!这是messageline[0],它的ascii值为"+print(messageLine[0].getBytes()));
        for(int i = 1;i < messageLine.length;i++)//逐行读入messageLine，选出所有首部，直到读到空行为止
        {
            String line = messageLine[i];
            //System.out.println(line);
            if(line.isEmpty())//读到了空行
                break;
            String[] splitedLine = line.split(": ",2);
            headers.put(splitedLine[0],splitedLine[1]);//存入字典中
        }
        //判断是请求报文还是响应报文
        String[] startLine = messageLine[0].split(" ",3);
        //System.out.println("一个报文结束");
//        if(startLine[0].equals("GET")||startLine[0].equals("POST"))
//            return new Request(startLine,headers,entityBody);
//        return new Response(startLine,headers,entityBody);

        if(startLine[0].startsWith("HTTP"))
            return new Response(startLine,headers,entityBody);
        return new Request(startLine,headers,entityBody);
    }


    public String print(byte[] bytes)
    {
        StringBuilder a = new StringBuilder();
        for(int i = 0;i < bytes.length;i++)
        {
            a.append(String.valueOf((int)bytes[i]));
        }
        return a.toString();
    }
}
