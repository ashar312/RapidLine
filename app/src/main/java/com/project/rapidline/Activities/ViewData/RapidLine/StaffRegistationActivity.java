package com.project.rapidline.Activities.ViewData.RapidLine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.project.rapidline.Activities.Forms.RapidLine.DriverSideKickForm;
import com.project.rapidline.Activities.Forms.RapidLine.OfficeStaffForm;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityStaffRegistationBinding;

public class StaffRegistationActivity extends AppCompatActivity {

    public enum StaffRegTabs {
        Driver,
        SideKick,
        OfficeStaff
    }

    private StaffRegTabs selectedTab=StaffRegTabs.Driver;
    private ActivityStaffRegistationBinding staffRegistationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staffRegistationBinding= DataBindingUtil.setContentView(this,R.layout.activity_staff_registation);

        staffRegistationBinding.driverBtn.setOnClickListener(view -> {
            if(selectedTab!=StaffRegTabs.Driver){
                //reset old tab
                resetOldTabDesign();

                //then set new tab design
                staffRegistationBinding.driverBtn.setTypeface(staffRegistationBinding.driverBtn.getTypeface(),Typeface.BOLD);
                staffRegistationBinding.driverLine.setVisibility(View.VISIBLE);
                selectedTab=StaffRegTabs.Driver;
            }
        });

        staffRegistationBinding.sidekickBtn.setOnClickListener(view -> {
            if(selectedTab!=StaffRegTabs.SideKick){
                resetOldTabDesign();

                //then set new tab design
                staffRegistationBinding.sidekickBtn.setTypeface(staffRegistationBinding.sidekickBtn.getTypeface(),Typeface.BOLD);
                staffRegistationBinding.sidekickLine.setVisibility(View.VISIBLE);
                selectedTab=StaffRegTabs.SideKick;
            }
        });

        staffRegistationBinding.officeStaffBtn.setOnClickListener(view -> {
            if(selectedTab!=StaffRegTabs.OfficeStaff){
                resetOldTabDesign();

                //then set new tab design
                staffRegistationBinding.officeStaffBtn.setTypeface(staffRegistationBinding.officeStaffBtn.getTypeface(),Typeface.BOLD);
                staffRegistationBinding.officeStaffLine.setVisibility(View.VISIBLE);
                selectedTab=StaffRegTabs.OfficeStaff;
            }
        });


        staffRegistationBinding.addCommonBtn.setOnClickListener(view -> {
            if(selectedTab==StaffRegTabs.Driver){
                Intent intent=new Intent(StaffRegistationActivity.this, DriverSideKickForm.class);
                intent.putExtra("ActivityName",String.valueOf(StaffRegTabs.Driver));
                startActivity(intent);

            }
            else if(selectedTab==StaffRegTabs.SideKick){
                Intent intent=new Intent(StaffRegistationActivity.this, DriverSideKickForm.class);
                intent.putExtra("ActivityName",String.valueOf(StaffRegTabs.SideKick));
                startActivity(intent);
            }
            else {
                //staff
                Intent intent=new Intent(StaffRegistationActivity.this, OfficeStaffForm.class);
                startActivity(intent);
            }
        });
    }

    private void resetOldTabDesign(){
        if(selectedTab==StaffRegTabs.Driver){

            staffRegistationBinding.driverBtn.setTypeface(Typeface.DEFAULT);

//            staffRegistationBinding.driverBtn.setTextColor(getResources().getColor(R.color.black));
            staffRegistationBinding.driverLine.setVisibility(View.GONE);
        }
        else if(selectedTab==StaffRegTabs.OfficeStaff){
            staffRegistationBinding.officeStaffBtn.setTypeface(Typeface.DEFAULT);
            staffRegistationBinding.officeStaffLine.setVisibility(View.GONE);
        }
        else {
            staffRegistationBinding.sidekickBtn.setTypeface(Typeface.DEFAULT);
            staffRegistationBinding.sidekickLine.setVisibility(View.GONE);
        }

    }

}
