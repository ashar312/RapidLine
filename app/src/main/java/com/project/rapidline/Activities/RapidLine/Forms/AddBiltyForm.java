package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rapidline.Activities.SaeedSons.Forms.AddBailForm;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.RapidLine.Supplier;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Cities;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.KindOfItem;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAddBiltyFormBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.google.common.reflect.Reflection.initialize;

public class AddBiltyForm extends AppCompatActivity {

    private ActivityAddBiltyFormBinding addBiltyFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private RapidLineViewModel rapidLineViewModel;


    private String action;
    private Bilty mBilty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBiltyFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_bilty_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);


        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        if (action.equals("edit")) {
            //Load data
            String id = bundle.getString("itemId");

            rapidLineViewModel.getBiltyById(id).observe(this, bilty -> {
                mBilty = bilty;
                loadData();
            });
        }
        initialize();

        addBiltyFormBinding.saveBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            //TODO generate builty no work
            if (action.equals("edit")) {

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

                mBilty.setBiltyNo(addBiltyFormBinding.supplierPnoText.getText().toString());

                rapidLineViewModel.addBilty(mBilty);
                Toast.makeText(this, "Bilty added sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            }


        });

        addBiltyFormBinding.backBtn.setOnClickListener(view -> finish());
    }


    private void initialize() {
        //Load Cities
        saeedSonsViewModel.getListAllCities().observe(this, cities -> {
            List<Cities> fromCityList = new ArrayList<>(cities);
            fromCityList.add(0, new Cities("From"));

            ArrayAdapter<Cities> fromCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, fromCityList);
            fromCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            List<Cities> toCityList = new ArrayList<>(cities);
            toCityList.add(0, new Cities("To"));

            ArrayAdapter<Cities> toCityArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, toCityList);
            toCityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);


            addBiltyFormBinding.fromSpinner.setAdapter(fromCityArrayAdapter);
            addBiltyFormBinding.toSpinner.setAdapter(toCityArrayAdapter);

        });

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
        saeedSonsViewModel.getListAllSuppliers().observe(this, suppliers -> {
            List<Supplier> supplierList = new ArrayList<>(suppliers);
            supplierList.add(0, new Supplier("Select a supplier"));

            ArrayAdapter<Supplier> supplierArrayAdapter = new ArrayAdapter<>(AddBiltyForm.this,
                    R.layout.spinner_item, supplierList);

            addBiltyFormBinding.supplierSpinner.setAdapter(supplierArrayAdapter);
        });
        //Set Listeners
        addBiltyFormBinding.addQuanBtn.setOnClickListener(view -> {
            String quan = addBiltyFormBinding.quanTxt.getText().toString();
            int val = Integer.parseInt(quan.substring(quan.length() - 1));
            val++;
            addBiltyFormBinding.quanTxt.setText("Qty: " + String.valueOf(val));
        });

        addBiltyFormBinding.subractQuanBtn.setOnClickListener(view -> {
            String quan = addBiltyFormBinding.quanTxt.getText().toString();
            int val = Integer.parseInt(String.valueOf(quan.charAt(quan.length() - 1)));
            if (val == 1) {
                Toast.makeText(this, "Min quantity reached",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            val = val - 1;
            addBiltyFormBinding.quanTxt.setText("Qty: " + String.valueOf(val));
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

    private void loadData() {
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
        int val = Integer.parseInt(String.valueOf(quan.charAt(quan.length() - 1)));
        return val;
    }
}