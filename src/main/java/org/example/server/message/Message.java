package org.example.server.message;


import java.util.HashMap;
import java.util.Map;

/**
 * 封装的报文抽象类，分为请求报文Request和响应报文Response
 */
public abstract class Message {
    /**报文的开始行，分为三个部分**/
    private final String[] line;
    /**报文的首部键值对**/
    private final Map<String,String> headers;
    /**报文的实体主体**/
    private final String entityBody;
    /**报文的类型，请求报文为REQUEST，响应报文为RESPONSE**/
    private final MessageType messageType;


    /**
     * 报文类的构造函数
     * @param line 报文的实体主体字符串数组（分为三段）
     * @param headers 报文的首部键值对
     * @param entityBody 报文的实体主体的字符串，使用Base64编码（需从二进制数据流转换）
     */
    public Message(String[] line,Map<String,String> headers,String entityBody)
    {
        this.line = line.clone();
        this.headers = new HashMap<String,String>(headers);
        this.entityBody = entityBody;
        messageType = getMessageType();
    }

    protected abstract MessageType getMessageType();//用于设定报文的类型

    public String[] getLine() {
        return line;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getEntityBody() {
        return entityBody;
    }
}

/**
 * 报文的类别，请求报文为REQUEST，响应报文为RESPONSE
 */
enum MessageType
{
    REQUEST,
    RESPONSE
}
