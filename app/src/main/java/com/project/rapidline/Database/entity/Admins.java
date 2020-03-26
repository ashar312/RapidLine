package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_admins")
public class Admins {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "adminName")
    public String adminName;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String pass;

    @ColumnInfo(name = "number")
    public String contactNo;

    @ColumnInfo(name = "nic")
    public String nic;

    @ColumnInfo(name = "madeBy")
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public Admins(){
    }

    public Admins(long id, String adminName, String username, String pass, String contactNo, String nic, String madeBy, Date madeDateTime) {
        this.id = id;
        this.adminName = adminName;
        this.username = username;
        this.pass = pass;
        this.contactNo = contactNo;
        this.nic = nic;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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
