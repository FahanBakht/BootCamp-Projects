package com.example.farhan.campussystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Farhan on 3/7/2018.
 */

public class CompanyProfile implements Parcelable {
    private String companyProfileImage;
    private String companyName;
    private String companyAboutUs;
    private String companyContact;
    private String companyEmail;
    private String companyLocation;
    private String companyProfileUID;
    private String companyProfileImageUri;

    public CompanyProfile() {
    }

    public CompanyProfile(String companyProfileImage, String companyName, String companyAboutUs, String companyContact, String companyEmail, String companyLocation, String companyProfileUID, String companyProfileImageUri) {
        this.companyProfileImage = companyProfileImage;
        this.companyName = companyName;
        this.companyAboutUs = companyAboutUs;
        this.companyContact = companyContact;
        this.companyEmail = companyEmail;
        this.companyLocation = companyLocation;
        this.companyProfileUID = companyProfileUID;
        this.companyProfileImageUri = companyProfileImageUri;
    }

    protected CompanyProfile(Parcel in) {
        companyProfileImage = in.readString();
        companyName = in.readString();
        companyAboutUs = in.readString();
        companyContact = in.readString();
        companyEmail = in.readString();
        companyLocation = in.readString();
        companyProfileUID = in.readString();
        companyProfileImageUri = in.readString();
    }

    public static final Creator<CompanyProfile> CREATOR = new Creator<CompanyProfile>() {
        @Override
        public CompanyProfile createFromParcel(Parcel in) {
            return new CompanyProfile(in);
        }

        @Override
        public CompanyProfile[] newArray(int size) {
            return new CompanyProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(companyProfileImage);
        parcel.writeString(companyName);
        parcel.writeString(companyAboutUs);
        parcel.writeString(companyContact);
        parcel.writeString(companyEmail);
        parcel.writeString(companyLocation);
        parcel.writeString(companyProfileUID);
        parcel.writeString(companyProfileImageUri);
    }

    public String getCompanyProfileImage() {
        return companyProfileImage;
    }

    public void setCompanyProfileImage(String companyProfileImage) {
        this.companyProfileImage = companyProfileImage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAboutUs() {
        return companyAboutUs;
    }

    public void setCompanyAboutUs(String companyAboutUs) {
        this.companyAboutUs = companyAboutUs;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyProfileUID() {
        return companyProfileUID;
    }

    public void setCompanyProfileUID(String companyProfileUID) {
        this.companyProfileUID = companyProfileUID;
    }

    public String getCompanyProfileImageUri() {
        return companyProfileImageUri;
    }

    public void setCompanyProfileImageUri(String companyProfileImageUri) {
        this.companyProfileImageUri = companyProfileImageUri;
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
                "companyProfileImage='" + companyProfileImage + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAboutUs='" + companyAboutUs + '\'' +
                ", companyContact='" + companyContact + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                ", companyProfileUID='" + companyProfileUID + '\'' +
                ", companyProfileImageUri='" + companyProfileImageUri + '\'' +
                '}';
    }
}
