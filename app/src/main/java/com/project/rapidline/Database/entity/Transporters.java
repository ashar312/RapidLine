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
    public int pocNo;

    @ColumnInfo(name = "madeBy")
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

}
