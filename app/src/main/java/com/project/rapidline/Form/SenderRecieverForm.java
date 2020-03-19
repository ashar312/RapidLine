package com.project.rapidline.Form;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.project.rapidline.Database.DatabaseHelper;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySenderRecieverFormBinding;


public class SenderRecieverForm extends AppCompatActivity {

    private ActivitySenderRecieverFormBinding senderRecieverFormBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        senderRecieverFormBinding= DataBindingUtil.setContentView(this,R.layout.activity_sender_reciever_form);

        //Set header text
        //Use for both sender and trasporter and for adding and updating

        //If for sender
        senderRecieverFormBinding.headerText.setText("Sender/Receiver");
        //check for adding or updating
        //if for transporter
        senderRecieverFormBinding.headerText.setText("Transporters");
        senderRecieverFormBinding.addressTxt.setVisibility(View.GONE);
        //chk for adding or updating


        Button button = findViewById(R.id.saveBtn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(QA()){
//                    boolean inserted = databaseHelper.insertData(CompanyName.getText().toString(),CompanyNum.getText().toString(),
//                            "Karachi",AddressSR.getText().toString(),
//                            pointOfname.getText().toString(),pointOfContactNo.getText().toString());
//                    if(inserted){
//                        Toast.makeText(SenderRecieverForm.this, "Data stored Successfully", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(SenderRecieverForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

    }

    private boolean QA(){
        return true;
    }
}
