package com.project.rapidline.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.rapidline.Activities.RapidLine.ViewData.RapidLineHomeActivity;
import com.project.rapidline.Activities.SaeedSons.ViewData.SaeedSonsHomeActivity;
import com.project.rapidline.R;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        boolean loggedIn = this.getSharedPreferences("LoginPref", Context.MODE_PRIVATE).getBoolean("loggedIn", false);

        new Handler().postDelayed(() -> {
//            if (loggedIn) {
//                startActivity(new Intent(SplashActivity.this, SaeedSonsHomeActivity.class));
//                finish();
//            } else {
                startActivity(new Intent(SplashActivity.this, SaeedSonsHomeActivity.class));
                finish();
//            }
        }, SPLASH_TIMEOUT);

    }

}
