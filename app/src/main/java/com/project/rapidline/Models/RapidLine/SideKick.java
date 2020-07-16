package com.project.rapidline.Models.RapidLine;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class SideKick {

    private String sideKickName;

    private String sideKickNo;

    private String sideKickNic;

    private String key;

    public SideKick(){

    }

    public SideKick(String sideKickName, String sideKickNo, String sideKickNic) {
        this.sideKickName = sideKickName;
        this.sideKickNo = sideKickNo;
        this.sideKickNic = sideKickNic;
    }

    public String getSideKickName() {
        return sideKickName;
    }

    public void setSideKickName(String sideKickName) {
        this.sideKickName = sideKickName;
    }

    public String getSideKickNo() {
        return sideKickNo;
    }

    public void setSideKickNo(String sideKickNo) {
        this.sideKickNo = sideKickNo;
    }

    public String getSideKickNic() {
        return sideKickNic;
    }

    public void setSideKickNic(String sideKickNic) {
        this.sideKickNic = sideKickNic;
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
        return this.sideKickName;
    }
}
