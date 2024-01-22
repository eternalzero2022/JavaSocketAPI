package org.example.client.control;

import org.example.client.service.RegisterService;
import org.example.client.service.Service;

import java.util.Scanner;

/**
 * 用于执行注册的功能的控制类
 */
public class RegisterControl implements ClientControl{

    /**
     * 执行注册的功能的方法
     */
    @Override
    public void runControl()
    {
        String userName,password;
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入用户名：");
        userName=scanner.next();
        System.out.println("请输入密码：");
        password=scanner.next();
        RegisterService registerService=new RegisterService();
        Service.StateObject stateObject=registerService.register(userName,password);
        System.out.println(stateObject.state+": "+stateObject.information);
    }
}
