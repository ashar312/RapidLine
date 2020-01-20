package com.project.rapidline.HomeScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import com.project.rapidline.R;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        View header_view = navigationView.getHeaderView(0);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawerOpen,R.string.drawerClose);
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
                Toast.makeText(Home.this, "Contact us Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.sender_receiver:
                Toast.makeText(Home.this, "About us Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.agents:
                Toast.makeText(Home.this, "recent_tour Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.transporters:
             //   startActivity(new Intent(Home.this, PlacesOfInterest.class));
                Toast.makeText(Home.this, "look_around Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.labour:
            //    startActivity(new Intent(Home.this, AR_Navigation.class));
                Toast.makeText(Home.this, "ar_nav Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.patri:
                Toast.makeText(Home.this, "Notifications Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.adminSettings:
                Toast.makeText(this, "Admin Settings", Toast.LENGTH_SHORT).show();

            case R.id.LoginBtn:
              //  LogOut();
                break;
            default:
                break;
        }
        return false;
    }
}
