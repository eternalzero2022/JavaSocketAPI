package org.example.server.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTable {
    private static final UserTable instance = new UserTable();

    private ArrayList<User> users;
    private int currentID;
    private UserTable()
    {
        users = new ArrayList<User>();
        currentID = 0;
    }

    public static UserTable getInstance()
    {
        return instance;
    }

    //添加用户
    public boolean addUser(String username,String password)
    {
        if(getUserByName(username)!=null)
            return false;
        users.add(new User(username,password,currentID++));
        return true;
    }

    public User getUserByName(String username)
    {
        for(User user:users)
        {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }


    //User内部类
    public static class User {
        private final String password;
        private final String username;
        private final int ID;

        User(String password, String username,int ID) {
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

        //比较密码是否正确
        public boolean isPasswordCorrect(String password)
        {
            return this.password.equals(password);
        }
    }
}
