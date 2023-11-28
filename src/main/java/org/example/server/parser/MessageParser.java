package org.example.server.parser;

import org.example.server.message.Message;
import org.example.server.message.Request;
import org.example.server.message.Response;

import java.util.HashMap;
import java.util.Map;

public class MessageParser {
    public Message parseMessage(StringBuilder request,byte[] entityBody) {
        String[] messageLine = request.toString().split("\n");
        int lineNum =messageLine.length;
        if(lineNum == 0) return null;
        Map<String,String> headers = new HashMap<String,String>();
        for(int i = 1;i < messageLine.length;i++)//逐行读入messageLine，选出所有首部，直到读到空行为止
        {
            String line = messageLine[i];
            if(line.isEmpty())//读到了空行
                break;
            String[] splitedLine = line.split(": ",2);
            headers.put(splitedLine[0],splitedLine[1]);//存入字典中
        }
        //判断是请求报文还是响应报文
        String[] startLine = messageLine[0].split(" ",3);
        if(startLine[0].equals("GET")||startLine[0].equals("POST"))
            return new Request(startLine,headers,entityBody);
        return new Response(startLine,headers,entityBody);
    }
}
