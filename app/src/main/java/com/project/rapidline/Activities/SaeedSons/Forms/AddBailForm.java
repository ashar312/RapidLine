package com.project.rapidline.Activities.SaeedSons.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Bails;
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
import java.util.Objects;

public class AddBailForm extends AppCompatActivity {

    private ActivityAddBailFormBinding activityAddBailFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private Bails bailEditUpdate;
    private String action;

    private ArrayAdapter<Customers> senderAdap;
    private ArrayAdapter<Customers> receiverArrayAdapter;
    private ArrayAdapter<Transporters> transportersArrayAdapter;
    private ArrayAdapter<Agents> agentsArrayAdapter;
    private ArrayAdapter<KindOfItem> itemArrayAdapter;


    private AdminViewModel adminViewModel;
    private Admins adminInfo;
    private String username;
    private List<String> cityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddBailFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bail_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);


        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);


        username = getApplicationContext().getSharedPreferences("LoginPref", 0).getString("username", "");
        adminViewModel.getAdminInfo(username).observe(this, admins -> {
            adminInfo = admins;
        });


        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();


        if (action.equals("edit")) {
            //Load data
            String id = bundle.getString("itemId");
            saeedSonsViewModel.getBailById(id).observe(this, bail -> {
                bailEditUpdate = bail;
                initialize();
                loadData();
            });
        } else {
            initialize();
        }


        activityAddBailFormBinding.saveBtn.setOnClickListener(view -> {

            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getCityIndex(activityAddBailFormBinding.fromSpinner.getText().toString()) == -1
                    || getCityIndex(activityAddBailFormBinding.toSpinner.getText().toString()) == -1) {
                Toast.makeText(this, "Please select city from list only", Toast.LENGTH_SHORT).show();
                return;
            }

            if (action.equals("edit")) {

                bailEditUpdate.setFromCity(activityAddBailFormBinding.fromSpinner.getText().toString());
                bailEditUpdate.setToCity(activityAddBailFormBinding.toSpinner.getText().toString());
                bailEditUpdate.setKindId(activityAddBailFormBinding.kindSpinner.getSelectedItem().toString());
                bailEditUpdate.setSenderId(activityAddBailFormBinding.senderSpiner.getSelectedItem().toString());
                bailEditUpdate.setReceiverId(activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString());
                bailEditUpdate.setTransporterId(activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString());
                bailEditUpdate.setAgentId(activityAddBailFormBinding.agentSpinner.getSelectedItem().toString());

//                bailEditUpdate.setVolume(Double.valueOf(activityAddBailFormBinding.volumeTxt.getText().toString()));
//                bailEditUpdate.setWeight(Double.valueOf(activityAddBailFormBinding.weightTxt.getText().toString()));
                bailEditUpdate.setQuantity(getIntQuantity(activityAddBailFormBinding.quanTxt.getText().toString()));


                bailEditUpdate.setMadeBy(getAdminName());

                bailEditUpdate.setTransport_charge(activityAddBailFormBinding.transportTxt.getText().toString());
                bailEditUpdate.setLabour_charge(activityAddBailFormBinding.laboutTxt.getText().toString());
                bailEditUpdate.setElectricity_charge(activityAddBailFormBinding.electricityTxt.getText().toString());
                bailEditUpdate.setPacking_charge(activityAddBailFormBinding.packingTxt.getText().toString());
                bailEditUpdate.setComments(activityAddBailFormBinding.commentsTxt.getText().toString());

                //TODO add admin field
                saeedSonsViewModel.updateBail(bailEditUpdate);
                Toast.makeText(this, "Bail updated sucessfully", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                bailEditUpdate = new Bails();

                //Genrate builty no
                bailEditUpdate.setBailNo(generateBailNo());


                bailEditUpdate.setFromCity(activityAddBailFormBinding.fromSpinner.getText().toString());
                bailEditUpdate.setToCity(activityAddBailFormBinding.toSpinner.getText().toString());
                bailEditUpdate.setKindId(activityAddBailFormBinding.kindSpinner.getSelectedItem().toString());
                bailEditUpdate.setSenderId(activityAddBailFormBinding.senderSpiner.getSelectedItem().toString());
                bailEditUpdate.setReceiverId(activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString());
                bailEditUpdate.setTransporterId(activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString());
                bailEditUpdate.setAgentId(activityAddBailFormBinding.agentSpinner.getSelectedItem().toString());

//                bailEditUpdate.setVolume(Double.valueOf(activityAddBailFormBinding.volumeTxt.getText().toString()));
//                bailEditUpdate.setWeight(Double.valueOf(activityAddBailFormBinding.weightTxt.getText().toString()));
                bailEditUpdate.setQuantity(getIntQuantity(activityAddBailFormBinding.quanTxt.getText().toString()));

                bailEditUpdate.setMadeBy(getAdminName());
                bailEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());


                bailEditUpdate.setTransport_charge(activityAddBailFormBinding.transportTxt.getText().toString());
                bailEditUpdate.setLabour_charge(activityAddBailFormBinding.laboutTxt.getText().toString());
                bailEditUpdate.setElectricity_charge(activityAddBailFormBinding.electricityTxt.getText().toString());
                bailEditUpdate.setPacking_charge(activityAddBailFormBinding.packingTxt.getText().toString());
                bailEditUpdate.setComments(activityAddBailFormBinding.commentsTxt.getText().toString());

                saeedSonsViewModel.addBail(bailEditUpdate);

                Toast.makeText(this, "Bail added sucessfully", Toast.LENGTH_SHORT).show();
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
            senderAdap = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, senderList);
            activityAddBailFormBinding.senderSpiner.setAdapter(senderAdap);


            //Receiver List
            List<Customers> receiverList = new ArrayList<>(customers);
            receiverList.add(0, new Customers("Select a Receiver"));
            receiverArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, receiverList);
            activityAddBailFormBinding.receiverSpinner.setAdapter(receiverArrayAdapter);

            if (action.equals("edit")) {
                loadSenderVal();
                loadReceiverVal();
            }
        });

        saeedSonsViewModel.getListAllTransporters().observe(this, transporters -> {
            List<Transporters> transportersList = new ArrayList<>(transporters);
            transportersList.add(0, new Transporters("Select a Transporter"));

            transportersArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, transportersList);
            activityAddBailFormBinding.transporterSpinner.setAdapter(transportersArrayAdapter);

            if (action.equals("edit")) {
                loadTransportVal();
            }

        });

        saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
            List<Agents> agentsList = new ArrayList<>(agents);
            agentsList.add(0, new Agents("Select a agent"));

            agentsArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, agentsList);
            agentsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            activityAddBailFormBinding.agentSpinner.setAdapter(agentsArrayAdapter);

            if (action.equals("edit")) {
                loadAgentVal();
            }
        });

        //Load kind of data
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            List<KindOfItem> itemList = new ArrayList<>(kindOfItems);
            itemList.add(0, new KindOfItem("Select a kind"));

            itemArrayAdapter = new ArrayAdapter<>(AddBailForm.this,
                    R.layout.spinner_item, itemList);
            itemArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            activityAddBailFormBinding.kindSpinner.setAdapter(itemArrayAdapter);

            if (action.equals("edit")) {
                loadKindVal();
            }
        });

        //Load cities
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            cityList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currCity = jsonObject.getString("city");
                cityList.add(currCity);
            }

            List<String> fromCityList = new ArrayList<>(cityList);


            ArrayAdapter<String> fromCityArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, fromCityList);
            fromCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            List<String> toCityList = new ArrayList<>(cityList);


            ArrayAdapter<String> toCityArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, toCityList);
            toCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);


            activityAddBailFormBinding.fromSpinner.setAdapter(fromCityArrayAdapter);
            activityAddBailFormBinding.toSpinner.setAdapter(toCityArrayAdapter);

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            activityAddBailFormBinding.fromSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            });

            activityAddBailFormBinding.toSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            });


