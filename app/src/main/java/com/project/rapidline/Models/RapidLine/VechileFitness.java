package com.project.rapidline.Models.RapidLine;

import java.util.Date;

public class VechileFitness {

    private String vechileRegNo;

    private int fitnessPercentage;

    private Date lastUpdated;

    public VechileFitness(){

    }

    public VechileFitness(String vechileRegNo, int fitnessPercentage, Date lastUpdated) {
        this.vechileRegNo = vechileRegNo;
        this.fitnessPercentage = fitnessPercentage;
        this.lastUpdated = lastUpdated;
    }

    public String getVechileRegNo() {
        return vechileRegNo;
    }

    public void setVechileRegNo(String vechileRegNo) {
        this.vechileRegNo = vechileRegNo;
    }

    public int getFitnessPercentage() {
        return fitnessPercentage;
    }

    public void setFitnessPercentage(int fitnessPercentage) {
        this.fitnessPercentage = fitnessPercentage;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
