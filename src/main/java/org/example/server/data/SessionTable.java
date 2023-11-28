package org.example.server.data;

import java.util.ArrayList;

public class SessionTable {
    private static final SessionTable instance = new SessionTable();
    private ArrayList<Session> sessions;
    private SessionTable()
    {
        sessions = new ArrayList<Session>();
    }
    public static SessionTable getInstance()
    {
        return instance;
    }

    public String createSession(String username,String password)
    {
        UserTable.User user = UserTable.getInstance().getUserByName(username);
        if(user == null || !user.isPasswordCorrect(password))
            return null;
        Session session = new Session(user.getID());
        sessions.add(session);
        return session.SessionID;
    }

    //session内部类
    private static class Session {
        private final String SessionID;//会话标识符
        private final int UserID;//会话用户
        Session(int UserID)
        {
            this.UserID = UserID;
            this.SessionID = generateSessionID();
        }

        private String generateSessionID()
        {
            StringBuilder builder = new StringBuilder();
            String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            for(int i = 0;i < 16;i++)
            {
                builder.append(characters.charAt(((int)(Math.random()*62))));
            }
            return builder.toString();
        }
    }

}
