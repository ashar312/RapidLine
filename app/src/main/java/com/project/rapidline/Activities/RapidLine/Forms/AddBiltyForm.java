package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.RapidLine.Supplier;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.KindOfItem;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAddBiltyFormBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
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

public class AddBiltyForm extends AppCompatActivity {

    private ActivityAddBiltyFormBinding addBiltyFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private RapidLineViewModel rapidLineViewModel;


    private String action;
    private Bilty mBilty=null;
    private Bails mBail=null;

    private AdminViewModel adminViewModel;
    private Admins adminInfo;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBiltyFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bilty_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);
        adminViewModel= ViewModelProviders.of(this).get(AdminViewModel.class);
        username=getApplicationContext().getSharedPreferences("LoginPref",0).getString("username","");
        adminViewModel.getAdminInfo(username).observe(this,admins -> {
            adminInfo=admins;
        });

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        if (action.equals("edit")) {
            //Load data
            String itemType = bundle.getString("itemType");
            String id = bundle.getString("itemId");

            if (itemType.equals("Bail")) {
                saeedSonsViewModel.getBailById(id).observe(this, bails -> {
                    mBail = bails;
                    loadBailData();
                });
            } else {
                rapidLineViewModel.getBiltyById(id).observe(this, bilty -> {
                    mBilty = bilty;
                    loadBiltyData();
                });
            }

        }
        initialize();

        addBiltyFormBinding.saveBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            //TODO generate builty no work
            if (action.equals("edit")) {

                if(mBail!=null){
                    mBail.setVolume(Double.valueOf(addBiltyFormBinding.volumeTxt.getText().toString()));
                    mBail.setWeight(Double.valueOf(addBiltyFormBinding.weightTxt.getText().toString()));


                    mBail.setTransport_charge(addBiltyFormBinding.transportTxt.getText().toString());
                    mBail.setLabour_charge(addBiltyFormBinding.laboutTxt.getText().toString());
                    mBail.setElectricity_charge(addBiltyFormBinding.electricityTxt.getText().toString());
                    mBail.setPacking_charge(addBiltyFormBinding.packingTxt.getText().toString());
                    mBail.setComments(addBiltyFormBinding.commentsTxt.getText().toString());

                    String id = bundle.getString("itemId");
                    mBail.setBailNo(id);

                    saeedSonsViewModel.updateBail(mBail);

                }
                else {
                    mBilty.setFromCity(addBiltyFormBinding.fromSpinner.getSelectedItem().toString());
                    mBilty.setToCity(addBiltyFormBinding.toSpinner.getSelectedItem().toString());
                    mBilty.setKindId(addBiltyFormBinding.kindSpinner.getSelectedItem().toString());
                    mBilty.setSenderId(addBiltyFormBinding.senderSpiner.getSelectedItem().toString());
                    mBilty.setReceiverId(addBiltyFormBinding.receiverSpinner.getSelectedItem().toString());

                    mBilty.setSupplierName(addBiltyFormBinding.supplierSpinner.getSelectedItem().toString());


                    mBilty.setAgentId(addBiltyFormBinding.agentSpinner.getSelectedItem().toString());

                    mBilty.setVolume(Double.valueOf(addBiltyFormBinding.volumeTxt.getText().toString()));
                    mBilty.setWeight(Double.valueOf(addBiltyFormBinding.weightTxt.getText().toString()));
                    mBilty.setQty(getIntQuantity(addBiltyFormBinding.quanTxt.getText().toString()));

                    mBilty.setMadeDateTime(Calendar.getInstance().getTime());


                    mBilty.setTransport_charge(addBiltyFormBinding.transportTxt.getText().toString());
                    mBilty.setLabour_charge(addBiltyFormBinding.laboutTxt.getText().toString());
                    mBilty.setElectricity_charge(addBiltyFormBinding.electricityTxt.getText().toString());
                    mBilty.setPacking_charge(addBiltyFormBinding.packingTxt.getText().toString());
                    mBilty.setComments(addBiltyFormBinding.commentsTxt.getText().toString());
                    mBilty.setSupplierPNo(addBiltyFormBinding.supplierPnoText.getText().toString());


                    //TODO add admin field
                    rapidLineViewModel.updateBilty(mBilty);

                }
                Toast.makeText(this, "Bilty updated sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                mBilty = new Bilty();

                mBilty.setFromCity(addBiltyFormBinding.fromSpinner.getSelectedItem().toString());
                mBilty.setToCity(addBiltyFormBinding.toSpinner.getSelectedItem().toString());
                mBilty.setKindId(addBiltyFormBinding.kindSpinner.getSelectedItem().toString());
                mBilty.setSenderId(addBiltyFormBinding.senderSpiner.getSelectedItem().toString());
                mBilty.setReceiverId(addBiltyFormBinding.receiverSpinner.getSelectedItem().toString());

                mBilty.setSupplierName(addBiltyFormBinding.supplierSpinner.getSelectedItem().toString());


                mBilty.setAgentId(addBiltyFormBinding.agentSpinner.getSelectedItem().toString());

                mBilty.setVolume(Double.valueOf(addBiltyFormBinding.volumeTxt.getText().toString()));
                mBilty.setWeight(Double.valueOf(addBiltyFormBinding.weightTxt.getText().toString()));
                mBilty.setQty(getIntQuantity(addBiltyFormBinding.quanTxt.getText().toString()));

                mBilty.setMadeDateTime(Calendar.getInstance().getTime());


                mBilty.setTransport_charge(addBiltyFormBinding.transportTxt.getText().toString());
                mBilty.setLabour_charge(addBiltyFormBinding.laboutTxt.getText().toString());
                mBilty.setElectricity_charge(addBiltyFormBinding.electricityTxt.getText().toString());
                mBilty.setPacking_charge(addBiltyFormBinding.packingTxt.getText().toString());
                mBilty.setComments(addBiltyFormBinding.commentsTxt.getText().toString());
                mBilty.setSupplierPNo(addBiltyFormBinding.supplierPnoText.getText().toString());

                mBilty.setBiltyNo(generateBiltyNo());
                mBilty.setMadeBy(getAdminName());

                rapidLineViewModel.addBilty(mBilty);

                Toast.makeText(this, "Bilty added sucessfully", Toast.LENGTH_SHORT).show();

                finish();
            }


        });

        addBiltyFormBinding.backBtn.setOnClickListener(view -> finish());
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
        //Load Cities
