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

        try {
            if (response.getLine()[1].equals("200")) {
                // 成功获取文件
                return new StateObject(State.SUCCESS, "成功获取文件");
            } else {
                return new StateObject(State.ERROR, "未找到资源");
            }
        } catch (NullPointerException e) {
            return new StateObject(State.ERROR, "未收到服务器响应");
        }

    }

    private void creatFile(String filePath, String entityBody, String type, String encoding) {
        // 解码
        byte[] content = entityBody.getBytes();
        if (encoding.equals("Base64")) {
            content = Base64.getDecoder().decode(content);
        }

        // 写入文件
        filePath = "./ClientResources" + filePath;
        File file = new File(filePath);
//        if(type.equals("text/plain")) {
        try {
            FileOutputStream write = new FileOutputStream(filePath);
            BufferedOutputStream bw = new BufferedOutputStream(write);
            bw.write(content);
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } else {
//            ByteArrayInputStream bais = null;
//            try {
//                //获取图片类型
//                String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
//                //构建字节数组输入流
//                bais = new ByteArrayInputStream(content);
//                //通过ImageIO把字节数组输入流转为BufferedImage
//                BufferedImage bufferedImage = ImageIO.read(bais);
//                //构建文件
//                File imageFile = new File(imageFileName);
//                //写入生成文件
//                ImageIO.write(bufferedImage, suffix, imageFile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bais != null) {
//                        bais.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

    }
//    private String findResource(String URL){
//        try {
//            File file = new File(URL);
//            FileInputStream fis = new FileInputStream(file);
//            byte[] content = new byte[(int)file.length()];
//            fis.read(content);
//            fis.close();
//            if(URL.endsWith(".txt")){
//                return new String(content);
//            }
//            else{
//                Base64.Encoder encoder = Base64.getEncoder();
//                return encoder.encodeToString(content);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public void main(String[] args) {
//        String entitybody = findResource("/ServerResources/Earth.png");

//    }
}

