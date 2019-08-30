package com.arnab.easylaundry;

public class LaundryItem {
    private String itemName;
    private int itemCount;

    public LaundryItem(){
        itemCount = 0;
    }

    public void setCount(int count){
        itemCount = count;
    }

    public int getItemCount(){
        return itemCount;
    }
    public void increaseCountByOne(){
        itemCount++;
    }

    public void decreaseCountByOne(){
        itemCount--;
    }

    public String getItemName(){
        return itemName;
    }

    public void setName(String name){
        itemName = name;
    }
}
