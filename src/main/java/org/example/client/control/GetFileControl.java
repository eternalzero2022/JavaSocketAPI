package org.example.client.control;

import org.example.client.service.GetFileService;
import org.example.client.service.Service;

import java.util.Scanner;

/**
 * 用于执行获取文件内容的功能的控制类
 */
public class GetFileControl implements ClientControl{
    /**
     * 执行获取文件的功能的方法
     */
    @Override
    public void runControl() {
        String filePath;
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入资源路径（需要以斜杠开头）：");
        filePath=scanner.next();
        GetFileService getFileService =new GetFileService();
        Service.StateObject stateObject=getFileService.getFile(filePath);
        //System.out.println(stateObject.state+": "+stateObject.information);
        if(stateObject.state == Service.State.SUCCESS){
            System.out.println("资源请求成功！成功信息："+stateObject.information);
        }
        else if(stateObject.state == Service.State.FAILURE){
            System.out.println("资源请求失败！失败信息："+stateObject.information);
        }
        else{
            System.out.println("请求出错！错误信息："+stateObject.information);
        }
    }
}
