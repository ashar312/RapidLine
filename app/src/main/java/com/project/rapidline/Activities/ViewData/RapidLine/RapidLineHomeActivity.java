package com.project.rapidline.Activities.ViewData.RapidLine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.project.rapidline.Activities.Forms.RapidLine.FitnessTest;
import com.project.rapidline.Activities.Forms.RapidLine.MaintainanceChartForm;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityRapidLineHomeBinding;

public class RapidLineHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityRapidLineHomeBinding rapidLineHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rapidLineHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_rapid_line_home);

        rapidLineHomeBinding.draweropenclose.setOnClickListener(view -> openDrawer());
        rapidLineHomeBinding.rapidLineNav.setNavigationItemSelectedListener(this);
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
        }
        return false;
    }
}
