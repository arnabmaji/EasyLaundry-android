package com.arnab.easylaundry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class SavedFilesList {
    private String lastDeletedFileName;
    private static final int MAX_ITEMS_ALLOWED = 3;
    private final List<String> savedFileNames;
    SavedFilesList(){
        savedFileNames = new LinkedList<>();
    }

    private int contains(String fileName){
        for(int i=0;i<savedFileNames.size();i++){
            if(savedFileNames.get(i).equals(fileName)){
                return i;
            }
        }
        return -1;
    }

    void add(String fileName){
        int index = contains(fileName);
        if(index < 0){
            if(savedFileNames.size() >= MAX_ITEMS_ALLOWED){
                lastDeletedFileName = savedFileNames.remove(0);
            }
            savedFileNames.add(fileName);
        }
    }

    List<String> list(){
        return Collections.unmodifiableList(new ArrayList<>(savedFileNames));
    }

    String getLastDeletedFileName(){
        return lastDeletedFileName;
    }
}
