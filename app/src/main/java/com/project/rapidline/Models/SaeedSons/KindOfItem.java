package com.project.rapidline.Models.SaeedSons;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class KindOfItem {

    public String name;

    private String key;

    public KindOfItem(){

    }

    public KindOfItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

}
