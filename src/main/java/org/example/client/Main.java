package org.example.client;

import org.example.client.control.GetFileControl;
import org.example.client.control.LoginControl;
import org.example.client.control.RegisterControl;
import org.example.client.event.listener.SocketListenerImpl;
import org.example.client.event.source.SocketManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
    /**
     * 整个客户端程序的入口
     * 需要
     * 线程会自动调用ConnectionController中的run方法
     */
    public static void main(String[] args) {
        try {
            SocketManager manager=new SocketManager(new SocketListenerImpl());
            manager.startConnection(InetAddress.getByName("127.0.0.1"),6666);
            if(!manager.getState())
            {
                //连接失败
                System.out.println("连接失败");
                return;
            }
            System.out.println("连接成功");
            Scanner scanner=new Scanner(System.in);
            String command;
            boolean close=true;
            while (close){
                System.out.println("请输入命令（1：登录；2：注册；3：请求资源；4：断开连接）");
                command=scanner.next();
                switch (command){
                    case "1":new LoginControl().runControl();break;
                    case "2":new RegisterControl().runControl();break;
                    case "3":new GetFileControl().runControl();break;
                    case"4":
                        manager.closeConnection();
                        close=false;
                        break;
                    default:System.out.println("命令错误");
                }
            }
        } catch (IOException e) {
            System.out.println("连接失败");
            throw new RuntimeException(e);
        }
        System.out.println("断开连接");
    }
}