//            activityAddBailFormBinding.fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    Toast.makeText(AddBailForm.this,"Selected",Toast.LENGTH_SHORT).show();
//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                    Toast.makeText(AddBailForm.this,"Select from list",Toast.LENGTH_SHORT).show();
//                }
//            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Set Listeners
        activityAddBailFormBinding.addQuanBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(activityAddBailFormBinding.quanTxt.getText())) {
                activityAddBailFormBinding.quanTxt.setText("1");
            } else {
                String quan = activityAddBailFormBinding.quanTxt.getText().toString();
                int val = Integer.parseInt(quan);
                val++;
                activityAddBailFormBinding.quanTxt.setText("" + val);
            }
        });

        activityAddBailFormBinding.subractQuanBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(activityAddBailFormBinding.quanTxt.getText())) {
                activityAddBailFormBinding.quanTxt.setText("1");
            } else {
                String quan = activityAddBailFormBinding.quanTxt.getText().toString();
                int val = Integer.parseInt(quan);
                if (val == 1) {
                    Toast.makeText(this, "Min quantity reached",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                val = val - 1;
                activityAddBailFormBinding.quanTxt.setText("" + val);
            }
        });

        activityAddBailFormBinding.transportChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                activityAddBailFormBinding.transportTxt.setEnabled(true);
                activityAddBailFormBinding.transportTxt.requestFocus();
            } else {
                activityAddBailFormBinding.transportTxt.setEnabled(false);
            }
        });

        activityAddBailFormBinding.labourChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                activityAddBailFormBinding.laboutTxt.setEnabled(true);
                activityAddBailFormBinding.laboutTxt.requestFocus();
            } else {
                activityAddBailFormBinding.laboutTxt.setEnabled(false);
            }
        });

        activityAddBailFormBinding.electricityChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                activityAddBailFormBinding.electricityTxt.setEnabled(true);
                activityAddBailFormBinding.electricityTxt.requestFocus();
            } else {
                activityAddBailFormBinding.electricityTxt.setEnabled(false);
            }
        });

        activityAddBailFormBinding.packingChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                activityAddBailFormBinding.packingTxt.setEnabled(true);
                activityAddBailFormBinding.packingTxt.requestFocus();
            } else {
                activityAddBailFormBinding.packingTxt.setEnabled(false);
            }
        });


    }

    private void loadData() {
        activityAddBailFormBinding.quanTxt.setText("" + bailEditUpdate.getQuantity());
//        activityAddBailFormBinding.volumeTxt.setText(String.valueOf(bailEditUpdate.getVolume()));
//        activityAddBailFormBinding.weightTxt.setText(String.valueOf(bailEditUpdate.getWeight()));

        if (!TextUtils.isEmpty(bailEditUpdate.getTransport_charge())) {
            activityAddBailFormBinding.transportChk.setChecked(true);
            activityAddBailFormBinding.transportTxt.setText(bailEditUpdate.getTransport_charge());
        }
        if (!TextUtils.isEmpty(bailEditUpdate.getLabour_charge())) {
            activityAddBailFormBinding.labourChk.setChecked(true);
            activityAddBailFormBinding.laboutTxt.setText(bailEditUpdate.getLabour_charge());
        }
        if (!TextUtils.isEmpty(bailEditUpdate.getElectricity_charge())) {
            activityAddBailFormBinding.electricityChk.setChecked(true);
            activityAddBailFormBinding.electricityTxt.setText(bailEditUpdate.getElectricity_charge());
        }
        if (!TextUtils.isEmpty(bailEditUpdate.getPacking_charge())) {
            activityAddBailFormBinding.packingChk.setChecked(true);
            activityAddBailFormBinding.packingTxt.setText(bailEditUpdate.getPacking_charge());
        }
        activityAddBailFormBinding.commentsTxt.setText(bailEditUpdate.getComments());

        activityAddBailFormBinding.fromSpinner.setText(bailEditUpdate.getFromCity());

        activityAddBailFormBinding.toSpinner.setText(bailEditUpdate.getToCity());

    }

    private void loadSenderVal() {
        int senderVal = getIndex(activityAddBailFormBinding.senderSpiner, bailEditUpdate.getSenderId());
        if (senderVal == -1) {
            senderAdap.add(new Customers(bailEditUpdate.getSenderId()));
            activityAddBailFormBinding.senderSpiner.setSelection(senderAdap.getCount() - 1);
        } else {
            activityAddBailFormBinding.senderSpiner.setSelection(senderVal);
        }
    }

    private void loadReceiverVal() {
        int receiverVal = getIndex(activityAddBailFormBinding.receiverSpinner, bailEditUpdate.getReceiverId());
        if (receiverVal == -1) {
            receiverArrayAdapter.add(new Customers(bailEditUpdate.getReceiverId()));
            activityAddBailFormBinding.receiverSpinner.setSelection(receiverArrayAdapter.getCount() - 1);
        } else {
            activityAddBailFormBinding.receiverSpinner.setSelection(receiverVal);
        }
    }

    private void loadTransportVal() {
        int transVal = getIndex(activityAddBailFormBinding.transporterSpinner, bailEditUpdate.getTransporterId());
        if (transVal == -1) {
            transportersArrayAdapter.add(new Transporters(bailEditUpdate.getTransporterId()));
            activityAddBailFormBinding.transporterSpinner.setSelection(transportersArrayAdapter.getCount() - 1);
        } else {
            activityAddBailFormBinding.transporterSpinner.setSelection(transVal);
        }

    }

    private void loadAgentVal() {
        int agentVal = getIndex(activityAddBailFormBinding.agentSpinner, bailEditUpdate.getAgentId());
        if (agentVal == -1) {
            agentsArrayAdapter.add(new Agents(bailEditUpdate.getAgentId()));
            activityAddBailFormBinding.agentSpinner.setSelection(agentsArrayAdapter.getCount() - 1);
        } else {
            activityAddBailFormBinding.agentSpinner.setSelection(agentVal);
        }
    }

    private void loadKindVal() {
        int itemVal = getIndex(activityAddBailFormBinding.kindSpinner, bailEditUpdate.getKindId());
        if (itemVal == -1) {
            itemArrayAdapter.add(new KindOfItem(bailEditUpdate.getKindId()));
            activityAddBailFormBinding.kindSpinner.setSelection(itemArrayAdapter.getCount() - 1);
        } else {
            activityAddBailFormBinding.kindSpinner.setSelection(itemVal);
        }

    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return -1;
    }


    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(activityAddBailFormBinding.fromSpinner.getText())
                || TextUtils.isEmpty(activityAddBailFormBinding.toSpinner.getText())
                || activityAddBailFormBinding.kindSpinner.getSelectedItem().toString().equals("Select a kind")
                || activityAddBailFormBinding.senderSpiner.getSelectedItem().toString().equals("Select a Sender")
                || activityAddBailFormBinding.receiverSpinner.getSelectedItem().toString().equals("Select a Receiver")
                || activityAddBailFormBinding.transporterSpinner.getSelectedItem().toString().equals("Select a Transporter")
                || activityAddBailFormBinding.agentSpinner.getSelectedItem().toString().equals("Select a agent")
                || TextUtils.isEmpty(activityAddBailFormBinding.quanTxt.getText())
        ) {
            return true;
        }
        return false;
    }

    private String generateBailNo() {
        String currentBailNo = adminInfo.getBailCounter();
        AddBailNumberGenrator bailNumberGenrator = new AddBailNumberGenrator(currentBailNo.toString());

        String newnumber = bailNumberGenrator.generateBailNumber();
        adminViewModel.updateAdminBailInfo(newnumber, username);

        return currentBailNo;
    }

    private int getIntQuantity(String quan) {
        int val = Integer.parseInt(quan);
        return val;
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
