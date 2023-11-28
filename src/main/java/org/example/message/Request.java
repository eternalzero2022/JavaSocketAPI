package org.example.message;

import java.util.Map;

public class Request extends Message{
    public Request(String[] line, Map<String, String> headers, String[] entityBody) {
        super(line, headers, entityBody);
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.REQUEST;
    }
}
