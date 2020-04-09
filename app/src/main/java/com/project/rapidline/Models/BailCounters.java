package com.project.rapidline.Models;

import java.util.HashMap;

public class BailCounters {
    public String bailSymbol;
    public int builtyCounter;

    public BailCounters(){

    }

    public BailCounters(String bailSymbol, int builtyCounter) {
        this.bailSymbol = bailSymbol;
        this.builtyCounter = builtyCounter;
    }

    public String getBailSymbol() {
        return bailSymbol;
    }

    public int getBuiltyCounter() {
        return builtyCounter;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("bailSymbol",this.getBailSymbol());
        map.put("builtyCounter",this.getBuiltyCounter());
        return map;
    }

    public void setBailSymbol(String bailSymbol) {
        this.bailSymbol = bailSymbol;
    }

    public void setBuiltyCounter(int builtyCounter) {
        this.builtyCounter = builtyCounter;
    }
}
