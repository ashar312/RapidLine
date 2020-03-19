package com.project.rapidline.Models;

public class ListItems {

    private long mItemId;
    private String mItemName;

    public ListItems(long mItemId, String mItemName) {
        this.mItemId = mItemId;
        this.mItemName = mItemName;
    }

    public long getmItemId() {
        return mItemId;
    }

    public String getmItemName() {
        return mItemName;
    }
}
