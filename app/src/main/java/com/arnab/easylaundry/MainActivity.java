package com.arnab.easylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private Fragment homeFragment;
    private LaundryModel laundryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showSplashScreen();
        laundryModel = new LaundryModel(MainActivity.this);//Initializing Laundry Model for current use.
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar); //setting up custom toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        homeFragment = new HomeFragment(laundryModel);
        setHomeFragment();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch(menuItem.getItemId()){
                    case R.id.home_frag:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.get_support:
                        selectedFragment = new SupportFragment(MainActivity.this);
                        break;
                    case R.id.app_exit:
                        Toast.makeText(MainActivity.this,R.string.thank_you_message,Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                if(selectedFragment != null){
                    currentFragment = selectedFragment;
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFrameLayout,selectedFragment).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }

    private void showSplashScreen() {
            Intent i = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(!(currentFragment instanceof HomeFragment)){
            setHomeFragment();
        } else{
            super.onBackPressed();
        }
    }

    private void setHomeFragment(){
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFrameLayout,currentFragment).commit();
        navigationView.setCheckedItem(R.id.home_frag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_reset){
            laundryModel = new LaundryModel(MainActivity.this);
            homeFragment = new HomeFragment(laundryModel);
            setHomeFragment();
        }
        return true;
    }
}