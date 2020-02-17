package com.project.rapidline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.rapidline.HomeScreens.AddActivities.ListActivities;
import com.project.rapidline.HomeScreens.Home;

public class Login extends AppCompatActivity {


    //rapidlineapp@gmail.com
    //Rline1234
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.LoginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Home.class));
            }
        });
    }
}
