package org.example.client.service;

/**
 * 用于提供业务逻辑的接口类，用于处理特定的业务逻辑
 * 需要同时能够打印出收到的报文和发送的报文的内容
 */
public interface Service {
    public enum State
    {
        SUCCESS,
        FAILURE,
        ERROR
    }

    public class StateObject
    {
        public State state;
        public String information;
        public StateObject(State state,String information)
        {
            this.state = state;
            this.information = information;
        }
    }
}