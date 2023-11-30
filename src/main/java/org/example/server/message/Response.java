package org.example.server.message;

import java.util.Map;

/**
 * 响应报文
 */
public class Response extends Message{
    public Response(String[] line, Map<String, String> headers, byte[] entityBody) {
        super(line, headers, entityBody);
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.RESPONSE;
    }
}
