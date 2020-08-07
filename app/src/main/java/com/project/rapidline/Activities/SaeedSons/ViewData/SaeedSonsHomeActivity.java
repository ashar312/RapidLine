package com.project.rapidline.Activities.SaeedSons.ViewData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.project.rapidline.Activities.Common.Login;
import com.project.rapidline.Activities.Common.SettingsActivity;
import com.project.rapidline.Activities.SaeedSons.Forms.AddBailForm;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityHomeBinding;
import com.project.rapidline.viewmodel.SaeedSons.AdminViewModel;
import com.project.rapidline.viewmodel.SaeedSons.HomeViewModel;

public class SaeedSonsHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    private HomeViewModel homeViewModel;
    private ActivityHomeBinding activityHomeBinding;
    private AdminViewModel adminViewModel;


    enum Activities {
        SenderReceiver,
        Agents,
        Transporters,
        Labour,
        Patri,
        AdminSettings,
        AddBail
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar1);
        ImageView imageView = findViewById(R.id.draweropenclose);
        navigationView = findViewById(R.id.navigationView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        imageView.setOnClickListener(v -> OpenDrawer());

        //Intialize viewmodel
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);
        initializeViewModel();


    }

    private void initializeViewModel() {

        homeViewModel.getCustomerCount().observe(this, value -> {
            activityHomeBinding.sendervalue.setText(value + "");
        });


        homeViewModel.getAgentCount().observe(this, value -> {
            activityHomeBinding.agentvalue.setText(value + "");
        });

        homeViewModel.getTransporterCount().observe(this, value -> {
            activityHomeBinding.transportervalue.setText(value + "");
        });
        homeViewModel.getLabourCount().observe(this, value -> {
            activityHomeBinding.labourvalue.setText(value + "");
        });
        homeViewModel.getPatriCount().observe(this, value -> {
            activityHomeBinding.patrisvalue.setText(value + "");
        });

        homeViewModel.getBailCount().observe(this, integerList -> {
            //Offline quantity
            int offline = integerList.get(0);
            activityHomeBinding.offlineTxt.setText(offline + "");
            activityHomeBinding.pendingVal.setText(offline + "");
            if (offline > 0)
                activityHomeBinding.notificationTxt.setText("You have " + offline + " offline bails/");

            int online = integerList.get(1);
            //Online quantity
            activityHomeBinding.onlineTxt.setText(online + "");

            //Set total
            int total = online + offline;
            activityHomeBinding.totalVal.setText(total + "");
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void OpenDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_bail:
                adminViewModel.getAdminPermissionsById(getAdminUsername()).observe(this, permissions -> {
                    if (permissions.isAddBail()) {
                        Intent intent = new Intent(SaeedSonsHomeActivity.this, AddBailForm.class);
                        intent.putExtra("action", "add");
                        startActivity(intent);
                    } else
                        Toast.makeText(this, "You do not have permissions to add a bail", Toast.LENGTH_SHORT).show();
                });
                break;

            case R.id.regEntries:
                startActivity(new Intent(this, ViewBailsActivity.class));
                break;

            case R.id.sender_receiver:
                SendDataToListActivities(String.valueOf(Activities.SenderReceiver));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.agents:
                SendDataToListActivities(String.valueOf(Activities.Agents));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.transporters:
                SendDataToListActivities(String.valueOf(Activities.Transporters));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.labour:
                //    startActivity(new Intent(Home.this, AR_Navigation.class));
                SendDataToListActivities(String.valueOf(Activities.Labour));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.patri:
                SendDataToListActivities(String.valueOf(Activities.Patri));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.adminSettings:
                startActivity(new Intent(this, SettingsActivity.class));
//                startActivity(new Intent(this, AdminRightsActivity.class));
                break;
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
            case R.id.logOut:
                LogOut();
                break;
            default:
                break;
        }
        return false;
    }

    private void LogOut() {
//        addCurrentStateToPref(false);
        startActivity(new Intent(SaeedSonsHomeActivity.this, Login.class));
        finish();
    }

    private void addCurrentStateToPref(boolean loginState) {
        SharedPreferences.Editor editor = this.getSharedPreferences("LoginPref", MODE_PRIVATE).edit();
        editor.putString("username", "");
        editor.putBoolean("loggedIn", loginState);
        editor.apply(); //apply writes the data in background process
    }

    private void SendDataToListActivities(String ListItem) {
        Intent intent = new Intent(SaeedSonsHomeActivity.this, ListActivities.class);
        intent.putExtra("ListItem", ListItem);
        intent.putExtra("activityName","SaeedSons");
        startActivity(intent);
    }

    private String getAdminUsername() {
        return getApplicationContext().getSharedPreferences("LoginPref", 0).getString("username", "");
    }

}
