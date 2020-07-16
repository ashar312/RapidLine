package com.project.rapidline.Models.RapidLine;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class Drivers {

    private String driverName;

    private String driverNo;

    private String driverNic;

    private String key;

    public Drivers(){

    }

    public Drivers(String driverName, String driverNo, String driverNic) {
        this.driverName = driverName;
        this.driverNo = driverNo;
        this.driverNic = driverNic;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverNo() {
        return driverNo;
    }

    public String getDriverNic() {
        return driverNic;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
    }

    public void setDriverNic(String driverNic) {
        this.driverNic = driverNic;
    }


    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return this.driverName;
    }
}
