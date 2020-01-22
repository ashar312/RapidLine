package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.rapidline.R;

public class AgentForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_form);
        String HeadText = getIntent().getExtras().getString("ListItem");
    }
}
