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
    public String agentNumber;

    @ColumnInfo(name = "dealType")
    public String dealType;

    @ColumnInfo(name = "dealAmount")
    public Double dealAmount;

    @ColumnInfo(name = "madeBy",index = true)
    public int madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;



    public Agents(long id, int agentName, String agentNumber, String dealType, Double dealAmount, int madeBy, Date madeDateTime) {
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

    public int getAgentName() {
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
}
