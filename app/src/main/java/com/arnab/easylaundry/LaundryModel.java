package com.arnab.easylaundry;

import android.content.Context;

public class LaundryModel {
    private Context context;
    private LaundryItem[] laundryItems;
    static final int MAX_COUNT = 18;

    public LaundryModel(Context context){
        this.context = context;
        String[] stringResources = context.getResources().getStringArray(R.array.laundry_items);
        laundryItems = new LaundryItem[MAX_COUNT];
        for(int i=0;i<MAX_COUNT;i++){
            laundryItems[i] = new LaundryItem();
            laundryItems[i].setName(stringResources[i]);
        }
    }

    public LaundryItem getLaundryItemByNumber(int serialNumber){
        if(serialNumber > MAX_COUNT){
            return null;
        }
        return laundryItems[serialNumber];
    }
}
