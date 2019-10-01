package com.arnab.easylaundry;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {
    
    private LaundryModel laundryModel;
    private Button[] buttons;
    private int lastUpdatedItemTag;
    private View view;

    HomeFragment(LaundryModel laundryModel){ //Getting current or last saved laundry models from Main Activity
        this.laundryModel = laundryModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,container,false);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.main_round_button_clicked);
                updateItemInfo(Integer.parseInt(view.getTag().toString()));
            }
        };
        buttons = new Button[LaundryModel.MAX_COUNT];
        buttons[0] = view.findViewById(R.id.button0);
        buttons[1] = view.findViewById(R.id.button1);
        buttons[2] = view.findViewById(R.id.button2);
        buttons[3] = view.findViewById(R.id.button3);
        buttons[4] = view.findViewById(R.id.button4);
        buttons[5] = view.findViewById(R.id.button5);
        buttons[6] = view.findViewById(R.id.button6);
        buttons[7] = view.findViewById(R.id.button7);
        buttons[8] = view.findViewById(R.id.button8);
        buttons[9] = view.findViewById(R.id.button9);
        buttons[10] = view.findViewById(R.id.button10);
        buttons[11] = view.findViewById(R.id.button11);
        buttons[12] = view.findViewById(R.id.button12);
        buttons[13] = view.findViewById(R.id.button13);
        buttons[14] = view.findViewById(R.id.button14);
        buttons[15] = view.findViewById(R.id.button15);
        buttons[16] = view.findViewById(R.id.button16);
        buttons[17] = view.findViewById(R.id.button17);
        for(Button button : buttons) { //sets click listeners for all buttons programmatically;
            button.setOnClickListener(onClickListener);
        }
        for(int i=0;i<LaundryModel.MAX_COUNT;i++) {
            updateButtonText(i);
        }
        return view;
    }


    private void updateItemInfo(int tag){
        lastUpdatedItemTag = tag;
        LaundryItem laundryItem = laundryModel.getLaundryItemByNumber(tag);
        laundryItem.increaseCountByOne();
        updateButtonText(tag);
        Snackbar.make(view,laundryItem.getItemName()+" added",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laundryModel.getLaundryItemByNumber(lastUpdatedItemTag).decreaseCountByOne();
                updateButtonText(lastUpdatedItemTag);
            }
        }).show();
    }

    private void updateButtonText(int tag){
        LaundryItem laundryItem  = laundryModel.getLaundryItemByNumber(tag);
        String buttonText = laundryItem.getItemName() + "\n" + laundryItem.getItemCount();
        buttons[tag].setText(buttonText);
    }
}
