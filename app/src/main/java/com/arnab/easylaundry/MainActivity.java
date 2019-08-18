package com.arnab.easylaundry;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private String[] laundryItems;
    private int[] laundryItemCounter;
    private Button[] buttons;
    private int lastItemClickedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showSplashScreen();
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);

        laundryItems = getResources().getStringArray(R.array.laundry_items);
        laundryItemCounter = new int[laundryItems.length];
        buttons = new Button[laundryItems.length];
        buttons[0] = findViewById(R.id.button0);
        buttons[1] = findViewById(R.id.button1);
        buttons[2] = findViewById(R.id.button2);
        buttons[3] = findViewById(R.id.button3);
        buttons[4] = findViewById(R.id.button4);
        buttons[5] = findViewById(R.id.button5);
        buttons[6] = findViewById(R.id.button6);
        buttons[7] = findViewById(R.id.button7);
        buttons[8] = findViewById(R.id.button8);
        buttons[9] = findViewById(R.id.button9);
        buttons[10] = findViewById(R.id.button10);
        buttons[11] = findViewById(R.id.button11);
        buttons[12] = findViewById(R.id.button12);
        buttons[13] = findViewById(R.id.button13);
        buttons[14] = findViewById(R.id.button14);
        buttons[15] = findViewById(R.id.button15);
        buttons[16] = findViewById(R.id.button16);
        buttons[17] = findViewById(R.id.button17);

        for (int i=0;i<laundryItemCounter.length;i++){
            updateButtonInfo(i);
        }

    }

    private void showSplashScreen() {
            Intent i = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    public void toggleNavigationDrawer(View view){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void updateButtonInfo(int itemId){
        String buttonText = laundryItems[itemId]+"\n"+laundryItemCounter[itemId];
        buttons[itemId].setText(buttonText);
    }

    public void onLaundryItemClicked(View view){
        int itemId = Integer.parseInt(view.getTag().toString());
        lastItemClickedId = itemId;
        laundryItemCounter[itemId]++;
        updateButtonInfo(itemId);

        String snackBarText = laundryItems[itemId] + " added";
        Snackbar snackbar = Snackbar.make(view,snackBarText,Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laundryItemCounter[lastItemClickedId]--;
                updateButtonInfo(lastItemClickedId);
            }
        });
        snackbar.show();
    }

    public void resetAllValues(View view){
        for(int i=0;i<laundryItemCounter.length;i++){
            laundryItemCounter[i] = 0;
            updateButtonInfo(i);
        }
    }
}
