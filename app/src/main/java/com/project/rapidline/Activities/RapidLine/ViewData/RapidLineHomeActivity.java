package com.project.rapidline.Activities.RapidLine.ViewData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.project.rapidline.Activities.Common.Login;
import com.project.rapidline.Activities.RapidLine.Forms.AddBiltyForm;
import com.project.rapidline.Activities.RapidLine.Forms.FitnessTest;
import com.project.rapidline.Activities.RapidLine.Forms.MaintainanceChartForm;
import com.project.rapidline.Activities.RapidLine.Forms.Shipment.ShipmentMainActivity;
import com.project.rapidline.Activities.SaeedSons.ViewData.ListActivities;
import com.project.rapidline.Activities.SaeedSons.ViewData.SaeedSonsHomeActivity;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityRapidLineHomeBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineHomeViewModel;
import com.project.rapidline.viewmodel.SaeedSons.HomeViewModel;

public class RapidLineHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    enum SaeedSonActivities {
        SenderReceiver,
        Agents,
        Supplier
    }


    private ActivityRapidLineHomeBinding rapidLineHomeBinding;
    private RapidLineHomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rapidLineHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_rapid_line_home);
        homeViewModel= ViewModelProviders.of(this).get(RapidLineHomeViewModel.class);

        rapidLineHomeBinding.draweropenclose.setOnClickListener(view -> openDrawer());
        rapidLineHomeBinding.rapidLineNav.setNavigationItemSelectedListener(this);


        rapidLineHomeBinding.addShipmentBtn.setOnClickListener(view -> {
            startActivity(new Intent(RapidLineHomeActivity.this, ShipmentMainActivity.class));
        });

        setupObservers();

        NavigationView navigationView = findViewById(R.id.rapid_line_nav);
        //Set Navigation header
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        TextView companyName = headerView.findViewById(R.id.company_name);
        username.setText(getAdminUsername());
        companyName.setText(rapidLineHomeBinding.companyName.getText().toString());



    }

    private void setupObservers(){

        homeViewModel.getDriverCount().observe(this,value -> {
            rapidLineHomeBinding.drivervalue.setText(value + "");
        });

        homeViewModel.getSidekickCount().observe(this,value -> {
            rapidLineHomeBinding.sidekickvalue.setText(value + "");
        });
        homeViewModel.getStaffCount().observe(this,value -> {
            rapidLineHomeBinding.officeStaffvalue.setText(value + "");
        });

        homeViewModel.getVechileCount().observe(this,value -> {
            rapidLineHomeBinding.vechilevalue.setText(value + "");
        });

        homeViewModel.getBiltyCount().observe(this,integerList -> {
            int offline = integerList.get(0);
            rapidLineHomeBinding.offlineTxt.setText(offline + "");
            rapidLineHomeBinding.pendingVal.setText(offline + "");
            if (offline > 0)
                rapidLineHomeBinding.notificationTxt.setText("You have " + offline + " offline bails/");

            int online = integerList.get(1);
            //Online quantity
            rapidLineHomeBinding.onlineTxt.setText(online + "");

            //Set total
            int total = online + offline;
            rapidLineHomeBinding.totalVal.setText(total + "");
        });

    }


    private void openDrawer() {
        rapidLineHomeBinding.drawerRapidLine.openDrawer(GravityCompat.START);
    }

    //Overides

    @Override
    public void onBackPressed() {
        if(rapidLineHomeBinding.drawerRapidLine.isDrawerOpen(GravityCompat.START)){
            rapidLineHomeBinding.drawerRapidLine.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_bilty:
                Intent intent=new Intent(RapidLineHomeActivity.this, AddBiltyForm.class);
                intent.putExtra("action","add");
                startActivity(intent);
                break;
            case R.id.staffReg:
                startActivity(new Intent(RapidLineHomeActivity.this,StaffRegistationActivity.class));
                break;
            case R.id.vechReg:
                startActivity(new Intent(RapidLineHomeActivity.this,VechilesActivity.class));
                break;
            case R.id.vechFit:
                startActivity(new Intent(RapidLineHomeActivity.this, FitnessTest.class));
                break;
            case R.id.maintainChart:
                startActivity(new Intent(RapidLineHomeActivity.this, MaintainanceChartForm.class));
                break;
            case R.id.rapidline_sender_receiver:
                SendDataToListActivities(String.valueOf(SaeedSonActivities.SenderReceiver));
                break;
            case R.id.rapidline_agents:
                SendDataToListActivities(String.valueOf(SaeedSonActivities.Agents));
                break;
            case R.id.rapidline_supplier:
                SendDataToListActivities(String.valueOf(SaeedSonActivities.Supplier));
                break;
            case R.id.regBilty:
                startActivity(new Intent(RapidLineHomeActivity.this,ViewBiltyActivity.class));
                break;
            case R.id.logOutRapidLine:
                startActivity(new Intent(RapidLineHomeActivity.this, Login.class));
                finish();
                break;
        }
        return false;
    }

    private void SendDataToListActivities(String ListItem) {
        Intent intent = new Intent(RapidLineHomeActivity.this, ListActivities.class);
        intent.putExtra("ListItem", ListItem);
        intent.putExtra("activityName","RapidLine");
        startActivity(intent);
    }

    private String getAdminUsername() {
        return getApplicationContext().getSharedPreferences("LoginPref", 0).getString("username", "");
    }
}
