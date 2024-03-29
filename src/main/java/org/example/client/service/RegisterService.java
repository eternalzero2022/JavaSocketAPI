package org.example.client.service;

import org.example.client.event.listener.SocketListenerImpl;
import org.example.client.event.source.SocketManager;
import org.example.message.Message;
import org.example.message.Request;

import java.util.HashMap;

public class RegisterService implements Service{
    public StateObject register(String username,String password)
    {
        // 构建请求报文
        String[] line = new String[]{"POST", "/", "HTTP/1.1"};
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Type", "Register");
        headers.put("Username", username);
        headers.put("Password", password);
        headers.put("Content-Length", "0");
        Request message = new Request(line, headers, "");
        // 打印请求报文
        System.out.println("即将发送请求报文：" + "\n" + "-".repeat(20));
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
        try {
            if (response.getLine()[1].equals("200")) {
                if (response.getHeaders().get("Status").equals("Failure")) {
                    // 注册失败
                    return new StateObject(State.FAILURE,"用户已被注册");
                } else {
                    // 注册成功
                    return new StateObject(State.SUCCESS,"注册成功");
                }
            } else {
                return new StateObject(State.ERROR,"注册请求出错");
            }
        } catch (NullPointerException e) {
            return new StateObject(State.ERROR,"未收到服务器响应");
        }
    }
}
