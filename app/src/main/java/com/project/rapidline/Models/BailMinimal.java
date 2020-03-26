package com.project.rapidline.Models;

import java.util.Date;

import androidx.room.ColumnInfo;

public class BailMinimal {

    @ColumnInfo(name = "id")
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @ColumnInfo(name = "biltyNo")
    private String biltyNo;

    @ColumnInfo(name = "madeDateTime")
    private Date time;

    @ColumnInfo(name = "sender")
    private String sendName;

    @ColumnInfo(name = "receiver")
    private String receiverName;

    @ColumnInfo(name = "agentName")
    private String name;

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
}
