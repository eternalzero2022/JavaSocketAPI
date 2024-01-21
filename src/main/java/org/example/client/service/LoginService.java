package org.example.client.service;

import org.example.client.event.listener.SocketListenerImpl;
import org.example.client.event.source.SocketManager;
import org.example.message.Message;
import org.example.message.Request;


import java.util.HashMap;

public class LoginService implements Service {
    // 保存登录成功获得的SessionID
    private static String sessionID = "";
    public static String getSessionID() {
        return sessionID;
    }

    public State login(String username, String password) {
        // 构建请求报文
        String[] line = new String[]{"POST", "/", "HTTP/1.1"};
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Type", "Login");
        headers.put("Username", username);
        headers.put("Password", password);
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
        System.out.println("接收到响应报文报文：" + "\n" + "-".repeat(20));
        response.printMessage();
        System.out.println("-".repeat(20));
        try {
            if (response.getLine()[1].equals("200")) {
                if (response.getHeaders().get("SessionID").isEmpty()) {
                    // SessionID为空，登录失败
                    return State.FAILURE;
                } else {
                    // 登陆成功
                    sessionID = response.getHeaders().get("SessionID");
                    return State.SUCCESS;
                }
            } else {
                return State.ERROR;
            }
        } catch (NullPointerException e) {
            return State.ERROR;
        }
    }
}
