package com.project.rapidline.HomeScreens.AddActivities;

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

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Form.AgentForm;
import com.project.rapidline.Form.LabourPatriForm;
import com.project.rapidline.Form.SenderRecieverTransporterForm;
import com.project.rapidline.HomeScreens.Adapter.ListAdapter;
import com.project.rapidline.HomeScreens.Adapter.Listeners.OnItemClickListener;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityListBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

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

        //Set title
        ActivityValue = getIntent().getExtras().get("ListItem").toString();
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

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String admName= saeedSonsViewModel.getAdminById(1).getUsername();
//                Toast.makeText(ListActivities.this,"Name:"+admName,Toast.LENGTH_SHORT).show();
//            }
//        });

//        saeedSonsViewModel.getListAllCustomers().observe(this, new Observer<List<Customers>>() {
//            @Override
//            public void onChanged(List<Customers> customers) {
//
//            }
//        });


//        recyclerView = findViewById(R.id.List_recycler);
//        setListModelArrayList();
//        mAdapter = new com.project.rapidline.HomeScreens.Adapter.ListAdapter(getApplicationContext(),
//                recyclerViewItemsCounts);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);


    }


    private void setupRecyclerView() {
        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
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
                    //get cust then delete
//                    saeedSonsViewModel.deleteCustomerById(itemId);
                    saeedSonsViewModel.deleteCustomerById(itemId);
                    Log.v("fs","ds");
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
                    //delete
                    saeedSonsViewModel.deleteTransporterById(itemId);
                    Log.v("fs","ddff");
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
                    //delete
//                    saeedSonsViewModel.deleteLabourById(itemId);
                    saeedSonsViewModel.deleteLabourById(itemId);
                    Log.v("fs","ddffsdf");
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
                    //delete
                    saeedSonsViewModel.deletePatriById(itemId);
                    Log.v("fs","ddffsdf");
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
                if(action.equals("delete")){
                    saeedSonsViewModel.deleteAgentById(itemId);
                }
                else {
                    intent = new Intent(ListActivities.this, AgentForm.class);
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
            default:
                break;

        }


    }
}
