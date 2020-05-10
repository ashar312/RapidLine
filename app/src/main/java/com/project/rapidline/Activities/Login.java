package com.project.rapidline.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.project.rapidline.Activities.ViewData.RapidLine.RapidLineHomeActivity;
import com.project.rapidline.Activities.ViewData.SaeedSons.SaeedSonsHomeActivity;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityLoginBinding;
import com.project.rapidline.viewmodel.AdminViewModel;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    private AdminViewModel adminViewModel;

    //rapidlineapp@gmail.com
    //Rline1234
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);
        adminViewModel= ViewModelProviders.of(this).get(AdminViewModel.class);

        activityLoginBinding.loginBtn.setOnClickListener(view -> {

            if(isFieldEmpty()){
                return;
            }

            if(activityLoginBinding.radioSaeed.isChecked()){
                adminViewModel.checkAdmin(activityLoginBinding.usernameTxt.getText().toString(),
                        activityLoginBinding.passTxt.getText().toString())
                        .observe(this, response -> {

                            if(response.equals("Login Sucessfull")){
                                Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                                addCurrentStateToPref(true,activityLoginBinding.usernameTxt.getText().toString(),adminViewModel.getAdminName());
                                startActivity(new Intent(Login.this, SaeedSonsHomeActivity.class));
                                finish();
                            }

                            else {
                                Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                            }

                        });
            }
            else {
                //Rapid Line Work
                Toast.makeText(this,"Rapid Line is under Development",Toast.LENGTH_SHORT).show();
                adminViewModel.checkAdmin(activityLoginBinding.usernameTxt.getText().toString(),
                        activityLoginBinding.passTxt.getText().toString())
                        .observe(this, response -> {

                            if(response.equals("Login Sucessfull")){
                                Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                                addCurrentStateToPref(true,activityLoginBinding.usernameTxt.getText().toString(),adminViewModel.getAdminName());
                                startActivity(new Intent(Login.this, RapidLineHomeActivity.class));
                                finish();
                            }

                            else {
                                Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                            }

                        });
            }

        });

    }

    private void addCurrentStateToPref(boolean loginState,String username,String adminName) {
        SharedPreferences.Editor editor = this.getSharedPreferences("LoginPref", Context.MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.putString("adminName",adminName);
        editor.putBoolean("loggedIn", loginState);
        editor.putString("companyName",activityLoginBinding.radioSaeed.getText().toString());

        editor.apply(); //apply writes the data in background process
    }

    private boolean isFieldEmpty(){
        if(TextUtils.isEmpty(activityLoginBinding.usernameTxt.getText())){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(TextUtils.isEmpty(activityLoginBinding.passTxt.getText())){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
