package com.example.farhan.campussystem.Models;

/**
 * Created by Farhan on 3/7/2018.
 */

public class Admin {
    private String adminName;
    private String adminEmail;
    private String adminUid;

    public Admin() {
    }

    public Admin(String adminName, String adminEmail, String adminUid) {
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminUid = adminUid;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminUid='" + adminUid + '\'' +
                '}';
    }
}
