package com.project.rapidline.Activities.ViewData.RapidLine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.project.rapidline.Activities.Forms.RapidLine.VechileForm;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityVechilesBinding;

public class VechilesActivity extends AppCompatActivity {

    private ActivityVechilesBinding vechilesBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vechilesBinding= DataBindingUtil.setContentView(this,R.layout.activity_vechiles);
        vechilesBinding.addCommonBtn.setOnClickListener(view -> {
            startActivity(new Intent(VechilesActivity.this, VechileForm.class));
        });
    }
}
