package org.example.server.message;


import java.util.HashMap;
import java.util.Map;

public abstract class Message {
    private final String[] line;
    private final Map<String,String> headers;
    private final byte[] entityBody;
    private final MessageType messageType;


    //构造函数
    public Message(String[] line,Map<String,String> headers,byte[] entityBody)
    {
        this.line = line.clone();
        this.headers = new HashMap<String,String>(headers);
        this.entityBody = entityBody.clone();
        messageType = getMessageType();
    }

    protected abstract MessageType getMessageType();//用于设定报文的类型

    public String[] getLine() {
        return line;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getEntityBody() {
        return entityBody;
    }
}

enum MessageType
{
    REQUEST,
    RESPONSE
}
