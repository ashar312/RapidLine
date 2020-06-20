package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.project.rapidline.Models.RapidLine.Drivers;
import com.project.rapidline.Models.RapidLine.SideKick;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityDriverSideKickFormBinding;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

public class DriverSideKickForm extends AppCompatActivity {

    private ActivityDriverSideKickFormBinding formBinding;
    private RapidLineViewModel rapidLineViewModel;

    private Drivers mDriver;
    private SideKick mSideKick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formBinding = DataBindingUtil.setContentView(this, R.layout.activity_driver_side_kick_form);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);


        Bundle bundle = getIntent().getExtras();
        String activityName = bundle.getString("ActivityName");
        String action = bundle.get("action").toString();

        if (activityName.equals("Driver")) {
            formBinding.headingTxt.setText(activityName);

            if (action.equals("edit")) {
                //Load the data
                String id = bundle.getString("itemId");
                rapidLineViewModel.getDriverById(id).observe(this, drivers -> {
                    mDriver = drivers;
                    loadDriver();
                });
            }


        } else if (activityName.equals("SideKick")) {
            formBinding.imagesLayout.setVisibility(View.GONE);
            formBinding.headingTxt.setText(activityName);


            if (action.equals("edit")) {

                formBinding.dsName.setEnabled(false);
                //Load the data
                String id = bundle.getString("itemId");
                rapidLineViewModel.getSideKickById(id).observe(this, sideKick -> {
                    mSideKick = sideKick;
                    loadSide();
                });
            }
        }

        formBinding.addDsBtn.setOnClickListener(view -> {
            if (isDataFieldsEmpty()) {
                Toast.makeText(this, "Please fill all field to continue",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (activityName.equals("Driver")) {
                if (action.equals("edit")) {

                    mDriver.setDriverName(formBinding.dsName.getText().toString());
                    mDriver.setDriverNo(formBinding.dsNum.getText().toString());
                    mDriver.setDriverNic(formBinding.dsNic.getText().toString());

                    rapidLineViewModel.updateDriver(mDriver);

                    Toast.makeText(this, "Driver updated sucessfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    mDriver = new Drivers();
                    mDriver.setDriverName(formBinding.dsName.getText().toString());
                    mDriver.setDriverNo(formBinding.dsNum.getText().toString());
                    mDriver.setDriverNic(formBinding.dsNic.getText().toString());

                    rapidLineViewModel.addDriver(mDriver).observe(this, response -> {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        if (response.equals(Responses.DRIVER_ADDED))
                            finish();
                    });

                }


            } else {
                if (action.equals("edit")) {
                    mSideKick.setSideKickName(formBinding.dsName.getText().toString());
                    mSideKick.setSideKickNic(formBinding.dsNic.getText().toString());
                    mSideKick.setSideKickNo(formBinding.dsNum.getText().toString());


                    rapidLineViewModel.updateSideKick(mSideKick);

                    Toast.makeText(this, "Sidekick updated sucessfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    mSideKick = new SideKick();
                    mSideKick.setSideKickName(formBinding.dsName.getText().toString());
                    mSideKick.setSideKickNic(formBinding.dsNic.getText().toString());
                    mSideKick.setSideKickNo(formBinding.dsNum.getText().toString());

                    rapidLineViewModel.addSideKick(mSideKick).observe(this, response -> {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        if (response.equals(Responses.SIDEKICK_ADDED))
                            finish();
                    });

                }

            }

        });

    }

    private void loadDriver() {
        formBinding.dsName.setText(mDriver.getDriverName());
        formBinding.dsNum.setText(mDriver.getDriverNo());
        formBinding.dsNic.setText(mDriver.getDriverNic());

    }

    private void loadSide() {
        formBinding.dsName.setText(mSideKick.getSideKickName());
        formBinding.dsNum.setText(mSideKick.getSideKickNo());
        formBinding.dsNic.setText(mSideKick.getSideKickNic());

    }

    private Boolean isDataFieldsEmpty() {
        if (TextUtils.isEmpty(formBinding.dsName.getText())
                || TextUtils.isEmpty(formBinding.dsNum.getText())) {
            return true;
        }
        return false;
    }


}
