package com.project.rapidline.Models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.HashMap;

public class Admins {


    public String adminName;

    public String username;

    public String pass;

    public String contactNo;

    public String nic;

    public String companyName;

    private String key;

    public String madeBy;

    public Date madeDateTime;

    public String bailCounter;

    public String biltyCounter;

    public String shipmentCounter;

    public Admins() {

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

    public Admins(String bailCounter, String biltyCounter,String shipmentCounter) {
        this.bailCounter = bailCounter;
        this.biltyCounter = biltyCounter;
        this.shipmentCounter=shipmentCounter;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", this.getUsername());
        map.put("adminName", this.getAdminName());
        map.put("pass", this.getPass());
        map.put("contactNo", this.getContactNo());
        map.put("nic", this.getNic());
        map.put("companyName", this.getCompanyName());
        map.put("madeBy", this.getMadeBy());
        map.put("madeDateTime", this.getMadeDateTime());

        map.put("bailCounter", this.getBailCounter());
        map.put("biltyCounter", this.getBiltyCounter());
        map.put("shipmentCounter",this.getShipmentCounter());
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

    public String getBailCounter() {
        return bailCounter;
    }

    public void setBailCounter(String bailCounter) {
        this.bailCounter = bailCounter;
    }


    public String getBiltyCounter() {
        return biltyCounter;
    }

    public void setBiltyCounter(String biltyCounter) {
        this.biltyCounter = biltyCounter;
    }

    public String getShipmentCounter() {
        return shipmentCounter;
    }

    public void setShipmentCounter(String shipmentCounter) {
        this.shipmentCounter = shipmentCounter;
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
