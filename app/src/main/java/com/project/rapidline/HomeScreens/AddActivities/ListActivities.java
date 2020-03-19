package com.project.rapidline.HomeScreens.AddActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Form.AgentForm;
import com.project.rapidline.Form.LabourForm;
import com.project.rapidline.Form.PatriForm;
import com.project.rapidline.Form.SenderRecieverForm;
import com.project.rapidline.Form.TransportersForm;
import com.project.rapidline.HomeScreens.Adapter.ListAdapter;
import com.project.rapidline.HomeScreens.Adapter.Listeners.OnItemClickListener;
import com.project.rapidline.Models.ListItems;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityListBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.List;

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
                    ArrayList<ListItems> listItems = new ArrayList<>();
                    //Copy list
                    for (Customers cust : customers) {
                        ListItems items = new ListItems(cust.getId(), cust.getCompanyName());
                        listItems.add(items);
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Agents":
                saeedSonsViewModel.getListAllAgents().observe(this, agents -> {
                    ArrayList<ListItems> listItems = new ArrayList<>();
                    //Copy list
                    for (Agents agent : agents) {
                        ListItems items = new ListItems(agent.getId(), agent.getAgentName());
                        listItems.add(items);
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Transporters":
                saeedSonsViewModel.getListAllTransporters().observe(this, transporters -> {
                    ArrayList<ListItems> listItems = new ArrayList<>();
                    //Copy list
                    for (Transporters trans : transporters) {
                        ListItems items = new ListItems(trans.getId(), trans.getCompanyName());
                        listItems.add(items);
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Labour":
                saeedSonsViewModel.getListAllLabours().observe(this, labours -> {
                    ArrayList<ListItems> listItems = new ArrayList<>();
                    //Copy list
                    for (Labours labour : labours) {
                        ListItems items = new ListItems(labour.getId(), labour.getName());
                        listItems.add(items);
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
            case "Patri":
                saeedSonsViewModel.getListAllPatris().observe(this, patris -> {
                    ArrayList<ListItems> listItems = new ArrayList<>();
                    //Copy list
                    for (Patri patri : patris) {
                        ListItems items = new ListItems(patri.getId(), patri.getName());
                        listItems.add(items);
                    }
                    //Set list
                    listAdapter.setListItems(listItems);

                });
                break;
        }

        setupRecyclerView();
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
    public void onItemClick(long itemId, String action) {

        Intent intent;
        switch (ActivityValue) {
            case "SenderReceiver":
                if (action.equals("delete")) {
                    //get cust then delete
                    saeedSonsViewModel.deleteCustomerById(itemId);
                } else {
                    //launch intent to edit
                    intent = new Intent(ListActivities.this, SenderRecieverForm.class);
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
                } else {
                    //edit
                    intent = new Intent(ListActivities.this, SenderRecieverForm.class);
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
                intent = new Intent(ListActivities.this, SenderRecieverForm.class);
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
                intent = new Intent(ListActivities.this, LabourForm.class);
                intent.putExtra("ListItem", listitem);
                intent.putExtra("action", "add");
                startActivity(intent);
                break;
            case "Patri":
                intent = new Intent(ListActivities.this, PatriForm.class);
                intent.putExtra("ListItem", listitem);
                intent.putExtra("action", "add");
                startActivity(intent);
            default:
                break;

        }


    }
}
