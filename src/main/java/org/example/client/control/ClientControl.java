package org.example.client.control;

/**
 * 用于与用户进行控制台交互，进行具体功能的类
 * 可以调用Service类中的业务逻辑实现方法来实现业务逻辑
 */
public interface ClientControl {
    /**
     * 用于与用户交互，并执行需要执行的功能的方法
     * 需要在控制台输出提示信息来引导用户进行操作，并调用Service中的方法实现相应的功能
     */
    public void runControl();
}
