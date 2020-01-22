package com.project.rapidline.Form;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.rapidline.R;

public class PatriForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_patri_form);
        String HeadText = getIntent().getExtras().getString("ListItem");
        TextView textView = findViewById(R.id.HeaderText);
        textView.setText(HeadText);
    }
}
