package org.example.server.message;

import java.util.Map;

/**
 * 请求报文
 */
public class Request extends Message{
    public Request(String[] line, Map<String, String> headers, byte[] entityBody) {
        super(line, headers, entityBody);
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.REQUEST;
    }
}
