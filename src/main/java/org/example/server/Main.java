package org.example.server;

import org.example.server.control.ConnectionController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    /**
     * 整个服务器端程序的入口
     * 需要创建一个线程池，并不断监听端口，一一旦建立连接就将这个连接分配给线程池中的一个线程
     * 线程会自动调用ConnectionController中的run方法
     */
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        ServerSocket serverSocket = new ServerSocket(6666);
        try {
            while(true)
            {
                Socket socket = serverSocket.accept();
                executor.execute(new ConnectionController(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }

    }
}