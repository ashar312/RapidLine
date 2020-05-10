package com.project.rapidline.Models.SaeedSons;

import com.google.firebase.firestore.Exclude;

import androidx.annotation.NonNull;

public class Cities {
    public String name;

    private String key;

    public Cities(){

    }

    public Cities(String name) {
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
