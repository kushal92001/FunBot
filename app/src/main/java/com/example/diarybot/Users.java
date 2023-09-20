package com.example.diarybot;

public class Users {
    String mail,userName,password,userId;
    public Users(){}

    public Users(String mail, String userName, String password, String userId) {
        this.mail = mail;
        this.userName = userName;
        this.password = password;
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
