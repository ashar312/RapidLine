package com.project.rapidline.HomeScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import com.project.rapidline.HomeScreens.AddActivities.ListActivities;
import com.project.rapidline.R;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

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
        navigationView = findViewById(R.id.navigationView);
        View header_view = navigationView.getHeaderView(0);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_bail:
            //    startActivity(new Intent(HomeActivity.this, Profile.class));
                break;

            case R.id.regEntries:
                break;
            case R.id.sender_receiver:
                SendDataToListActivities(String.valueOf(Activities.SenderReceiver));
                break;

            case R.id.agents:
                SendDataToListActivities(String.valueOf(Activities.Agents));
                break;

            case R.id.transporters:
                SendDataToListActivities(String.valueOf(Activities.Transporters));
                break;

            case R.id.labour:
            //    startActivity(new Intent(Home.this, AR_Navigation.class));
                SendDataToListActivities(String.valueOf(Activities.Labour));
                break;

            case R.id.patri:
                SendDataToListActivities(String.valueOf(Activities.Patri));
                break;

            case R.id.adminSettings:

            case R.id.LoginBtn:
              //  LogOut();
                break;
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
