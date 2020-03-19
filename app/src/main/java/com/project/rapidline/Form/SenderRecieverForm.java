package com.project.rapidline.Form;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProviders;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.project.rapidline.Database.DatabaseHelper;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySenderRecieverFormBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;


public class SenderRecieverForm extends AppCompatActivity {

    private ActivitySenderRecieverFormBinding senderRecieverFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;

    private String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        senderRecieverFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_sender_reciever_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        //Used for both sender and trasporter

        Bundle bundle = getIntent().getExtras();
        String activityValue = bundle.get("ListItem").toString();
        action = bundle.get("action").toString();

        //If for sender
        if (activityValue.equals("SenderReceiver")) {
            //initialize UI
            senderRecieverFormBinding.headerText.setText(activityValue);

            if (action.equals("edit")) {
                //Load the data
                long id = bundle.getLong("itemId");
                loadCustomerData(saeedSonsViewModel.getCustById(id));
            }

        }
        else {
            //for transporter
            senderRecieverFormBinding.headerText.setText(activityValue);
            senderRecieverFormBinding.addressTxt.setVisibility(View.GONE);

            if (action.equals("edit")) {
                //Load the data
                long id = bundle.getLong("itemId");
                loadTransporterData(saeedSonsViewModel.getAllTransporterById(id));
            }
        }

        Button button = findViewById(R.id.saveBtn);

        senderRecieverFormBinding.saveBtn.setOnClickListener(view -> {
            //Check for adding or updating
            if (action.equals("edit")){

            }
            else {

            }

        });

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

    private void loadTransporterData(Transporters transporter) {
        senderRecieverFormBinding.comanyNameTxt.setText(transporter.getCompanyName());
        senderRecieverFormBinding.companyNum.setText(transporter.getCompanyNo());
        senderRecieverFormBinding.pointOfname.setText(transporter.getPocName());
        senderRecieverFormBinding.pointOfContactNo.setText(transporter.getPocNo());
    }

    private void loadCustomerData(Customers customer) {
        senderRecieverFormBinding.comanyNameTxt.setText(customer.getCompanyName());
        senderRecieverFormBinding.companyNum.setText(customer.getCompanyNo());
        senderRecieverFormBinding.addressTxt.setText(customer.getAddress());
        senderRecieverFormBinding.pointOfname.setText(customer.getPocName());
        senderRecieverFormBinding.pointOfContactNo.setText(customer.getPocNo());
    }

}
