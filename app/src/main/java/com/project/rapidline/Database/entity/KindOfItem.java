package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_kindOfItem"
        ,foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class KindOfItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public int name;

    @ColumnInfo(name = "madeBy")
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

}
