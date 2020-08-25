package com.project.rapidline.Models.RapidLine;

import com.google.firebase.firestore.Exclude;

import androidx.annotation.NonNull;

import java.util.Date;

public class Vechile {

    private String regNo;

    private String chasisNo;

    private String vechileCompany;

    private String vechileCategory;

    private String model;

    private int capacity;

    private String key;

    private int fitnessPercentage;

    private Date lastTestTaken;

    public Vechile() {

    }

    public Vechile(String regNo, String chasisNo, String vechileCompany, String vechileCategory, String model, int capacity, int fitnessPercentage, Date lastTestTaken) {
        this.regNo = regNo;
        this.chasisNo = chasisNo;
        this.vechileCompany = vechileCompany;
        this.vechileCategory = vechileCategory;
        this.model = model;
        this.capacity = capacity;
        this.fitnessPercentage = fitnessPercentage;
        this.lastTestTaken = lastTestTaken;
    }

    public Vechile(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getChasisNo() {
        return chasisNo;
    }

    public void setChasisNo(String chasisNo) {
        this.chasisNo = chasisNo;
    }

    public String getVechileCompany() {
        return vechileCompany;
    }

    public void setVechileCompany(String vechileCompany) {
        this.vechileCompany = vechileCompany;
    }

    public String getVechileCategory() {
        return vechileCategory;
    }

    public void setVechileCategory(String vechileCategory) {
        this.vechileCategory = vechileCategory;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFitnessPercentage() {
        return fitnessPercentage;
    }

    public void setFitnessPercentage(int fitnessPercentage) {
        this.fitnessPercentage = fitnessPercentage;
    }

    public Date getLastTestTaken() {
        return lastTestTaken;
    }

    public void setLastTestTaken(Date lastTestTaken) {
        this.lastTestTaken = lastTestTaken;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return this.regNo;
    }
}
