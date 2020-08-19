package com.project.rapidline.Activities.SaeedSons.ViewData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.project.rapidline.Activities.Common.PrintOutActivity;
import com.project.rapidline.Activities.SaeedSons.Forms.AddBailForm;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.Adapters.BailsAdapter;

import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.databinding.ActivityRegisterBailsBinding;
import com.project.rapidline.utils.StaticClasses;
import com.project.rapidline.R;
import com.project.rapidline.viewmodel.SaeedSons.AdminViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import java.util.ArrayList;

public class ViewBailsActivity extends AppCompatActivity implements OnItemClickListener, BailsAdapter.OnNoteListener {

    private ActivityRegisterBailsBinding entriesBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private BailsAdapter bailsAdapter;
    private AdminViewModel adminViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_bails);
        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);


        //Initialize sort spinner
        ArrayList<String> sortOrder = StaticClasses.sortOrder;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                sortOrder);
        entriesBinding.sortSpinner.setAdapter(arrayAdapter);
        setupRecyclerView();


        entriesBinding.searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bailsAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


        entriesBinding.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerItem = entriesBinding.sortSpinner.getSelectedItem().toString();
                if (spinnerItem.equals(StaticClasses.sortOrder.get(1))) {

                    //Remove Old Observers
                    saeedSonsViewModel.getBailDataRv().removeObservers(ViewBailsActivity.this);

                    //sort by date
                    saeedSonsViewModel.getBailDataRvByDate().observe(ViewBailsActivity.this, bails -> {
                        bailsAdapter.setBailsArrayList((ArrayList<Bails>) bails);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        entriesBinding.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String spinnerItem=entriesBinding.sortSpinner.getSelectedItem().toString();
//
//                //Sort Order
//                if(spinnerItem.equals(StaticClasses.sortOrder.get(0))){
//                    enteriesAdapter.setBailsArrayList((ArrayList<BailMinimal>) minimalList);
//                }
//                else {
//                    sortBails(spinnerItem);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        saeedSonsViewModel.getListAllCustomers().observe(this, customers -> {
//            for (Customers cust : customers
//            ) {
//                Log.v("custom",""+cust.getId());
//            }
//        });

//        int sendId = saeedSonsViewModel.getBailById(1).getSenderId();
//
//        Log.v("bail 0", "" + sendId);

//        saeedSonsViewModel.getListAllBails().observe(this,bails -> {
//            Log.v("bails",""+bails.get(1).getSenderId());
//        });

//        List<BailMinimal> min = saeedSonsViewModel.data();
//        Log.v("sails",""+min.get(1).getTime());


//        List<BailMinimal> bailmin = saeedSonsViewModel.data();
//        Log.v("lpc", "" + bailmin.size());

        entriesBinding.backBtn.setOnClickListener(view -> {
            finish();
        });
    }

    private void setupRecyclerView() {
        bailsAdapter = new BailsAdapter(this, new ArrayList<>(), this::onItemClick, this::onBailClick);
        entriesBinding.enteriesRv.setLayoutManager(new LinearLayoutManager(this));
        entriesBinding.enteriesRv.setAdapter(bailsAdapter);
        entriesBinding.enteriesRv.setItemAnimator(new DefaultItemAnimator());

        saeedSonsViewModel.getBailDataRv().observe(this, bails -> {
            bailsAdapter.setBailsArrayList((ArrayList<Bails>) bails);
        });

        //On Right swipe listener
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adminViewModel.getAdminPermissionsById(getAdminUsername()).observe(ViewBailsActivity.this, permissions -> {
                    if (permissions.isDeleteBail()) {
                        Bails bail = bailsAdapter.getBailsArrayList().get(viewHolder.getAdapterPosition());
                        saeedSonsViewModel.deleteBail(bail.getBailNo());
                    } else
                        Toast.makeText(ViewBailsActivity.this, "You do not have permissions to delete bail", Toast.LENGTH_SHORT).show();
                });

            }
        }).attachToRecyclerView(entriesBinding.enteriesRv);

    }

//    private void sortBails(String field) {
//        ArrayList<Bails> sortedList = new ArrayList<>(enteriesAdapter.getBailsArrayList());
//        switch (field) {
//            case "Date":
//                Collections.sort(sortedList, (Comparator<Bails>) (bailMinimal, t1) -> bailMinimal.getMadeDateTime().compareTo(t1.getMadeDateTime()));
//
//
//            case "Online":
//                Collections.sort(sortedList, (Comparator<Bails>) (bailMinimal, t1) -> bailMinimal.getMadeDateTime().compareTo(t1.getMadeDateTime()));
//
//            case "Offline":
//                Collections.sort(sortedList, (Comparator<Bails>) (bailMinimal, t1) -> bailMinimal.getMadeDateTime().compareTo(t1.getMadeDateTime()));
//
//        }
//        enteriesAdapter.setBailsArrayList(sortedList);
//    }

    //Click listener for print
    @Override
    public void onItemClick(String itemId, String action) {

        Intent intent = new Intent(ViewBailsActivity.this, PrintOutActivity.class);
        intent.putExtra("action", action);
        intent.putExtra("itemId", itemId);
        startActivity(intent);

    }

    //Recycler View entry
    @Override
    public void onBailClick(String itemId) {

        adminViewModel.getAdminPermissionsById(getAdminUsername()).observe(this, permissions -> {
            if (permissions.isUpdateBail()) {
                Intent intent = new Intent(ViewBailsActivity.this, AddBailForm.class);
                intent.putExtra("action", "edit");
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            } else
                Toast.makeText(this, "You don not have permissions to update bail", Toast.LENGTH_SHORT).show();
        });
    }

    private String getAdminUsername() {
        return getApplicationContext().getSharedPreferences("LoginPref", 0).getString("username", "");
    }

}
