package com.project.rapidline.Models.RapidLine;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Shipment implements Parcelable {
    private String driverName;
    private String sidekickName;
    private String vechileNo;
    private List<String> stops;
    private List<String> stopsArrivalDate;
    private String shipmentExpiry;
    private List<String> bails;
    private List<String> biltys;
    private int totalBiltys;
    private Double totalWeight;
    private String shipmentNo;

    public Shipment() {

    }


    protected Shipment(Parcel in) {
        driverName = in.readString();
        sidekickName = in.readString();
        vechileNo = in.readString();
        stops = in.createStringArrayList();
        stopsArrivalDate = in.createStringArrayList();
        shipmentExpiry=in.readString();
        bails=in.createStringArrayList();
        biltys=in.createStringArrayList();
        totalBiltys=in.readInt();
        totalWeight=in.readDouble();
        shipmentNo=in.readString();
    }

    public static final Creator<Shipment> CREATOR = new Creator<Shipment>() {
        @Override
        public Shipment createFromParcel(Parcel in) {
            return new Shipment(in);
        }

        @Override
        public Shipment[] newArray(int size) {
            return new Shipment[size];
        }
    };


    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSidekickName() {
        return sidekickName;
    }

    public void setSidekickName(String sidekickName) {
        this.sidekickName = sidekickName;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    public List<String> getStopsArrivalDate() {
        return stopsArrivalDate;
    }

    public void setStopsArrivalDate(List<String> stopsArrivalDate) {
        this.stopsArrivalDate = stopsArrivalDate;
    }

    public String getVechileNo() {
        return vechileNo;
    }

    public void setVechileNo(String vechileNo) {
        this.vechileNo = vechileNo;
    }

    public void setShipmentExpiry(String shipmentExpiry) {
        this.shipmentExpiry = shipmentExpiry;
    }

    public String getShipmentExpiry() {
        return shipmentExpiry;
    }


    public List<String> getBails() {
        return bails;
    }

    public void setBails(List<String> bails) {
        this.bails = bails;
    }

    public List<String> getBiltys() {
        return biltys;
    }

    public void setBiltys(List<String> biltys) {
        this.biltys = biltys;
    }

    public int getTotalBiltys() {
        return totalBiltys;
    }

    public void setTotalBiltys(int totalBiltys) {
        this.totalBiltys = totalBiltys;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(driverName);
        parcel.writeString(sidekickName);
        parcel.writeString(vechileNo);
        parcel.writeString(shipmentExpiry);
        parcel.writeStringList(stops);
        parcel.writeStringList(stopsArrivalDate);
        parcel.writeStringList(bails);
        parcel.writeStringList(biltys);
        parcel.writeInt(totalBiltys);
        parcel.writeDouble(totalWeight);
        parcel.writeString(shipmentNo);
    }
}
