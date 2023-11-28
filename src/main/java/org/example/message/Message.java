package org.example.message;


import java.util.Map;

public abstract class Message {
    private String[] line;
    private Map<String,String> headers;
    private String[] entityBody;

    //构造函数
    public Message(String[] line,Map<String,String> headers,String[] entityBody)
    {
        this.line = line;
        this.headers = headers;
        this.entityBody = entityBody;
    }

    public String[] getLine() {
        return line;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String[] getEntityBody() {
        return entityBody;
    }
}
