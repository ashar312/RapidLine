package com.project.rapidline.Models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.HashMap;

import androidx.room.ColumnInfo;

public class Admins {


    public String adminName;

    public String username;

    public String pass;

    public String contactNo;

    public String nic;

    public String companyName;

    private String key;

    @ColumnInfo(name = "madeBy")
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    public String bailSymbol;
    public int bailCounter;

    public int builtyRange;
    public int builtyCounter;

    public Admins(){

    }

    public Admins(String adminName, String username, String pass, String contactNo, String nic, String companyName, String madeBy, Date madeDateTime) {
        this.adminName = adminName;
        this.username = username;
        this.pass = pass;
        this.contactNo = contactNo;
        this.nic = nic;
        this.companyName = companyName;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public Admins(String bailSymbol, Double bailCounter, Double builtyRange, Double builtyCounter) {
        this.bailSymbol = bailSymbol;
        this.bailCounter = bailCounter.intValue();
        this.builtyRange = builtyRange.intValue();
        this.builtyCounter = builtyCounter.intValue();
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",this.getUsername());
        map.put("adminName",this.getAdminName());
        map.put("pass",this.getPass());
        map.put("contactNo",this.getContactNo());
        map.put("nic",this.getNic());
        map.put("companyName",this.getCompanyName());
        map.put("madeBy", this.getMadeBy());
        map.put("madeDateTime", this.getMadeDateTime());

        map.put("bailSymbol",this.getBailSymbol());
        map.put("bailCounter",this.getBailCounter());
        map.put("builtyRange",this.getBuiltyRange());
        map.put("builtyCounter",this.getBailCounter());

        return map;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }

    public String getBailSymbol() {
        return bailSymbol;
    }

    public void setBailSymbol(String bailSymbol) {
        this.bailSymbol = bailSymbol;
    }

    public int getBailCounter() {
        return bailCounter;
    }

    public void setBailCounter(int bailCounter) {
        this.bailCounter = bailCounter;
    }

    public int getBuiltyRange() {
        return builtyRange;
    }

    public void setBuiltyRange(int builtyRange) {
        this.builtyRange = builtyRange;
    }

    public int getBuiltyCounter() {
        return builtyCounter;
    }

    public void setBuiltyCounter(int builtyCounter) {
        this.builtyCounter = builtyCounter;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
