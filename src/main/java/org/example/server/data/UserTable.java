package org.example.server.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户表，用来记录用户数据。使用单例模式
 */
public class UserTable {
    /**用户表的唯一单例**/
    private static final UserTable instance = new UserTable();
    /**用户表的物理结构**/
    private ArrayList<User> users;
    /**用户表当前可以被分配的ID，每次创价一个新用户时会自增**/
    private int currentID;
    /**私有构造函数，实例化用户表并将当前ID置为0**/
    private UserTable()
    {
        users = new ArrayList<User>();
        currentID = 0;
    }

    /**
     * 获取用户表的单例
     * @return 用户表的实例
     */

    public static UserTable getInstance()
    {
        return instance;
    }

    /**
     * 往数据表中添加一个新用户，
     * @param username 要添加的用户名
     * @param password 要添加的用户密码
     * @return 是否添加成功
     */
    public boolean addUser(String username,String password)
    {
        if(getUserByName(username)!=null)
            return false;
        users.add(new User(username,password,currentID++));
        return true;
    }

    /**
     * 通过名字查找用户
     * @param username 要查找的用户的用户名
     * @return 用户实例，未找到则为null
     */

    public User getUserByName(String username)
    {
        for(User user:users)
        {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }


    /**
     * 用户类，包含了用户信息，包括用户名、ID、密码
     */
    public static class User {
        /**用户密码**/
        private final String password;
        /**用户名**/
        private final String username;
        /**用户ID**/
        private final int ID;

        /**
         * 用户类构造函数
         * @param password 用户密码
         * @param username 用户名
         * @param ID 用户ID，由CurrentID得到
         */
        User(String username, String password,int ID) {
            this.password = password;
            this.username = username;
            this.ID = ID;
        }


        public String getUsername() {
            return username;
        }

        public int getID(){
            return ID;
        }

        /**
         * 判断输入的用户密码是否正确
         * @param password 输入的用户密码
         * @return 输入的用户密码与用户本身的密码是否相等
         */
        public boolean isPasswordCorrect(String password)
        {
            return this.password.equals(password);
        }
    }
}
