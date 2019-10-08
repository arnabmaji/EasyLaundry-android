package com.arnab.easylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private Fragment homeFragment;
    private LaundryModel laundryModel;
    private static final String TAG = "EasyLaundry";
    SavedFilesList savedFilesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showSplashScreen();
        setContentView(R.layout.activity_main);
        laundryModel = new LaundryModel(MainActivity.this);//Initializing Laundry Model for current use.
        savedFilesList = new SavedFilesList();
        getHistory();
        //Setting up drawer menu and toolbar
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar); //setting up custom toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Setting u home fragment for first time
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
                    case R.id.history:
                        showSavedFilesNames();
                        drawerLayout.closeDrawer(GravityCompat.START);
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
        } else if(item.getItemId() == R.id.action_save){
            Toast.makeText(MainActivity.this,R.string.all_saved_toast_message,Toast.LENGTH_SHORT).show();
            saveCurrentLaundryItemCounts();
        }
        return true;
    }

    private void saveCurrentLaundryItemCounts(){
        Calendar calendar = Calendar.getInstance();
        //Current count name matches the date in format "dd:mm:yyyy"
        String fileName = calendar.get(Calendar.DAY_OF_MONTH) + ":" + calendar.get(Calendar.MONTH) + ":" + + calendar.get(Calendar.YEAR);
        savedFilesList.add(fileName);
        String fileNameToBeDeleted = savedFilesList.getLastDeletedFileName();
        if(fileNameToBeDeleted != null){ //Delete the oldest file to free up memory
            File file = new File(getFilesDir(),fileNameToBeDeleted+".dat");
            if(file.delete()){ //On successful deletion
                Log.d(TAG, "saveCurrentLaundryItemCounts: successfully deleted "+fileNameToBeDeleted);
            }
        }
        File historyFile = new File(getFilesDir(),"history.dat");
        try(DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(historyFile)))){
            for(String name : savedFilesList.list()){
                stream.writeUTF(name);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        File fileToBeSaved = new File(getFilesDir(),fileName+".dat");
        //Now serialize the current laundryModel object into its own file.
        try(ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileToBeSaved)))){
            stream.writeObject(laundryModel);
            Log.d(TAG, "saveCurrentLaundryItemCounts: saved file "+fileToBeSaved.getName());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getHistory(){
        File historyFile = new File(getFilesDir(),"history.dat");
        try(DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(historyFile)))){
            boolean eof = false;
            while(!eof){
                try{
                    String fileName = stream.readUTF();
                    savedFilesList.add(fileName);
                } catch (EOFException e){
                    eof = true;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showSavedFilesNames(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        int size = savedFilesList.list().size();
        String[] fileNames = new String[size];
        for(int i=0;i<size;i++){
            fileNames[i] = savedFilesList.list().get(i);
        }
        builder.setTitle("Choose Date");
        builder.setItems(fileNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                retrieveLaundryModel(savedFilesList.list().get(i));
            }
        });
        builder.show();
    }

    private void retrieveLaundryModel(String fileName){
        File file = new File(getFilesDir(),fileName+".dat");
        try(ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
            boolean eof = false;
            while(!eof){
                try{
                    laundryModel = (LaundryModel) stream.readObject();
                    homeFragment = new HomeFragment(laundryModel);
                    setHomeFragment();
                } catch (ClassNotFoundException | EOFException e){
                    eof = true;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}