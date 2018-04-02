package com.example.farhan.parkingbookingsystem.Models;

/**
 * Created by Farhan on 3/25/2018.
 */

public class CheckAvailability {
    private String bookedStartTime;
    private String bookedDndTime;
    private String bookedDate;
    private String bookedKey;
    private String slotKey;

    public CheckAvailability() {
    }

    public CheckAvailability(String bookedStartTime, String bookedDndTime, String bookedDate, String bookedKey, String slotKey) {
        this.bookedStartTime = bookedStartTime;
        this.bookedDndTime = bookedDndTime;
        this.bookedDate = bookedDate;
        this.bookedKey = bookedKey;
        this.slotKey = slotKey;
    }

    public String getBookedStartTime() {
        return bookedStartTime;
    }

    public void setBookedStartTime(String bookedStartTime) {
        this.bookedStartTime = bookedStartTime;
    }

    public String getBookedDndTime() {
        return bookedDndTime;
    }

    public void setBookedDndTime(String bookedDndTime) {
        this.bookedDndTime = bookedDndTime;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getBookedKey() {
        return bookedKey;
    }

    public void setBookedKey(String bookedKey) {
        this.bookedKey = bookedKey;
    }

    public String getSlotKey() {
        return slotKey;
    }

    public void setSlotKey(String slotKey) {
        this.slotKey = slotKey;
    }
}
