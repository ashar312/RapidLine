package com.project.rapidline.Database.entity;

import java.util.Date;
import java.util.HashMap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Admins {


    public String adminName;

    public String username;

    public String pass;

    public String contactNo;

    public String nic;

    public String companyName;

    @ColumnInfo(name = "madeBy")
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

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
}
