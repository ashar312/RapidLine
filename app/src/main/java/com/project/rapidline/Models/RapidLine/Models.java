package com.project.rapidline.Models.RapidLine;

import java.util.List;

public class Models {

    private List<Integer> modelYear;

    public Models(){

    }

    public Models(List<Integer> modelYear) {
        this.modelYear = modelYear;
    }

    public List<Integer> getModelYear() {
        return modelYear;
    }

    public void setModelYear(List<Integer> modelYear) {
        this.modelYear = modelYear;
    }

}
