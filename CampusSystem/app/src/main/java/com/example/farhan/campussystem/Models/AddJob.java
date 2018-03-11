package com.example.farhan.campussystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Farhan on 3/8/2018.
 */

public class AddJob implements Parcelable {
    private String addJobRole;
    private String addJobType;
    private String addJobExperience;
    private String addJobSalary;
    private String addJobCompanySize;
    private String addJobCompanyType;
    private String addJobIndustry;
    private String addJobDescription;
    private String companyUid;
    private String jobUid;

    public AddJob() {
    }

    public AddJob(String addJobRole, String addJobType, String addJobExperience, String addJobSalary, String addJobCompanySize, String addJobCompanyType, String addJobIndustry, String addJobDescription, String companyUid, String jobUid) {
        this.addJobRole = addJobRole;
        this.addJobType = addJobType;
        this.addJobExperience = addJobExperience;
        this.addJobSalary = addJobSalary;
        this.addJobCompanySize = addJobCompanySize;
        this.addJobCompanyType = addJobCompanyType;
        this.addJobIndustry = addJobIndustry;
        this.addJobDescription = addJobDescription;
        this.companyUid = companyUid;
        this.jobUid = jobUid;
    }

    protected AddJob(Parcel in) {
        addJobRole = in.readString();
        addJobType = in.readString();
        addJobExperience = in.readString();
        addJobSalary = in.readString();
        addJobCompanySize = in.readString();
        addJobCompanyType = in.readString();
        addJobIndustry = in.readString();
        addJobDescription = in.readString();
        companyUid = in.readString();
        jobUid = in.readString();
    }

    public static final Creator<AddJob> CREATOR = new Creator<AddJob>() {
        @Override
        public AddJob createFromParcel(Parcel in) {
            return new AddJob(in);
        }

        @Override
        public AddJob[] newArray(int size) {
            return new AddJob[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(addJobRole);
        parcel.writeString(addJobType);
        parcel.writeString(addJobExperience);
        parcel.writeString(addJobSalary);
        parcel.writeString(addJobCompanySize);
        parcel.writeString(addJobCompanyType);
        parcel.writeString(addJobIndustry);
        parcel.writeString(addJobDescription);
        parcel.writeString(companyUid);
        parcel.writeString(jobUid);
    }

    public String getAddJobRole() {
        return addJobRole;
    }

    public void setAddJobRole(String addJobRole) {
        this.addJobRole = addJobRole;
    }

    public String getAddJobType() {
        return addJobType;
    }

    public void setAddJobType(String addJobType) {
        this.addJobType = addJobType;
    }

    public String getAddJobExperience() {
        return addJobExperience;
    }

    public void setAddJobExperience(String addJobExperience) {
        this.addJobExperience = addJobExperience;
    }

    public String getAddJobSalary() {
        return addJobSalary;
    }

    public void setAddJobSalary(String addJobSalary) {
        this.addJobSalary = addJobSalary;
    }

    public String getAddJobCompanySize() {
        return addJobCompanySize;
    }

    public void setAddJobCompanySize(String addJobCompanySize) {
        this.addJobCompanySize = addJobCompanySize;
    }

    public String getAddJobCompanyType() {
        return addJobCompanyType;
    }

    public void setAddJobCompanyType(String addJobCompanyType) {
        this.addJobCompanyType = addJobCompanyType;
    }

    public String getAddJobIndustry() {
        return addJobIndustry;
    }

    public void setAddJobIndustry(String addJobIndustry) {
        this.addJobIndustry = addJobIndustry;
    }

    public String getAddJobDescription() {
        return addJobDescription;
    }

    public void setAddJobDescription(String addJobDescription) {
        this.addJobDescription = addJobDescription;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getJobUid() {
        return jobUid;
    }

    public void setJobUid(String jobUid) {
        this.jobUid = jobUid;
    }

    @Override
    public String toString() {
        return "AddJob{" +
                "addJobRole='" + addJobRole + '\'' +
                ", addJobType='" + addJobType + '\'' +
                ", addJobExperience='" + addJobExperience + '\'' +
                ", addJobSalary='" + addJobSalary + '\'' +
                ", addJobCompanySize='" + addJobCompanySize + '\'' +
                ", addJobCompanyType='" + addJobCompanyType + '\'' +
                ", addJobIndustry='" + addJobIndustry + '\'' +
                ", addJobDescription='" + addJobDescription + '\'' +
                ", companyUid='" + companyUid + '\'' +
                ", jobUid='" + jobUid + '\'' +
                '}';
    }
}
