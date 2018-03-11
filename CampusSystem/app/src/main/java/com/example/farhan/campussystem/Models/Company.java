package com.example.farhan.campussystem.Models;

/**
 * Created by Farhan on 3/7/2018.
 */

public class Company {
    private String companyName;
    private String companyEmail;
    private String companyUid;

    public Company() {
    }

    public Company(String companyName, String companyEmail, String companyUid) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyUid='" + companyUid + '\'' +
                '}';
    }
}
