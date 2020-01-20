package com.project.rapidline.Models;

public class RecyclerViewItemsCount {

    private String Count;
    private String Item;

    public RecyclerViewItemsCount(){

    }

    public RecyclerViewItemsCount(String count, String item) {
        Count = count;
        Item = item;
    }

    public String getCount() {
        return Count;
    }

    public String getItem() {
        return Item;
    }
}
