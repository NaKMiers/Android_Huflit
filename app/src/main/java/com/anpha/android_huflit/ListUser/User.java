package com.anpha.android_huflit.ListUser;

import java.io.Serializable;

public class User implements Serializable {
    private String IDUser;
    private String UserName;
    private String EmailUser;
    private String Avatar;

    public User() {
    }

    public User(String IDUser, String userName, String emailUser, String avatar) {
        this.IDUser = IDUser;
        UserName = userName;
        EmailUser = emailUser;
        Avatar = avatar;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }
}
