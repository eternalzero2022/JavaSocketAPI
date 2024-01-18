package org.example.server.service;

import org.example.message.Message;
import org.example.message.Response;
import org.example.server.data.SessionTable;

import java.util.HashMap;

/**
 * 当报文是GET报文时处理报文的业务类，需要包含处理报文的方法。主要用于请求资源
 * 可以自行添加其他函数
 */
public class GetService implements MethodService{
    /**
     * 处理GET报文，提供报文需要的东西
     * @param requestMessage 需要处理的报文对象
     * @return 需要发送回去的响应报文
     */
    @Override
    public Message serve(Message requestMessage) {
        // TODO
        String SessionID;
        try {
            SessionID = requestMessage.getHeaders().get("SessionID");
        } catch (NullPointerException e) {
            return getErrorResponse();
        }
        if (!SessionTable.getInstance().hasSession(SessionID)) {//如果没有会话
            return NotFoundSession();
        }
        return null;
    }
    private Message getErrorResponse() {
        return new Response(new String[]{"HTTP/1.1", "500", "Internal Server Error"}, new HashMap<>(), "Lack of header.");
    }
    private Message NotFoundSession() {
        System.out.println("Can't find session in session table.");
        String[] line;String entityBody;
        HashMap<String, String> headers = new HashMap<>();
        line = new String[]{"HTTP/1.1", "200", "OK"};
        entityBody = "Can't find session in session table.";
        return new Response(line, headers, entityBody);
    }
}
