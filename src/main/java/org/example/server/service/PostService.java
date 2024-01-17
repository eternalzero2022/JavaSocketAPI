package org.example.server.service;

import org.example.message.Message;
import org.example.message.Request;
import org.example.message.Response;
import org.example.server.data.SessionTable;
import org.example.server.data.UserTable;

import java.util.HashMap;

/**
 * 当报文是POST报文时处理报文的业务类，需要包含处理报文的方法。主要用于登录或注册。
 * 可以自行添加其他函数
 * 发送登录或注册请求的POST报文需包含以下三个首部字段：
 * 1. Type: Login / Register
 * 2. Username
 * 3. Password
 */
public class PostService implements MethodService {
    public static void main(String[] args) {
        PostService postService = new PostService();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Type", "Register");
        headers.put("Username", "Sprooc");
        headers.put("Password", "123456");
        Request req = new Request(new String[]{"PSOT", "/", "HTTP/1.1"}, headers, "");
        Message response = postService.serve(req);
        System.out.println(response.getEntityBody());
        headers.put("Type", "Login");
        headers.put("Username", "Sprooc");
        headers.put("Password", "123456");
        req = new Request(new String[]{"POST", "/", "HTTP/1.1"}, headers, "");
        response = postService.serve(req);
        System.out.println(response.getEntityBody());
    }
    /**
     * 处理POST报文。
     *
     * @param requestMessage 需要处理的报文对象
     * @return 需要发送回去的响应报文
     */
    @Override
    public Message serve(Message requestMessage) {
        String type, username, password;
        try {
            type = requestMessage.getHeaders().get("Type");
            username = requestMessage.getHeaders().get("Username");
            password = requestMessage.getHeaders().get("Password");
        } catch (NullPointerException e) {
            // POST报文缺少必需的首部字段，无法完成登录或注册
            return getErrorResponse();
        }
        if (type.equals("Login")) {
            return doLogin(username, password);
        } else if (type.equals("Register")) {
            return doRegister(username, password);
        } else {
            return getErrorResponse();
        }
    }

    /**
     * 生成一个状态码500的响应报文
     */
    private Message getErrorResponse() {
        return new Response(new String[]{"HTTP/1.1", "500", "Internal Server Error"}, new HashMap<>(), "Lack of header.");
    }

    /**
     * 处理登录请求
     *
     * @param username 用户名
     * @param password 密码
     * @return 响应报文
     */
    private Message doLogin(String username, String password) {
        System.out.println("DoLogin username: " + username + " password: " + password);
        String sessionID = SessionTable.getInstance().createSession(username, password);
        String[] line;
        HashMap<String, String> headers = new HashMap<>();
        String entityBody;
        if (sessionID == null) {
            // 用户名或密码错误
            line = new String[]{"HTTP/1.1", "200", "OK"};
            entityBody = "Error Username of Password.";
        } else {
            // 登录成功，响应报文带有首部字段SessionID
            line = new String[]{"HTTP/1.1", "200", "OK"};
            headers.put("SessionID", sessionID);
            entityBody = "Login successfully!";
        }
        return new Response(line, headers, entityBody);
    }

    /**
     * 处理注册请求
     *
     * @param username 用户名
     * @param password 密码
     * @return 响应报文
     */
    private Message doRegister(String username, String password) {
        System.out.println("DoRegister username: " + username + " password: " + password);
        String[] line;
        HashMap<String, String> headers = new HashMap<>();
        String entityBody;
        if (UserTable.getInstance().addUser(username, password)) {
            // 注册成功
            line = new String[]{"HTTP/1.1", "200", "OK"};
            entityBody = "Register successfully!";
        } else {
            // 用户名已存在，注册失败
            line = new String[]{"HTTP/1.1", "200", "OK"};
            entityBody = "Username already exists.";
        }
        return new Response(line, headers, entityBody);
    }
}
