package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_kindOfItem"
        ,foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class KindOfItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "madeBy",index = true)
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public KindOfItem(){

    }

    public KindOfItem(long id, String name, int madeBy, Date madeDateTime) {
        this.id = id;
        this.name = name;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }
    @Ignore
    public KindOfItem(long id, String name, int madeBy) {
        this.id = id;
        this.name = name;
        this.madeBy = madeBy;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

}
