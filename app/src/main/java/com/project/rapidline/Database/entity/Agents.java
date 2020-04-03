package com.project.rapidline.Database.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Agents {

    @ColumnInfo(name = "name")
    public String agentName;

    @ColumnInfo(name = "number")
    public String agentNumber;

    @ColumnInfo(name = "dealType")
    public String dealType;

    @ColumnInfo(name = "dealAmount")
    public Double dealAmount;

    @ColumnInfo(name = "madeBy", index = true)
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    public Agents() {

    }

    public Agents(String agentName, String agentNumber, String dealType, Double dealAmount, String madeBy, Date madeDateTime) {

        this.agentName = agentName;
        this.agentNumber = agentNumber;
        this.dealType = dealType;
        this.dealAmount = dealAmount;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }



    @Ignore
    public Agents(String agentName) {

        this.agentName = agentName;
        this.madeBy = madeBy;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String, Object> agentMap = new HashMap<>();
        agentMap.put("agentNumber", this.getAgentNumber());
        agentMap.put("dealType", this.getDealType());
        agentMap.put("dealAmount", this.getDealAmount());
        agentMap.put("madeBy", this.madeBy);
        agentMap.put("madeDateTime", this.getMadeDateTime());
       return agentMap;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public String getDealType() {
        return dealType;
    }

    public Double getDealAmount() {
        return dealAmount;
    }

    public String  getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }

    @NonNull
    @Override
    public String toString() {
        return this.agentName;
    }
}
