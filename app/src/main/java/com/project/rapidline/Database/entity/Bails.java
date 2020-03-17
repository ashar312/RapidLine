package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_bails"
        ,foreignKeys = @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy"))
public class Bails {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "biltyNo")
    public String biltyNo;

    @ColumnInfo(name = "fromCity")
    public int fromCity;

    @ColumnInfo(name = "toCity")
    public int toCity;

    @ColumnInfo(name = "kindId")
    @ForeignKey(entity = KindOfItem.class,parentColumns = "id",childColumns = "kindId")
    public int kindId;

    @ColumnInfo(name = "qty")
    public int quantity;

    @ForeignKey(entity = Customers.class,parentColumns = "id",childColumns = "senderId")
    @ColumnInfo(name = "senderId")
    public int senderId;

    @ForeignKey(entity = Customers.class,parentColumns = "id",childColumns = "receiverId")
    @ColumnInfo(name = "receiverId")
    public int receiverId;

    @ForeignKey(entity = Transporters.class,parentColumns = "id",childColumns = "transporterId")
    @ColumnInfo(name = "transporterId")
    public int mTransporterId;

    @ForeignKey(entity = Agents.class,parentColumns = "id",childColumns = "agentId")
    @ColumnInfo(name = "agentId")
    public int mAgentId;

    @ColumnInfo(name = "volume")
    public int mVolume;

    @ColumnInfo(name = "weight")
    public int mWeight;

    @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy")
    @ColumnInfo(name = "madeBy")
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

}
