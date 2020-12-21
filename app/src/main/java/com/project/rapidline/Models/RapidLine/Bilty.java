package com.project.rapidline.Models.RapidLine;
import java.util.Date;
import androidx.room.Ignore;

public class Bilty {

    public String biltyNo;

    public String fromCity;

    public String toCity;

    public String kindId;

    public int qty;

    public String senderId;

    public String receiverId;

    public String supplierName;

    public String supplierPNo;

    public String agentId;

    public Double volume;

    public Double weight;

    public String madeBy;

    public Date madeDateTime;

    public String transport_charge;
    public String labour_charge;
    public String electricity_charge;
    public String packing_charge;
    public String comments;
    public boolean shipped = false;

    @Ignore
    public Bilty() {

    }

    public Bilty(String biltyNo, String fromCity, String toCity, String kindId, Double qty, String senderId, String receiverId, String supplierName, String supplierPNo, String agentId, Double volume, Double weight, String madeBy, Date madeDateTime, String transport_charge, String labour_charge, String electricity_charge, String packing_charge, String comments,boolean shipped) {
        this.biltyNo = biltyNo;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.kindId = kindId;
        this.qty = qty.intValue();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.supplierName = supplierName;
        this.supplierPNo = supplierPNo;
        this.agentId = agentId;
        this.volume = volume;
        this.weight = weight;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
        this.transport_charge = transport_charge;
        this.labour_charge = labour_charge;
        this.electricity_charge = electricity_charge;
        this.packing_charge = packing_charge;
        this.comments = comments;
        this.shipped=shipped;
    }

    public Bilty(String biltyNo, String fromCity, String toCity, String kindId, int qty, String senderId, String receiverId, String supplierName, String supplierPNo, String agentId, Double volume, Double weight, String madeBy, Date madeDateTime, String transport_charge, String labour_charge, String electricity_charge, String packing_charge, String comments) {
        this.biltyNo = biltyNo;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.kindId = kindId;
        this.qty = qty;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.supplierName = supplierName;
        this.supplierPNo = supplierPNo;
        this.agentId = agentId;
        this.volume = volume;
        this.weight = weight;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
        this.transport_charge = transport_charge;
        this.labour_charge = labour_charge;
        this.electricity_charge = electricity_charge;
        this.packing_charge = packing_charge;
        this.comments = comments;
    }

    public String getBiltyNo() {
        return biltyNo;
    }

    public void setBiltyNo(String biltyNo) {
        this.biltyNo = biltyNo;
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

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPNo() {
        return supplierPNo;
    }

    public void setSupplierPNo(String supplierPNo) {
        this.supplierPNo = supplierPNo;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
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
