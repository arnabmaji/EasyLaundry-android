package com.arnab.easylaundry;

import java.io.Serializable;

public class LaundryItem implements Serializable {
    private String itemName;
    private int itemCount;
    private final static long serialVersionUID = 1L;

    LaundryItem(){
        itemCount = 0;
    }

    int getItemCount(){
        return itemCount;
    }

    void increaseCountByOne(){
        itemCount++;
    }

    void decreaseCountByOne(){
        itemCount--;
    }

    String getItemName(){
        return itemName;
    }

    public void setName(String name){
        itemName = name;
    }
}
