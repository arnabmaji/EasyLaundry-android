package com.arnab.easylaundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSplashScreen();
        setContentView(R.layout.activity_main);


    }

    private void showSplashScreen() {
            Intent i = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(i);
    }
}
