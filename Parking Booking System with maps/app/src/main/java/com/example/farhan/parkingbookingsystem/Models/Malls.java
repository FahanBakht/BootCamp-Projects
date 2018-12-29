package com.example.farhan.parkingbookingsystem.Models;

/**
 * Created by Farhan on 3/20/2018.
 */

public class Malls {
    private String mallImage;
    private String mallName;

    public Malls() {
    }

    public Malls(String mallImage, String mallName) {
        this.mallImage = mallImage;
        this.mallName = mallName;
    }

    public String getMallImage() {
        return mallImage;
    }

    public void setMallImage(String mallImage) {
        this.mallImage = mallImage;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }
}
