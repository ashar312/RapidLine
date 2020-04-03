package com.project.rapidline.Database.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Customers {

    @ColumnInfo(name = "companyName")
    public String companyName;

    @ColumnInfo(name = "companyNo")
    public String companyNo;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "pocName")
    public String pocName;

    @ColumnInfo(name = "pocNo")
    public String pocNo;


    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public Customers(){

    }

    public Customers( String companyName, String companyNo, String  city, String address, String pocName, String pocNo, String madeBy, Date madeDateTime) {

        this.companyName = companyName;
        this.companyNo = companyNo;
        this.city = city;
        this.address = address;
        this.pocName = pocName;
        this.pocNo = pocNo;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }


    @Ignore
    public Customers( String companyName){
        this.companyName = companyName;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("companyNo",this.getCompanyNo());
        map.put("city",this.getCity());
        map.put("address",this.getAddress());
        map.put("pocName",this.getPocName());
        map.put("pocNo",this.getPocNo());
        map.put("madeBy",this.getMadeBy());
        map.put("madeDateTime",this.getMadeDateTime());
        return map;
    }


    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPocName() {
        return pocName;
    }

    public String getPocNo() {
        return pocNo;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public void setPocNo(String pocNo) {
        this.pocNo = pocNo;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }

    @NonNull
    @Override
    public String toString() {
        return this.companyName;
    }
}
