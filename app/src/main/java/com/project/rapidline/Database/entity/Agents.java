package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_agents",foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class Agents {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public int agentName;

    @ColumnInfo(name = "number")
    public int agentNumber;

    @ColumnInfo(name = "dealType")
    public int dealType;

    @ColumnInfo(name = "dealAmount")
    public long dealAmount;

    @ColumnInfo(name = "madeBy")
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;
}
