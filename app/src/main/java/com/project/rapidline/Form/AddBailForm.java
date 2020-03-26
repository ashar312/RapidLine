package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAddBailFormBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBailForm extends AppCompatActivity {

    private ActivityAddBailFormBinding activityAddBailFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private Bails bailEditUpdate;
    private String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddBailFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bail_form);
        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);


        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();
        if (action.equals("edit")) {
            //Load data
            long id = bundle.getLong("itemId");
            bailEditUpdate = saeedSonsViewModel.getBailById(id);
            //Load data
            loadData();
        }
        initialize();

        activityAddBailFormBinding.saveBtn.setOnClickListener(view -> {
            if(isFieldEmpty()){
                Toast.makeText(this,"Fields are empty",Toast.LENGTH_SHORT).show();
                return;
            }

            if(action.equals("edit")){

                bailEditUpdate.setFromCity(activityAddBailFormBinding.fromSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setToCity(activityAddBailFormBinding.toSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setKindId(activityAddBailFormBinding.kindSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setSenderId(activityAddBailFormBinding.senderSpiner.getSelectedItemPosition()-1);
                bailEditUpdate.setReceiverId(activityAddBailFormBinding.receiverSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setTransporterId(activityAddBailFormBinding.transporterSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setAgentId(activityAddBailFormBinding.agentSpinner.getSelectedItemPosition()-1);

                bailEditUpdate.setVolume(Double.valueOf(activityAddBailFormBinding.volumeTxt.getText().toString()));
                bailEditUpdate.setWeight(Double.valueOf(activityAddBailFormBinding.weightTxt.getText().toString()));

                bailEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                //TODO add admin field
                saeedSonsViewModel.updateBail(bailEditUpdate);
            }
            else {
                bailEditUpdate=new Bails();

                //Genrate builty no
                bailEditUpdate.setBiltyNo(generateBuiltyNo());

                bailEditUpdate.setFromCity(activityAddBailFormBinding.fromSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setToCity(activityAddBailFormBinding.toSpinner.getSelectedItemPosition()-1);
                bailEditUpdate.setKindId(activityAddBailFormBinding.kindSpinner.getSelectedItemPosition());
                bailEditUpdate.setSenderId(activityAddBailFormBinding.senderSpiner.getSelectedItemPosition());
                bailEditUpdate.setReceiverId(activityAddBailFormBinding.receiverSpinner.getSelectedItemPosition());
                bailEditUpdate.setTransporterId(activityAddBailFormBinding.transporterSpinner.getSelectedItemPosition());
                bailEditUpdate.setAgentId(activityAddBailFormBinding.agentSpinner.getSelectedItemPosition());

                bailEditUpdate.setVolume(Double.valueOf(activityAddBailFormBinding.volumeTxt.getText().toString()));
                bailEditUpdate.setWeight(Double.valueOf(activityAddBailFormBinding.weightTxt.getText().toString()));


                bailEditUpdate.setMadeBy(1);
                bailEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                saeedSonsViewModel.addBail(bailEditUpdate);

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

        if (action.equals("edit")) {
            activityAddBailFormBinding.fromSpinner.setSelection(bailEditUpdate.getFromCity());
            activityAddBailFormBinding.toSpinner.setSelection(bailEditUpdate.getToCity());
        }


        //Load observers
        saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
            //Sender List
            List<Customers> senderList = new ArrayList<>(customers);
            senderList.add(0, new Customers(0, "Sender", 1));
            ArrayAdapter<Customers> senderArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, senderList);
            activityAddBailFormBinding.senderSpiner.setAdapter(senderArrayAdapter);


            //Receiver List
            List<Customers> receiverList = new ArrayList<>(customers);
            receiverList.add(0, new Customers(0, "Receiver", 1));
            ArrayAdapter<Customers> receiverArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, receiverList);
            activityAddBailFormBinding.receiverSpinner.setAdapter(receiverArrayAdapter);

            if (action.equals("edit")) {
                activityAddBailFormBinding.senderSpiner.setSelection(bailEditUpdate.getSenderId());
                activityAddBailFormBinding.fromSpinner.setSelection(bailEditUpdate.getReceiverId());
            }

        });

        saeedSonsViewModel.getListAllTransporters().observe(this, transporters -> {
            List<Transporters> transportersList = new ArrayList<>(transporters);
            transportersList.add(0, new Transporters(0, "Transporter", 1));

            ArrayAdapter<Transporters> arrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, transportersList);
            activityAddBailFormBinding.transporterSpinner.setAdapter(arrayAdapter);

            if (action.equals("edit")) {
                activityAddBailFormBinding.transporterSpinner.setSelection(bailEditUpdate.getTransporterId());
            }
        });

        saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
            List<Agents> agentsList = new ArrayList<>(agents);
            agentsList.add(0, new Agents(0, "Agent", 1));

            ArrayAdapter<Agents> agentsArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, agentsList);
            activityAddBailFormBinding.agentSpinner.setAdapter(agentsArrayAdapter);

            if (action.equals("edit")) {
                activityAddBailFormBinding.agentSpinner.setSelection(bailEditUpdate.getAgentId());
            }
        });

        //Load kind of data
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            List<KindOfItem> itemList=new ArrayList<>(kindOfItems);
            itemList.add(0,new KindOfItem("Kind", 1));

            ArrayAdapter<KindOfItem> itemArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, itemList);
            itemArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            activityAddBailFormBinding.kindSpinner.setAdapter(itemArrayAdapter);

            if (action.equals("edit")) {
                activityAddBailFormBinding.kindSpinner.setSelection(bailEditUpdate.getKindId());
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

    }

    private void loadData() {

        activityAddBailFormBinding.quanTxt.setText("Qty: " + bailEditUpdate.getQuantity());
        activityAddBailFormBinding.volumeTxt.setText(String.valueOf(bailEditUpdate.getVolume()));
        activityAddBailFormBinding.weightTxt.setText(String.valueOf(bailEditUpdate.getWeight()));


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

    private String generateBuiltyNo(){
        return "bala";
    }


}
