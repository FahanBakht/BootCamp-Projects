package com.example.farhan.parkingbookingsystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Farhan on 3/20/2018.
 */

public class Slot implements Parcelable {
    private String slotName;
    private String id;
    private String mallKey;
    private String parkingAreaKey;
    private CheckAvailability checkAvailability;

    public Slot() {
    }

    public Slot(String slotName, String id, String mallKey, String parkingAreaKey) {
        this.slotName = slotName;
        this.id = id;
        this.mallKey = mallKey;
        this.parkingAreaKey = parkingAreaKey;
    }

    protected Slot(Parcel in) {
        slotName = in.readString();
        id = in.readString();
        mallKey = in.readString();
        parkingAreaKey = in.readString();
    }

    public static final Creator<Slot> CREATOR = new Creator<Slot>() {
        @Override
        public Slot createFromParcel(Parcel in) {
            return new Slot(in);
        }

        @Override
        public Slot[] newArray(int size) {
            return new Slot[size];
        }
    };

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CheckAvailability getCheckAvailability() {
        return checkAvailability;
    }

    public void setCheckAvailability(CheckAvailability checkAvailability) {
        this.checkAvailability = checkAvailability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(slotName);
        parcel.writeString(id);
        parcel.writeString(mallKey);
        parcel.writeString(parkingAreaKey);
    }
}
