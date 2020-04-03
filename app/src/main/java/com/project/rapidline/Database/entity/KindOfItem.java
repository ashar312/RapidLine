package com.project.rapidline.Database.entity;

import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class KindOfItem {

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "madeBy")
    public String madeBy;

    @ColumnInfo(name = "madeDateTime")
    public Date madeDateTime;

    public KindOfItem(){

    }

    public KindOfItem( String name, String madeBy, Date madeDateTime) {
        this.name = name;
        this.madeBy = madeBy;
        this.madeDateTime = madeDateTime;
    }

    @Ignore
    public KindOfItem(String name) {
        this.name = name;
    }


    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("madeBy",this.getMadeBy());
        map.put("madeDateTime",this.getMadeDateTime());
        return map;
    }

    public String getName() {
        return name;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public Date getMadeDateTime() {
        return madeDateTime;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

}
