package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.AdminRights;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAdminFormBinding;
import com.project.rapidline.viewmodel.AdminViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.Calendar;

public class AdminForm extends AppCompatActivity {

    private ActivityAdminFormBinding adminFormBinding;
    private AdminViewModel adminViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_form);
        adminViewModel= ViewModelProviders.of(this).get(AdminViewModel.class);

        adminFormBinding.saveBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Admins admins = new Admins();
            admins.setUsername(adminFormBinding.usernameTxt.getText().toString());
            admins.setPass(adminFormBinding.passTxt.getText().toString());

            admins.setAdminName(adminFormBinding.adminNameTxt.getText().toString());
            admins.setContactNo(adminFormBinding.contactNoTxt.getText().toString());

            admins.setMadeDateTime(Calendar.getInstance().getTime());
            admins.setMadeBy(getAdminName());
            admins.setCompanyName(getCompanyName());

            //TODO set admin
            if (!TextUtils.isEmpty(adminFormBinding.nicTxt.getText())) {
                admins.setNic(adminFormBinding.nicTxt.getText().toString());
            }

            adminViewModel.addAdmin(admins).observe(this,response -> {
                Toast.makeText(this,response,Toast.LENGTH_SHORT).show();
                if(response.equals("Admin Sucessfully Added")){
                    startActivity(new Intent(AdminForm.this, AdminRights.class));
                    finish();
                }
            });


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

    private String getAdminName(){
        return getApplicationContext().getSharedPreferences("LoginPref",0).getString("adminName","");
    }

    private String getCompanyName()
    {
        String name= getApplicationContext().getSharedPreferences("LoginPref",0).getString("companyName","");
        return name;
    }

}
