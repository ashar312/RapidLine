package com.project.rapidline.Models;

import java.util.Date;
import androidx.room.ColumnInfo;

public class BailMinimal {

    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "biltyNo")
    private String biltyNo;

    @ColumnInfo(name = "madeDateTime")
    private Date time;

    @ColumnInfo(name = "fromCity")
    public String fromCity;

    @ColumnInfo(name = "toCity")
    public String toCity;

    @ColumnInfo(name = "qty")
    public int quantity;

    @ColumnInfo(name = "volume")
    public Double volume;

    @ColumnInfo(name = "weight")
    public Double weight;



    @ColumnInfo(name = "sender")
    private String sendName;

    @ColumnInfo(name = "receiver")
    private String receiverName;

    @ColumnInfo(name = "agentName")
    private String name;


    @ColumnInfo(name = "transporter")
    private String transporterName;

    @ColumnInfo(name = "itemName")
    private String itemName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiltyNo() {
        return biltyNo;
    }

    public void setBiltyNo(String biltyNo) {
        this.biltyNo = biltyNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }



    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
