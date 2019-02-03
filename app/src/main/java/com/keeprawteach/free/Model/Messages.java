package com.keeprawteach.free.Model;

public class Messages {
    String title,message;

    public Messages(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Messages() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
