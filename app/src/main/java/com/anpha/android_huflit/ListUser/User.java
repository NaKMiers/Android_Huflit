package com.anpha.android_huflit.ListUser;

import java.io.Serializable;

public class User implements Serializable {
    private int imageUser;
    private String IDUser;
    private String UserName;
    private String EmailUser;

    public User() {
    }

    public User(int imageUser, String IDUser, String userName, String emailUser) {
        this.imageUser = imageUser;
        this.IDUser = IDUser;
        this.UserName = userName;
        this.EmailUser = emailUser;
    }

    public int getImageUser() {
        return imageUser;
    }

    public void setImageUser(int imageUser) {
        this.imageUser = imageUser;
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
