package com.project.rapidline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.rapidline.Activities.ViewData.RapidLine.RapidLineHomeActivity;
import com.project.rapidline.Activities.ViewData.SaeedSons.SaeedSonsHomeActivity;
import com.project.rapidline.R;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIMEOUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean loggedIn = this.getSharedPreferences("LoginPref", Context.MODE_PRIVATE).getBoolean("loggedIn",false);

        new Handler().postDelayed(() -> {
            if(loggedIn){
                startActivity(new Intent(SplashActivity.this, SaeedSonsHomeActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(SplashActivity.this,Login.class));
                finish();
            }
        },SPLASH_TIMEOUT);

    }
}
