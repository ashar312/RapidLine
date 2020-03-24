package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_bails")
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

    @ForeignKey(entity = KindOfItem.class,parentColumns = "id",childColumns = "kindId")
    @ColumnInfo(name = "kindId",index = true)
    public int kindId;

    @ColumnInfo(name = "qty")
    public int quantity;

    @ForeignKey(entity = Customers.class,parentColumns = "id",childColumns = "senderId")
    @ColumnInfo(name = "senderId",index = true)
    public int senderId;

    @ForeignKey(entity = Customers.class,parentColumns = "id",childColumns = "receiverId")
    @ColumnInfo(name = "receiverId",index = true)
    public int receiverId;

    @ForeignKey(entity = Transporters.class,parentColumns = "id",childColumns = "transporterId")
    @ColumnInfo(name = "transporterId",index = true)
    public int transporterId;

    @ForeignKey(entity = Agents.class,parentColumns = "id",childColumns = "agentId")
    @ColumnInfo(name = "agentId",index = true)
    public int agentId;

    @ColumnInfo(name = "volume")
    public Double volume;

    @ColumnInfo(name = "weight")
    public Double weight;

    @ForeignKey(entity = Admins.class,parentColumns = "id",childColumns = "madeBy")
    @ColumnInfo(name = "madeBy",index = true)
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public Bails(){

    }

    public Bails(long id, String biltyNo, int fromCity, int toCity, int kindId, int quantity, int senderId, int receiverId, int transporterId, int agentId, Double volume, Double weight, int madeBy, Date madeDateTime) {
        this.id = id;
        this.biltyNo = biltyNo;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.kindId = kindId;
        this.quantity = quantity;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transporterId = transporterId;
        this.agentId = agentId;
        this.volume = volume;
        this.weight = weight;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public long getId() {
        return id;
    }

    public String getBiltyNo() {
        return biltyNo;
    }

    public int getFromCity() {
        return fromCity;
    }

    public int getToCity() {
        return toCity;
    }

    public int getKindId() {
        return kindId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public int getAgentId() {
        return agentId;
    }

    public Double getVolume() {
        return volume;
    }

    public Double getWeight() {
        return weight;
    }

    public int getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    public void setBiltyNo(String biltyNo) {
        this.biltyNo = biltyNo;
    }

    public void setFromCity(int fromCity) {
        this.fromCity = fromCity;
    }

    public void setToCity(int toCity) {
        this.toCity = toCity;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void setTransporterId(int transporterId) {
        this.transporterId = transporterId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setMadeBy(int madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }
}
