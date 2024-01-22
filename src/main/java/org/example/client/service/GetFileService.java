package org.example.client.service;

import org.example.client.event.listener.SocketListenerImpl;
import org.example.client.event.source.SocketManager;
import org.example.message.Message;
import org.example.message.Request;

import java.util.HashMap;

public class GetFileService implements Service {
    public StateObject getFile(String filePath) {
        // 构建报文
        String[] line = new String[]{"GET", filePath, "HTTP/1.1"};
        HashMap<String, String> headers = new HashMap<>();
        if (LoginService.getSessionID().isEmpty()) {
            // 未登录，无SessionID
            return new StateObject(State.FAILURE,"未登录，请先登录");
        } else {
            headers.put("SessionID", LoginService.getSessionID());
        }
        Request message = new Request(line, headers, "");
        // 打印请求报文
        System.out.println("即将请求发送报文：" + "\n" + "-".repeat(20));
        message.printMessage();
        System.out.println("-".repeat(20));
        // 发送并接收报文
        SocketManager socketManager = new SocketManager(new SocketListenerImpl());
        socketManager.sendMessage(message);
        Message response = socketManager.getMessage();
        // 打印响应报文
        System.out.println("接收到响应报文：" + "\n" + "-".repeat(20));
        response.printMessage();
        System.out.println("-".repeat(20));

        try{
            if (response.getLine()[1].equals("200")) {
                // 成功获取文件
                return new StateObject(State.SUCCESS,"成功获取文件");
            } else {
                return new StateObject(State.ERROR,"未找到资源");
            }
        }catch (NullPointerException e)
        {
            return new StateObject(State.ERROR,"未收到服务器响应");
        }

    }
}
