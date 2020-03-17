package com.project.rapidline.HomeScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import com.project.rapidline.Form.AddBailForm;
import com.project.rapidline.HomeScreens.AddActivities.ListActivities;
import com.project.rapidline.R;
import com.project.rapidline.viewmodel.DataViewModel;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    //ViewModel
    private DataViewModel dataViewModel;

    enum Activities {
        SenderReceiver,
        Agents,
        Transporters,
        Labour,
        Patri,
        AdminSettings,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar1);
        ImageView imageView = findViewById(R.id.draweropenclose);
        navigationView = findViewById(R.id.navigationView);
        View header_view = navigationView.getHeaderView(0);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDrawer();
            }
        });



        //Intialize viewmodel

        //Get cities
//        dataViewModel= ViewModelProviders.of(this).get(DataViewModel.class);
//        dataViewModel.getListAllCities().observe(this, new Observer<List<Cities>>() {
//            @Override
//            public void onChanged(List<Cities> cities) {
//                for (Cities city : cities) {
//                    Log.v("citynamess", city.getCity_name());
//                }
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void OpenDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_bail:
                startActivity(new Intent(Home.this, AddBailForm.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.regEntries:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.sender_receiver:
                SendDataToListActivities(String.valueOf(Activities.SenderReceiver));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.agents:
                SendDataToListActivities(String.valueOf(Activities.Agents));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.transporters:
                SendDataToListActivities(String.valueOf(Activities.Transporters));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.labour:
            //    startActivity(new Intent(Home.this, AR_Navigation.class));
                SendDataToListActivities(String.valueOf(Activities.Labour));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.patri:
                SendDataToListActivities(String.valueOf(Activities.Patri));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.adminSettings:
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
            case R.id.LoginBtn:
              //  LogOut();
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
            default:
                break;
        }
        return false;
    }

    private void SendDataToListActivities(String ListItem){
        Intent intent = new Intent(Home.this, ListActivities.class);
        intent.putExtra("ListItem",ListItem);
        startActivity(intent);
    }
}
