package com.example.farhan.parkingbookingsystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Farhan on 3/21/2018.
 */

public class Booking implements Parcelable {
    private String slotKey;
    private String slotName;
    private String mallKey;
    private String parkingAreaKey;

    private String userName;
    private String userEmail;
    private String userCnicNumber;
    private String userCarName;
    private String userCarLicenseNumber;
    private String userUid;

    private String userPickTime;
    private String userPickDate;

    private String bookingKey;
    private String selectedHours;

    public Booking() {
    }

    public Booking(String slotKey, String slotName, String mallKey, String parkingAreaKey, String userName, String userEmail, String userCnicNumber, String userCarName, String userCarLicenseNumber, String userUid, String userPickTime, String userPickDate, String bookingKey, String selectedHours) {
        this.slotKey = slotKey;
        this.slotName = slotName;
        this.mallKey = mallKey;
        this.parkingAreaKey = parkingAreaKey;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCnicNumber = userCnicNumber;
        this.userCarName = userCarName;
        this.userCarLicenseNumber = userCarLicenseNumber;
        this.userUid = userUid;
        this.userPickTime = userPickTime;
        this.userPickDate = userPickDate;
        this.bookingKey = bookingKey;
        this.selectedHours = selectedHours;
    }

    protected Booking(Parcel in) {
        slotKey = in.readString();
        slotName = in.readString();
        mallKey = in.readString();
        parkingAreaKey = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userCnicNumber = in.readString();
        userCarName = in.readString();
        userCarLicenseNumber = in.readString();
        userUid = in.readString();
        userPickTime = in.readString();
        userPickDate = in.readString();
        bookingKey = in.readString();
        selectedHours = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public String getSlotKey() {
        return slotKey;
    }

    public void setSlotKey(String slotKey) {
        this.slotKey = slotKey;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getMallKey() {
        return mallKey;
    }

    public void setMallKey(String mallKey) {
        this.mallKey = mallKey;
    }

    public String getParkingAreaKey() {
        return parkingAreaKey;
    }

    public void setParkingAreaKey(String parkingAreaKey) {
        this.parkingAreaKey = parkingAreaKey;
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

    public String getUserCarLicenseNumber() {
        return userCarLicenseNumber;
    }

    public void setUserCarLicenseNumber(String userCarLicenseNumber) {
        this.userCarLicenseNumber = userCarLicenseNumber;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserPickTime() {
        return userPickTime;
    }

    public void setUserPickTime(String userPickTime) {
        this.userPickTime = userPickTime;
    }

    public String getUserPickDate() {
        return userPickDate;
    }

    public void setUserPickDate(String userPickDate) {
        this.userPickDate = userPickDate;
    }

    public String getBookingKey() {
        return bookingKey;
    }

    public void setBookingKey(String bookingKey) {
        this.bookingKey = bookingKey;
    }

    public String getSelectedHours() {
        return selectedHours;
    }

    public void setSelectedHours(String selectedHours) {
        this.selectedHours = selectedHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(slotKey);
        parcel.writeString(slotName);
        parcel.writeString(mallKey);
        parcel.writeString(parkingAreaKey);
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userCnicNumber);
        parcel.writeString(userCarName);
        parcel.writeString(userCarLicenseNumber);
        parcel.writeString(userUid);
        parcel.writeString(userPickTime);
        parcel.writeString(userPickDate);
        parcel.writeString(bookingKey);
        parcel.writeString(selectedHours);
    }
}
