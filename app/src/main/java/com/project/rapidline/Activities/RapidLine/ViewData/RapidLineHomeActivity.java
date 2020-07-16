package com.project.rapidline.Activities.RapidLine.ViewData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.project.rapidline.Activities.RapidLine.Forms.AddBiltyForm;
import com.project.rapidline.Activities.RapidLine.Forms.FitnessTest;
import com.project.rapidline.Activities.RapidLine.Forms.MaintainanceChartForm;
import com.project.rapidline.Activities.RapidLine.Forms.Shipment.ShipmentMainActivity;
import com.project.rapidline.Activities.SaeedSons.ViewData.ListActivities;
import com.project.rapidline.Activities.SaeedSons.ViewData.SaeedSonsHomeActivity;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityRapidLineHomeBinding;

public class RapidLineHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    enum SaeedSonActivities {
        SenderReceiver,
        Agents,
        Supplier
    }


    private ActivityRapidLineHomeBinding rapidLineHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rapidLineHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_rapid_line_home);

        rapidLineHomeBinding.draweropenclose.setOnClickListener(view -> openDrawer());
        rapidLineHomeBinding.rapidLineNav.setNavigationItemSelectedListener(this);


        rapidLineHomeBinding.addShipmentBtn.setOnClickListener(view -> {
            startActivity(new Intent(RapidLineHomeActivity.this, ShipmentMainActivity.class));
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
        }
        return false;
    }

    private void SendDataToListActivities(String ListItem) {
        Intent intent = new Intent(RapidLineHomeActivity.this, ListActivities.class);
        intent.putExtra("ListItem", ListItem);
        intent.putExtra("activityName","RapidLine");
        startActivity(intent);
    }

}
