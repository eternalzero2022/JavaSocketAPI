package org.example.server.data;

import java.util.ArrayList;

/**
 * 会话表，用于管理以创建的会话，使用单例模式
 */
public class SessionTable {
    /**
     * 单例模式中的会话表的实例
     */
    private static final SessionTable instance = new SessionTable();
    /**
     * 会话表的物理结构
     */
    private ArrayList<Session> sessions;

    /**
     * 私有构造函数，实例化一个会话表
     */
    private SessionTable()
    {
        sessions = new ArrayList<Session>();
    }

    /**
     * 获取会话表的单例
     * @return 会话表的实例
     */
    public static SessionTable getInstance()
    {
        return instance;
    }

    /**
     * 创建一个会话，在创建会话前会先进行用户身份的验证（相当于登录）
     * @param username 需要创建会话的用户的用户名
     * @param password 需要创建会话的用户的密码
     * @return 会话标识符，创建失败则返回null
     */
    public String createSession(String username,String password)
    {
        UserTable.User user = UserTable.getInstance().getUserByName(username);
        if(user == null || !user.isPasswordCorrect(password))
            return null;
        Session session = new Session(user.getID());
        sessions.add(session);
        return session.SessionID;
    }


    /**
     * 检查当前会话表中是否存在属于这个会话ID的会话
     * @param sessionID 要检查的会话ID
     * @return 会话ID是否存在于会话表中
     */
    public boolean hasSession(String sessionID)
    {
        for(Session session:sessions)
        {
            if(session.SessionID.equals(sessionID))
                return true;
        }
        return false;
    }



    /**
     * 会话类，用户登录时会在会话表中分配一个会话，用来表示用户的登录信息
     */

    //session内部类
    private static class Session {
        /**会话标识符，随机字符串，在用户登录时创建并分配，同时用户也享有一份属于用户的会话标识符，**/
        private final String SessionID;
        /**会话所属的用户的ID**/
        private final int UserID;

        /**
         * 会话构造函数，传入一个用户ID，自动生成一个随机会话标识符
         * @param UserID 需要创建会话的用户的ID
         */
        Session(int UserID)
        {
            this.UserID = UserID;
            this.SessionID = generateSessionID();
        }

        /**
         * 生成随机会话标识符
         * @return 生成的会话标识符
         */

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
