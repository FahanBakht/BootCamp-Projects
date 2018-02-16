package com.example.farhan.rcswipetodelete;

/**
 * Created by Farhan on 2/16/2018.
 */

public class dataModel {
    private String fruitsName;

    public dataModel(String fruitsName) {
        this.fruitsName = fruitsName;
    }

    public String getFruitsName() {
        return fruitsName;
    }

    public void setFruitsName(String fruitsName) {
        this.fruitsName = fruitsName;
    }

    @Override
    public String toString() {
        return "dataModel{" +
                "fruitsName='" + fruitsName + '\'' +
                '}';
    }
}
