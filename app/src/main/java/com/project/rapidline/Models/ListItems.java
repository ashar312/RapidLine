package com.project.rapidline.Models;

public class ListItems {

    private String mItemId;
    private String mItemName;

    public ListItems(String mItemId, String mItemName) {
        this.mItemId = mItemId;
        this.mItemName = mItemName;
    }

    public String getmItemId() {
        return mItemId;
    }

    public String getmItemName() {
        return mItemName;
    }
}
