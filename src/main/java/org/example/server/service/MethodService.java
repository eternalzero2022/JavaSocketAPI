package org.example.server.service;

import org.example.server.message.Message;

public interface MethodService {
    public Message serve(Message requestMessage);
}
