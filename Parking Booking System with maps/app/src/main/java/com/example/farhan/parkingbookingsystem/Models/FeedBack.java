package com.example.farhan.parkingbookingsystem.Models;

/**
 * Created by Farhan on 3/21/2018.
 */

public class FeedBack {

    private String feedBack;
    private String key;
    private String userName;
    private String date;
    private String adminReply;

    public FeedBack() {
    }

    public FeedBack(String feedBack, String key, String userName, String date, String adminReply) {
        this.feedBack = feedBack;
        this.key = key;
        this.userName = userName;
        this.date = date;
        this.adminReply = adminReply;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }
}
