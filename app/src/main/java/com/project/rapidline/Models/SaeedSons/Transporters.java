package com.project.rapidline.Models.SaeedSons;

import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class Transporters {

    @ColumnInfo(name = "companyName")
    public String companyName;

    @ColumnInfo(name = "companyNo")
    public String companyNo;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "pocName")
    public String pocName;

    @ColumnInfo(name = "pocNo")
    public String pocNo;

    @ColumnInfo(name = "madeBy")
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    public Transporters(){

    }


    public Transporters(String companyName){
        this.companyName=companyName;
    }

    public Transporters(String companyName, String companyNo, String city, String pocName, String pocNo, String madeBy, Date madeDateTime) {
        this.companyName = companyName;
        this.companyNo = companyNo;
        this.city = city;
        this.pocName = pocName;
        this.pocNo = pocNo;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("companyNo",this.getCompanyNo());
        map.put("city",this.getCity());
        map.put("pocName",this.getPocName());
        map.put("pocNo",this.getPocNo());
        map.put("madeBy",this.getMadeBy());
        map.put("madeDateTime",this.getMadeDateTime());
        return map;
    }


    @Ignore
    public Transporters(String companyName,String madeBy){
        this.companyName = companyName;
        this.madeBy = madeBy;
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

