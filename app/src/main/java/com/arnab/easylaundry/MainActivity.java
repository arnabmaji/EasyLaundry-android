package com.arnab.easylaundry;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showSplashScreen();
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        currentFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFrameLayout,currentFragment).commit();
        navigationView.setCheckedItem(R.id.home_frag);
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
}
