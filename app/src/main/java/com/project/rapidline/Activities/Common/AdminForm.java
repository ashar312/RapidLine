package com.project.rapidline.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.BailCounters;
import com.project.rapidline.Models.Permissions;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAdminFormBinding;
import com.project.rapidline.viewmodel.SaeedSons.AdminViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class AdminForm extends AppCompatActivity {

    private ActivityAdminFormBinding adminFormBinding;
    private AdminViewModel adminViewModel;
    private BailCounters counterData;
    private String action;
    private Admins adminUpdateData;
    private String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_form);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        if (action.equals("edit")) {
            //Load the data
            adminFormBinding.usernameTxt.setEnabled(false);
            adminFormBinding.saveBtn.setText("Update Admin");
            adminId = bundle.getString("itemId");
            adminViewModel.getAdminById(adminId).observe(this, admins -> {
                adminUpdateData = admins;
                loadData();
            });
        } else {
            adminFormBinding.continueBtn.setVisibility(View.GONE);
        }


        adminViewModel.getBailCounterData().observe(this, bailCounters -> {
            counterData = bailCounters;
        });

        adminFormBinding.saveBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (action.equals("edit")) {
                adminUpdateData.setAdminName(adminFormBinding.adminNameTxt.getText().toString());
                adminUpdateData.setPass(adminFormBinding.passTxt.getText().toString());
                adminUpdateData.setContactNo(adminFormBinding.contactNoTxt.getText().toString());
                adminUpdateData.setNic(adminFormBinding.nicTxt.getText().toString());
                adminViewModel.updateAdmin(adminUpdateData);

                Toast.makeText(this, "Admin updated sucessfully", Toast.LENGTH_SHORT).show();

                finish();
            } else {
                Admins admins = new Admins();
                admins.setUsername(adminFormBinding.usernameTxt.getText().toString());
                admins.setPass(adminFormBinding.passTxt.getText().toString());

                admins.setAdminName(adminFormBinding.adminNameTxt.getText().toString());
                admins.setContactNo(adminFormBinding.contactNoTxt.getText().toString());

                admins.setMadeDateTime(Calendar.getInstance().getTime());
                admins.setMadeBy(getAdminName());
                admins.setCompanyName(getCompanyName());


                admins.setNic(adminFormBinding.nicTxt.getText().toString());

                //Set the counters for new user
                //max limit of users 20

                //Bail work
                admins.setBailSymbol(counterData.getBailSymbol());
                admins.setBailCounter(0);

                //Builty work
                int curr_range = counterData.getBuiltyCounter();
                admins.setBuiltyRange(curr_range);

                if (curr_range == 49999)
                    admins.setBuiltyCounter(0);
                else {
                    int startCounter = (curr_range - 49999) + 1;
                    admins.setBuiltyCounter(startCounter);
                }

                adminViewModel.addAdmin(admins).observe(this, response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals(Responses.ADMIN_ADDED)) {

                        //Now update the value
                        int nextvalue = counterData.getBuiltyCounter() + 49999;
                        char nextSymbol = (char) (counterData.getBailSymbol().charAt(0) + 1);

                        //set updated value

                        counterData.setBailSymbol(String.valueOf(nextSymbol));
                        counterData.setBuiltyCounter(nextvalue);
                        adminViewModel.updateBailCounter(counterData);


                        //set permissions

                        Permissions adminPerm = new Permissions();
                        adminPerm.setAddBail(true);
                        adminPerm.setDeleteBail(false);
                        adminPerm.setUpdateBail(false);

                        adminViewModel.addAdminPermissions(admins.getUsername(), adminPerm);
                        finish();

                    }
                });
            }

        });

        adminFormBinding.backBtn.setOnClickListener(view -> {
            finish();
        });

        adminFormBinding.continueBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminForm.this, AdminRightsActivity.class);
            intent.putExtra("adminId",adminId);
            startActivity(intent);
        });
    }


    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(adminFormBinding.adminNameTxt.getText())
                || TextUtils.isEmpty(adminFormBinding.usernameTxt.getText())
                || TextUtils.isEmpty(adminFormBinding.passTxt.getText())
                || TextUtils.isEmpty(adminFormBinding.contactNoTxt.getText())) {
            return true;
        }
        return false;
    }

    private String getAdminName() {
        return getApplicationContext().getSharedPreferences("LoginPref", 0).getString("adminName", "");
    }

    private String getCompanyName() {
        String name = getApplicationContext().getSharedPreferences("LoginPref", 0).getString("companyName", "");
        return name;
    }

    private void loadData() {
        adminFormBinding.adminNameTxt.setText(adminUpdateData.getAdminName());
        adminFormBinding.usernameTxt.setText(adminUpdateData.getUsername());
        adminFormBinding.passTxt.setText(adminUpdateData.getPass());
        adminFormBinding.contactNoTxt.setText(adminUpdateData.getContactNo());
        adminFormBinding.nicTxt.setText(adminUpdateData.getNic());
    }

}
