package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.project.rapidline.R;

public class LabourForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_patri_form);
        try {
            String HeadText = getIntent().getExtras().getString("ListItem");
            TextView textView = findViewById(R.id.headerText);
            textView.setText(HeadText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
