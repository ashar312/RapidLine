package com.project.rapidline.Activities.Forms.RapidLine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.project.rapidline.Activities.ViewData.RapidLine.StaffRegistationActivity;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityDriverSideKickFormBinding;

public class DriverSideKickForm extends AppCompatActivity {

    private ActivityDriverSideKickFormBinding formBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formBinding= DataBindingUtil.setContentView(this,R.layout.activity_driver_side_kick_form);

        Bundle bundle= getIntent().getExtras();
        String activityName=bundle.getString("ActivityName");

        if(activityName.equals("Driver")){
            formBinding.headingTxt.setText(activityName);

        }
        else if(activityName.equals("SideKick")){
            formBinding.imagesLayout.setVisibility(View.GONE);
            formBinding.headingTxt.setText(activityName);

        }

    }
}
