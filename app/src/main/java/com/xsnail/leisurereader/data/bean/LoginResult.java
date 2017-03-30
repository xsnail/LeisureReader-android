package com.xsnail.leisurereader.data.bean;

/**
 * Created by xsnail on 2017/3/23.
 */

public class LoginResult extends Base {
    public String error;
    public User data;

    public static class User{
        public String userName;
        public String passWord;
    }
}
