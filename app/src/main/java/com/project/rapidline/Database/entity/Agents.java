package com.project.rapidline.Database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_agents", foreignKeys = @ForeignKey(entity = Admins.class, parentColumns = "id", childColumns = "madeBy"))
public class Agents {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String agentName;

    @ColumnInfo(name = "number")
    public String agentNumber;

    @ColumnInfo(name = "dealType")
    public String dealType;

    @ColumnInfo(name = "dealAmount")
    public Double dealAmount;

    @ColumnInfo(name = "madeBy", index = true)
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    @Ignore
    public Agents() {

    }

    public Agents(long id, String agentName, String agentNumber, String dealType, Double dealAmount, int madeBy, Date madeDateTime) {
        this.id = id;
        this.agentName = agentName;
        this.agentNumber = agentNumber;
        this.dealType = dealType;
        this.dealAmount = dealAmount;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    public long getId() {
        return id;
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

    public int getMadeBy() {
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

    public void setMadeBy(int madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }

}
