package com.project.rapidline.Models.RapidLine;

import java.util.List;

public class MaintainanceData {
    private List<String> maintainanceCategory;
    private List<String> maintainanceSpecifics;

    public MaintainanceData(){

    }

    public MaintainanceData(List<String> maintainanceCategory, List<String> maintainanceSpecifics) {
        this.maintainanceCategory = maintainanceCategory;
        this.maintainanceSpecifics = maintainanceSpecifics;
    }

    public List<String> getMaintainanceCategory() {
        return maintainanceCategory;
    }

    public List<String> getMaintainanceSpecifics() {
        return maintainanceSpecifics;
    }

}
