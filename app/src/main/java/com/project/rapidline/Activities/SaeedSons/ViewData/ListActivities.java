package com.project.rapidline.Activities.SaeedSons.ViewData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.project.rapidline.Activities.RapidLine.Forms.SupplierForm;
import com.project.rapidline.Models.RapidLine.Supplier;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.Labours;
import com.project.rapidline.Models.SaeedSons.Patri;
import com.project.rapidline.Models.SaeedSons.Transporters;
import com.project.rapidline.Activities.SaeedSons.Forms.AgentForm;
import com.project.rapidline.Activities.SaeedSons.Forms.LabourPatriForm;
import com.project.rapidline.Activities.SaeedSons.Forms.SenderRecieverTransporterForm;
import com.project.rapidline.Adapters.ListAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityListBinding;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import java.util.ArrayList;

public class ListActivities extends AppCompatActivity implements OnItemClickListener {

    private ActivityListBinding activityListBinding;
    private ListAdapter listAdapter;
    //ViewModel
    private SaeedSonsViewModel saeedSonsViewModel;
    private String ActivityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);


        Bundle bundle = getIntent().getExtras();

        ActivityValue = bundle.get("ListItem").toString();

        //Set UI
        String uiName = bundle.get("activityName").toString();

        if (uiName.equals("RapidLine")) {
            //Change ui acc to rapid line
            activityListBinding.listItemLayout.setBackgroundResource(R.drawable.rapid_line_bg);
        }

        //Set title
        activityListBinding.listItemText.setText(ActivityValue);


        //Button to add a field
        activityListBinding.addCommonBtn.setOnClickListener(v -> OpenWhichActivity(ActivityValue));

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        //Activate observer based on activity value
        switch (ActivityValue) {
            case "SenderReceiver":
                saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
                    ArrayList<String> listItems = new ArrayList<>();
                    //Copy list
                    for (Customers cust : customers) {
                        listItems.add(cust.getCompanyName());
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Agents":
                saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
                    ArrayList<String> listItems = new ArrayList<>();
                    //Copy list
                    for (Agents agent : agents) {

                        listItems.add(agent.getAgentName());
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Transporters":
                saeedSonsViewModel.getListAllTransporters().observe(this, transporters -> {
                    ArrayList<String> listItems = new ArrayList<>();
                    //Copy list
                    for (Transporters trans : transporters) {
                        listItems.add(trans.getCompanyName());
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Labour":
                saeedSonsViewModel.getListAllLabours().observe(this, labours -> {
                    ArrayList<String> listItems = new ArrayList<>();
                    //Copy list
                    for (Labours labour : labours) {
                        listItems.add(labour.getName());
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Patri":
                saeedSonsViewModel.getListAllPatris().observe(this, patris -> {
                    ArrayList<String> listItems = new ArrayList<>();
                    //Copy list
                    for (Patri patri : patris) {
                        listItems.add(patri.getName());
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Supplier":

                saeedSonsViewModel.getListAllSuppliers().observe(this, suppliers -> {
                    ArrayList<String> listItems = new ArrayList<>();
                    //Copy list
                    for (Supplier supplier : suppliers) {
                        listItems.add(supplier.getSupplierName());
                    }
                    //Set list
                    listAdapter.setListItems(listItems);
                });
                break;
        }

        setupRecyclerView();


        activityListBinding.searchCommon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activityListBinding.backBtn.setOnClickListener(view -> {
            finish();
        });

    }


    private void setupRecyclerView() {
        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
        listAdapter.setUpdateOptionVisible(true);
        activityListBinding.listRecycler.setLayoutManager(new LinearLayoutManager(this));
        activityListBinding.listRecycler.setAdapter(listAdapter);
        activityListBinding.listRecycler.setItemAnimator(new DefaultItemAnimator());

    }

    //When a item from recycler view is clicked
    @Override
    public void onItemClick(String itemId, String action) {


        Intent intent;
        switch (ActivityValue) {
            case "SenderReceiver":
                if (action.equals("delete")) {
                    checkListSize();
//                    saeedSonsViewModel.deleteCustomerById(itemId);
                    saeedSonsViewModel.deleteCustomerById(itemId);
                } else {
                    //launch intent to edit
                    intent = new Intent(ListActivities.this, SenderRecieverTransporterForm.class);
                    intent.putExtra("ListItem", ActivityValue);
                    intent.putExtra("action", action);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
                break;
            case "Transporters":
                if (action.equals("delete")) {
                    checkListSize();
                    //delete
                    saeedSonsViewModel.deleteTransporterById(itemId);
                } else {
                    //edit
                    intent = new Intent(ListActivities.this, SenderRecieverTransporterForm.class);
                    intent.putExtra("ListItem", ActivityValue);
                    intent.putExtra("action", action);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
                break;
            case "Labour":
                if (action.equals("delete")) {
                    checkListSize();
                    //delete
//                    saeedSonsViewModel.deleteLabourById(itemId);
                    saeedSonsViewModel.deleteLabourById(itemId);
                    Log.v("fs", "ddffsdf");
                } else {
                    //edit
                    intent = new Intent(ListActivities.this, LabourPatriForm.class);
                    intent.putExtra("ListItem", ActivityValue);
                    intent.putExtra("action", action);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
                break;
            case "Patri":
                if (action.equals("delete")) {
                    checkListSize();
                    //delete
                    saeedSonsViewModel.deletePatriById(itemId);
                } else {
                    //edit
                    intent = new Intent(ListActivities.this, LabourPatriForm.class);
                    intent.putExtra("ListItem", ActivityValue);
                    intent.putExtra("action", action);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
                break;
            case "Agents":
                if (action.equals("delete")) {
                    checkListSize();
                    saeedSonsViewModel.deleteAgentById(itemId);
                } else {
                    intent = new Intent(ListActivities.this, AgentForm.class);
                    intent.putExtra("ListItem", ActivityValue);
                    intent.putExtra("action", action);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
                break;
            case "Supplier":
                if (action.equals("delete")) {
                    checkListSize();
                    saeedSonsViewModel.deleteSupplierById(itemId);
                } else {
                    intent = new Intent(ListActivities.this, SupplierForm.class);
                    intent.putExtra("ListItem", ActivityValue);
                    intent.putExtra("action", action);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
                break;
        }

        //When edit or delete button is clicked

    }

    //Open activity on add button click
    private void OpenWhichActivity(String listitem) {
        Intent intent;
        switch (listitem) {
            case "SenderReceiver":
            case "Transporters":
                intent = new Intent(ListActivities.this, SenderRecieverTransporterForm.class);
                intent.putExtra("ListItem", listitem);
                intent.putExtra("action", "add");
                startActivity(intent);
                break;
            case "Agents":
                intent = new Intent(ListActivities.this, AgentForm.class);
                intent.putExtra("ListItem", listitem);
                intent.putExtra("action", "add");
                startActivity(intent);
                break;
            case "Labour":
            case "Patri":
                intent = new Intent(ListActivities.this, LabourPatriForm.class);
                intent.putExtra("ListItem", listitem);
                intent.putExtra("action", "add");
                startActivity(intent);
                break;
            case "Supplier":
                intent = new Intent(ListActivities.this, SupplierForm.class);
                intent.putExtra("ListItem", listitem);
                intent.putExtra("action", "add");
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    //when last item is present the listener attached is destroyed as table is deleted
    //so update it
    private void checkListSize() {
        if (listAdapter.getItemCount() == 1) {
            listAdapter.setListItems(new ArrayList<>());
        }
    }

}
