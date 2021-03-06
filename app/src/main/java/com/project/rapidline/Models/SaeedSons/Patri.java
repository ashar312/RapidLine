package com.project.rapidline.Models.SaeedSons;

import com.project.rapidline.Models.Admins;

import java.util.Date;
import java.util.HashMap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(tableName = "tbl_patri",
        foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class Patri {
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "nic")
    public String nic;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNo;

    @ColumnInfo(name = "madeBy",index = true)
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public Patri() {
    }

    public Patri(String name, String nic, String phoneNo, String madeBy, Date madeDateTime) {
        this.name = name;
        this.nic = nic;
        this.phoneNo = phoneNo;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("nic",this.getNic());
        map.put("phoneNumber",this.getPhoneNo());
        map.put("madeBy",this.getMadeBy());
        map.put("madeDateTime",this.getMadeDateTime());
        return map;
    }
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }

    public String getName() {
        return name;
    }

    public String getNic() {
        return nic;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }
}
