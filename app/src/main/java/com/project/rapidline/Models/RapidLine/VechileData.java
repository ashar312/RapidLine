package com.project.rapidline.Models.RapidLine;

import java.util.List;

public class VechileData {
    private List<String> vechileCategory;

    public VechileData(){

    }

    public VechileData(List<String> vechileCategory) {
        this.vechileCategory = vechileCategory;
    }

    public List<String> getVechileCategory() {
        return vechileCategory;
    }

}