//        saeedSonsViewModel.getListAllCities().observe(this, cities -> {
//            List<Cities> fromCityList = new ArrayList<>(cities);
//            fromCityList.add(0, new Cities("From"));
//
//            ArrayAdapter<Cities> fromCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
//                    R.layout.spinner_item, fromCityList);
//            fromCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//
//            List<Cities> toCityList = new ArrayList<>(cities);
//            toCityList.add(0, new Cities("To"));
//
//            ArrayAdapter<Cities> toCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
//                    R.layout.spinner_item, toCityList);
//            toCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//
//
//            addBiltyFormBinding.fromSpinner.setAdapter(fromCityArrayAdapter);
//            addBiltyFormBinding.toSpinner.setAdapter(toCityArrayAdapter);
//
//        });

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

            ArrayAdapter<String> fromCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, fromCityList);
            fromCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            List<String> toCityList = new ArrayList<>(cityList);
            toCityList.add(0, "To");

            ArrayAdapter<String> toCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, toCityList);
            toCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);


            addBiltyFormBinding.fromSpinner.setAdapter(fromCityArrayAdapter);
            addBiltyFormBinding.toSpinner.setAdapter(toCityArrayAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Load kind of data
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            List<KindOfItem> itemList = new ArrayList<>(kindOfItems);
            itemList.add(0, new KindOfItem("Select a kind"));

            ArrayAdapter<KindOfItem> itemArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, itemList);
            itemArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            addBiltyFormBinding.kindSpinner.setAdapter(itemArrayAdapter);

        });

        saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
            //Sender List
            List<Customers> senderList = new ArrayList<>(customers);
            senderList.add(0, new Customers("Select a Sender"));
            ArrayAdapter<Customers> senderAdap = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, senderList);
            addBiltyFormBinding.senderSpiner.setAdapter(senderAdap);


            //Receiver List
            List<Customers> receiverList = new ArrayList<>(customers);
            receiverList.add(0, new Customers("Select a Receiver"));
            ArrayAdapter<Customers> receiverArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, receiverList);
            addBiltyFormBinding.receiverSpinner.setAdapter(receiverArrayAdapter);


        });


        saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
            List<Agents> agentsList = new ArrayList<>(agents);
            agentsList.add(0, new Agents("Select a agent"));

            ArrayAdapter<Agents> agentsArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, agentsList);
            agentsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            addBiltyFormBinding.agentSpinner.setAdapter(agentsArrayAdapter);

        });

        //TODO fix spinners when no data is present to show in bail also
        if(mBail==null){
            saeedSonsViewModel.getListAllSuppliers().observe(this, suppliers -> {
                List<Supplier> supplierList = new ArrayList<>(suppliers);
                supplierList.add(0, new Supplier("Select a supplier"));

                ArrayAdapter<Supplier> supplierArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                        R.layout.spinner_item, supplierList);

                addBiltyFormBinding.supplierSpinner.setAdapter(supplierArrayAdapter);
            });

        }

        //Set Listeners
        addBiltyFormBinding.addQuanBtn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(addBiltyFormBinding.quanTxt.getText())){
                addBiltyFormBinding.quanTxt.setText("1");
            }
            else {
                String quan = addBiltyFormBinding.quanTxt.getText().toString();
                int val = Integer.parseInt(quan);
                val++;
                addBiltyFormBinding.quanTxt.setText(""+val);
            }
        });

        addBiltyFormBinding.subractQuanBtn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(addBiltyFormBinding.quanTxt.getText())){
                addBiltyFormBinding.quanTxt.setText("1");
            }
            else {
                String quan = addBiltyFormBinding.quanTxt.getText().toString();
                int val = Integer.parseInt(quan);
                if (val == 1) {
                    Toast.makeText(this, "Min quantity reached",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                val = val - 1;
                addBiltyFormBinding.quanTxt.setText(""+val);
            }
        });

        addBiltyFormBinding.transportChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                addBiltyFormBinding.transportTxt.setEnabled(true);
                addBiltyFormBinding.transportTxt.requestFocus();
            } else {
                addBiltyFormBinding.transportTxt.setEnabled(false);
            }
        });

        addBiltyFormBinding.labourChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                addBiltyFormBinding.laboutTxt.setEnabled(true);
                addBiltyFormBinding.laboutTxt.requestFocus();
            } else {
                addBiltyFormBinding.laboutTxt.setEnabled(false);
            }
        });

        addBiltyFormBinding.electricityChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                addBiltyFormBinding.electricityTxt.setEnabled(true);
                addBiltyFormBinding.electricityTxt.requestFocus();
            } else {
                addBiltyFormBinding.electricityTxt.setEnabled(false);
            }
        });

        addBiltyFormBinding.packingChk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                addBiltyFormBinding.packingTxt.setEnabled(true);
                addBiltyFormBinding.packingTxt.requestFocus();
            } else {
                addBiltyFormBinding.packingTxt.setEnabled(false);
            }
        });
    }

    private void loadBailData() {
        addBiltyFormBinding.quanTxt.setText("Qty: " + mBail.getQuantity());
        addBiltyFormBinding.volumeTxt.setText(String.valueOf(mBail.getVolume()));
        addBiltyFormBinding.weightTxt.setText(String.valueOf(mBail.getWeight()));
        addBiltyFormBinding.supplierPnoText.setText(mBail.getBailNo());
        addBiltyFormBinding.supplierPnoText.setEnabled(false);

        if (!TextUtils.isEmpty(mBail.getTransport_charge())) {
            addBiltyFormBinding.transportChk.setChecked(true);
            addBiltyFormBinding.transportTxt.setText(mBail.getTransport_charge());
        }
        if (!TextUtils.isEmpty(mBail.getLabour_charge())) {
            addBiltyFormBinding.labourChk.setChecked(true);
            addBiltyFormBinding.laboutTxt.setText(mBail.getLabour_charge());
        }
        if (!TextUtils.isEmpty(mBail.getElectricity_charge())) {
            addBiltyFormBinding.electricityChk.setChecked(true);
            addBiltyFormBinding.electricityTxt.setText(mBail.getElectricity_charge());
        }
        if (!TextUtils.isEmpty(mBail.getPacking_charge())) {
            addBiltyFormBinding.packingChk.setChecked(true);
            addBiltyFormBinding.packingTxt.setText(mBail.getPacking_charge());
        }
        addBiltyFormBinding.commentsTxt.setText(mBail.getComments());


        int senderVal = getIndex(addBiltyFormBinding.senderSpiner, mBail.getSenderId());
        addBiltyFormBinding.senderSpiner.setSelection(senderVal);

        int receiverVal = getIndex(addBiltyFormBinding.receiverSpinner, mBail.getReceiverId());
        addBiltyFormBinding.receiverSpinner.setSelection(receiverVal);

        int agentVal = getIndex(addBiltyFormBinding.agentSpinner, mBail.getAgentId());
        addBiltyFormBinding.agentSpinner.setSelection(agentVal);

        int itemVal = getIndex(addBiltyFormBinding.kindSpinner, mBail.getKindId());
        addBiltyFormBinding.kindSpinner.setSelection(itemVal);

        int fromCityVal = getIndex(addBiltyFormBinding.fromSpinner, mBail.getFromCity());
        addBiltyFormBinding.fromSpinner.setSelection(fromCityVal);

        int toCityVal = getIndex(addBiltyFormBinding.toSpinner, mBail.getToCity());
        addBiltyFormBinding.toSpinner.setSelection(toCityVal);

        List<String> supplierList=new ArrayList<>();
        supplierList.add(mBail.getTransporterId());
        ArrayAdapter<String> supplierArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                R.layout.spinner_item, supplierList);
        addBiltyFormBinding.supplierSpinner.setAdapter(supplierArrayAdapter);
        addBiltyFormBinding.supplierSpinner.setSelection(0);

    }

    private void loadBiltyData() {
        addBiltyFormBinding.quanTxt.setText("Qty: " + mBilty.getQty());
        addBiltyFormBinding.volumeTxt.setText(String.valueOf(mBilty.getVolume()));
        addBiltyFormBinding.weightTxt.setText(String.valueOf(mBilty.getWeight()));
        addBiltyFormBinding.supplierPnoText.setText(mBilty.getSupplierPNo());

        if (!TextUtils.isEmpty(mBilty.getTransport_charge())) {
            addBiltyFormBinding.transportChk.setChecked(true);
            addBiltyFormBinding.transportTxt.setText(mBilty.getTransport_charge());
        }
        if (!TextUtils.isEmpty(mBilty.getLabour_charge())) {
            addBiltyFormBinding.labourChk.setChecked(true);
            addBiltyFormBinding.laboutTxt.setText(mBilty.getLabour_charge());
        }
        if (!TextUtils.isEmpty(mBilty.getElectricity_charge())) {
            addBiltyFormBinding.electricityChk.setChecked(true);
            addBiltyFormBinding.electricityTxt.setText(mBilty.getElectricity_charge());
        }
        if (!TextUtils.isEmpty(mBilty.getPacking_charge())) {
            addBiltyFormBinding.packingChk.setChecked(true);
            addBiltyFormBinding.packingTxt.setText(mBilty.getPacking_charge());
        }
        addBiltyFormBinding.commentsTxt.setText(mBilty.getComments());


        int senderVal = getIndex(addBiltyFormBinding.senderSpiner, mBilty.getSenderId());
        addBiltyFormBinding.senderSpiner.setSelection(senderVal);

        int receiverVal = getIndex(addBiltyFormBinding.receiverSpinner, mBilty.getReceiverId());
        addBiltyFormBinding.receiverSpinner.setSelection(receiverVal);

        int agentVal = getIndex(addBiltyFormBinding.agentSpinner, mBilty.getAgentId());
        addBiltyFormBinding.agentSpinner.setSelection(agentVal);

        int itemVal = getIndex(addBiltyFormBinding.kindSpinner, mBilty.getKindId());
        addBiltyFormBinding.kindSpinner.setSelection(itemVal);

        int fromCityVal = getIndex(addBiltyFormBinding.fromSpinner, mBilty.getFromCity());
        addBiltyFormBinding.fromSpinner.setSelection(fromCityVal);

        int toCityVal = getIndex(addBiltyFormBinding.toSpinner, mBilty.getToCity());
        addBiltyFormBinding.toSpinner.setSelection(toCityVal);

        int supplierVal = getIndex(addBiltyFormBinding.supplierSpinner, mBilty.getSupplierName());
        addBiltyFormBinding.supplierSpinner.setSelection(supplierVal);


    }

    private boolean isFieldEmpty() {
        if (addBiltyFormBinding.fromSpinner.getSelectedItem().toString().equals("From")
                || addBiltyFormBinding.toSpinner.getSelectedItem().toString().equals("To")
                || addBiltyFormBinding.kindSpinner.getSelectedItem().toString().equals("Select a kind")
                || addBiltyFormBinding.senderSpiner.getSelectedItem().toString().equals("Select a Sender")
                || addBiltyFormBinding.receiverSpinner.getSelectedItem().toString().equals("Select a Receiver")
                || addBiltyFormBinding.supplierSpinner.getSelectedItem().toString().equals("Select a supplier")
                || addBiltyFormBinding.agentSpinner.getSelectedItem().toString().equals("Select a agent")
                || TextUtils.isEmpty(addBiltyFormBinding.volumeTxt.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.weightTxt.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.supplierPnoText.getText())) {
            return true;
        }
        return false;
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

    private int getIntQuantity(String quan) {
        int val = Integer.parseInt(quan);
        return val;
    }

    private String generateBiltyNo(){
        String currentBiltyNo= adminInfo.getBiltyCounter();

        int updatedNo= Integer.parseInt(currentBiltyNo)+1;

        String number = String.format(Locale.ENGLISH, "%06d", updatedNo);
        adminViewModel.updateBiltyCounter(number,username);

        return currentBiltyNo;
    }
    private String getAdminName(){
        return getApplicationContext().getSharedPreferences("LoginPref",0).getString("adminName","");
    }
}