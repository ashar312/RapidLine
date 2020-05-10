package com.project.rapidline.Activities.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.BailCounters;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAdminFormBinding;
import com.project.rapidline.viewmodel.AdminViewModel;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.Calendar;

public class AdminForm extends AppCompatActivity {

    private ActivityAdminFormBinding adminFormBinding;
    private AdminViewModel adminViewModel;
    private BailCounters counterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_form);
        adminViewModel= ViewModelProviders.of(this).get(AdminViewModel.class);


        adminViewModel.getBailCounterData().observe(this,bailCounters -> {
            counterData=bailCounters;
        });

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

            //Set the counters for new user
            //max limit of users 20

            //Bail work
            admins.setBailSymbol(counterData.getBailSymbol());
            admins.setBailCounter(0);

            //Builty work
            int curr_range=counterData.getBuiltyCounter();
            admins.setBuiltyRange(curr_range);

            if(curr_range==49999)
                admins.setBuiltyCounter(0);
            else{
                int startCounter=(curr_range-49999)+1;
                admins.setBuiltyCounter(startCounter);
            }

            adminViewModel.addAdmin(admins).observe(this,response -> {
                Toast.makeText(this,response,Toast.LENGTH_SHORT).show();
                if(response.equals(Responses.ADMIN_ADDED)){

                    //Now update the value
                    int nextvalue=counterData.getBuiltyCounter()+49999;
                    char nextSymbol= (char) (counterData.getBailSymbol().charAt(0)+1);

                    //set updated value

                    counterData.setBailSymbol(String.valueOf(nextSymbol));
                    counterData.setBuiltyCounter(nextvalue);
                    adminViewModel.updateBailCounter(counterData);

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
