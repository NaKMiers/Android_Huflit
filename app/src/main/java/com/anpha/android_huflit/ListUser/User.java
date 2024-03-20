package com.anpha.android_huflit.ListUser;

import java.io.Serializable;

public class User implements Serializable {
    private String IDUser;
    private String UserName;
    private String EmailUser;
    private String Avatar;
    private String Firstname;
    private String Lastname;
    private String Birthday;
    private String Job;
    private String Adderss;
    private String Role;
    private String AuthType;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getAdderss() {
        return Adderss;
    }

    public void setAdderss(String adderss) {
        Adderss = adderss;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getAuthType() {
        return AuthType;
    }

    public void setAuthType(String authType) {
        AuthType = authType;
    }

    public User(String IDUser, String userName, String emailUser, String avatar, String firstname, String lastname, String birthday, String job, String adderss, String role, String authType) {
        this.IDUser = IDUser;
        UserName = userName;
        EmailUser = emailUser;
        Avatar = avatar;
        Firstname = firstname;
        Lastname = lastname;
        Birthday = birthday;
        Job = job;
        Adderss = adderss;
        Role = role;
        AuthType = authType;
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
