package com.project.rapidline.Activities.SaeedSons.Forms;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.rapidline.Activities.RapidLine.Forms.AddBiltyForm;
import com.project.rapidline.Models.SaeedSons.Cities;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.Transporters;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySenderTransporterformBinding;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SenderRecieverTransporterForm extends AppCompatActivity {

    private ActivitySenderTransporterformBinding senderRecieverFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private String action;
    private Customers customerEditUpdate;
    private Transporters transporterEditUpdate;
    private final String CITY_PLACEHOLDER = "Select a city";

    private List<String> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        senderRecieverFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_sender_transporterform);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

//        ArrayList<String> cities = (ArrayList<String>) saeedSonsViewModel.getListAllCities().clone();
//        cities.add(0, CITY_PLACEHOLDER);
//        //Adding spinner
//        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
//                R.layout.spinner_item,
//                cities);
//        cityAdapter.setDropDownViewResource(R.layout.spinner_item);
//        senderRecieverFormBinding.citySpinner.setAdapter(cityAdapter);

        //Load cities
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            cityList = new ArrayList<>();
            cityList.add(0, CITY_PLACEHOLDER);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currCity = jsonObject.getString("city");
                cityList.add(currCity);
            }


            ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, cityList);
            cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            senderRecieverFormBinding.citySpinner.setAdapter(cityArrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


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
            senderRecieverFormBinding.headerText.setText("Transporter");
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

                if(getCityIndex(senderRecieverFormBinding.citySpinner.getText().toString())==-1){
                    Toast.makeText(SenderRecieverTransporterForm.this, "Please select city from list only",
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
                            getText().toString());

                    customerEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    customerEditUpdate.setMadeBy(getAdminName());

                    saeedSonsViewModel.updateCustomer(customerEditUpdate);
                    Toast.makeText(this, "Customer updated sucessfully", Toast.LENGTH_SHORT).show();
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
                            getText().toString());

                    //TODO Save admin and date time
                    customerEditUpdate.setMadeBy(getAdminName());
                    customerEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.addCustomer(customerEditUpdate).observe(this, response -> {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        if (response.equals(Responses.CUSTOMER_ADDED))
                            finish();
                    });


                }
            } else {
                if (isTranporterDataFieldsEmpty()) {
                    Toast.makeText(SenderRecieverTransporterForm.this, "Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(getCityIndex(senderRecieverFormBinding.citySpinner.getText().toString())==-1){
                    Toast.makeText(SenderRecieverTransporterForm.this, "Please select city from list only",
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
                            getText().toString());
                    //Save admin and date time

                    transporterEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    transporterEditUpdate.setMadeBy(getAdminName());

                    saeedSonsViewModel.updateTransporter(transporterEditUpdate);
                    Toast.makeText(this, "Transporter updated sucessfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    transporterEditUpdate = new Transporters();
                    transporterEditUpdate.setCompanyName(senderRecieverFormBinding.comanyNameTxt.getText().toString());
                    transporterEditUpdate.setCompanyNo(senderRecieverFormBinding.companyNum.getText().toString());
                    transporterEditUpdate.setPocName(senderRecieverFormBinding.pointOfname.getText().toString());
                    transporterEditUpdate.setPocNo(senderRecieverFormBinding.pointOfContactNo.getText().toString());
                    transporterEditUpdate.setCity(senderRecieverFormBinding.citySpinner.
                            getText().toString());

                    //Save admin and date time
                    transporterEditUpdate.setMadeBy(getAdminName());
                    transporterEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.addTransporter(transporterEditUpdate).observe(this, response -> {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        if (response.equals(Responses.TRANSPORTER_ADDED))
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

        senderRecieverFormBinding.backBtn.setOnClickListener(view -> finish());
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("pk.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadTransporterData(Transporters transporter) {
        senderRecieverFormBinding.comanyNameTxt.setText(transporter.getCompanyName());
        senderRecieverFormBinding.companyNum.setText(transporter.getCompanyNo());
        senderRecieverFormBinding.pointOfname.setText(transporter.getPocName());
        senderRecieverFormBinding.pointOfContactNo.setText(transporter.getPocNo());


//        int city=StaticClasses.cities.indexOf(transporter.getCity())+1;
//        senderRecieverFormBinding.citySpinner.setSelection(city);

        senderRecieverFormBinding.citySpinner.setText(transporter.getCity());

    }

    private void loadCustomerData(Customers customer) {
        senderRecieverFormBinding.comanyNameTxt.setText(customer.getCompanyName());
        senderRecieverFormBinding.companyNum.setText(customer.getCompanyNo());
        senderRecieverFormBinding.addressTxt.setText(customer.getAddress());
        senderRecieverFormBinding.pointOfname.setText(customer.getPocName());
        senderRecieverFormBinding.pointOfContactNo.setText(customer.getPocNo());

        senderRecieverFormBinding.citySpinner.setText(customer.getCity());
    }

    private Boolean isCustomerDataFieldsEmpty() {
        if (TextUtils.isEmpty(senderRecieverFormBinding.comanyNameTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.companyNum.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.addressTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfname.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfContactNo.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.citySpinner.getText())) {
            return true;
        }
        return false;
    }

    private Boolean isTranporterDataFieldsEmpty() {
        if (TextUtils.isEmpty(senderRecieverFormBinding.comanyNameTxt.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.companyNum.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfname.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.pointOfContactNo.getText())
                || TextUtils.isEmpty(senderRecieverFormBinding.citySpinner.getText())) {
            return true;
        }
        return false;
    }

    private String getAdminName() {
        return getApplicationContext().getSharedPreferences("LoginPref", 0).getString("adminName", "");
    }

    private int getCityIndex(String city) {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).equals(city)) {
                return i;
            }
        }
        return -1;
    }


}
