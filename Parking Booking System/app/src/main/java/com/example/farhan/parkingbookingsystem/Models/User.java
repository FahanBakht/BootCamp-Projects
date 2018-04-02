package com.example.farhan.parkingbookingsystem.Models;

/**
 * Created by Farhan on 3/20/2018.
 */

public class User {
    private String userName;
    private String userEmail;
    private String userCnicNumber;
    private String userCarName;
    private String userCarLicensePlateNumber;
    private String userUid;

    public User() {
    }

    public User(String userName, String userEmail, String userCnicNumber, String userCarName, String userCarLicensePlateNumber, String userUid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCnicNumber = userCnicNumber;
        this.userCarName = userCarName;
        this.userCarLicensePlateNumber = userCarLicensePlateNumber;
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getUserCnicNumber() {
        return userCnicNumber;
    }

    public void setUserCnicNumber(String userCnicNumber) {
        this.userCnicNumber = userCnicNumber;
    }

    public String getUserCarName() {
        return userCarName;
    }

    public void setUserCarName(String userCarName) {
        this.userCarName = userCarName;
    }

    public String getUserCarLicensePlateNumber() {
        return userCarLicensePlateNumber;
    }

    public void setUserCarLicensePlateNumber(String userCarLicensePlateNumber) {
        this.userCarLicensePlateNumber = userCarLicensePlateNumber;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
