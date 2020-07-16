package com.project.rapidline.Activities.SaeedSons.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rapidline.Activities.RapidLine.Forms.AddBiltyForm;
import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.Models.SaeedSons.Cities;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.KindOfItem;
import com.project.rapidline.Models.SaeedSons.Transporters;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAddBailFormBinding;
import com.project.rapidline.viewmodel.SaeedSons.AdminViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddBailForm extends AppCompatActivity {

    private ActivityAddBailFormBinding activityAddBailFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private Bails bailEditUpdate;
    private String action;
    private ArrayAdapter<Customers> senderAdap;
    private AdminViewModel adminViewModel;
    private Admins adminInfo;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddBailFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bail_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);


        adminViewModel= ViewModelProviders.of(this).get(AdminViewModel.class);
        username=getApplicationContext().getSharedPreferences("LoginPref",0).getString("username","");
        adminViewModel.getAdminInfo(username).observe(this,admins -> {
            adminInfo=admins;
        });


        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        if (action.equals("edit")) {
            //Load data
            String id = bundle.getString("itemId");
            saeedSonsViewModel.getBailById(id).observe(this, bail -> {
                bailEditUpdate = bail;
                loadData();
            });
        }
        initialize();

        activityAddBailFormBinding.saveBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (action.equals("edit")) {

                bailEditUpdate.setFromCity(activityAddBailFormBinding.fromSpinner.getSelectedItem().toString());
                bailEditUpdate.setToCity(activityAddBailFormBinding.toSpinner.getSelectedItem().toString());
                bailEditUpdate.setKindId(activityAddBailFormBinding.kindSpinner.getSelectedItem().toString());
                bailEditUpdate.setSenderId(activityAddBailFormBinding.senderSpiner.getSelectedItem().toString());
                bailEditUpdate.setReceiverId(activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString());
                bailEditUpdate.setTransporterId(activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString());
                bailEditUpdate.setAgentId(activityAddBailFormBinding.agentSpinner.getSelectedItem().toString());

                bailEditUpdate.setVolume(Double.valueOf(activityAddBailFormBinding.volumeTxt.getText().toString()));
                bailEditUpdate.setWeight(Double.valueOf(activityAddBailFormBinding.weightTxt.getText().toString()));
                bailEditUpdate.setQuantity(getIntQuantity(activityAddBailFormBinding.quanTxt.getText().toString()));

                bailEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                bailEditUpdate.setMadeBy(getAdminName());

                bailEditUpdate.setTransport_charge(activityAddBailFormBinding.transportTxt.getText().toString());
                bailEditUpdate.setLabour_charge(activityAddBailFormBinding.laboutTxt.getText().toString());
                bailEditUpdate.setElectricity_charge(activityAddBailFormBinding.electricityTxt.getText().toString());
                bailEditUpdate.setPacking_charge(activityAddBailFormBinding.packingTxt.getText().toString());
                bailEditUpdate.setComments(activityAddBailFormBinding.commentsTxt.getText().toString());

                //TODO add admin field
                saeedSonsViewModel.updateBail(bailEditUpdate);
                Toast.makeText(this,"Bail updated sucessfully",Toast.LENGTH_SHORT).show();
                finish();

            } else {
                bailEditUpdate = new Bails();

                //Genrate builty no
                bailEditUpdate.setBiltyNo(generateBuiltyNo());


                bailEditUpdate.setFromCity(activityAddBailFormBinding.fromSpinner.getSelectedItem().toString());
                bailEditUpdate.setToCity(activityAddBailFormBinding.toSpinner.getSelectedItem().toString());
                bailEditUpdate.setKindId(activityAddBailFormBinding.kindSpinner.getSelectedItem().toString());
                bailEditUpdate.setSenderId(activityAddBailFormBinding.senderSpiner.getSelectedItem().toString());
                bailEditUpdate.setReceiverId(activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString());
                bailEditUpdate.setTransporterId(activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString());
                bailEditUpdate.setAgentId(activityAddBailFormBinding.agentSpinner.getSelectedItem().toString());

                bailEditUpdate.setVolume(Double.valueOf(activityAddBailFormBinding.volumeTxt.getText().toString()));
                bailEditUpdate.setWeight(Double.valueOf(activityAddBailFormBinding.weightTxt.getText().toString()));
                bailEditUpdate.setQuantity(getIntQuantity(activityAddBailFormBinding.quanTxt.getText().toString()));

                bailEditUpdate.setMadeBy(getAdminName());
                bailEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());


                bailEditUpdate.setTransport_charge(activityAddBailFormBinding.transportTxt.getText().toString());
                bailEditUpdate.setLabour_charge(activityAddBailFormBinding.laboutTxt.getText().toString());
                bailEditUpdate.setElectricity_charge(activityAddBailFormBinding.electricityTxt.getText().toString());
                bailEditUpdate.setPacking_charge(activityAddBailFormBinding.packingTxt.getText().toString());
                bailEditUpdate.setComments(activityAddBailFormBinding.commentsTxt.getText().toString());

                saeedSonsViewModel.addBail(bailEditUpdate);

                Toast.makeText(this,"Bail added sucessfully",Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        activityAddBailFormBinding.backBtn.setOnClickListener(view -> finish());
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

    private void initialize() {

//        //Load cities
//        ArrayList<String> citiesFrom = (ArrayList<String>) saeedSonsViewModel.getListAllCities().clone();
//        citiesFrom.add(0, "From");
//        ArrayAdapter<String> cityFromAdapter = new ArrayAdapter<>(this,
//                R.layout.spinner_item,
//                citiesFrom);
//        cityFromAdapter.setDropDownViewResource(R.layout.spinner_item);
//        activityAddBailFormBinding.fromSpinner.setAdapter(cityFromAdapter);
//
//
//        ArrayList<String> citiesTo = (ArrayList<String>) saeedSonsViewModel.getListAllCities().clone();
//        citiesTo.add(0, "To");
//        ArrayAdapter<String> cityToAdapter = new ArrayAdapter<>(this,
//                R.layout.spinner_item,
//                citiesTo);
//        cityToAdapter.setDropDownViewResource(R.layout.spinner_item);
//        activityAddBailFormBinding.toSpinner.setAdapter(cityToAdapter);


        //Load observers
        saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
            //Sender List
            List<Customers> senderList = new ArrayList<>(customers);
            senderList.add(0, new Customers("Select a Sender"));
            senderAdap= new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, senderList);
            activityAddBailFormBinding.senderSpiner.setAdapter(senderAdap);


            //Receiver List
            List<Customers> receiverList = new ArrayList<>(customers);
            receiverList.add(0, new Customers("Select a Receiver"));
            ArrayAdapter<Customers> receiverArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, receiverList);
            activityAddBailFormBinding.receiverSpinner.setAdapter(receiverArrayAdapter);

        });

        saeedSonsViewModel.getListAllTransporters().observe(this, transporters -> {
            List<Transporters> transportersList = new ArrayList<>(transporters);
            transportersList.add(0, new Transporters("Select a Transporter"));

            ArrayAdapter<Transporters> arrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, transportersList);
            activityAddBailFormBinding.transporterSpinner.setAdapter(arrayAdapter);


        });

        saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
            List<Agents> agentsList = new ArrayList<>(agents);
            agentsList.add(0, new Agents("Select a agent"));

            ArrayAdapter<Agents> agentsArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, agentsList);
            agentsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            activityAddBailFormBinding.agentSpinner.setAdapter(agentsArrayAdapter);

        });

        //Load kind of data
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            List<KindOfItem> itemList = new ArrayList<>(kindOfItems);
            itemList.add(0, new KindOfItem("Select a kind"));

            ArrayAdapter<KindOfItem> itemArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, itemList);
            itemArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            activityAddBailFormBinding.kindSpinner.setAdapter(itemArrayAdapter);

        });

        //Load cities
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            List<String> cityList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currCity = jsonObject.getString("city");
                cityList.add(currCity);
            }

            List<String> fromCityList = new ArrayList<>(cityList);
            fromCityList.add(0, "From");

            ArrayAdapter<String> fromCityArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, fromCityList);
            fromCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            List<String> toCityList = new ArrayList<>(cityList);
            toCityList.add(0, "To");

            ArrayAdapter<String> toCityArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, toCityList);
            toCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);


            activityAddBailFormBinding.fromSpinner.setAdapter(fromCityArrayAdapter);
            activityAddBailFormBinding.toSpinner.setAdapter(toCityArrayAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }




        //Set Listeners
        activityAddBailFormBinding.addQuanBtn.setOnClickListener(view -> {
            String quan = activityAddBailFormBinding.quanTxt.getText().toString();
            int val = Integer.parseInt(quan.substring(quan.length() - 1));
            val++;
            activityAddBailFormBinding.quanTxt.setText("Qty: " + String.valueOf(val));
        });

        activityAddBailFormBinding.subractQuanBtn.setOnClickListener(view -> {
            String quan = activityAddBailFormBinding.quanTxt.getText().toString();
            int val = Integer.parseInt(String.valueOf(quan.charAt(quan.length() - 1)));
            if (val == 1) {
                Toast.makeText(this, "Min quantity reached",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            val = val - 1;
            activityAddBailFormBinding.quanTxt.setText("Qty: " + String.valueOf(val));
        });

        activityAddBailFormBinding.transportChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                activityAddBailFormBinding.transportTxt.setEnabled(true);
                activityAddBailFormBinding.transportTxt.requestFocus();
            }
            else {
                activityAddBailFormBinding.transportTxt.setEnabled(false);
            }
        });

        activityAddBailFormBinding.labourChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                activityAddBailFormBinding.laboutTxt.setEnabled(true);
                activityAddBailFormBinding.laboutTxt.requestFocus();
            }
            else {
                activityAddBailFormBinding.laboutTxt.setEnabled(false);
            }
        });

        activityAddBailFormBinding.electricityChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                activityAddBailFormBinding.electricityTxt.setEnabled(true);
                activityAddBailFormBinding.electricityTxt.requestFocus();
            }
            else {
                activityAddBailFormBinding.electricityTxt.setEnabled(false);
            }
        });

        activityAddBailFormBinding.packingChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                activityAddBailFormBinding.packingTxt.setEnabled(true);
                activityAddBailFormBinding.packingTxt.requestFocus();
            }
            else {
                activityAddBailFormBinding.packingTxt.setEnabled(false);
            }
        });


    }

    private void loadData() {
        activityAddBailFormBinding.quanTxt.setText("Qty: " + bailEditUpdate.getQuantity());
        activityAddBailFormBinding.volumeTxt.setText(String.valueOf(bailEditUpdate.getVolume()));
        activityAddBailFormBinding.weightTxt.setText(String.valueOf(bailEditUpdate.getWeight()));

        if(!TextUtils.isEmpty(bailEditUpdate.getTransport_charge())){
            activityAddBailFormBinding.transportChk.setChecked(true);
            activityAddBailFormBinding.transportTxt.setText(bailEditUpdate.getTransport_charge());
        }
        if(!TextUtils.isEmpty(bailEditUpdate.getLabour_charge())){
            activityAddBailFormBinding.labourChk.setChecked(true);
            activityAddBailFormBinding.laboutTxt.setText(bailEditUpdate.getLabour_charge());
        }
        if(!TextUtils.isEmpty(bailEditUpdate.getElectricity_charge())){
            activityAddBailFormBinding.electricityChk.setChecked(true);
            activityAddBailFormBinding.electricityTxt.setText(bailEditUpdate.getElectricity_charge());
        }
        if(!TextUtils.isEmpty(bailEditUpdate.getPacking_charge())){
            activityAddBailFormBinding.packingChk.setChecked(true);
            activityAddBailFormBinding.packingTxt.setText(bailEditUpdate.getPacking_charge());
        }
        activityAddBailFormBinding.commentsTxt.setText(bailEditUpdate.getComments());



        int senderVal=getIndex(activityAddBailFormBinding.senderSpiner,bailEditUpdate.getSenderId());
        activityAddBailFormBinding.senderSpiner.setSelection(senderVal);

        int receiverVal=getIndex(activityAddBailFormBinding.receiverSpinner,bailEditUpdate.getReceiverId());
        activityAddBailFormBinding.receiverSpinner.setSelection(receiverVal);

        int transVal=getIndex(activityAddBailFormBinding.transporterSpinner,bailEditUpdate.getTransporterId());
        activityAddBailFormBinding.transporterSpinner.setSelection(transVal);

        int agentVal=getIndex(activityAddBailFormBinding.agentSpinner,bailEditUpdate.getAgentId());
        activityAddBailFormBinding.agentSpinner.setSelection(agentVal);

        int itemVal=getIndex(activityAddBailFormBinding.kindSpinner,bailEditUpdate.getKindId());
        activityAddBailFormBinding.kindSpinner.setSelection(itemVal);

        int fromCityVal=getIndex(activityAddBailFormBinding.fromSpinner,bailEditUpdate.getFromCity());
        activityAddBailFormBinding.fromSpinner.setSelection(fromCityVal);

        int toCityVal=getIndex(activityAddBailFormBinding.toSpinner,bailEditUpdate.getToCity());
        activityAddBailFormBinding.toSpinner.setSelection(toCityVal);




    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


    private boolean isFieldEmpty() {
        if (activityAddBailFormBinding.fromSpinner.getSelectedItem().toString().equals("From")
                || activityAddBailFormBinding.toSpinner.getSelectedItem().toString().equals("To")
                || activityAddBailFormBinding.kindSpinner.getSelectedItem().toString().equals("Select a kind")
                || activityAddBailFormBinding.senderSpiner.getSelectedItem().toString().equals("Select a Sender")
                || activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString().equals("Select a Receiver")
                || activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString().equals("Select a Transporter")
                || activityAddBailFormBinding.agentSpinner.getSelectedItem().toString().equals("Select a agent")
                || TextUtils.isEmpty(activityAddBailFormBinding.volumeTxt.getText())
                || TextUtils.isEmpty(activityAddBailFormBinding.weightTxt.getText())
        ) {
            return true;
        }
        return false;
    }

    private String generateBuiltyNo() {
        String symbol=adminInfo.getBailSymbol();
        int value=adminInfo.getBailCounter();

        //format value
        String formatted_value=String.format(Locale.ENGLISH, "%03d", value);
        String bailId=formatted_value+""+symbol;

        //update value
        int updated_val=value+1;
        adminViewModel.updateAdminBailInfo(updated_val,username);


        return bailId;
    }

    private int getIntQuantity(String quan){
         int val = Integer.parseInt(String.valueOf(quan.charAt(quan.length() - 1)));
         return val;
    }
    private String getAdminName(){
        return getApplicationContext().getSharedPreferences("LoginPref",0).getString("adminName","");
    }
}
