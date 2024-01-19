package org.example.server.service;

import org.example.message.Message;
import org.example.message.Response;
import org.example.server.data.SessionTable;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
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
        String URL = requestMessage.getLine()[1];
        if(isResourceFound(URL)){
            String entityBody = findResource(URL);
            Message message = new Response(new String[]{"HTTP/1.1", "200", "OK"}, new HashMap<>(), entityBody);
            return message;
        }
        else{  }//如果没有找到资源

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
        entityBody = "Please log in.";
        return new Response(line, headers, entityBody);
    }
    private boolean isResourceFound(String URL){
        File file = new File(URL);
        return file.exists();
    }
    private String findResource(String URL){
        try {
            File file = new File(URL);
            FileInputStream fis = new FileInputStream(file);
            byte[] content = new byte[(int)file.length()];
            fis.read(content);
            fis.close();
            if(URL.endsWith(".txt")){
                return new String(content);
            }
            else{
                Base64.Encoder encoder = Base64.getEncoder();
                String encoded = encoder.encodeToString(content);
                return encoded;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
