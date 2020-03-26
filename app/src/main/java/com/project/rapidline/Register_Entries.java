package com.project.rapidline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.HomeScreens.Adapter.EnteriesAdapter;
import com.project.rapidline.HomeScreens.Adapter.ListAdapter;
import com.project.rapidline.Models.BailMinimal;
import com.project.rapidline.Models.StaticClasses;
import com.project.rapidline.databinding.ActivityRegisterEntriesBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.List;

public class Register_Entries extends AppCompatActivity {

    private ActivityRegisterEntriesBinding entriesBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private EnteriesAdapter enteriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_entries);
        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        //Initialize sort spinner
        ArrayList<String> sortOrder= StaticClasses.sortOrder;
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,
                R.layout.spinner_item,
                sortOrder);
        entriesBinding.sortSpinner.setAdapter(arrayAdapter);

        setupRecyclerView();

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

    }

    private void setupRecyclerView() {
        enteriesAdapter=new EnteriesAdapter(this,new ArrayList<>());
        entriesBinding.enteriesRv.setLayoutManager(new LinearLayoutManager(this));
        entriesBinding.enteriesRv.setAdapter(enteriesAdapter);
        entriesBinding.enteriesRv.setItemAnimator(new DefaultItemAnimator());

        enteriesAdapter.setBailsArrayList((ArrayList<BailMinimal>) saeedSonsViewModel.data());
    }

}
