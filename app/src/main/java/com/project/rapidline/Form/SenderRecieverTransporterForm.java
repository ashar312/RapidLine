package com.project.rapidline.Form;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.utils.StaticClasses;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySenderTransporterformBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.Calendar;


public class SenderRecieverTransporterForm extends AppCompatActivity {

    private ActivitySenderTransporterformBinding senderRecieverFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private String action;
    private Customers customerEditUpdate;
    private Transporters transporterEditUpdate;
    private final String CITY_PLACEHOLDER = "Select a city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        senderRecieverFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_sender_transporterform);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        ArrayList<String> cities = (ArrayList<String>) saeedSonsViewModel.getListAllCities().clone();
        cities.add(0, CITY_PLACEHOLDER);
        //Adding spinner
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                cities);
        cityAdapter.setDropDownViewResource(R.layout.spinner_item);
        senderRecieverFormBinding.citySpinner.setAdapter(cityAdapter);


        //Used for both sender and trasporter

        Bundle bundle = getIntent().getExtras();
        String activityValue = bundle.get("ListItem").toString();
        action = bundle.get("action").toString();

        //If for sender
        if (activityValue.equals("SenderReceiver")) {
            //initialize UI
            senderRecieverFormBinding.headerText.setText(activityValue);

            if (action.equals("edit")) {
                senderRecieverFormBinding.comanyNameTxt.setEnabled(false);

                //Load the data
                String id = bundle.getString("itemId");
                saeedSonsViewModel.getCustById(id).observe(this, customers -> {
                    customerEditUpdate = customers;
                    loadCustomerData(customerEditUpdate);
                });
            }

        } else {
            //for transporter
            senderRecieverFormBinding.headerText.setText(activityValue);
            senderRecieverFormBinding.addressTxt.setVisibility(View.GONE);

            if (action.equals("edit")) {
                //Load the data
                senderRecieverFormBinding.comanyNameTxt.setEnabled(false);

                String id = bundle.getString("itemId");
                saeedSonsViewModel.getAllTransporterById(id).observe(this, transporters -> {
                    transporterEditUpdate = transporters;
                    loadTransporterData(transporterEditUpdate);
                });

            }
        }


        senderRecieverFormBinding.saveBtn.setOnClickListener(view -> {

            if (activityValue.equals("SenderReceiver")) {
                if (isCustomerDataFieldsEmpty()) {
                    Toast.makeText(SenderRecieverTransporterForm.this, "Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check for adding or updating
                if (action.equals("edit")) {
                    customerEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    customerEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    customerEditUpdate.setAddress(senderRecieverFormBinding.addressTxt.getText().toString());
                    customerEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    customerEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());

                    //add city
                    customerEditUpdate.setCity(senderRecieverFormBinding.citySpinner.
                            getSelectedItem().toString());

                    customerEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    customerEditUpdate.setMadeBy(getAdminName());

                    saeedSonsViewModel.updateCustomer(customerEditUpdate);
                    Toast.makeText(this,"Customer updated sucessfully",Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    customerEditUpdate = new Customers();
                    customerEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    customerEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    customerEditUpdate.setAddress(senderRecieverFormBinding.addressTxt.getText().toString());
                    customerEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    customerEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());

                    //add city
                    customerEditUpdate.setCity(senderRecieverFormBinding.citySpinner.
                            getSelectedItem().toString());

                    //TODO Save admin and date time
                    customerEditUpdate.setMadeBy(getAdminName());
                    customerEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.addCustomer(customerEditUpdate).observe(this,response -> {
                        Toast.makeText(this,response,Toast.LENGTH_SHORT).show();
                        if(response.equals(Responses.CUSTOMER_ADDED))
                            finish();
                    });


                }
            } else {
                if (isTranporterDataFieldsEmpty()) {
                    Toast.makeText(SenderRecieverTransporterForm.this, "Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check for adding or updating
                if (action.equals("edit")) {
                    transporterEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    transporterEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    transporterEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    transporterEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());
                    transporterEditUpdate.setCity(senderRecieverFormBinding.citySpinner.
                            getSelectedItem().toString());
                    //Save admin and date time

                    transporterEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    transporterEditUpdate.setMadeBy(getAdminName());

                    saeedSonsViewModel.updateTransporter(transporterEditUpdate);
                    Toast.makeText(this,"Transporter updated sucessfully",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    transporterEditUpdate = new Transporters();
                    transporterEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    transporterEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    transporterEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    transporterEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());
                    transporterEditUpdate.setCity(senderRecieverFormBinding.citySpinner.
                            getSelectedItem().toString());

                    //Save admin and date time
                    transporterEditUpdate.setMadeBy(getAdminName());
                    transporterEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.addTransporter(transporterEditUpdate).observe(this,response -> {
                        Toast.makeText(this,response,Toast.LENGTH_SHORT).show();
                        if(response.equals(Responses.TRANSPORTER_ADDED))
                            finish();
                    });

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
        int city=StaticClasses.cities.indexOf(transporter.getCity())+1;
        senderRecieverFormBinding.citySpinner.setSelection(city);
    }

    private void loadCustomerData(Customers customer) {
        senderRecieverFormBinding.comanyNameTxt.setText(customer.getCompanyName());
        senderRecieverFormBinding.companyNum.setText(customer.getCompanyNo());
        senderRecieverFormBinding.addressTxt.setText(customer.getAddress());
        senderRecieverFormBinding.pointOfname.setText(customer.getPocName());
        senderRecieverFormBinding.pointOfContactNo.setText(customer.getPocNo());
        int city=StaticClasses.cities.indexOf(customer.getCity())+1;
        senderRecieverFormBinding.citySpinner.setSelection(city);
    }

    private Boolean isCustomerDataFieldsEmpty() {
        if (TextUtils.isEmpty(senderRecieverFormBinding.comanyNameTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.companyNum.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.addressTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfname.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfContactNo.getText())
                || senderRecieverFormBinding.citySpinner.getSelectedItem().toString().equals(CITY_PLACEHOLDER)) {
            return true;
        }
        return false;
    }

    private Boolean isTranporterDataFieldsEmpty() {
        if (TextUtils.isEmpty(senderRecieverFormBinding.comanyNameTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.companyNum.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfname.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfContactNo.getText())
                || senderRecieverFormBinding.citySpinner.getSelectedItem().toString().equals(CITY_PLACEHOLDER)) {
            return true;
        }
        return false;
    }

    private String getAdminName(){
        return getApplicationContext().getSharedPreferences("LoginPref",0).getString("adminName","");
    }


}
