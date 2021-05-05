package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.project.rapidline.Models.SaeedSons.Transporters;
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
    private String itemType;
    private Bilty mBilty = null;
    private Bails mBail = null;

    private AdminViewModel adminViewModel;
    private Admins adminInfo;
    private String username;

    private List<String> cityList;

    private ArrayAdapter<Customers> senderArrayAdapter;
    private ArrayAdapter<Customers> receiverArrayAdapter;
    private ArrayAdapter<Supplier> supplierArrayAdapter;
    private ArrayAdapter<Agents> agentsArrayAdapter;
    private ArrayAdapter<KindOfItem> itemArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBiltyFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bilty_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);
        username = getApplicationContext().getSharedPreferences("LoginPref", 0).getString("username", "");
        adminViewModel.getAdminInfo(username).observe(this, admins -> {
            adminInfo = admins;
        });

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        if (action.equals("edit")) {
            //Load data
            itemType = bundle.getString("itemType");
            String id = bundle.getString("itemId");

            if (itemType.equals("Bail")) {
                saeedSonsViewModel.getBailById(id).observe(this, bails -> {
                    mBail = bails;
                    addBiltyFormBinding.volumeTxt.setVisibility(View.GONE);
                    addBiltyFormBinding.weightTxt.setVisibility(View.GONE);
                    initialize();
                    loadBailData();

                });
            } else {
                rapidLineViewModel.getBiltyById(id).observe(this, bilty -> {
                    mBilty = bilty;
                    initialize();
                    loadBiltyData();
                });
            }

        } else {
            initialize();
        }


        addBiltyFormBinding.saveBtn.setOnClickListener(view -> {

            if (getCityIndex(addBiltyFormBinding.fromSpinner.getText().toString()) == -1
                    || getCityIndex(addBiltyFormBinding.toSpinner.getText().toString()) == -1) {
                Toast.makeText(this, "Please select city from list only", Toast.LENGTH_SHORT).show();
                return;
            }


            //TODO generate builty no work
            if (action.equals("edit")) {

                if (mBail != null) {
                    if (isBailFieldEmpty()) {
                        Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    mBail.setVolume(Double.valueOf(addBiltyFormBinding.volumeTxt.getText().toString()));
//                    mBail.setWeight(Double.valueOf(addBiltyFormBinding.weightTxt.getText().toString()));

                    mBail.setTransport_charge(addBiltyFormBinding.transportTxt.getText().toString());
                    mBail.setLabour_charge(addBiltyFormBinding.laboutTxt.getText().toString());
                    mBail.setElectricity_charge(addBiltyFormBinding.electricityTxt.getText().toString());
                    mBail.setPacking_charge(addBiltyFormBinding.packingTxt.getText().toString());
                    mBail.setComments(addBiltyFormBinding.commentsTxt.getText().toString());

                    String id = bundle.getString("itemId");
                    mBail.setBailNo(id);

                    saeedSonsViewModel.updateBail(mBail);

                } else {

                    if (isFieldEmpty()) {
                        Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    mBilty.setFromCity(addBiltyFormBinding.fromSpinner.getText().toString());
                    mBilty.setToCity(addBiltyFormBinding.toSpinner.getText().toString());
                    mBilty.setKindId(addBiltyFormBinding.kindSpinner.getSelectedItem().toString());
                    mBilty.setSenderId(addBiltyFormBinding.senderSpiner.getSelectedItem().toString());
                    mBilty.setReceiverId(addBiltyFormBinding.receiverSpinner.getSelectedItem().toString());

                    mBilty.setSupplierName(addBiltyFormBinding.supplierSpinner.getSelectedItem().toString());


                    mBilty.setAgentId(addBiltyFormBinding.agentSpinner.getSelectedItem().toString());

                    mBilty.setVolume(Double.valueOf(addBiltyFormBinding.volumeTxt.getText().toString()));
                    mBilty.setWeight(Double.valueOf(addBiltyFormBinding.weightTxt.getText().toString()));
                    mBilty.setQty(getIntQuantity(addBiltyFormBinding.quanTxt.getText().toString()));


                    mBilty.setTransport_charge(addBiltyFormBinding.transportTxt.getText().toString());
                    mBilty.setLabour_charge(addBiltyFormBinding.laboutTxt.getText().toString());
                    mBilty.setElectricity_charge(addBiltyFormBinding.electricityTxt.getText().toString());
                    mBilty.setPacking_charge(addBiltyFormBinding.packingTxt.getText().toString());
                    mBilty.setComments(addBiltyFormBinding.commentsTxt.getText().toString());
                    mBilty.setSupplierPNo(addBiltyFormBinding.supplierPnoText.getText().toString());


                    //TODO add admin field
                    rapidLineViewModel.updateBilty(mBilty);

                }
//                Toast.makeText(this, "Bilty updated sucessfully", Toast.LENGTH_SHORT).show();

            } else {

                if (isFieldEmpty()) {
                    Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                mBilty = new Bilty();

                mBilty.setFromCity(addBiltyFormBinding.fromSpinner.getText().toString());
                mBilty.setToCity(addBiltyFormBinding.toSpinner.getText().toString());
                mBilty.setKindId(addBiltyFormBinding.kindSpinner.getSelectedItem().toString());

                if(addBiltyFormBinding.biltySwitch.isChecked()){
                    mBilty.setSenderId(getPocName(senderList,addBiltyFormBinding.senderSpiner.getSelectedItemPosition()));
                    mBilty.setReceiverId(getPocName(receiverList,addBiltyFormBinding.receiverSpinner.getSelectedItemPosition()));
                }else{
                    mBilty.setSenderId(addBiltyFormBinding.senderSpiner.getSelectedItem().toString());
                    mBilty.setReceiverId(addBiltyFormBinding.receiverSpinner.getSelectedItem().toString());
                }
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

    private String getPocName(List<Customers> array,int index){
        return array.get(index).pocName;
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
    List<Customers> senderList;
    List<Customers> receiverList;
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
            cityList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currCity = jsonObject.getString("city");
                cityList.add(currCity);
            }

            List<String> fromCityList = new ArrayList<>(cityList);


            ArrayAdapter<String> fromCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, fromCityList);
            fromCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);


            addBiltyFormBinding.fromSpinner.setAdapter(fromCityArrayAdapter);
            addBiltyFormBinding.toSpinner.setAdapter(fromCityArrayAdapter);

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            addBiltyFormBinding.fromSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            });

            addBiltyFormBinding.toSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Load kind of data
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            List<KindOfItem> itemList = new ArrayList<>(kindOfItems);
            itemList.add(0, new KindOfItem("Select a kind"));

            itemArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, itemList);
            itemArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            addBiltyFormBinding.kindSpinner.setAdapter(itemArrayAdapter);

            if (action.equals("edit")) {
                if (itemType.equals("Bail")) {
                    loadKindBailVal();

                } else {
                    loadKindBiltyVal();
                }
            }


        });

        saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
            //Sender List
            senderList = new ArrayList<>(customers);
            senderList.add(0, new Customers("Select a Sender"));
            senderArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, senderList);
            addBiltyFormBinding.senderSpiner.setAdapter(senderArrayAdapter);


            //Receiver List
            receiverList = new ArrayList<>(customers);
            receiverList.add(0, new Customers("Select a Receiver"));
            receiverArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, receiverList);
            addBiltyFormBinding.receiverSpinner.setAdapter(receiverArrayAdapter);

            if (action.equals("edit")) {
                if (itemType.equals("Bail")) {
                    loadSenderBailVal();
                    loadReceiverBailVal();

                } else {
                    loadSenderBiltyVal();
                    loadReceiverBiltyVal();
                }
            }

        });


        saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
            List<Agents> agentsList = new ArrayList<>(agents);
            agentsList.add(0, new Agents("Select a agent"));

            agentsArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, agentsList);
            agentsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            addBiltyFormBinding.agentSpinner.setAdapter(agentsArrayAdapter);

            if (action.equals("edit")) {
                if (itemType.equals("Bail")) {
                    loadAgentBailVal();

                } else {
                    loadAgentBiltyVal();
                }
            }

        });


        saeedSonsViewModel.getListAllSuppliers().observe(this, suppliers -> {
            List<Supplier> supplierList = new ArrayList<>(suppliers);
            supplierList.add(0, new Supplier("Select a supplier"));

            supplierArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, supplierList);

            addBiltyFormBinding.supplierSpinner.setAdapter(supplierArrayAdapter);


            if (action.equals("edit")) {
                if (itemType.equals("Bail")) {
                    loadSupplierBailVal();

                } else {
                    loadTransporterBiltyVal();
                }
            }
        });


        //Set Listeners
        addBiltyFormBinding.addQuanBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(addBiltyFormBinding.quanTxt.getText())) {
                addBiltyFormBinding.quanTxt.setText("1");
            } else {
                String quan = addBiltyFormBinding.quanTxt.getText().toString();
                int val = Integer.parseInt(quan);
                val++;
                addBiltyFormBinding.quanTxt.setText("" + val);
            }
        });

        addBiltyFormBinding.subractQuanBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(addBiltyFormBinding.quanTxt.getText())) {
                addBiltyFormBinding.quanTxt.setText("1");
            } else {
                String quan = addBiltyFormBinding.quanTxt.getText().toString();
                int val = Integer.parseInt(quan);
                if (val == 1) {
                    Toast.makeText(this, "Min quantity reached",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                val = val - 1;
                addBiltyFormBinding.quanTxt.setText("" + val);
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
        addBiltyFormBinding.quanTxt.setText("" + mBail.getQuantity());
//        addBiltyFormBinding.volumeTxt.setText(String.valueOf(mBail.getVolume()));
//        addBiltyFormBinding.weightTxt.setText(String.valueOf(mBail.getWeight()));
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

        addBiltyFormBinding.fromSpinner.setText(mBail.getFromCity());

        addBiltyFormBinding.toSpinner.setText(mBail.getToCity());


//        List<String> supplierList = new ArrayList<>();
//        supplierList.add(mBail.getTransporterId());
//        ArrayAdapter<String> supplierArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
//                R.layout.spinner_item, supplierList);
//        addBiltyFormBinding.supplierSpinner.setAdapter(supplierArrayAdapter);
//        addBiltyFormBinding.supplierSpinner.setSelection(0);

    }

    private void loadSenderBailVal() {
        int senderVal = getIndex(addBiltyFormBinding.senderSpiner, mBail.getSenderId());
        if (senderVal == -1) {
            senderArrayAdapter.add(new Customers(mBail.getSenderId()));
            addBiltyFormBinding.senderSpiner.setSelection(senderArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.senderSpiner.setSelection(senderVal);
        }

    }

    private void loadReceiverBailVal() {
        int receiverVal = getIndex(addBiltyFormBinding.receiverSpinner, mBail.getReceiverId());
        if (receiverVal == -1) {
            receiverArrayAdapter.add(new Customers(mBail.getReceiverId()));
            addBiltyFormBinding.receiverSpinner.setSelection(receiverArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.receiverSpinner.setSelection(receiverVal);
        }

    }

    private void loadAgentBailVal() {
        int agentVal = getIndex(addBiltyFormBinding.agentSpinner, mBail.getAgentId());
        if (agentVal == -1) {
            agentsArrayAdapter.add(new Agents(mBail.getAgentId()));
            addBiltyFormBinding.agentSpinner.setSelection(agentsArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.agentSpinner.setSelection(agentVal);
        }

    }

    private void loadKindBailVal() {
        int itemVal = getIndex(addBiltyFormBinding.kindSpinner, mBail.getKindId());
        if (itemVal == -1) {
            itemArrayAdapter.add(new KindOfItem(mBail.getKindId()));
            addBiltyFormBinding.kindSpinner.setSelection(itemArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.kindSpinner.setSelection(itemVal);
        }

    }

    private void loadSupplierBailVal() {
        supplierArrayAdapter.clear();
        supplierArrayAdapter.add(new Supplier(mBail.getTransporterId()));
        addBiltyFormBinding.supplierSpinner.setSelection(0);
    }


    private void loadBiltyData() {
        addBiltyFormBinding.quanTxt.setText("" + mBilty.getQty());
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

        addBiltyFormBinding.fromSpinner.setText(mBilty.getFromCity());

        addBiltyFormBinding.toSpinner.setText(mBilty.getToCity());


    }

    private void loadSenderBiltyVal() {
        int senderVal = getIndex(addBiltyFormBinding.senderSpiner, mBilty.getSenderId());
        if (senderVal == -1) {
            senderArrayAdapter.add(new Customers(mBilty.getSenderId()));
            addBiltyFormBinding.senderSpiner.setSelection(senderArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.senderSpiner.setSelection(senderVal);
        }
    }

    private void loadReceiverBiltyVal() {
        int receiverVal = getIndex(addBiltyFormBinding.receiverSpinner, mBilty.getReceiverId());
        if (receiverVal == -1) {
            receiverArrayAdapter.add(new Customers(mBilty.getReceiverId()));
            addBiltyFormBinding.receiverSpinner.setSelection(receiverArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.receiverSpinner.setSelection(receiverVal);
        }
    }

    private void loadAgentBiltyVal() {
        int agentVal = getIndex(addBiltyFormBinding.agentSpinner, mBilty.getAgentId());
        if (agentVal == -1) {
            agentsArrayAdapter.add(new Agents(mBilty.getAgentId()));
            addBiltyFormBinding.agentSpinner.setSelection(agentsArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.agentSpinner.setSelection(agentVal);
        }
    }

    private void loadTransporterBiltyVal() {
        int supplierVal = getIndex(addBiltyFormBinding.supplierSpinner, mBilty.getSupplierName());
        if (supplierVal == -1) {
            supplierArrayAdapter.add(new Supplier(mBilty.getSupplierName()));
            addBiltyFormBinding.supplierSpinner.setSelection(supplierArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.supplierSpinner.setSelection(supplierVal);
        }

    }

    private void loadKindBiltyVal() {
        int itemVal = getIndex(addBiltyFormBinding.kindSpinner, mBilty.getKindId());
        if (itemVal == -1) {
            itemArrayAdapter.add(new KindOfItem(mBilty.getKindId()));
            addBiltyFormBinding.kindSpinner.setSelection(itemArrayAdapter.getCount() - 1);
        } else {
            addBiltyFormBinding.kindSpinner.setSelection(itemVal);
        }
    }

    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(addBiltyFormBinding.fromSpinner.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.toSpinner.getText())
                || addBiltyFormBinding.kindSpinner.getSelectedItem().toString().equals("Select a kind")
                || addBiltyFormBinding.senderSpiner.getSelectedItem().toString().equals("Select a Sender")
                || addBiltyFormBinding.receiverSpinner.getSelectedItem().toString().equals("Select a Receiver")
                || addBiltyFormBinding.supplierSpinner.getSelectedItem().toString().equals("Select a supplier")
                || addBiltyFormBinding.agentSpinner.getSelectedItem().toString().equals("Select a agent")
                || TextUtils.isEmpty(addBiltyFormBinding.volumeTxt.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.weightTxt.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.supplierPnoText.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.quanTxt.getText())) {
            return true;
        }
        return false;
    }

    private boolean isBailFieldEmpty() {
        if (TextUtils.isEmpty(addBiltyFormBinding.fromSpinner.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.toSpinner.getText())
                || addBiltyFormBinding.kindSpinner.getSelectedItem().toString().equals("Select a kind")
                || addBiltyFormBinding.senderSpiner.getSelectedItem().toString().equals("Select a Sender")
                || addBiltyFormBinding.receiverSpinner.getSelectedItem().toString().equals("Select a Receiver")
                || addBiltyFormBinding.supplierSpinner.getSelectedItem().toString().equals("Select a supplier")
                || addBiltyFormBinding.agentSpinner.getSelectedItem().toString().equals("Select a agent")
                || TextUtils.isEmpty(addBiltyFormBinding.supplierPnoText.getText())
                || TextUtils.isEmpty(addBiltyFormBinding.quanTxt.getText())) {
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

        return -1;
    }

    private int getIntQuantity(String quan) {
        int val = Integer.parseInt(quan);
        return val;
    }

    private String generateBiltyNo() {
        String currentBiltyNo = adminInfo.getBiltyCounter();

        int updatedNo = Integer.parseInt(currentBiltyNo) + 1;

        String number = String.format(Locale.ENGLISH, "%06d", updatedNo);
        adminViewModel.updateBiltyCounter(number, username);

        return currentBiltyNo;
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