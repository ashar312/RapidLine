package com.project.rapidline.Form;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySenderTransporterformBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.Calendar;


public class SenderRecieverTransporterForm extends AppCompatActivity {

    private ActivitySenderTransporterformBinding senderRecieverFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private String action;
    private Customers customerEditUpdate;
    private Transporters transporterEditUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        senderRecieverFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_sender_transporterform);

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
                customerEditUpdate=saeedSonsViewModel.getCustById(id);
                loadCustomerData(customerEditUpdate);
            }

        }
        else {
            //for transporter
            senderRecieverFormBinding.headerText.setText(activityValue);
            senderRecieverFormBinding.addressTxt.setVisibility(View.GONE);

            if (action.equals("edit")) {
                //Load the data
                long id = bundle.getLong("itemId");
                transporterEditUpdate=saeedSonsViewModel.getAllTransporterById(id);
                loadTransporterData(transporterEditUpdate);
            }
        }

        Button button = findViewById(R.id.saveBtn);

        senderRecieverFormBinding.saveBtn.setOnClickListener(view -> {

            if (activityValue.equals("SenderReceiver")) {
                if(isCustomerDataFieldsEmpty()){
                    Toast.makeText(SenderRecieverTransporterForm.this,"Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //Check for adding or updating
                if (action.equals("edit")){
                    customerEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    customerEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    customerEditUpdate.setAddress(senderRecieverFormBinding.addressTxt.getText().toString());
                    customerEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    customerEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());
                    //TODO Save admin and date time

                    customerEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.updateCustomer(customerEditUpdate);
                }
                else {
                    customerEditUpdate=new Customers();
                    customerEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    customerEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    customerEditUpdate.setAddress(senderRecieverFormBinding.addressTxt.getText().toString());
                    customerEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    customerEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());

                    //TODO Save admin and date time
                    customerEditUpdate.setMadeBy(1);
                    customerEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.addCustomer(customerEditUpdate);


                }
            }
            else {
                if(isTranporterDataFieldsEmpty()){
                    Toast.makeText(SenderRecieverTransporterForm.this,"Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check for adding or updating
                if (action.equals("edit")){
                    transporterEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    transporterEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    transporterEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    transporterEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());
                    //Save admin and date time

                    transporterEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.updateTransporter(transporterEditUpdate);
                }
                else {
                    transporterEditUpdate=new Transporters();
                    transporterEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    transporterEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    transporterEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    transporterEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());

                    //Save admin and date time
                    transporterEditUpdate.setMadeBy(1);
                    transporterEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.addTransporter(transporterEditUpdate);

                }
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

    private Boolean isCustomerDataFieldsEmpty(){
        if(TextUtils.isEmpty(senderRecieverFormBinding.comanyNameTxt.getText())
        || TextUtils.isEmpty(senderRecieverFormBinding.companyNum.getText())
        || TextUtils.isEmpty(senderRecieverFormBinding.addressTxt.getText())
        || TextUtils.isEmpty(senderRecieverFormBinding.pointOfname.getText())
        || TextUtils.isEmpty(senderRecieverFormBinding.pointOfContactNo.getText())){
            return true;
        }
        return false;
    }

    private Boolean isTranporterDataFieldsEmpty(){
        if(TextUtils.isEmpty(senderRecieverFormBinding.comanyNameTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.companyNum.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfname.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfContactNo.getText())){
            return true;
        }
        return false;
    }


}
