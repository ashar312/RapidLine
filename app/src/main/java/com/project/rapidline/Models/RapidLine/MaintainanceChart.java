package com.project.rapidline.Models.RapidLine;

import java.util.List;

public class MaintainanceChart {

    private String regNo;

    private String vechileCurrentKm;

    private List<String> maintainanceCategory;

    private List<String> maintainanceSpecifics;

    private String notes;

    public MaintainanceChart(){

    }

    public MaintainanceChart(String regNo, String vechileCurrentKm, List<String> maintainanceCategory, List<String> maintainanceSpecifics, String notes) {
        this.regNo = regNo;
        this.vechileCurrentKm = vechileCurrentKm;
        this.maintainanceCategory = maintainanceCategory;
        this.maintainanceSpecifics = maintainanceSpecifics;
        this.notes = notes;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getVechileCurrentKm() {
        return vechileCurrentKm;
    }

    public void setVechileCurrentKm(String vechileCurrentKm) {
        this.vechileCurrentKm = vechileCurrentKm;
    }

    public List<String> getMaintainanceCategory() {
        return maintainanceCategory;
    }

    public void setMaintainanceCategory(List<String> maintainanceCategory) {
        this.maintainanceCategory = maintainanceCategory;
    }

    public List<String> getMaintainanceSpecifics() {
        return maintainanceSpecifics;
    }

    public void setMaintainanceSpecifics(List<String> maintainanceSpecifics) {
        this.maintainanceSpecifics = maintainanceSpecifics;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

