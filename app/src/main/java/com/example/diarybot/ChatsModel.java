package com.example.diarybot;

public class ChatsModel {
    private String message;
    private String sender;
    long timeStamp;


    public ChatsModel(String message, String sender, long timeStamp) {
        this.message = message;
        this.sender = sender;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
