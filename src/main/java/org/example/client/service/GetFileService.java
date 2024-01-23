package org.example.client.service;

import org.example.client.event.listener.SocketListenerImpl;
import org.example.client.event.source.SocketManager;
import org.example.message.Message;
import org.example.message.Request;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;

public class GetFileService implements Service {
    public StateObject getFile(String filePath) {
        // 构建报文
        String[] line = new String[]{"GET", filePath, "HTTP/1.1"};
        HashMap<String, String> headers = new HashMap<>();
        if (LoginService.getSessionID().isEmpty()) {
            // 未登录，无SessionID
            return new StateObject(State.FAILURE, "未登录，请先登录");
        } else {
            headers.put("SessionID", LoginService.getSessionID());
        }
        File file = new File("./ClientResources" + filePath);
        if (file.exists()) {
            headers.put("If-Modified-Since", file.lastModified() + "");
        }
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
                // 成功获取文件
                creatFile(filePath, response.getEntityBody(), response.getHeaders().get("Content-Type"), response.getHeaders().get("Content-Encoding"));
                return new StateObject(State.SUCCESS, "成功获取文件，资源已被存放在ClientResources文件夹中");
            } else if(response.getLine()[1].equals("304")){
                return new StateObject(State.SUCCESS, "客户端已缓存该文件，资源已被存放在ClientResources文件夹中");
            }else{
                return new StateObject(State.ERROR,"未找到资源");
            }
        } catch (NullPointerException e) {
            return new StateObject(State.ERROR, "未收到服务器响应");
        }

    }

    private void creatFile(String filePath, String entityBody, String type, String encoding) {
        // 解码
        byte[] content;
        if (encoding.equals("Base64")) {
            content = entityBody.replaceAll("\0", "").getBytes();
            content = Base64.getDecoder().decode(content);
        } else {
            content = entityBody.getBytes();
        }

        // 写入文件
        filePath = "./ClientResources" + filePath;
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            FileOutputStream write = new FileOutputStream(filePath);
            BufferedOutputStream bw = new BufferedOutputStream(write);
            bw.write(content);
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

