package com.project.rapidline.Form;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

import com.project.rapidline.Database.DatabaseHelper;
import com.project.rapidline.R;


public class SenderRecieverForm extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView HeaderText;
    EditText CompanyName;
    EditText CompanyNum;
    Spinner spinner;
    EditText AddressSR;
    EditText pointOfname;
    EditText pointOfContactNo;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_reciever_form);
        ButterKnife.bind(this);
        databaseHelper = new DatabaseHelper(SenderRecieverForm.this);

        HeaderText = findViewById(R.id.HeaderText);
        CompanyName = findViewById(R.id.CompanyName);
        CompanyNum = findViewById(R.id.CompanyNum);
        spinner = findViewById(R.id.spinner);
        AddressSR = findViewById(R.id.AddressSR);
        pointOfname = findViewById(R.id.pointOfname);
        pointOfContactNo = findViewById(R.id.pointOfContactNo);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QA()){
                    boolean inserted = databaseHelper.insertData(CompanyName.getText().toString(),CompanyNum.getText().toString(),
                            "Karachi",AddressSR.getText().toString(),
                            pointOfname.getText().toString(),pointOfContactNo.getText().toString());
                    if(inserted){
                        Toast.makeText(SenderRecieverForm.this, "Data stored Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SenderRecieverForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.viewall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getAllData();
                if(cursor.getCount() == 0){
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext()){
                    stringBuffer.append("id " + cursor.getString(0) + "\n");
                    stringBuffer.append("CompanyName" + cursor.getString(1) + "\n");
                    stringBuffer.append("CompanyNum" + cursor.getString(2) + "\n");
                    stringBuffer.append("City" + cursor.getString(3) + "\n");
                    stringBuffer.append("Address" + cursor.getString(4) + "\n");
                    stringBuffer.append("PointOfContactName" + cursor.getString(5) + "\n");
                    stringBuffer.append("PointOfContactNumber" + cursor.getString(6) + "\n\n");
                }
                String str = stringBuffer.toString();
                System.out.println(str);
            }
        });

    }

    private boolean QA(){
        return true;
    }
}
