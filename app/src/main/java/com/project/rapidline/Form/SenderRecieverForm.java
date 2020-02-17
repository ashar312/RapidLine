package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.rapidline.Database.DatabaseHelper;
import com.project.rapidline.R;

public class SenderRecieverForm extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_reciever_form);
        databaseHelper = new DatabaseHelper(SenderRecieverForm.this);
    }
}
