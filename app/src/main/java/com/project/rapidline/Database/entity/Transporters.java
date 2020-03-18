package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_transporters"
        ,foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class Transporters {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "companyName")
    public String companyName;

    @ColumnInfo(name = "companyNo")
    public String companyNo;

    @ColumnInfo(name = "city")
    public int city;

    @ColumnInfo(name = "pocName")
    public String pocName;

    @ColumnInfo(name = "pocNo")
    public String pocNo;

    @ColumnInfo(name = "madeBy",index = true)
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    public Transporters(long id, String companyName, String companyNo, int city, String pocName, String pocNo, int madeBy, Date madeDateTime) {
        this.id = id;
        this.companyName = companyName;
        this.companyNo = companyNo;
        this.city = city;
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
}

