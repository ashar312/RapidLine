package com.project.rapidline.Database.entity;

import java.util.Date;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_customers",
foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class Customers {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "companyName")
    public String companyName;

    @ColumnInfo(name = "companyNo")
    public String companyNo;

    @ColumnInfo(name = "city")
    public int city;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "pocName")
    public String pocName;

    @ColumnInfo(name = "pocNo")
    public String pocNo;

    @ColumnInfo(name = "madeBy",index = true)
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public Customers(){

    }

    public Customers(long id, String companyName, String companyNo, int city, String address, String pocName, String pocNo, int madeBy, Date madeDateTime) {
        this.id = id;
        this.companyName = companyName;
        this.companyNo = companyNo;
        this.city = city;
        this.address = address;
        this.pocName = pocName;
        this.pocNo = pocNo;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public int getCity() {
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

    public int getMadeBy() {
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

    public void setCity(int city) {
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

    public void setMadeBy(int madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }
}
