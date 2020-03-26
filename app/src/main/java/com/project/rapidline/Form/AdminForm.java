package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.AdminRights;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAdminFormBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class AdminForm extends AppCompatActivity {

    private ActivityAdminFormBinding adminFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

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
            //TODO set admin
            if (!TextUtils.isEmpty(adminFormBinding.nicTxt.getText())) {
                admins.setNic(adminFormBinding.nicTxt.getText().toString());
            }

            saeedSonsViewModel.addAdmin(admins);
           startActivity(new Intent(AdminForm.this, AdminRights.class));

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

}
