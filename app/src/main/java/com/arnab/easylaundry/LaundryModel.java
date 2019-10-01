package com.arnab.easylaundry;

import android.content.Context;

import java.io.Serializable;

class LaundryModel implements Serializable {
    private LaundryItem[] laundryItems;
    static final int MAX_COUNT = 18;
    private final static long serialVersionUID = 1L;

    LaundryModel(Context context){
        String[] stringResources = context.getResources().getStringArray(R.array.laundry_items);
        laundryItems = new LaundryItem[MAX_COUNT];
        for(int i=0;i<MAX_COUNT;i++){
            laundryItems[i] = new LaundryItem();
            laundryItems[i].setName(stringResources[i]);
        }
    }

    LaundryItem getLaundryItemByNumber(int serialNumber){
        if(serialNumber > MAX_COUNT){
            return null;
        }
        return laundryItems[serialNumber];
    }

    LaundryItem[] getLaundryItems(){
        return laundryItems;
    }
}
