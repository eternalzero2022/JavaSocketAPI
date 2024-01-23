package org.example.server.service;
import org.example.message.Message;
import org.example.message.Response;
import org.example.server.data.SessionTable;
import org.example.server.data.UrlTable;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
        SessionID = requestMessage.getHeaders().get("SessionID");
        if(SessionID == null)
            return getErrorResponse();
        if (!SessionTable.getInstance().hasSession(SessionID)) {//如果没有会话
            return NotFoundSession();
        }
        String URL = "."+requestMessage.getLine()[1];
        if(isResourceFound(URL)){
            if(requestMessage.getHeaders().get("If-Modified-Since") != null){
                String last_modified = requestMessage.getHeaders().get("If-Modified-Since");
                File file = new File(URL);
                long last_modified_time = file.lastModified();
                if(last_modified_time <= Long.parseLong(last_modified)){
                    return Response_304();
                }
            }
            return Response_file(URL);
        }
        else{//如果没有找到资源
            UrlTable urlTable = UrlTable.getInstance();
            if(urlTable.getUrl(URL) != null){//如果是重定向
                UrlTable.Url urlnode = urlTable.getUrl(URL);
                UrlTable.Url.Status k = urlnode.get_Status();
                UrlTable.Url new_url = urlnode.get_New_url();
                if(k == UrlTable.Url.Status.temporary){//如果是临时移动
                    //String entityBody = findResource(new_url.get_url());
                    return Response_302(new_url.get_url());
                }
                else{//如果是永久移动
                    //String entityBody = findResource(new_url.get_url());
                    return Response_301(new_url.get_url());
                }

            }
            return Response_404();
        }
    }
    private Message getErrorResponse() {
        String entityBody = "Lack of header";
        Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Length",String.valueOf(entityBody.getBytes().length));
        return new Response(new String[]{"HTTP/1.1", "500", "Internal Server Error"}, map, entityBody);
    }
    private Message NotFoundSession() {
        System.out.println("Can't find session in session table.");
        String[] line;String entityBody;
        HashMap<String, String> headers = new HashMap<>();
        line = new String[]{"HTTP/1.1", "200", "OK"};
        entityBody = "Please log in.";
        headers.put("Content-Length",String.valueOf(entityBody.getBytes().length));
        return new Response(line, headers, entityBody);
    }
    private Message Response_301(String new_url){
        Map<String,String> map = new HashMap<String,String>();
        map.put("Location",new_url);
        map.put("Content-Length","0");
        return new Response(new String[]{"HTTP/1.1", "301", "Moved Permanently"}, map, "");
    }
    private Message Response_302(String new_url){
        Map<String,String> map = new HashMap<String,String>();
        map.put("Location",new_url);
        map.put("Content-Length","0");
        return new Response(new String[]{"HTTP/1.1", "302", "Found"}, map, "");
    }
    private Message Response_304(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Length","0");
        return new Response(new String[]{"HTTP/1.1", "304", "Not Modified"}, map, "");
    }
    private Message Response_404(){
        String entityBody = "Not found";
        Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Length",String.valueOf(entityBody.getBytes().length));
        return new Response(new String[]{"HTTP/1.1", "404", "Not Found"}, map, entityBody);
    }
    private Message Response_file(String URL){
        String entityBody = findResource(URL);
        Map<String,String> headers = new HashMap<String,String>();
        int length = entityBody == null?0:entityBody.getBytes().length;
        headers.put("Content-Length",String.valueOf(length));
        File file = new File(URL);
        long last_modified_time = file.lastModified();
        headers.put("Last-Modified",String.valueOf(last_modified_time));
        if(URL.endsWith(".txt")){
            headers.put("Content-Type","text/plain");
            headers.put("Content-Encoding","ASCII");
        }
        else if(URL.endsWith(".png")){
            headers.put("Content-Type","image/png");
            headers.put("Content-Encoding","Base64");
        }
        else if(URL.endsWith(".jpeg")){
            headers.put("Content-Type","image/jpeg");
            headers.put("Content-Encoding","Base64");
        }
        else if(URL.endsWith(".mp3")){
            headers.put("Content-Type","audio/mp3");
            headers.put("Content-Encoding","Base64");
        }
        Message message = new Response(new String[]{"HTTP/1.1", "200", "OK"}, headers, entityBody);
        return message;
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
                return encoder.encodeToString(content);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}