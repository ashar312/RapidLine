package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Models.StaticClasses;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAddBailFormBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AddBailForm extends AppCompatActivity {

    private ActivityAddBailFormBinding activityAddBailFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private Bails bailEditUpdate;
    private String action;
    private ArrayAdapter<Customers> senderAdap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddBailFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bail_form);
        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);


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

    }

    private void initialize() {

        //Load cities
        ArrayList<String> citiesFrom = (ArrayList<String>) saeedSonsViewModel.getListAllCities().clone();
        citiesFrom.add(0, "From");
        ArrayAdapter<String> cityFromAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                citiesFrom);
        cityFromAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityAddBailFormBinding.fromSpinner.setAdapter(cityFromAdapter);


        ArrayList<String> citiesTo = (ArrayList<String>) saeedSonsViewModel.getListAllCities().clone();
        citiesTo.add(0, "To");
        ArrayAdapter<String> cityToAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                citiesTo);
        cityToAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityAddBailFormBinding.toSpinner.setAdapter(cityToAdapter);


        //Load observers
        saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
            //Sender List
            List<Customers> senderList = new ArrayList<>(customers);
            senderList.add(0, new Customers("Sender"));
            senderAdap= new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, senderList);
            activityAddBailFormBinding.senderSpiner.setAdapter(senderAdap);


            //Receiver List
            List<Customers> receiverList = new ArrayList<>(customers);
            receiverList.add(0, new Customers("Receiver"));
            ArrayAdapter<Customers> receiverArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, receiverList);
            activityAddBailFormBinding.receiverSpinner.setAdapter(receiverArrayAdapter);


            if (action.equals("edit")) {
//                int pos=senderArrayAdapter.getPosition(new Customers(bailEditUpdate.getSenderId()));
//                activityAddBailFormBinding.senderSpiner.setSelection(pos);

//                activityAddBailFormBinding.fromSpinner.setSelection(bailEditUpdate.getReceiverId());
            }

        });

        saeedSonsViewModel.getListAllTransporters().observe(this, transporters -> {
            List<Transporters> transportersList = new ArrayList<>(transporters);
            transportersList.add(0, new Transporters("Transporter"));

            ArrayAdapter<Transporters> arrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, transportersList);
            activityAddBailFormBinding.transporterSpinner.setAdapter(arrayAdapter);

            if (action.equals("edit")) {
                //   activityAddBailFormBinding.transporterSpinner.setSelection(bailEditUpdate.getTransporterId());
            }
        });

        saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
            List<Agents> agentsList = new ArrayList<>(agents);
            agentsList.add(0, new Agents("Agent"));

            ArrayAdapter<Agents> agentsArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, agentsList);
            activityAddBailFormBinding.agentSpinner.setAdapter(agentsArrayAdapter);

            if (action.equals("edit")) {
                // activityAddBailFormBinding.agentSpinner.setSelection(bailEditUpdate.getAgentId());
            }
        });

        //Load kind of data
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            List<KindOfItem> itemList = new ArrayList<>(kindOfItems);
            itemList.add(0, new KindOfItem("Kind"));

            ArrayAdapter<KindOfItem> itemArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, itemList);
            itemArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            activityAddBailFormBinding.kindSpinner.setAdapter(itemArrayAdapter);

            if (action.equals("edit")) {
//                activityAddBailFormBinding.kindSpinner.setSelection(bailEditUpdate.getKindId());
            }
        });

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
        activityAddBailFormBinding.fromSpinner.setSelection(StaticClasses.cities.indexOf(bailEditUpdate.getFromCity()));
        activityAddBailFormBinding.toSpinner.setSelection(StaticClasses.cities.indexOf(bailEditUpdate.getToCity()));

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
                || activityAddBailFormBinding.kindSpinner.getSelectedItem().toString().equals("Kind")
                || activityAddBailFormBinding.senderSpiner.getSelectedItem().toString().equals("Sender")
                || activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString().equals("Receiver")
                || activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString().equals("Transporter")
                || activityAddBailFormBinding.agentSpinner.getSelectedItem().toString().equals("Agent")
                || TextUtils.isEmpty(activityAddBailFormBinding.volumeTxt.getText())
                || TextUtils.isEmpty(activityAddBailFormBinding.weightTxt.getText())
        ) {
            return true;
        }
        return false;
    }

    private String generateBuiltyNo() {
        //TODO generate bailID
        Random random = new Random();
        return String.valueOf(random.nextInt());
    }

    private int getIntQuantity(String quan){
         int val = Integer.parseInt(String.valueOf(quan.charAt(quan.length() - 1)));
         return val;
    }
    private String getAdminName(){
        return getApplicationContext().getSharedPreferences("LoginPref",0).getString("adminName","");
    }
}
