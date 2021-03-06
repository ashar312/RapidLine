package com.project.rapidline.Models.SaeedSons;

import java.util.Date;
import java.util.HashMap;

import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

public class Bails {

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bailNo", this.getBailNo());
        map.put("fromCity", this.getFromCity());
        map.put("toCity", this.getToCity());
        map.put("kindId", this.getKindId());
        map.put("qty", this.getQuantity());
        map.put("senderId", this.getSenderId());
        map.put("receiverId", this.getReceiverId());

        map.put("transporterId", this.getTransporterId());
        map.put("agentId", this.getAgentId());
//        map.put("volume", this.getVolume());
//        map.put("weight", this.getWeight());
        map.put("madeBy", this.getMadeBy());
        map.put("madeDateTime", this.getMadeDateTime());
        map.put("transportCharge", this.getTransport_charge());
        map.put("labourCharge", this.getLabour_charge());
        map.put("electric_charge", this.getElectricity_charge());
        map.put("packingCharge", this.getPacking_charge());
        map.put("comments", this.getComments());
        map.put("shipped", this.isShipped());
        return map;
    }

    @ColumnInfo(name = "bailNo")
    public String bailNo;


    @ColumnInfo(name = "fromCity")
    public String fromCity;

    @ColumnInfo(name = "toCity")
    public String toCity;

    @ForeignKey(entity = KindOfItem.class, parentColumns = "id", childColumns = "kindId")
    @ColumnInfo(name = "kindId", index = true)
    public String kindId;

    @ColumnInfo(name = "qty")
    public int quantity;

    @ForeignKey(entity = Customers.class, parentColumns = "id", childColumns = "senderId")
    @ColumnInfo(name = "senderId", index = true)
    public String senderId;

    @ForeignKey(entity = Customers.class, parentColumns = "id", childColumns = "receiverId")
    @ColumnInfo(name = "receiverId", index = true)
    public String receiverId;

    @ForeignKey(entity = Transporters.class, parentColumns = "id", childColumns = "transporterId")
    @ColumnInfo(name = "transporterId", index = true)
    public String transporterId;

    @ForeignKey(entity = Agents.class, parentColumns = "id", childColumns = "agentId")
    @ColumnInfo(name = "agentId", index = true)
    public String agentId;


    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    public String transport_charge;
    public String labour_charge;
    public String electricity_charge;
    public String packing_charge;
    public String comments;

    public boolean shipped = false;

    @Ignore
    public Bails() {

    }

    public Bails(String bailNo, String fromCity, String toCity, String kindId, Double quantity, String senderId, String receiverId, String transporterId, String agentId, String madeBy, Date madeDateTime, String transport_charge, String labour_charge, String electricity_charge, String packing_charge, String comments, boolean shipped) {
        this.bailNo = bailNo;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.kindId = kindId;
        this.quantity = quantity.intValue();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transporterId = transporterId;
        this.agentId = agentId;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
        this.transport_charge = transport_charge;
        this.labour_charge = labour_charge;
        this.electricity_charge = electricity_charge;
        this.packing_charge = packing_charge;
        this.comments = comments;
        this.shipped = shipped;
    }

    public Bails(String bailNo, String senderId, String receiverId, String madeBy, Date madeDateTime, String fromCity, String toCity) {
        this.bailNo = bailNo;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public String getBailNo() {
        return bailNo;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public String getKindId() {
        return kindId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getTransporterId() {
        return transporterId;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    public void setBailNo(String bailNo) {
        this.bailNo = bailNo;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setTransporterId(String transporterId) {
        this.transporterId = transporterId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }


    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public void setMadeDateTime(Date madeDateTime) {
        this.madeDateTime = madeDateTime;
    }

    public String getTransport_charge() {
        return transport_charge;
    }

    public void setTransport_charge(String transport_charge) {
        this.transport_charge = transport_charge;
    }

    public String getLabour_charge() {
        return labour_charge;
    }

    public void setLabour_charge(String labour_charge) {
        this.labour_charge = labour_charge;
    }

    public String getElectricity_charge() {
        return electricity_charge;
    }

    public void setElectricity_charge(String electricity_charge) {
        this.electricity_charge = electricity_charge;
    }

    public String getPacking_charge() {
        return packing_charge;
    }

    public void setPacking_charge(String packing_charge) {
        this.packing_charge = packing_charge;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }
}
